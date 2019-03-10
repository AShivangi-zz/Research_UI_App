package com.example.shivangi.messaging_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.List;
import java.sql.Timestamp;


public class Main extends AppCompatActivity {

    private long startMilli;
    private long finishMilli;
    public static boolean s = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //connection thread
        ClientListen client = new ClientListen();
        new Thread(client).start();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMilli = System.currentTimeMillis();
                startActivity(new Intent(Main.this, MessageListActivity.class));
            }
        });

        //setup button
        Button setup_btn = findViewById(R.id.button2);
        setup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Main.this, Setup_pop.class), 0);
            }
        });

        final ImageView imageView = findViewById(R.id.imageView1);
        final List<Drawable> images = new ArrayList<>(3);
        images.add(getResources().getDrawable(R.drawable.img1min));
        images.add(getResources().getDrawable(R.drawable.img2min));
        images.add(getResources().getDrawable(R.drawable.img3min));

        final int[] cur = {1};

//        Button changeImage = findViewById(R.id.btnChangeImage);
//        changeImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageView.setImageDrawable(images.get((++1)%3));
//            }
//        });

        //changes images based on messages
        while(ClientListen.udpSocket.isConnected()){
            String msg = "";
            try {
                msg = client.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(msg.equals("STRAIO"))
                imageView.setImageDrawable(images.get(1));
            if(msg.equals("STRAIT"))
                imageView.setImageDrawable(images.get(0));
            if(msg.equals("RIGHTO"))
                imageView.setImageDrawable(images.get(2));
            if(msg.equals("RIGHTT"))
                imageView.setImageDrawable(images.get(1));
            if(msg.equals("LEFTTO"))
                imageView.setImageDrawable(images.get(0));
            if(msg.equals("LEFTTT"))
                imageView.setImageDrawable(images.get(2));
            if(msg.equals("TAEXIT"))
                imageView.setImageDrawable(images.get(1));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.hasExtra("result")) {
            finishMilli = System.currentTimeMillis();
            long time = finishMilli - startMilli;
            Log.d("TAG", Long.toString(time));
            Toast.makeText(getApplicationContext(), "Time from one click to next: " + time, Toast.LENGTH_LONG).show();
            File logFile = new File(getExternalCacheDir(), "RiSA2S_log.txt");
            String text = data.getStringExtra("result");
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
                out.print("[" + new Timestamp(System.currentTimeMillis()) + "] Resp Time: ");
                out.println(Long.toString(time) + " ms, Text: \"" + text + "\"");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
