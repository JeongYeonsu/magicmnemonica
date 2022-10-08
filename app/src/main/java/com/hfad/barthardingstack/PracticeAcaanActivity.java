package com.hfad.barthardingstack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PracticeAcaanActivity extends AppCompatActivity implements View.OnClickListener{
    int deckSize = 52;

    ActionBar mActionBar;

    ImageView mAccanImageView;

    TextView mAcaanNumberView;

    Button mAcaanNextButton;

    ArrayList<CardData> mArr;

    int[] mAcaanImages = new int[] {
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

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.practice_acaan_view);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle("ACAAN");

        mAccanImageView = findViewById(R.id.accan_image_view);
        mAcaanNumberView = findViewById(R.id.accan_number_text);
        mAcaanNextButton = findViewById(R.id.next_practice_button);
        mAcaanNextButton.setOnClickListener(this);

        mArr = new ArrayList<CardData>();

        buildData();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_practice_button:
                showImage();
                break;
        }
    }


    private void buildData() {
        mArr.clear();
        for (int i = 0; i < deckSize; i++) {
            CardData cardData = new CardData(mAcaanImages[i], i);
            mArr.add(cardData);
        }
    }

    private void showImage() {
        mAcaanNumberView.setText("");

        if (deckSize == 0) {
            Toast.makeText(this, "Restart!", Toast.LENGTH_SHORT).show();
            deckSize = 52;
            buildData();
        }

        ImageView imageView = (ImageView) findViewById(R.id.accan_image_view);
        int imageId = (int) (Math.random() * deckSize);
        imageView.setImageResource(mArr.get(imageId).imgID);
        swapCard(imageId);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setAcaanNumber();
            }
        }, 3000);
    }

    private void setAcaanNumber() {
        int number = (int) (Math.random() * 52);
        mAcaanNumberView.setText(Integer.toString(number));
    }

    private void swapCard(int imageId) {
        if (deckSize > 0) {
            //images[imageId] = images[--deckSize];
            CardData cardData = mArr.get(--deckSize);
            mArr.set(imageId, cardData);
        }
    }
}
