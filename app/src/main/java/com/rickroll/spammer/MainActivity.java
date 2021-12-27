package com.rickroll.spammer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String pNumber = "";
    private String strRickRollLong = "We're no strangers to love You know the rules and so do I A full "+
            "commitment's what I'm thinking of You wouldn't get this from any other guy I just wanna " +
            "tell you how I'm feeling Gotta make you understand Never gonna give you up Never gonna " +
            "let you down Never gonna run around and desert you Never gonna make you cry Never gonna " +
            "say goodbye Never gonna tell a lie and hurt you " +
            "https://bit.ly/2KMnbz5";
    private String[] arrRickRollLong = strRickRollLong.split("\\s+");

    private String strRickRollShort = "Never gonna give you up Never gonna " +
            "let you down Never gonna run around and desert you Never gonna make you cry Never gonna " +
            "say goodbye Never gonna tell a lie and hurt you " +
            "https://bit.ly/2KMnbz5";
    private String[] arrRickRollShort = strRickRollShort.split("\\s+");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButtonListenerMethod();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        7);
            }
        }
    }

    public void myButtonListenerMethod() {
        Button buttonRR = (Button) findViewById(R.id.btnRickRoll);
        radioGroup = (RadioGroup) findViewById(R.id.radioG);
        buttonRR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().getDecorView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                EditText txtname = findViewById(R.id.phoneNumber);
                pNumber =  txtname.getText().toString();

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                SmsManager sms = SmsManager.getDefault();
                try {
                    if(pNumber.length() < 10){
                        throw new Exception();
                    }
                    if(radioButton.getText().equals("Verse and Chorus")) {
                        for (int i = 0; i < arrRickRollLong.length; i++) {
                            sms.sendTextMessage(pNumber, null, arrRickRollLong[i], null, null);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                //don't really need this
                                //e.printStackTrace();
                            }
                        }
                    }else{
                        for (int i = 0; i < arrRickRollShort.length; i++) {
                            sms.sendTextMessage(pNumber, null, arrRickRollShort[i], null, null);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                //don't really need this
                                //e.printStackTrace();
                            }
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Rick Roll sent!", Toast.LENGTH_SHORT).show();
                    ImageView imgSuccess = (ImageView)findViewById(R.id.rickrollkim);
                    imgSuccess.setVisibility(View.VISIBLE);
                    hidePictures();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Message sending failed!!", Toast.LENGTH_LONG).show();
                    ImageView imgFailure = (ImageView)findViewById(R.id.sadkim);
                    imgFailure.setVisibility(View.VISIBLE);
                    hidePictures();
                }
            }
        });
    }

    public void hidePictures(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ImageView imgSuccess = (ImageView)findViewById(R.id.rickrollkim);
                ImageView imgFailure = (ImageView)findViewById(R.id.sadkim);
                imgSuccess.setVisibility(View.INVISIBLE);
                imgFailure.setVisibility(View.INVISIBLE);
                cancel();
            }
        }, 3000);
    }
}