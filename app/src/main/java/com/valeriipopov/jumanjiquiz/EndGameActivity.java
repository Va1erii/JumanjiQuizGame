package com.valeriipopov.jumanjiquiz;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    private TextView mConditionTextView;
    private TextView mScoreTextView;
    private Resources mResources;
    private Drawable mDrawable;
    private RelativeLayout mLayout;
    private Handler mHandler;
    private String mStage;
    private int mScore;
    private String mCondition;
    private EndGameActivityTask mTask;
    private boolean mIsLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        mResources = getResources();
        int orientation = mResources.getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            mIsLandscape = true;
        }
        mLayout = findViewById(R.id.end_game);
        mHandler = new Handler(getMainLooper());
        mTask = new EndGameActivityTask();
        if (savedInstanceState != null) {
            mCondition = savedInstanceState.getString(QuestionActivity.CONDITION);
            mScore = savedInstanceState.getInt(QuestionActivity.SCORE);
            mStage = savedInstanceState.getString(MainActivity.STAGE);
        } else {
            mCondition = getIntent().getStringExtra(QuestionActivity.CONDITION);
            mScore = getIntent().getIntExtra(QuestionActivity.SCORE, 0);
            mStage = getIntent().getStringExtra(MainActivity.STAGE);
        }

        mConditionTextView = findViewById(R.id.game_condition);
        if (mCondition.equals(QuestionActivity.GAME_WIN)){
            if (mStage.equals(HeroesActivity.STAGE_4)){
                if (mIsLandscape){
                    mDrawable = mResources.getDrawable(R.drawable.bg_main_activity_land);
                    mLayout.setBackground(mDrawable);
                } else {
                    mDrawable = mResources.getDrawable(R.drawable.bg_main_activity);
                    mLayout.setBackground(mDrawable);
                }
                mConditionTextView.setText(mResources.getText(R.string.win_game));
                mConditionTextView.setTextSize(30);
                mHandler.postDelayed(mTask, 6000);
            }
            else {
                if (mIsLandscape){
                    mDrawable = mResources.getDrawable(R.drawable.bg_win_land);
                    mLayout.setBackground(mDrawable);
                } else {
                    mDrawable = mResources.getDrawable(R.drawable.bg_win);
                    mLayout.setBackground(mDrawable);
                }
                mConditionTextView.setText(mResources.getText(R.string.win_stage));
                mHandler.postDelayed(mTask, 3000);
            }
        }
        else {
            if (mIsLandscape){
                mDrawable = mResources.getDrawable(R.drawable.bg_lose_land);
                mLayout.setBackground(mDrawable);
            } else {
                mDrawable = mResources.getDrawable(R.drawable.bg_lose);
                mLayout.setBackground(mDrawable);
            }
            mConditionTextView.setText(mResources.getText(R.string.lose_game));
            mScore = 0;
            mHandler.postDelayed(mTask, 3000);
        }
        mScoreTextView = findViewById(R.id.stage_score);
        mScoreTextView.setText(String.valueOf(mScore));
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(QuestionActivity.CONDITION, mCondition);
        outState.putInt(QuestionActivity.SCORE, mScore);
        outState.putString(MainActivity.STAGE, mStage);
        mHandler.removeCallbacks(mTask);
        super.onSaveInstanceState(outState);
    }

    class EndGameActivityTask implements Runnable{
        @Override
        public void run() {
            Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
            intent.putExtra(QuestionActivity.SCORE, mScore);
            startActivity(intent);
        }
    }
}
