package com.hfad.barthardingstack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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
    TextView mTextView;
    TextView mAnswerView;
    TextView mAnswerCountView;
    TextView mWrongCountView;
    boolean isVisible;
    ArrayList<CardData> mArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.next_button);
        mPracticeAccanButton = (Button) findViewById(R.id.practice_acaan_button);
        mStartButton = (Button) findViewById(R.id.start_button);
        mTextView = (TextView) findViewById(R.id.count_text_view);
        mAnswerView = (TextView) findViewById(R.id.answer_view);
        mAnswerCountView = (TextView) findViewById(R.id.answer_count_view);
        mWrongCountView = (TextView) findViewById(R.id.wrong_count_view);
        mButton.setOnClickListener(this);
        mPracticeAccanButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
        mTextView.setText(String.valueOf(deckSize));
        mArr = new ArrayList<CardData>();
        buildData();
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
            Toast.makeText(this, "Restart!", Toast.LENGTH_SHORT).show();
            deckSize = 52;
            wrongCount = 0;
            answerCount = 0;
            buildData();
        }
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        int imageId = (int) (Math.random() * deckSize);

//        if (imageId == 0) {
//         //deck swapttr
//         //deckSize--
//         //get ImageId
//            swapCard(imageId);
//            imageId = (int) (Math.random() * deckSize);
//        }

        imageView.setBackgroundResource(mArr.get(imageId).imgID);
        mAnswerView.setText(String.valueOf(mArr.get(imageId).index + 1));

        swapCard(imageId);
        //mTextView.setText(String.valueOf(deckSize));

    }

    private void swapCard(int imageId) {
        if (deckSize > 0) {
            //images[imageId] = images[--deckSize];
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
                break;
            case R.id.next_button:
                checkAnswer();
                showImage2();
                initInputTextView();
                setAnswerCountView();
                break;
            case R.id.practice_acaan_button:
                //randomCount();
                Intent intent = new Intent(this, PracticeAcaanActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void checkAnswer() {
        String userInput = ((EditText) findViewById(R.id.input_answer_view)).getText().toString();
        String answer = mAnswerView.getText().toString();

//        if (userInput.equals("")) {
//            Log.i("TEST123", "-> userInput is Null, answer : " + answer);
//        } else {
//            Log.i("TEST123", "-> " + userInput + " answer : " + answer);
//        }

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

    private void randomCount() {
        int[] nResult = new int[10];
        int[] list = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int idx;
        int nRand = 10;
        for (int i = 0; i < 10; i++) {
            idx = (int)(Math.random() * nRand);
//            nResult[i] = list[idx];
            Log.i("TEST123", " : " + list[idx]);
            list[idx] = list[--nRand];
        }

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

}