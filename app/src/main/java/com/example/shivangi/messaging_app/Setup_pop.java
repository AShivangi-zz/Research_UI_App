package com.example.shivangi.messaging_app;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Setup_pop extends Activity {

    ClientListen client = new ClientListen();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setup_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

        //connect button
        Button connect = findViewById(R.id.button3);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    client.connect();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(client.connect())
                    Toast.makeText(getApplicationContext(), "Connected" , Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(getApplicationContext(), "Not connected" , Toast.LENGTH_SHORT).show();
            }
        });

        //disconnect button
        Button disconnect = findViewById(R.id.button4);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(client.disconnect())
                    Toast.makeText(getApplicationContext(), "Disconnected" , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Still connected" , Toast.LENGTH_SHORT).show();
            }
        });

        //testID
        final EditText testID = findViewById(R.id.testId);

        //voice switch
//        final Switch v_switch = findViewById(R.id.switch_voice);
//        v_switch.setChecked(Main.voice_switch);
//        v_switch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(v_switch.isChecked()) {
//                    Toast.makeText(getApplicationContext(), "Voice Mode", Toast.LENGTH_SHORT).show();
//                    Main.voice_switch = true;
//                }
//                if(!v_switch.isChecked()) {
//                    Toast.makeText(getApplicationContext(), "Text Mode", Toast.LENGTH_SHORT).show();
//                    Main.voice_switch = false;
//                }
//            }
//        });

        //scenatio swicth
//        final Switch s_switch = findViewById(R.id.switch_scenario);
//        s_switch.setChecked(Main.scenario_switch);
//        s_switch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(s_switch.isChecked()) {
//                    Toast.makeText(getApplicationContext(), "Urban Mode", Toast.LENGTH_SHORT).show();
//                    Main.scenario_switch = true;
//                }
//                if(!s_switch.isChecked()) {
//                    Toast.makeText(getApplicationContext(), "Highway Mode", Toast.LENGTH_SHORT).show();
//                    Main.scenario_switch = false;
//                }
//            }
//        });

        //close dialog box
        Button close = findViewById(R.id.button5);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Main.test_id = Integer.parseInt(testID.getText().toString());
                //set to voice
                List<Integer> voice =Arrays.asList(1,2,5,6,7,9,11,13,18,20,22,23,24,26,29,30,33,34,37,40);
                //set to manual
                List<Integer> manual =Arrays.asList(3,4,8,10,12,14,15,16,17,19,21,25,27,28,31,32,35,36,38,39);
                if(voice.contains(Main.test_id))
                    Main.voice_switch = true;

                finish();
            }
        });


    }
}
