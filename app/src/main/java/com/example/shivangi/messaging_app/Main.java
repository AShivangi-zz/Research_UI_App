package com.example.shivangi.messaging_app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Timestamp;


public class Main extends AppCompatActivity {

    private long startMilli;
    private long finishMilli;
    public static boolean voice_switch = true;
    public static int test_id = 0;
    public static int scenario_count = 1;
    private MediaPlayer m;

    public ImageView imageView;
    public static Integer[] mThumbIds = {R.drawable.l, R.drawable.s, R.drawable.r};
    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //connection thread
        ClientListen client = new ClientListen();
        new Thread(client).start();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView1);
        i=0;
        t.start();

//        //final MediaPlayer m = MediaPlayer.create(this, R.raw.test1); for audio
//        Button button1 = findViewById(R.id.button);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startMilli = System.currentTimeMillis();
//                //m.start(); //for audio
//                startActivityForResult(new Intent(Main.this, MessageListActivity.class), 0);
//            }
//        });
//
//        //setup button
//        Button setup_btn = findViewById(R.id.button2);
//        setup_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(Main.this, Setup_pop.class), 0);
//            }
//        });



//        Runnable myRunnable = new Runnable() {
//        @Override
//        public void run(){
//            try {
//                Thread.sleep(3000);
//                imageView.setImageDrawable(images.get(1));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                Thread.sleep(3000);
//                imageView.setImageDrawable(images.get(2));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                Thread.sleep(3000);
//                imageView.setImageDrawable(images.get(0));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }}.run();


//        for(i = 0; i<images.size(); i++)
//        {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                    Thread.sleep(3000);
//                    imageView.setImageDrawable(images.get(i));
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                }
//            });
//        }

        //changes images based on messages
