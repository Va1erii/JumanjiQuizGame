package com.valeriipopov.jumanjiquiz;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HeroesActivity extends AppCompatActivity {

    public static final String STAGE_1 = "Stage 1";
    public static final String STAGE_2 = "Stage 2";
    public static final String STAGE_3 = "Stage 3";
    public static final String STAGE_4 = "Stage 4";

    private RelativeLayout mHeroBackground;
    private TextView mStageTextView;
    private TextView mHeroName;
    private TextView mHeroStrength;
    private TextView mHeroWeakness;
    private TextView mHeroHistory;
    private Handler mHandler;
    private Resources mResources;
    private int mScore;
    private String mStage;
    private boolean mIsLandScape;
    private HeroesActivityTask mActivityTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes);
        mHandler = new Handler(getMainLooper());
        mResources = getResources();
        mHeroBackground = findViewById(R.id.hero_background);
        mHeroName = findViewById(R.id.hero_name);
        mHeroStrength = findViewById(R.id.strengths);
        mHeroWeakness = findViewById(R.id.weakness);
        mHeroHistory = findViewById(R.id.hero_history);
        mActivityTask = new HeroesActivityTask();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            mIsLandScape = true;
        }
        mStageTextView = findViewById(R.id.stage);
        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt(QuestionActivity.SCORE);
            mStage = savedInstanceState.getString(MainActivity.STAGE);
        } else {
            mScore = getIntent().getIntExtra(QuestionActivity.SCORE, 0);
            mStage = getIntent().getStringExtra(MainActivity.STAGE);
        }
        switch (mStage){
            case STAGE_1:
                if (mIsLandScape){
                    mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_shelly_land));
                }
                else {
                    mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_shelly));
                }
                mStageTextView.setText(mStage);
                mHeroName.setText(mResources.getText(R.string.shelly));
                mHeroStrength.setText(mResources.getText(R.string.shelly_strengths));
                mHeroWeakness.setText(mResources.getText(R.string.shelly_weakness));
                mHeroHistory.setText(mResources.getText(R.string.shelly_history));
                break;
            case STAGE_2:
                if (mIsLandScape){
                    mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_ruby_land));
                }
                else {
                    mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_ruby));
                }
                mStageTextView.setText(mStage);
                mHeroName.setText(mResources.getText(R.string.ruby));
                mHeroStrength.setText(mResources.getText(R.string.ruby_strengths));
                mHeroWeakness.setText(mResources.getText(R.string.ruby_weakness));
                mHeroHistory.setText(mResources.getText(R.string.ruby_history));
                break;
            case STAGE_3:
                if (mIsLandScape){
                    mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_franklin_land));
                }
                else {
                    mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_franklin));
                }
                mStageTextView.setText(mStage);
                mHeroName.setText(mResources.getText(R.string.franklin));
                mHeroStrength.setText(mResources.getText(R.string.franklin_strengths));
                mHeroWeakness.setText(mResources.getText(R.string.franklin_weakness));
                mHeroHistory.setText(mResources.getText(R.string.franklin_history));
                break;
            case STAGE_4:
                if (mIsLandScape){
                    mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_bravestone_land));
                }
                else {
                    mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_bravestone));
                }
                mStageTextView.setText(mStage);
                mHeroName.setText(mResources.getText(R.string.bravestone));
                mHeroStrength.setText(mResources.getText(R.string.bravestone_strengths));
                mHeroWeakness.setText(mResources.getText(R.string.bravestone_weakness));
                mHeroHistory.setText(mResources.getText(R.string.bravestone_history));
                break;
        }
        mStageTextView.setText(mStage);
        if (savedInstanceState != null){
            mHandler.postDelayed(mActivityTask, 5000);
        } else {
            mHandler.postDelayed(mActivityTask, 8000);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(QuestionActivity.SCORE, mScore);
        outState.putString(MainActivity.STAGE, mStage);
        mHandler.removeCallbacks(mActivityTask);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

    }

    class HeroesActivityTask implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(HeroesActivity.this, QuestionActivity.class);
            intent.putExtra(MainActivity.STAGE, mStage);
            intent.putExtra(QuestionActivity.SCORE, mScore);
            startActivity(intent);
        }
    }
}
