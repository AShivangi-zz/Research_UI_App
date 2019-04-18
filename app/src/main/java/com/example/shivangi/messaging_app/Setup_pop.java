package com.example.shivangi.messaging_app;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

public class Setup_pop extends Activity {

    ClientListen client = new ClientListen();
    public void setWindowParams() {
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.dimAmount = 0;
        wlp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        getWindow().setAttributes(wlp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setup_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        setWindowParams();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

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
