package com.smartsoftwaresolutions.blackout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.smartsoftwaresolutions.blackout.R;

public class Black extends AppCompatActivity {
    int C=0;
    Context context;
    SharedPreferences settings;
  //  public static final String PREFS_NAME = "MyApp_Settings";
    int default_Timeout,current_volume;
    AudioManager mAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black);
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        current_volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        // Set media volume level to zero
        mAudioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC, // Stream type
                0, // Index
                AudioManager.FLAG_SHOW_UI // Flags
        );
        settings = getSharedPreferences("MyApp_Settings", MODE_PRIVATE);

default_Timeout=settings.getInt("S_timeout",120000);


        Button btn_black=findViewById(R.id.btn_black);
context=getApplicationContext();
        btn_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C++;// C is the number of times you will hit the screen to get the bright on

                if (C==3){
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, default_Timeout);
                    // Check whether has the write settings permission or not.
                    boolean settingsCanWrite = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        settingsCanWrite = hasWriteSettingsPermission(context);
                    }

                    // If do not have then open the Can modify system settings panel.
                    if(!settingsCanWrite && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)) {
                        changeWriteSettingsPermission(context);
                    }else {
                        changeScreenBrightness(context, 255);
                        mAudioManager.setStreamVolume(
                                AudioManager.STREAM_MUSIC, // Stream type
                                current_volume, // Index
                                AudioManager.FLAG_SHOW_UI // Flags
                        );
                    finish();
                    }

                }


            }
        });
    }
    // Check whether this app has android write settings permission.
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasWriteSettingsPermission(Context context)
    {
        boolean ret = true;
        // Get the result from below code.
        ret = Settings.System.canWrite(context);
        return ret;
    }

    // Start can modify system settings panel to let user change the write settings permission.
    private void changeWriteSettingsPermission(Context context)
    {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        context.startActivity(intent);
    }

    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private void changeScreenBrightness(Context context, int screenBrightnessValue)
    {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);

        /*
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = screenBrightnessValue / 255f;
        window.setAttributes(layoutParams);
        */
    }



//    @Override
//    public void onAttachedToWindow() {
//       // this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_HOME)
//        {
//            Log.i("Home Button","Clicked");
//        }
//        if(keyCode== KeyEvent.KEYCODE_BACK)
//        {
//            finish();
//        }
//        return false;
//    }
//@Override
//public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//  //  if (event.getAction() == KeyEvent.ACTION_DOWN) {
//
//        switch (event.getKeyCode()) {
//            case  KeyEvent.KEYCODE_DPAD_UP:
//                showToast("UP pressed");
//                return true;
//            case  KeyEvent.KEYCODE_DPAD_DOWN:
//                showToast("DOWN pressed");
//                return true;
//            case  KeyEvent.KEYCODE_DPAD_RIGHT:
//                showToast("RIGHT pressed");
//                return true;
//            case  KeyEvent.KEYCODE_DPAD_LEFT:
//                showToast("LEFT pressed");
//                return true;
//            case  KeyEvent.KEYCODE_DPAD_CENTER:
//                showToast("CENTER pressed");
//                return true;
//        }
//   // }
//    return super.onKeyDown(keyCode, event);
//}
//
//    void showToast(String msg)
//    {
//        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
//    }
}
