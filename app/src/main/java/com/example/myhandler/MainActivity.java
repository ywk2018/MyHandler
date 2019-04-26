package com.example.myhandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {


    public static final int UPDATE_DATA = 1;
    private static TextView mTextView;

    static class TestHandler extends Handler{

        private WeakReference<Activity> mActivity;

        TestHandler(Activity activity){
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = (MainActivity) mActivity.get();
            if (activity != null) {
                switch (msg.what){
                    case UPDATE_DATA:
                        mTextView.setText("nice to meet you");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private TestHandler handler = new TestHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.tv_hello);
        findViewById(R.id.btn_updata_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_DATA;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
