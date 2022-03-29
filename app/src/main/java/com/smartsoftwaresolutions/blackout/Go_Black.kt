package com.smartsoftwaresolutions.blackout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.CountDownTimer
import android.provider.Settings
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Go_Black(private val context: Context?) {
    private var shardPreferencesSettings: SharedPreferences? = null
    var count_down: Int
    var volume = 0
    var Black_count_down: Int
    var S_timeout = 0
    var default_Timeout: Int

    // AudioManager mAudioManager;
    //    int  current_volume;
    var Timer: CountDownTimer?
    var counter: Int
    var textView: TextView? = (context as Activity?)?.findViewById(R.id.textView)

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
        context?.startActivity(intent)
    }

    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private fun changeScreenBrightness(context: Context?, screenBrightnessValue: Int) {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context?.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context?.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue)

        /*
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = screenBrightnessValue / 255f;
        window.setAttributes(layoutParams);
        */
    } //    public void Update_int(int counter){

    //        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.textView);
    //        txtView.setText(String.valueOf(counter));
    //    }
    //    public void Update(String s){
    //        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.textView);
    //        txtView.setText(s);
    //    }
    companion object {
        val PREFS_NAME: String? = "MyApp_Settings"
    }

    init {
        //   this.context = context1.getApplicationContext();
        // the millisecound in the future is the time set by you
        //  int Set_Time=1000;// every 1000 mili will make a 1 secound
        //freeApp
        if (context != null) {
            shardPreferencesSettings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
        //textView=  this.context.findViewById(R.id.textView);
        count_down = shardPreferencesSettings!!.getInt("key_counter", 15)
        Black_count_down = shardPreferencesSettings!!.getInt("Black_counter", 15)
        default_Timeout = shardPreferencesSettings!!.getInt("S_timeout", 120000)
        //   count_down= settings.getInt("key_counter", 3);
        //   Black_count_down=settings.getInt("Black_counter",3);

        // count_down=count_down*1000;
        //free app
        count_down = count_down * 1000 * 60
        counter = 0
        //   new CountDownTimer(Set_Time, 1000){
        Timer = object : CountDownTimer(count_down.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView?.setText(counter.toString())
                counter++
            }

            override fun onFinish() {
                val `in` = Intent(context, Black::class.java)
                context?.startActivity(`in`)
                textView?.setText("FINISH!!")
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
                    //
//                    // Set media volume level to zero
//                   mAudioManager.setStreamVolume(
//                            AudioManager.STREAM_MUSIC, // Stream type
//                            volume, // Index
//                            AudioManager.FLAG_SHOW_UI // Flags
//                    );
                    // set the screen time out to half of a second when the bright count down is finsh
                    if (context != null) {
                        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 500)
                    }
                }


                // send a notification
                val nm = NotificationManagerCompat.from(context!!)
                val notif = NotificationCompat.Builder(context)
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
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, default_Timeout)
                            changeScreenBrightness(context, 255)
                        }
                        // change the volume to current one
                        // Set media volume level to zero
//                        mAudioManager.setStreamVolume(
//                                AudioManager.STREAM_MUSIC, // Stream type
//                                current_volume, // Index
//                                AudioManager.FLAG_SHOW_UI // Flags
//                        );
                    }
                }.start()
            }
        }.start()
    }
}