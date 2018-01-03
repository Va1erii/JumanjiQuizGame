package com.valeriipopov.jumanjiquiz;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    private TextView mCondition;
    private TextView mScore;
    private Resources mResources;
    private Drawable mDrawable;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        mResources = getResources();
        mLayout = findViewById(R.id.end_game);

        String condition = getIntent().getStringExtra(QuestionActivity.CONDITION);
        int score = getIntent().getIntExtra(QuestionActivity.SCORE, 0);

        mCondition = findViewById(R.id.game_condition);
        if (condition.equals(QuestionActivity.GAME_WIN)){
            mDrawable = mResources.getDrawable(R.drawable.bg_win);
            mCondition.setText(mResources.getText(R.string.win_game));
            mLayout.setBackground(mDrawable);
        }
        else {
            mDrawable = mResources.getDrawable(R.drawable.bg_win);
            mCondition.setText(mResources.getText(R.string.lose_game));
            mLayout.setBackground(mDrawable);
        }
        mScore = findViewById(R.id.stage_score);
        mScore.setText(String.valueOf(score));
    }
}
