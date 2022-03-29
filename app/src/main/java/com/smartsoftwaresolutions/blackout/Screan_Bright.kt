package com.smartsoftwaresolutions.blackout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.ads.*

class Screan_Bright : AppCompatActivity() {
    //ca-app-pub-8537373371656761~5050991485 this is the test admob id
    private var mAdView: AdView? = null
    var counter = 0
    var btn_counter: Button? = null
    var btn_save: Button? = null
    var btn_play_Dead: Button? = null
    var turnOnScreenButton: Button? = null
    var textView: TextView? = null
    var tv_current_count: TextView? = null
    var Black_Time: TextView? = null
    var context: Context? = null
    var count_down = 0
    var current_volume = 0
    var volume = 0
    var Black_count_down = 0
    var S_timeout = 0
    var default_Timeout = 0
    var et: EditText? = null
    var shardPreferencesSettings: SharedPreferences? = null
    var mAudioManager: AudioManager? = null
    var Timer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scean__bright)

//facebook
        //Stetho.initializeWithDefaults(this);
        /********* ****************************AdMOb */
        if (BuildConfig.FLAVOR.equals("Free")) {
            MobileAds.initialize(this) { }
            mAdView = findViewById(R.id.adView)


            // AdRequest adRequest = new AdRequest.Builder().build();
            //  AdRequest adRequest = new AdRequest.Builder().addTestDevice("35663D4E3BC0B627A8E5C8571E17588F").build();
            val adRequest = AdRequest.Builder().build()
            mAdView!!.loadAd(adRequest)
            mAdView!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.e("onAdLoaded", "AdLoaded")
                }

                override fun onAdFailedToLoad(errorCode: LoadAdError) {
                    super.onAdFailedToLoad(errorCode)
                    Log.e("onAdFailedToLoad", "" + errorCode)
                }

                //                override fun onAdFailedToLoad(errorCode: Int) {
                //                    super.onAdFailedToLoad(errorCode)
                //                    Log.e("onAdFailedToLoad", "" + errorCode)
                //                }
            }
            Toast.makeText(this@Screan_Bright, "this is a free version with ads", Toast.LENGTH_LONG)
                .show()
        }
        /** */
        btn_play_Dead = findViewById(R.id.btn_play_Dead)
        //        if(BuildConfig.FLAVOR.equals("Free")){
//            btn_play_Dead.setEnabled(false);
//        }else {
//            btn_play_Dead.setEnabled(true);
//        }
        Black_Time = findViewById(R.id.tv2)
        tv_current_count = findViewById(R.id.tv_current_count)
        et = findViewById(R.id.et)
        btn_save = findViewById(R.id.btn_save)
        // turn off the save setting in free app
//if (BuildConfig.FLAVOR.equals("Free")){
//    btn_save.setEnabled(false);
//    btn_save.setText(R.string.pro_message);
//}else{
//    btn_save.setEnabled(true);
//    btn_save.setText(R.string.save_setting);
//}

        /**control the audio**/
        mAudioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
        // get the current volume
        current_volume = mAudioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)


        //sharedpreferance
        //read the screen time out
        shardPreferencesSettings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        try {
            // save the phone time out
            S_timeout = Settings.System.getInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)
            val editor = shardPreferencesSettings!!.edit()
            editor.putInt("S_timeout", S_timeout)
            editor.commit()
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        default_Timeout = shardPreferencesSettings!!.getInt("S_timeout", 120000)
        // first thing i will read the sharedprefrance then i will check the edit text
        count_down = shardPreferencesSettings!!.getInt("key_counter", 3)
        val s = count_down.toString()
        tv_current_count!!.text = s + " " + getString(R.string.mint)
        //        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt("current_volume", current_volume);
