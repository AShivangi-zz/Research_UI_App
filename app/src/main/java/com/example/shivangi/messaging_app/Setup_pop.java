package com.example.shivangi.messaging_app;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Arrays;
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
        //close dialog box
        Button close = findViewById(R.id.button5);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Main.test_id = Integer.parseInt(testID.getText().toString());
                //set to voice
                List<Integer> voice =Arrays.asList(1,2,5,6,7,9,11,13,18,20,22,23,24,26,29,30,33,34,37,40);
                if(voice.contains(Main.test_id))
                    Main.voice_switch = true;

                finish();
            }
        });


    }
}
