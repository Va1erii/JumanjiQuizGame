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

/**
 * EndGameActivity show us our condition after end game. And then return to MainActivity
 * We check an orientation, if the orientation equals landscape, we'll use the landscape drawable
 */

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
            //To check orientation
        }
        mLayout = findViewById(R.id.end_game);
        mHandler = new Handler(getMainLooper());
        mTask = new EndGameActivityTask();
        // mTask runs MainActivity with a current score

        if (savedInstanceState != null) {
            mCondition = savedInstanceState.getString(QuestionActivity.CONDITION);
            mScore = savedInstanceState.getInt(QuestionActivity.SCORE);
            mStage = savedInstanceState.getString(MainActivity.STAGE);
            // If we change orientation
        } else {
            mCondition = getIntent().getStringExtra(QuestionActivity.CONDITION);
            mScore = getIntent().getIntExtra(QuestionActivity.SCORE, 0);
            mStage = getIntent().getStringExtra(MainActivity.STAGE);
        }
        mConditionTextView = findViewById(R.id.game_condition);

        /**
         * Check our condition. If we won, we show win's text. If we lost, our score = 0
         */
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
                // If we won stage 4, we show text win game. Also we check orientation mode
                // Handler.postDelayed is method when we use to run after some time (millis)
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
            mHandler.postDelayed(mTask, 3000);
        }
        mScoreTextView = findViewById(R.id.stage_score);
        mScoreTextView.setText(String.valueOf(mScore));
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * @param outState is our savedInstanceState. We save our scor, stage and condition
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(QuestionActivity.CONDITION, mCondition);
        outState.putInt(QuestionActivity.SCORE, mScore);
        outState.putString(MainActivity.STAGE, mStage);
        mHandler.removeCallbacks(mTask);
        super.onSaveInstanceState(outState);
    }

    /**
     * This class is implementing Runnable class, it opens MainActivity with our current score
     */
    class EndGameActivityTask implements Runnable{
        @Override
        public void run() {
            if (mCondition.equals(QuestionActivity.GAME_LOSE)){
                mScore = 0;
            }
            Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
            intent.putExtra(QuestionActivity.SCORE, mScore);
            startActivity(intent);
        }
    }
}