//
//        editor.commit();
        btn_save!!.setOnClickListener(View.OnClickListener {
            val et_text = et!!.getText().toString()
            //   if (et_text != null && !et_text.isEmpty()) {
            if (!et_text.isNullOrEmpty()) {
                // the text is not empty
                if (BuildConfig.FLAVOR.equals("Free")) {

                    if (et_text.toInt() >= 1 && et_text.toInt() <= 20) {
                        //Writing data to SharedPreferences
                        count_down = et_text.toInt()
                        val editor = shardPreferencesSettings!!.edit()
                        editor.putInt("key_counter", count_down)
                        editor.commit()
                        et!!.setText("")
                        count_down = shardPreferencesSettings!!.getInt("key_counter", 3)
                        val s = count_down.toString()
                        tv_current_count!!.setText(s + " " + getString(R.string.mint))
                        //btn_counter.setText("Stop Counter");
                        // btn_counter.setTextColor(getResources().getColor(R.color.Red));
                        //  Go_Black();
                    } else {
                        Toast.makeText(
                            this@Screan_Bright,
                            getString(R.string.please_buy_pro),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    // this is a pro version
                } else {
                    //Writing data to SharedPreferences
                    count_down = et_text.toInt()
                    val editor = shardPreferencesSettings!!.edit()
                    editor.putInt("key_counter", count_down)
                    editor.commit()
                    et!!.setText("")
                    count_down = shardPreferencesSettings!!.getInt("key_counter", 3)
                    val s = count_down.toString()
                    tv_current_count!!.setText(s + " " + getString(R.string.mint))
                    // read the black time
                    val S_balck_time = Black_Time!!.getText().toString()
                    // if (S_balck_time != null && !S_balck_time.isEmpty()) {
                    if (S_balck_time.isEmpty()) {
                        // save the value in sharedpreferance
                        Black_count_down = S_balck_time.toInt()
                        // SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("Black_counter", Black_count_down)
                        editor.commit()
                        Black_Time!!.setText("")
                    } else {
                        // the black time didn't change so read from shared prefrance
                    }
                    // Go_Black();
                }
            } else {
                Toast.makeText(this@Screan_Bright, "please enter a value ", Toast.LENGTH_LONG)
                    .show()
            }
        })
        context = applicationContext
        // btn_counter =  findViewById(R.id.btn_counter);
        textView = findViewById(R.id.textView)
        // for test the system
        /**btn_counter.setOnClickListener(new View.OnClickListener()
         * {
         * @Override
         * public void onClick(View v) {
         * if (btn_counter.getText().equals("Start counter")){
         * btn_counter.setText("Stop Counter");
         * btn_counter.setTextColor(getResources().getColor(R.color.Red));
         * Go_Black();
         * }else if (btn_counter.getText().equals("Stop Counter")){
         * btn_counter.setText(R.string.counter);
         *
         * Timer.cancel();
         * }
         *
         *
         * }
         * }); */
        btn_play_Dead!!.setOnClickListener(View.OnClickListener {
            if (BuildConfig.FLAVOR.equals("Free")) {
                Toast.makeText(this@Screan_Bright, R.string.please_buy_pro, Toast.LENGTH_LONG)
                    .show()
            } else {
                var settingsCanWrite = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    settingsCanWrite = hasWriteSettingsPermission(context)
                }
                //                }else{
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                    startActivity(intent);
//                }

                // If do not have then open the Can modify system settings panel.
                if (!settingsCanWrite && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    changeWriteSettingsPermission(context)
                } else {
                    Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 500)
                    changeScreenBrightness(context, 1)
                    val intent = Intent(this@Screan_Bright, Black::class.java)
                    startActivity(intent)
                }
            }
        })
        // Get turn off screen btn_counter
        val test_screen = findViewById<View?>(R.id.test_screen) as Button
        test_screen.setOnClickListener {
            // Get app context object.
            //    Context context = getApplicationContext();

            // Check whether has the write settings permission or not.
            var settingsCanWrite = false
            // the phone android version where the app is running
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                settingsCanWrite = hasWriteSettingsPermission(context)
            }

            // If do not have then open the Can modify system settings panel.
            if (!settingsCanWrite && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // if it is a false
                changeWriteSettingsPermission(context)
            } else {
                val intent = Intent(this@Screan_Bright, Black::class.java)
                startActivity(intent)
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 1000)
                changeScreenBrightness(context, 1)
                // the millisecound in the future is the time set by you
                val set_Time = 3000 // every 1000 mili will make a 1 secound
                object : CountDownTimer(set_Time.toLong(), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        textView!!.setText(counter.toString())
                        counter++
                    }

                    override fun onFinish() {
                        object : CountDownTimer(1000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                counter = 0
                                textView!!.setText("FINISH!!")
                                Settings.System.putInt(
                                    contentResolver,
                                    Settings.System.SCREEN_OFF_TIMEOUT,
                                    default_Timeout
                                )
                                changeScreenBrightness(context, 255)
                            }
                        }.start()
                    }
                }.start()
                // turn off the screan
                // changeScreenBrightness(context, 1);
            }
        }

        // Get turn on screen btn_counter
        turnOnScreenButton = findViewById(R.id.turn_on_screen_button)
        turnOnScreenButton!!.setOnClickListener(View.OnClickListener {
            //     Context context = getApplicationContext();

            // Check whether has the write settings permission or not.
            var settingsCanWrite = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                settingsCanWrite = hasWriteSettingsPermission(context)
            }

            // If do not have then open the Can modify system settings panel.
            if (!settingsCanWrite && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                changeWriteSettingsPermission(context)
            } else {
                // turn on the screan
                changeScreenBrightness(context, 255)
            }
        })
    }

    // Check whether this app has android write settings permission.
    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun hasWriteSettingsPermission(context: Context?): Boolean {
        // Get the result from below code.
        return Settings.System.canWrite(context)
    }

    // Start can modify system settings panel to let user change the write settings permission.
    private fun changeWriteSettingsPermission(context: Context?) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        //        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
        context!!.startActivity(intent)
    }

    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private fun changeScreenBrightness(context: Context?, screenBrightnessValue: Int) {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(
            context!!.getContentResolver(),
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            screenBrightnessValue
        )

        /*
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = screenBrightnessValue / 255f;
        window.setAttributes(layoutParams);
        */
    }

    private fun Go_Black() {
        // the millisecound in the future is the time set by you
        //  int Set_Time=1000;// every 1000 mili will make a 1 secound
        //freeApp
        count_down = shardPreferencesSettings!!.getInt("key_counter", 15)
        Black_count_down = shardPreferencesSettings!!.getInt("Black_counter", 15)
        //   count_down= settings.getInt("key_counter", 3);
        //   Black_count_down=settings.getInt("Black_counter",3);

        // count_down=count_down*1000;
        //free app
        count_down = count_down * 1000 * 60
        counter = 0
        //   new CountDownTimer(Set_Time, 1000){
        Timer = object : CountDownTimer(count_down.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView!!.setText(counter.toString())
                counter++
            }

            override fun onFinish() {
                val `in` = Intent(this@Screan_Bright, Black::class.java)
                startActivity(`in`)
                textView!!.setText("FINISH!!")
                counter = 0
                volume = 0
                // Check whether has the write settings permission or not.
                var settingsCanWrite = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    settingsCanWrite = hasWriteSettingsPermission(context)
                }

                // If do not have then open the Can modify system settings panel.
                if (!settingsCanWrite && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    changeWriteSettingsPermission(context)
                } else {
                    changeScreenBrightness(context, 1)

                    // Set media volume level to zero
                    mAudioManager!!.setStreamVolume(
                        AudioManager.STREAM_MUSIC,  // Stream type
                        volume,  // Index
                        AudioManager.FLAG_SHOW_UI // Flags
                    )
                    // set the sceen time out to half of a second
                    Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 500)
                }


                // send a notification
                val nm = NotificationManagerCompat.from(context!!)
                val notif = NotificationCompat.Builder(context!!)
                notif.setLights(0xff0000, 1000, 1000)
                notif.setSmallIcon(R.mipmap.ic_launcher)
                notif.setContentTitle("Time is finished")
                nm.notify(5, notif.build())
                // then begin a new conunt down to make the screen bright again
                //     new CountDownTimer(5000, 1000) {
                object : CountDownTimer((Black_count_down * 1000).toLong(), 1000) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        // Check whether has the write settings permission or not.
                        var settingsCanWrite = false
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            settingsCanWrite = hasWriteSettingsPermission(context)
                        }

                        // If do not have then open the Can modify system settings panel.
                        if (!settingsCanWrite && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            changeWriteSettingsPermission(context)
                        } else {
                            Settings.System.putInt(
                                contentResolver,
                                Settings.System.SCREEN_OFF_TIMEOUT,
                                default_Timeout
                            )
                            changeScreenBrightness(context, 255)
                        }
                        // change the volume to current one
                        // Set media volume level to zero
                        mAudioManager!!.setStreamVolume(
                            AudioManager.STREAM_MUSIC,  // Stream type
                            current_volume,  // Index
                            AudioManager.FLAG_SHOW_UI // Flags
                        )
                    }
                }.start()
            }
        }.start()
    }

    companion object {
        val PREFS_NAME: String? = "MyApp_Settings"
    }
}