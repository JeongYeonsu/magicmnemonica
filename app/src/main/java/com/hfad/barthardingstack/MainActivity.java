package com.hfad.barthardingstack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    boolean AdOn = true;

    int deckSize = 52;
    int answerCount = 0;
    int wrongCount = 0;
    long backKeyPressedTime = 0;

    int[] images = new int[] {
            R.drawable.c4,  //1
            R.drawable.h2,
            R.drawable.d7,
            R.drawable.c3,
            R.drawable.h4,  //5
            R.drawable.d6,
            R.drawable.s1,
            R.drawable.h5,
            R.drawable.s9,
            R.drawable.s2,  //10
            R.drawable.h12,
            R.drawable.d3,
            R.drawable.c12,
            R.drawable.h8,
            R.drawable.s6,  //15
            R.drawable.s5,
            R.drawable.h9,
            R.drawable.c13,
            R.drawable.d2,
            R.drawable.h11,  //20
            R.drawable.s3,
            R.drawable.s8,
            R.drawable.h6,
            R.drawable.c10,
            R.drawable.d5,  //25
            R.drawable.d13,
            R.drawable.c2,
            R.drawable.h3,
            R.drawable.d8,
            R.drawable.c5,  //30
            R.drawable.s13,
            R.drawable.d11,
            R.drawable.c8,
            R.drawable.s10,
            R.drawable.h13,  //35
            R.drawable.c11,
            R.drawable.s7,
            R.drawable.h10,
            R.drawable.d1,
            R.drawable.s4,  //40
            R.drawable.h7,
            R.drawable.d4,
            R.drawable.c1,
            R.drawable.c9,
            R.drawable.s11, // 45
            R.drawable.d12,
            R.drawable.c7,
            R.drawable.s12,
            R.drawable.d10,
            R.drawable.c6, //50
            R.drawable.h1,
            R.drawable.d9  //52
    };

    Button mButton;
    Button mPracticeAccanButton;
    Button mStartButton;
    Button mShowAnswerButton;
    Button mTestButton;
    TextView mTextView;
    TextView mAnswerView;
    TextView mAnswerCountView;
    TextView mWrongCountView;

    ArrayList<CardData> mArr;
    private CustomDialog customDialog;

    AdCallback adCallback = new AdCallback() {
        @Override
        public void adDone(boolean isGoAcaan) {
            if (AdOn && isGoAcaan) {
                callAcaanActivity();
            }
        }
    };

    CustomDialogCallback customDialogCallback = new CustomDialogCallback() {
        @Override
        public void dialogDone() {
            if (AdOn) {
                adHelper.showFullAd(false);
            }
        }
    };

    AdHelper adHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.next_button);
        mPracticeAccanButton = (Button) findViewById(R.id.practice_acaan_button);
        mStartButton = (Button) findViewById(R.id.start_button);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mTestButton = (Button) findViewById(R.id.test_button);
        mTextView = (TextView) findViewById(R.id.count_text_view);
        mAnswerView = (TextView) findViewById(R.id.answer_view);
        mAnswerCountView = (TextView) findViewById(R.id.answer_count_view);
        mWrongCountView = (TextView) findViewById(R.id.wrong_count_view);
        mButton.setOnClickListener(this);
        mPracticeAccanButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
        mShowAnswerButton.setOnClickListener(this);
        mTestButton.setOnClickListener(this);
        mTextView.setText(String.valueOf(deckSize));
        mArr = new ArrayList<CardData>();
        buildData();
        setDimmingLayoutShowingDialog();

        if (AdOn) {
            adHelper = new AdHelper(this, adCallback);
        }
    }

    private void buildData() {
        mArr.clear();
        for (int i = 0; i < deckSize; i++) {
            CardData cardData = new CardData(images[i], i);
            mArr.add(cardData);
        }
    }

    private void showImage() {
        if (deckSize == 0) {
            Toast.makeText(this, "Restart!", Toast.LENGTH_SHORT).show();
            deckSize = 52;
        }
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        int imageId = (int) (Math.random() * deckSize);
        imageView.setBackgroundResource(images[imageId]);
        mAnswerView.setText(String.valueOf(imageId));

        if (deckSize > 0) {
            images[imageId] = images[--deckSize];
        }
        mTextView.setText(String.valueOf(deckSize));
    }


    private void showImage2() {
        if (deckSize == 0) {
            showCustomPopup();
            deckSize = 52;
            wrongCount = 0;
            answerCount = 0;
            buildData();
            mAnswerView.setVisibility(View.GONE);
            mShowAnswerButton.setVisibility(View.VISIBLE);
        }
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int imageId = (int) (Math.random() * deckSize);

        imageView.setImageResource(mArr.get(imageId).imgID);
        mAnswerView.setText(String.valueOf(mArr.get(imageId).index + 1));

        swapCard(imageId);
    }

    private void swapCard(int imageId) {
        if (deckSize > 0) {
            CardData cardData = mArr.get(--deckSize);
            mArr.set(imageId, cardData);
        }
    }

    private void setStartButtonGone() {
        mStartButton.setVisibility(View.GONE);

        mButton.setEnabled(true);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                setStartButtonGone();
                showImage2();
                setFocusTextview();
                break;
            case R.id.next_button:
                checkAnswer();
                showImage2();
                initInputTextView();
                setAnswerCountView();
                break;
            case R.id.practice_acaan_button:
                if (AdOn) {
                    adHelper.showFullAd(true);
                } else {
                    callAcaanActivity();
                }
                break;
            case R.id.show_answer_button:
                if (AdOn) {
                    adHelper.showFullAd(false);
                }
                clickShowAnswerButton();
                break;
            case R.id.test_button:
                clickTestButton();
                break;
        }
    }

    private void setFocusTextview() {
        EditText editText = (EditText) findViewById(R.id.input_answer_view);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (editText != null) {
            editText.requestFocus();
        }
        imm.showSoftInput(editText, 0);
    }

    private void clickShowAnswerButton() {
        mAnswerView.setVisibility(View.VISIBLE);
        mShowAnswerButton.setVisibility(View.GONE);
    }

    private void clickTestButton() {
        deckSize = 0;
        answerCount = 50;
        wrongCount = 1;
        mAnswerCountView.setText(Integer.toString(answerCount));
        mWrongCountView.setText(Integer.toString(wrongCount));

        if (AdOn) {
            adHelper.showFullAd(false);
        }
    }

    private void checkAnswer() {
        String userInput = ((EditText) findViewById(R.id.input_answer_view)).getText().toString();
        String answer = mAnswerView.getText().toString();

        if (answer.equals(userInput)) {
            //Toast.makeText(this, "CORRECT !", Toast.LENGTH_SHORT).show();
            answerCount++;
        } else {
            wrongCount++;
            Toast.makeText(this, "ANSWER IS " + answer, Toast.LENGTH_SHORT).show();
        }
    }

    private void initInputTextView() {
        ((EditText) findViewById(R.id.input_answer_view)).setText("");
    }

    private void setAnswerCountView() {
        mAnswerCountView.setText(Integer.toString(answerCount));
        mWrongCountView.setText(Integer.toString(wrongCount));
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "BackKey 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }

    private void showCustomPopup() {
        CustomDialogData data = new CustomDialogData(answerCount, wrongCount);
        customDialog = new CustomDialog(this, data, customDialogCallback, AdOn);
        customDialog.show();
    }

    private void setDimmingLayoutShowingDialog() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
    }

    private void callAcaanActivity() {
        Intent intent = new Intent(this, PracticeAcaanActivity.class);
        startActivity(intent);
    }
}