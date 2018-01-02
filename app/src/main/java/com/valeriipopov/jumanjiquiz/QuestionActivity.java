package com.valeriipopov.jumanjiquiz;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {

    private TextView mQuestion;
    private TextView mQuestionCount;
    private Handler mHandler;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;
    private LinearLayout mCheckBoxGroup;
    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private CheckBox mCheckBox4;
    private Button mButtonNext;
    private Resources mResources;
    private int mCount;
    private String[] mQuestions;
    private HashMap <String, Boolean> mQuestionBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mHandler = new Handler(getMainLooper());
        mResources = getResources();
        setRadioCheckBoxGroup();
        mButtonNext = findViewById(R.id.btn_next);
        mQuestionCount = findViewById(R.id.countQuestion);

        mQuestion = findViewById(R.id.question);
        mCount = 0;
        String stage = getIntent().getStringExtra(MainActivity.STAGE);
        mQuestionBank = setQuestionBank(stage);
        mQuestionCount.setText("Question " + (mCount+1) + "/10");
        setButtonNextListenner();
    }

    private void setRadioCheckBoxGroup(){
        mRadioGroup = findViewById(R.id.radioGroup);
        mRadioButton1 = findViewById(R.id.radio1);
        mRadioButton2 = findViewById(R.id.radio2);
        mRadioButton3 = findViewById(R.id.radio3);
        mRadioButton4 = findViewById(R.id.radio4);
        mCheckBoxGroup = findViewById(R.id.checkboxGroup);
        mCheckBox1 = findViewById(R.id.checkbox1);
        mCheckBox2 = findViewById(R.id.checkbox2);
        mCheckBox3 = findViewById(R.id.checkbox3);
        mCheckBox4 = findViewById(R.id.checkbox4);
    }

    private void setButtonNextListenner(){
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonNext.setTextColor(mResources.getColor(R.color.colorSelectTextJumanji));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mButtonNext.setTextColor(mResources.getColor(R.color.colorTextSubTitle));
                    }
                }, 100);
                if (mCount < 9) {
                    mCount++;
                    mQuestion.setText(mQuestions[mCount]);
                    mQuestionCount.setText("Question " + (mCount+1) + "/10");
                }
            }
        });
    }

    private HashMap <String, Boolean> setQuestionBank (String stage){
        HashMap <String, Boolean> questionBank = new HashMap<>();
        switch (stage){
            case HeroesActivity.STAGE_1:
                mQuestions = mResources.getStringArray(R.array.question_stage_1);
                for (String question: mQuestions){
                    questionBank.put(question, false);
                }
                mQuestion.setText(mQuestions[mCount]);
                break;
            case HeroesActivity.STAGE_2:
                mQuestions = mResources.getStringArray(R.array.question_stage_2);
                for (String question: mQuestions){
                    questionBank.put(question, false);
                }
                mQuestion.setText(mQuestions[mCount]);
                break;
            case HeroesActivity.STAGE_3:
                mQuestions = mResources.getStringArray(R.array.question_stage_3);
                for (String question: mQuestions){
                    questionBank.put(question, false);
                }
                mQuestion.setText(mQuestions[mCount]);
                break;
            case HeroesActivity.STAGE_4:
                mQuestions = mResources.getStringArray(R.array.question_stage_4);
                for (String question: mQuestions){
                    questionBank.put(question, false);
                }
                mQuestion.setText(mQuestions[mCount]);
                break;
        }
        return questionBank;
    }
}
