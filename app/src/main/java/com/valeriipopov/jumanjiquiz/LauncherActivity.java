package com.valeriipopov.jumanjiquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LauncherActivity extends AppCompatActivity {

    private Button mStartButton;
    private Resources mResources;
    private TextView mTextViewQuote;
    private Handler mHandler;
    private String[] mJumangiQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mTextViewQuote = findViewById(R.id.quote);
        mResources = getResources();
        mHandler = new Handler(getMainLooper());
        mJumangiQuote = mResources.getStringArray(R.array.jumangi_quote);

        mStartButton = findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextViewQuote.setText("");
                mStartButton.setTextColor(mResources.getColor(R.color.colorSelectTextJumanji));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mStartButton.setTextColor(mResources.getColor(R.color.colorTextJumanjiStartButton));
                    }
                }, 100);
                long time = 1000;
                for (int i = 0; i < mJumangiQuote.length; i++) {
                    final int finalI = i;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewQuote.append(mJumangiQuote[finalI] + " ");
                        }
                    }, time);
                    time += 500;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    }
                }, mJumangiQuote.length * 550);
            }
        });
    }
}
