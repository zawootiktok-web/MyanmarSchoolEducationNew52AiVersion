package com.mmschooledu.MyanmarSpelling.fragment;
import android.view.View;


public class BackgroundManager {

    private int[] backgrounds;
    private int currentBackgroundIndex = 0;
    private View mainView;

    public BackgroundManager(View mainView, int[] backgrounds) {
        this.mainView = mainView;
        this.backgrounds = backgrounds;
    }

    public void updateBackground() {
        currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.length;
        mainView.setBackgroundResource(backgrounds[currentBackgroundIndex]);
    }

    public void resetBackground() {
        currentBackgroundIndex = 0; // รีเซ็ตไปที่ bg_night เสมอ
        mainView.setBackgroundResource(backgrounds[currentBackgroundIndex]);
    }
}

