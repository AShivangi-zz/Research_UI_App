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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMilli = System.currentTimeMillis();
                startActivityForResult(new Intent(Main.this, MessageListActivity.class), 0);
            }
        });

        final ImageView imageView = findViewById(R.id.imageView1);
        final List<Drawable> images = new ArrayList<>(3);
        images.add(getResources().getDrawable(R.drawable.img1min));
        images.add(getResources().getDrawable(R.drawable.img2min));
        images.add(getResources().getDrawable(R.drawable.img3min));

        final int[] cur = {1};

        Button changeImage = findViewById(R.id.btnChangeImage);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageDrawable(images.get((++cur[0])%3));
            }
        });
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
