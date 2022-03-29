package com.smartsoftwaresolutions.blackout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class Black : AppCompatActivity() {
    private var C = 0
    private var context: Context? = null
    private var shardPreferencesSettings: SharedPreferences? = null

    //  public static final String PREFS_NAME = "MyApp_Settings";
    var default_Timeout = 0
    var current_volume = 0
    var mAudioManager: AudioManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black)

        mAudioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
        current_volume = mAudioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
        // Set media volume level to zero
        mAudioManager!!.setStreamVolume(
                AudioManager.STREAM_MUSIC,  // Stream type
                0,  // Index
                AudioManager.FLAG_SHOW_UI // Flags
        )
        shardPreferencesSettings = getSharedPreferences("MyApp_Settings", MODE_PRIVATE)
        default_Timeout= shardPreferencesSettings?.getInt("S_timeOut",120000)!!
        //default_Timeout = shardPreferencesSettings.getInt("S_timeout", 120000)
        val btn_black = findViewById<Button?>(R.id.btnBlack)
        context = applicationContext
        btn_black.setOnClickListener {
            C++ // C is the number of times you will hit the screen to get the bright on
            if (C == 3) {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, default_Timeout)
                // Check whether has the write settings permission or not.
                var settingsCanWrite = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    settingsCanWrite = hasWriteSettingsPermission(context)
                }

                // If do not have then open the Can modify system settings panel.
                if (!settingsCanWrite && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    changeWriteSettingsPermission(context)
                } else {
                    changeScreenBrightness(context, 255)
                    mAudioManager!!.setStreamVolume(
                            AudioManager.STREAM_MUSIC,  // Stream type
                            current_volume,  // Index
                            AudioManager.FLAG_SHOW_UI // Flags
                    )
                    finish()
                }
            }
        }
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
        if (context != null) {
            context.startActivity(intent)
        }
    }

    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private fun changeScreenBrightness(context: Context?, screenBrightnessValue: Int) {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context?.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context?.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue)


    }

}