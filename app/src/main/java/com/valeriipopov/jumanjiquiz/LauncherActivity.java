package com.valeriipopov.jumanjiquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * LauncherActivity show us animating text. I used Handler for it
 */

public class LauncherActivity extends AppCompatActivity {

    private Button mStartButton;
    private Resources mResources;
    private TextView mTextViewQuote;
    private Handler mHandler;
    private String[] mJumangiQuote;
    private LauncherActivityTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mTextViewQuote = findViewById(R.id.quote);
        mResources = getResources();
        mHandler = new Handler(getMainLooper());
        mJumangiQuote = mResources.getStringArray(R.array.jumangi_quote);
        mTask = new LauncherActivityTask();

        mStartButton = findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextViewQuote.setText("");
                mStartButton.setClickable(false);
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
                    time += 400;
                }
                mHandler.postDelayed(mTask, mJumangiQuote.length * 550);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        mHandler.removeCallbacks(mTask);
        LauncherActivity.this.finish();
        // To remove our previous task and quit from game
    }

    /**
     * This class is implementing Runnable class, it opens MainActivity
     */
    class LauncherActivityTask implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        }
    }
}
