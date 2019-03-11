package com.example.shivangi.messaging_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private EditText mMessageText;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("KK:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        final List<BaseMessage> messageList = new ArrayList<>();
        if(Main.voice_switch) {
            messageList.add(new BaseMessage("Sara", "Me", "How long will you be?",
                    dateFormat.format(new Date()))); } else {
            messageList.add(new BaseMessage("Sara", "Me", "Pepperoni or cheese pizza?",
                    dateFormat.format(new Date())));
        }
        setTitle(messageList.get(0).sender);

        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        mMessageText = findViewById(R.id.edittext_chatbox);
        Button mSendButton = findViewById(R.id.button_chatbox_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager =
                        (InputMethodManager) view.getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        MessageListActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String messageText = mMessageText.getText().toString();
                BaseMessage message = new BaseMessage("Me", "shivangi",
                        messageText,dateFormat.format(new Date()));
                messageList.add(message);
                mMessageAdapter.notifyDataSetChanged();


                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",messageText);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


        ImageButton voice = findViewById(R.id.button_voice);
        voice.setVisibility(Main.voice_switch? View.VISIBLE : View.GONE);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

                //new code
                long tt_click = System.currentTimeMillis();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("time",tt_click);
                setResult(Activity.RESULT_OK,returnIntent);
                //
                startActivityForResult(intent,200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            if(resultCode == RESULT_OK && data != null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mMessageText.setText(result.get(0));
            }
        }
    }
}