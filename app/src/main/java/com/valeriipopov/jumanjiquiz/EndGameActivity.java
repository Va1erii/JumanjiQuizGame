package com.valeriipopov.jumanjiquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    private TextView mCondition;
    private TextView mScoreTextView;
    private Resources mResources;
    private Drawable mDrawable;
    private RelativeLayout mLayout;
    private Handler mHandler;
    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        mResources = getResources();
        mLayout = findViewById(R.id.end_game);
        mHandler = new Handler(getMainLooper());

        String condition = getIntent().getStringExtra(QuestionActivity.CONDITION);
        mScore = getIntent().getIntExtra(QuestionActivity.SCORE, 0);

        mCondition = findViewById(R.id.game_condition);
        if (condition.equals(QuestionActivity.GAME_WIN)){
            mDrawable = mResources.getDrawable(R.drawable.bg_win);
            mCondition.setText(mResources.getText(R.string.win_game));
            mLayout.setBackground(mDrawable);
        }
        else {
            mDrawable = mResources.getDrawable(R.drawable.bg_lose);
            mCondition.setText(mResources.getText(R.string.lose_game));
            mScore = 0;
            mLayout.setBackground(mDrawable);
        }
        mScoreTextView = findViewById(R.id.stage_score);
        mScoreTextView.setText(String.valueOf(mScore));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                intent.putExtra(QuestionActivity.SCORE, mScore);
                startActivity(intent);
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {

    }
}
