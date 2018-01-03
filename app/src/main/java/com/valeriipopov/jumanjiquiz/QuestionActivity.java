package com.valeriipopov.jumanjiquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {
    public static final String SCORE = "score";
    public static final String CONDITION = "condition";
    public static final String GAME_WIN = "win";
    public static final String GAME_LOSE = "lose";

    private TextView mQuestionScore;
    private TextView mQuestion;
    private TextView mQuestionCount;
    private Handler mHandler;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;
    private RadioButton[] mRadioButtons;
    private LinearLayout mCheckBoxGroup;
    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private CheckBox mCheckBox4;
    private CheckBox[] mCheckBoxes;
    private ImageView mImageHP1;
    private ImageView mImageHP2;
    private ImageView mImageHP3;
    private Button mButtonNext;
    private Resources mResources;
    private int mCount;
    private int mHealth = 3;
    private int mScore = 0;
    private String[] mQuestions;
    private String mAnswer;
    private String mStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mHandler = new Handler(getMainLooper());
        mResources = getResources();

        mImageHP1 = findViewById(R.id.hp_1);
        mImageHP2 = findViewById(R.id.hp_2);
        mImageHP3 = findViewById(R.id.hp_3);
        mQuestionScore = findViewById(R.id.question_score);
        mQuestionScore.setText(String.valueOf(mScore));

        setRadioCheckBoxGroup();
        mButtonNext = findViewById(R.id.btn_next);
        mQuestionCount = findViewById(R.id.countQuestion);

        mQuestion = findViewById(R.id.question);
        mCount = 0;
        mStage = getIntent().getStringExtra(MainActivity.STAGE);
        mQuestionCount.setText("Question " + (mCount+1) + "/10");
        setQuestions(mStage);
        setButtonNextListenner();
        checkHealth(mHealth);
    }

    private void setRadioCheckBoxGroup(){
        mRadioGroup = findViewById(R.id.radioGroup);
        mRadioButton1 = findViewById(R.id.radio1);
        mRadioButton2 = findViewById(R.id.radio2);
        mRadioButton3 = findViewById(R.id.radio3);
        mRadioButton4 = findViewById(R.id.radio4);
        mRadioButtons = new RadioButton[4];
        mRadioButtons[0] = mRadioButton1;
        mRadioButtons[1] = mRadioButton2;
        mRadioButtons[2] = mRadioButton3;
        mRadioButtons[3] = mRadioButton4;
        mCheckBoxGroup = findViewById(R.id.checkboxGroup);
        mCheckBox1 = findViewById(R.id.checkbox1);
        mCheckBox2 = findViewById(R.id.checkbox2);
        mCheckBox3 = findViewById(R.id.checkbox3);
        mCheckBox4 = findViewById(R.id.checkbox4);
        mCheckBoxes = new CheckBox[4];
        mCheckBoxes[0] = mCheckBox1;
        mCheckBoxes[1] = mCheckBox2;
        mCheckBoxes[2] = mCheckBox3;
        mCheckBoxes[3] = mCheckBox4;
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
                if (mCount < 5 || mCount > 7 && mCount < 9) {
                    for (final RadioButton radioButton: mRadioButtons){
                        if (radioButton.isChecked()){
                            String choice = radioButton.getHint().toString();
                            if (choice.equals(mAnswer)){
                                mScore += 10;
                                radioButton.setBackground(mResources.getDrawable(R.drawable.bg_quiz_correct));
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        radioButton.setBackground(mResources.getDrawable(R.drawable.bg_quiz));
                                        mQuestionScore.setText(String.valueOf(mScore));
                                    }
                                }, 100);
                            }
                            else {
                                mHealth--;
                                radioButton.setBackground(mResources.getDrawable(R.drawable.bg_quiz_wrong));
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        radioButton.setBackground(mResources.getDrawable(R.drawable.bg_quiz));
                                        checkHealth(mHealth);
                                    }
                                }, 100);
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mCount++;
                                    mQuestion.setText(mQuestions[mCount]);
                                    mQuestionCount.setText("Question " + (mCount+1) + "/10");
                                    setAnswerChoice(mCount, mStage);
                                }
                            }, 500);
                        }
                    }
                }
                else if (mCount > 4 && mCount < 8){
                    StringBuilder choice = new StringBuilder();
                    for (CheckBox checkBox: mCheckBoxes){
                        if (checkBox.isChecked()){
                            choice.append(checkBox.getHint());
                        }
                    }
                    if (choice.toString().equals(mAnswer)){
                        mScore +=10;
                        for (final CheckBox checkBox: mCheckBoxes){
                            if (checkBox.isChecked()){
                                checkBox.setBackground(mResources.getDrawable(R.drawable.bg_quiz_correct));
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        checkBox.setBackground(mResources.getDrawable(R.drawable.bg_quiz));
                                    }
                                }, 100);
                            }
                        }
                        mQuestionScore.setText(String.valueOf(mScore));
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCount++;
                                mQuestion.setText(mQuestions[mCount]);
                                mQuestionCount.setText("Question " + (mCount+1) + "/10");
                                setAnswerChoice(mCount, mStage);
                            }
                        }, 500);
                    }
                    else {
                        mHealth--;
                        for (final CheckBox checkBox: mCheckBoxes){
                            if (checkBox.isChecked()){
                                checkBox.setBackground(mResources.getDrawable(R.drawable.bg_quiz_wrong));
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        checkBox.setBackground(mResources.getDrawable(R.drawable.bg_quiz));
                                    }
                                }, 100);
                            }
                        }
                        checkHealth(mHealth);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCount++;
                                mQuestion.setText(mQuestions[mCount]);
                                mQuestionCount.setText("Question " + (mCount+1) + "/10");
                                setAnswerChoice(mCount, mStage);
                            }
                        }, 500);
                    }
                }
                else {
                    for (final RadioButton radioButton: mRadioButtons){
                        if (radioButton.isChecked()){
                            String choice = radioButton.getHint().toString();
                            if (choice.equals(mAnswer)){
                                mScore += 10;
                                radioButton.setBackground(mResources.getDrawable(R.drawable.bg_quiz_correct));
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        radioButton.setBackground(mResources.getDrawable(R.drawable.bg_quiz));
                                        mQuestionScore.setText(String.valueOf(mScore));
                                    }
                                }, 100);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(QuestionActivity.this, EndGameActivity.class);
                                        intent.putExtra(SCORE, mScore);
                                        intent.putExtra(CONDITION, GAME_WIN);
                                        startActivity(intent);
                                    }
                                }, 500);
                            }
                            else {
                                mHealth--;
                                radioButton.setBackground(mResources.getDrawable(R.drawable.bg_quiz_wrong));
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        radioButton.setBackground(mResources.getDrawable(R.drawable.bg_quiz));
                                        checkHealth(mHealth);
                                    }
                                }, 100);
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(QuestionActivity.this, EndGameActivity.class);
                                    intent.putExtra(SCORE, mScore);
                                    intent.putExtra(CONDITION, GAME_WIN);
                                    startActivity(intent);
                                }
                            }, 500);
                        }
                    }
                }
            }
        });
    }

    private void setQuestions (String stage){
        switch (stage){
            case HeroesActivity.STAGE_1:
                mQuestions = mResources.getStringArray(R.array.question_stage_1);
                mQuestion.setText(mQuestions[mCount]);
                setAnswerChoice(mCount, stage);
                break;
            case HeroesActivity.STAGE_2:
                mQuestions = mResources.getStringArray(R.array.question_stage_2);
                mQuestion.setText(mQuestions[mCount]);
                setAnswerChoice(mCount, stage);
                break;
            case HeroesActivity.STAGE_3:
                mQuestions = mResources.getStringArray(R.array.question_stage_3);
                mQuestion.setText(mQuestions[mCount]);
                setAnswerChoice(mCount, stage);
                break;
            case HeroesActivity.STAGE_4:
                mQuestions = mResources.getStringArray(R.array.question_stage_4);
                mQuestion.setText(mQuestions[mCount]);
                setAnswerChoice(mCount, stage);
                break;
        }
    }

    private void setAnswerChoice(int numQuestion, String stage){
        String [] answers;
        if (stage.equals(HeroesActivity.STAGE_1)){
            switch (numQuestion){
                case 0:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_1);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 1:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_2);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 2:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_3);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 3:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_4);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 4:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_5);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 5:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_6);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 6:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_7);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 7:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_8);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 8:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_9);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 9:
                    answers = mResources.getStringArray(R.array.answer_stage1_question_10);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
            }
        }
        else if (stage.equals(HeroesActivity.STAGE_2)){
            switch (numQuestion){
                case 0:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_1);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 1:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_2);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 2:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_3);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 3:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_4);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 4:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_5);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 5:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_6);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 6:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_7);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 7:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_8);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 8:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_9);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 9:
                    answers = mResources.getStringArray(R.array.answer_stage2_question_10);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
            }
        }
        else if (stage.equals(HeroesActivity.STAGE_3)){
            switch (numQuestion){
                case 0:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_1);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 1:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_2);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 2:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_3);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 3:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_4);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 4:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_5);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 5:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_6);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 6:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_7);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 7:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_8);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 8:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_9);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 9:
                    answers = mResources.getStringArray(R.array.answer_stage3_question_10);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
            }
        }
        else if (stage.equals(HeroesActivity.STAGE_4)){
            switch (numQuestion){
                case 0:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_1);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 1:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_2);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 2:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_3);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 3:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_4);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 4:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_5);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 5:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_6);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 6:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_7);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 7:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_8);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 8:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_9);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
                case 9:
                    answers = mResources.getStringArray(R.array.answer_stage4_question_10);
                    setRadioOrCheckBox(numQuestion, answers);
                    break;
            }
        }
    }

    private void setRadioOrCheckBox (int numQuestion, String[] answers){
        if (numQuestion < 5 || numQuestion > 7) {
            mCheckBoxGroup.setVisibility(View.INVISIBLE);
            mRadioGroup.setVisibility(View.VISIBLE);
            for (int i = 0; i < mRadioButtons.length; i++){
                mRadioButtons[i].setText(answers[i]);
                mRadioButtons[i].setChecked(false);
            }
            mAnswer = answers[4];
        }
        else {
            mRadioGroup.setVisibility(View.INVISIBLE);
            mCheckBoxGroup.setVisibility(View.VISIBLE);
            for (int i = 0; i < mCheckBoxes.length; i++){
                mCheckBoxes[i].setText(answers[i]);
                mCheckBoxes[i].setChecked(false);
            }
            mAnswer = answers[4];
        }
    }

    private void checkHealth (int health){
        switch (health){
            case 0:
                mImageHP1.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                mImageHP2.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                mImageHP3.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                Intent intent = new Intent(QuestionActivity.this, EndGameActivity.class);
                intent.putExtra(SCORE, mScore);
                intent.putExtra(CONDITION, GAME_LOSE);
                startActivity(intent);
                break;
            case 1:
                mImageHP2.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                mImageHP3.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                break;
            case 2:
                mImageHP3.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                break;
            case 3:
                break;
        }
    }
}
