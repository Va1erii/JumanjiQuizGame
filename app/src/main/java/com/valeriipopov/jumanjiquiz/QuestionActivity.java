package com.valeriipopov.jumanjiquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * QuestionActivity is our important activity where we show questions.
 * Health = 3, if health = 0 Game Over.
 * InitialScore is our score before we start the current level, if we
 * quit from stage before we win or lose, we return InitialScore
 */

public class QuestionActivity extends AppCompatActivity {
    public static final String SCORE = "score";
    public static final String CONDITION = "condition";
    public static final String GAME_WIN = "win";
    public static final String GAME_LOSE = "lose";
    public static final String COUNT = "count";
    public static final String HEALTH = "health";
    public static final String INITIAL_SCORE = "initialScore";

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
    private View mCheckBoxGroup;
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
    private int mHealth;
    private int mInitialScore;
    private int mScore;
    private String[] mQuestions;
    private String mAnswer;
    private String mStage;
    private boolean mIsLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mHandler = new Handler(getMainLooper());
        mResources = getResources();
        int orientation = mResources.getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            mIsLandscape = true;
            // to check orientation
        }
        mImageHP1 = findViewById(R.id.hp_1);
        mImageHP2 = findViewById(R.id.hp_2);
        mImageHP3 = findViewById(R.id.hp_3);
        mQuestionScore = findViewById(R.id.question_score);

        setRadioCheckBoxGroup();
        mButtonNext = findViewById(R.id.btn_next);
        mQuestionCount = findViewById(R.id.countQuestion);

        mQuestion = findViewById(R.id.question);

        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt(COUNT);
            mHealth = savedInstanceState.getInt(HEALTH);
            mInitialScore = savedInstanceState.getInt(INITIAL_SCORE);
            mScore = savedInstanceState.getInt(SCORE);
            mStage = savedInstanceState.getString(MainActivity.STAGE);
            // if we change orientation
        } else {
            mCount = 0;
            mHealth = 3;
            mInitialScore = getIntent().getIntExtra(SCORE, 0);
            mScore = mInitialScore;
            mStage = getIntent().getStringExtra(MainActivity.STAGE);
        }
        mQuestionScore.setText(String.valueOf(mScore));
        mQuestionCount.setText("Question " + (mCount+1) + "/10");
        setQuestions(mStage);
        setButtonNextListenner();
        checkHealth(mHealth);
    }

    /**
     * This method find our RadioButtons and CheckBoxes. We put our buttons into arrays
     */
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
        if (mIsLandscape){
            mCheckBoxGroup = (RelativeLayout) findViewById(R.id.checkboxGroup);
            setRadioClickListener();
            // In landscape I used RelativeLayout
        } else {
            mCheckBoxGroup = (LinearLayout) findViewById(R.id.checkboxGroup);
        }
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

    /**
     * This method setClickListener in mButtonNext. We check the current question, because
     * question 6 until question 8 used checkBoxes. If our answer is wrong we decrease health.
     * If answer is correct we increase score. Handler runs EndGameActivity with current score, stage and condition.
     */
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
                    // RadioButtons
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
                    // CheckBoxes
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
                    // RadioButtons
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
                                        showToastScore();
                                        Intent intent = new Intent(QuestionActivity.this, EndGameActivity.class);
                                        intent.putExtra(SCORE, mScore);
                                        intent.putExtra(CONDITION, GAME_WIN);
                                        intent.putExtra(MainActivity.STAGE, mStage);
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
                                    showToastScore();
                                    Intent intent = new Intent(QuestionActivity.this, EndGameActivity.class);
                                    intent.putExtra(SCORE, mScore);
                                    intent.putExtra(CONDITION, GAME_WIN);
                                    intent.putExtra(MainActivity.STAGE, mStage);
                                    startActivity(intent);
                                }
                            }, 500);
                        }
                    }
                }
            }
        });
    }

    /**
     * @param stage is our current level.
     * We fill Question's array for current stage. And we fill our answer's buttons
     */
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

    /**
     * @param numQuestion is current numder of question, we fill our answer's buttons
     * @param stage is current stage.
     */
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

    /**
     * @param numQuestion is current number of question, we used checkboxes for questions number 6, 7 and 8.
     * @param answers is array where we saved answer's choice. Our answer is in answer[4]
     */
    private void setRadioOrCheckBox (int numQuestion, String[] answers){
        if (numQuestion < 5 || numQuestion > 7) {
            mCheckBoxGroup.setVisibility(View.INVISIBLE);
            mRadioGroup.setVisibility(View.VISIBLE);
            for (int i = 0; i < mRadioButtons.length; i++){
                mRadioButtons[i].setText(answers[i]);
                mRadioButtons[i].setChecked(false);
            }
            mRadioGroup.clearCheck();
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

    private void showToastScore(){
        if (mHealth > 0){
            Toast.makeText(this, getString(R.string.toast_score, mScore), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.toast_game_over, mScore), Toast.LENGTH_LONG).show();
        }

    }

    /**
     * This method we use only in Landscape orientation. Because I didn't use RadioGroup in landscape mode
     * It cause we can choose multiple answers, but it incorrect
     */
    private void setRadioClickListener(){
        for (final RadioButton radio: mRadioButtons){
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (RadioButton radio: mRadioButtons){
                        radio.setChecked(false);
                    }
                    radio.setChecked(true);
                }
            });
        }
    }

    /**
     * @param health is current health, if health = 0 our score will be zero in the EndGameActivity
     */
    private void checkHealth (int health){
        switch (health){
            case 0:
                mImageHP1.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                mImageHP2.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                mImageHP3.setImageDrawable(mResources.getDrawable(R.drawable.ic_dead));
                showToastScore();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(QuestionActivity.this, EndGameActivity.class);
                        intent.putExtra(SCORE, mScore);
                        intent.putExtra(CONDITION, GAME_LOSE);
                        startActivity(intent);
                    }
                }, 500);
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

    /**
     * If we press back button we show AlertDialog
     */
    @Override
    public void onBackPressed() {
        AlertDialog mAlertDialog = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                .setCancelable(false)
                .setTitle(getResources().getText(R.string.app_title))
                .setMessage(getResources().getText(R.string.quit))
                .setPositiveButton(getResources().getText(R.string.positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                        intent.putExtra(SCORE, mInitialScore);
                        startActivity(intent);
                        QuestionActivity.this.finish();
                    }
                })
                .setNegativeButton(getResources().getText(R.string.negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(COUNT, mCount);
        outState.putInt(HEALTH, mHealth);
        outState.putInt(INITIAL_SCORE, mInitialScore);
        outState.putInt(SCORE, mScore);
        outState.putString(MainActivity.STAGE, mStage);
        super.onSaveInstanceState(outState);
        // Save our current count, score and stage when we change orientation
    }
}
