package com.hfad.barthardingstack;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    private TextView mTextView;
    private TextView mCorrectTextView;
    private TextView mWrongTextView;
    private Button shutDownButton;

    public CustomDialog(@NonNull Context context, CustomDialogData contents) {
        super(context);
        setContentView(R.layout.custom_dialog);

        mTextView = findViewById(R.id.percentage_result);
        mTextView.setText(getPercentStr(contents.getCorrectCnt()));

        shutDownButton = findViewById(R.id.btn_shutdown);
        shutDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mCorrectTextView = findViewById(R.id.correct_dialog_text);
        mWrongTextView = findViewById(R.id.wrong_dialog_text);
        mCorrectTextView.setText(String.valueOf(contents.getCorrectCnt()));
        mWrongTextView.setText(String.valueOf(contents.getWrongCnt()));
    }

    private String getPercentStr(int correct) {
        StringBuilder builder = new StringBuilder();
        String res = String.format("%.1f", (float)(correct * 100 / 52));
        builder.append(res);
        builder.append(" %");
        return builder.toString();
    }
}
