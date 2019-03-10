package com.example.shivangi.messaging_app;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

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

        //close voice switch
        final Switch voice_switch = findViewById(R.id.switch_voice);
        voice_switch.setChecked(Main.s);
        voice_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(voice_switch.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Voice Mode", Toast.LENGTH_SHORT).show();
                    Main.s = true;
                }
                if(!voice_switch.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Text Mode", Toast.LENGTH_SHORT).show();
                    Main.s = false;
                }
            }
        });

        //close dialog box
        Button close = findViewById(R.id.button5);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
