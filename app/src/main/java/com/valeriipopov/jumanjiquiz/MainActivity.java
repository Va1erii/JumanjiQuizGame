package com.valeriipopov.jumanjiquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String STAGE = "stage";

    private Button mStage1;
    private Button mStage2;
    private Button mStage3;
    private Button mStage4;
    private Resources mResources;
    private ArrayList <Button> mButtons;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResources = getResources();
        mHandler = new Handler(getMainLooper());

        mStage1 = findViewById(R.id.btn_stage_1);
        mStage2 = findViewById(R.id.btn_stage_2);
        mStage3 = findViewById(R.id.btn_stage_3);
        mStage4 = findViewById(R.id.btn_stage_4);
        setBtnOnClick();
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
                            startActivity(intent);
                        }
                    }, 200);
                }
            });
        }
    }
}
