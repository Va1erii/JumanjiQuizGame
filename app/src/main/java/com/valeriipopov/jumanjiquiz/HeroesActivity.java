package com.valeriipopov.jumanjiquiz;

import android.content.Intent;
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
    private TextView mStage;
    private TextView mHeroName;
    private TextView mHeroStrength;
    private TextView mHeroWeakness;
    private TextView mHeroHistory;
    private Handler mHandler;
    private Resources mResources;

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

        mStage = findViewById(R.id.stage);
        final String stage = getIntent().getStringExtra(MainActivity.STAGE);
        switch (stage){
            case STAGE_1:
                mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_shelly));
                mStage.setText(stage);
                mHeroName.setText(mResources.getText(R.string.shelly));
                mHeroStrength.setText(mResources.getText(R.string.shelly_strengths));
                mHeroWeakness.setText(mResources.getText(R.string.shelly_weakness));
                mHeroHistory.setText(mResources.getText(R.string.shelly_history));
                break;
            case STAGE_2:
                mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_ruby));
                mStage.setText(stage);
                mHeroName.setText(mResources.getText(R.string.ruby));
                mHeroStrength.setText(mResources.getText(R.string.ruby_strengths));
                mHeroWeakness.setText(mResources.getText(R.string.ruby_weakness));
                mHeroHistory.setText(mResources.getText(R.string.ruby_history));
                break;
            case STAGE_3:
                mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_franklin));
                mStage.setText(stage);
                mHeroName.setText(mResources.getText(R.string.franklin));
                mHeroStrength.setText(mResources.getText(R.string.franklin_strengths));
                mHeroWeakness.setText(mResources.getText(R.string.franklin_weakness));
                mHeroHistory.setText(mResources.getText(R.string.franklin_history));
                break;
            case STAGE_4:
                mHeroBackground.setBackground(mResources.getDrawable(R.drawable.bg_bravestone));
                mStage.setText(stage);
                mHeroName.setText(mResources.getText(R.string.bravestone));
                mHeroStrength.setText(mResources.getText(R.string.bravestone_strengths));
                mHeroWeakness.setText(mResources.getText(R.string.bravestone_weakness));
                mHeroHistory.setText(mResources.getText(R.string.bravestone_history));
                break;
        }
        mStage.setText(stage);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HeroesActivity.this, QuestionActivity.class);
                intent.putExtra(MainActivity.STAGE, stage);
                startActivity(intent);
            }
        }, 10000);
    }
}