//        while(ClientListen.udpSocket.isConnected()){
//            String msg = "";
//            try {
//                msg = client.getData();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if(msg.equals("STRAIO"))
//                imageView.setImageDrawable(images.get(1));
//            if(msg.equals("STRAIT"))
//                imageView.setImageDrawable(images.get(0));
//            if(msg.equals("RIGHTO"))
//                imageView.setImageDrawable(images.get(2));
//            if(msg.equals("RIGHTT"))
//                imageView.setImageDrawable(images.get(1));
//            if(msg.equals("LEFFTO"))
//                imageView.setImageDrawable(images.get(0));
//            if(msg.equals("LEFFTT"))
//                imageView.setImageDrawable(images.get(2));
//            if(msg.equals("TAEXIT"))
//                imageView.setImageDrawable(images.get(1));
//        }
    }

    Thread t = new Thread() {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(3000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageResource(mThumbIds[i]);
                            if(i==0)
                                m = MediaPlayer.create(Main.this, R.raw.t_left);
                            if(i==1)
                                m = MediaPlayer.create(Main.this, R.raw.straight);
                            if(i==2)
                                m = MediaPlayer.create(Main.this, R.raw.t_right);
                            m.start();

                            i++;
                            if(i >= mThumbIds.length){
                                t.interrupt();
                                //add a 2 sec delay
//                                try {
//                                    Thread.sleep(2000);
                                    startActivityForResult(new Intent(Main.this, MessageListActivity.class), 0);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }
                    });
                }
            } catch (InterruptedException e) { }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.hasExtra("result")) {
            finishMilli = System.currentTimeMillis();
            long time = finishMilli - startMilli;
            Log.d("TAG", Long.toString(time));
            //Toast.makeText(getApplicationContext(), "Time from one click to next: " + time, Toast.LENGTH_LONG).show();
            File logFile = new File(getExternalCacheDir(), "RiSA2S_log.txt");
            String text = data.getStringExtra("result");
            long tt_click = data.getLongExtra("time", 0);
            long tt_click2 = data.getLongExtra("time2", 0);
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
                out.print("[" + new Timestamp(System.currentTimeMillis()) + "] [TID_count:" + test_id + "_" +scenario_count +
                        "] [" +  reset_flags() + "] [Total: " + Long.toString(time) + " ms] ");
                if(tt_click!=0)
                    out.print("[V: " + Long.toString(tt_click-startMilli)+ " ms] ");
                if(tt_click2!=0)
                    out.print("[M: " + Long.toString(tt_click2-startMilli)+ " ms] ");
                out.println(" Text: \"" + text + "\"");
                scenario_count+=1;
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String reset_flags() {
        List<Integer> A = Arrays.asList(1,7,13,24,30); //C v C m H v H m
        List<Integer> B = Arrays.asList(2,11,18,29,40); // C v C m H m H v
        List<Integer> C = Arrays.asList(4,15,27,36,38); //C m C v H v H m
        List<Integer> D = Arrays.asList(3,14,25,31,32,37); //C m C v H m H v
        List<Integer> E = Arrays.asList(5,23,26,34,37); //H v H m C v C m
        List<Integer> F = Arrays.asList(6,9,20,22,33); // H v H m C m C v
        List<Integer> G = Arrays.asList(8,17,19,21,35); //H m H v C v C m
        List<Integer> H = Arrays.asList(10,12,16,28,39); //H m H v C m C v
        if(A.contains(test_id)){
            if(scenario_count == 1) {
                voice_switch = false;
                return "Cv";
            }
            if(scenario_count ==2) {
                voice_switch = true;
                return "Cm";
            }
            if(scenario_count == 3) {
                voice_switch = false;
                return "Hv";
            }
            if(scenario_count == 4) {
                return "Hm";
            }
        }
        else if(B.contains(test_id)) { // C v C m H m H v
            if(scenario_count == 1) {
                voice_switch = false;
                return "Cv";
            }
            if(scenario_count ==2) {
                return "Cm";
            }
            if(scenario_count == 3) {
                voice_switch = true;
                return "Hm";
            }
            if(scenario_count == 4) {
                return "Hv";
            }
        }
        else if(C.contains(test_id)) { //C m C v H v H m
            if(scenario_count == 1) {
                voice_switch = true;
                return "Cm";
            }
            if(scenario_count ==2) {
                return "Cv";
            }
            if(scenario_count == 3) {
                voice_switch = false;
                return "Hv";
            }
            if(scenario_count == 4) {
                return "Hm";
            }
        }
        else if(D.contains(test_id)) { //C m C v H m H v
            if(scenario_count == 1) {
                voice_switch = true;
                return "Cm";
            }
            if(scenario_count == 2) {
                voice_switch = false;
                return "Cv";
            }
            if(scenario_count == 3) {
                voice_switch = true;
                return "Hm";
            }
            if(scenario_count == 4) {
                return "Hv";
            }
        }
        else if(E.contains(test_id)) { //H v H m C v C m
            if(scenario_count == 1) {
                voice_switch = false;
                return "Hv";
            }
            if(scenario_count == 2) {
                voice_switch = true;
                return "Hm";
            }
            if(scenario_count == 3) {
                voice_switch = false;
                return "Cv";
            }
            if(scenario_count == 4) {
                return "Cm";
            }
        }
        else if(F.contains(test_id)) { // H v H m C m C v
            if(scenario_count == 1) {
                voice_switch = false;
                return "Hv";
            }
            if(scenario_count == 2) {
                return "Hm";
            }
            if(scenario_count == 3) {
                voice_switch = true;
                return "Cm";
            }
            if(scenario_count == 4) {
                return "Cv";
            }
        }
        else if(G.contains(test_id)) { //H m H v C v C m
            if(scenario_count == 1) {
                voice_switch = true;
                return "Hm";
            }
            if(scenario_count == 2) {
                return "Hv";
            }
            if(scenario_count == 3) {
                voice_switch = false;
                return "Cv";
            }
            if(scenario_count == 4) {
                return "Cm";
            }
        }
        else if(H.contains(test_id)) { //H m H v C m C v
            if(scenario_count == 1) {
                voice_switch = true;
                return "Hm";
            }
            if(scenario_count == 2) {
                voice_switch = false;
                return "Hv";
            }
            if(scenario_count == 3) {
                voice_switch = true;
                return "Cm";
            }
            if(scenario_count == 4) {
                return "Cv";
            }
        }
        return "";
    }
}
