package com.hfad.barthardingstack;

public class CustomDialogData {
    private int correctCnt;
    private int wrongCnt;

    public CustomDialogData(int correct, int wrong) {
        correctCnt = correct;
        wrongCnt = wrong;
    }

    public int getCorrectCnt() {
        return correctCnt;
    }

    public int getWrongCnt() {
        return wrongCnt;
    }
}
