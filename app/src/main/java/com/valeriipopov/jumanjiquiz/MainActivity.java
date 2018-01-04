package com.valeriipopov.jumanjiquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String STAGE = "stage";

    private Button mStage1;
    private Button mStage2;
    private Button mStage3;
    private Button mStage4;
    private ImageView mStageLock2;
    private ImageView mStageLock3;
    private ImageView mStageLock4;
    private Resources mResources;
    private TextView mScoreTextView;
    private int mScore;
    private ArrayList <Button> mButtons;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResources = getResources();
        mHandler = new Handler(getMainLooper());
        mScoreTextView = findViewById(R.id.textview_score);
        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt(QuestionActivity.SCORE);
        } else {
            mScore = getIntent().getIntExtra(QuestionActivity.SCORE, 0);
        }
        mScoreTextView.setText(String.valueOf(mScore));
        mStage1 = findViewById(R.id.btn_stage_1);
        mStage2 = findViewById(R.id.btn_stage_2);
        mStage3 = findViewById(R.id.btn_stage_3);
        mStage4 = findViewById(R.id.btn_stage_4);
        mStageLock2 = findViewById(R.id.lock_stage_2);
        mStageLock3 = findViewById(R.id.lock_stage_3);
        mStageLock4 = findViewById(R.id.lock_stage_4);
        setBtnOnClick();

        if (mScore < 70){
            mStage2.setClickable(false);
            mStage3.setClickable(false);
            mStage4.setClickable(false);
        }
        else if (mScore > 70 && mScore < 140) {
            mStage2.setClickable(true);
            mStageLock2.setImageDrawable(mResources.getDrawable(R.drawable.ic_lock_open));
            mStage3.setClickable(false);
            mStage4.setClickable(false);
        }
        else if (mScore > 140 && mScore < 210) {
            mStage2.setClickable(true);
            mStageLock2.setImageDrawable(mResources.getDrawable(R.drawable.ic_lock_open));
            mStage3.setClickable(true);
            mStageLock3.setImageDrawable(mResources.getDrawable(R.drawable.ic_lock_open));
            mStage4.setClickable(false);
        }
        else {
            mStage2.setClickable(true);
            mStageLock2.setImageDrawable(mResources.getDrawable(R.drawable.ic_lock_open));
            mStage3.setClickable(true);
            mStageLock3.setImageDrawable(mResources.getDrawable(R.drawable.ic_lock_open));
            mStage4.setClickable(true);
            mStageLock4.setImageDrawable(mResources.getDrawable(R.drawable.ic_lock_open));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(QuestionActivity.SCORE, mScore);
        super.onSaveInstanceState(outState);
    }

    private void setBtnOnClick(){
        mButtons = new ArrayList<>();
        mButtons.add(mStage1);
        mButtons.add(mStage2);
        mButtons.add(mStage3);
        mButtons.add(mStage4);
        for (final Button btn: mButtons){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn.setTextColor(mResources.getColor(R.color.colorSelectTextJumanji));
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn.setTextColor(mResources.getColor(R.color.colorTextSubTitle));
                        }
                    }, 100);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, HeroesActivity.class);
                            intent.putExtra(STAGE, btn.getText().toString());
                            intent.putExtra(QuestionActivity.SCORE, mScore);
                            startActivity(intent);
                        }
                    }, 200);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog mAlertDialog = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                .setCancelable(false)
                .setTitle(getResources().getText(R.string.app_title))
                .setMessage(getResources().getText(R.string.quit_game))
                .setPositiveButton(getResources().getText(R.string.positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, LauncherActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(getResources().getText(R.string.negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}
