package com.mmschooledu.MyanmarSpelling.fragment;
import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.mmschooledu.R;


public class WitchAnimationManager {
    private final Handler handler = new Handler();
    private final ImageView witchImageView;
    private final Context context;

    private int witchState = 1;
    private boolean isLooping = false;
    private boolean isPaused = false;

    public WitchAnimationManager(Context context, ImageView witchImageView) {
        this.context = context;
        this.witchImageView = witchImageView;
    }

    // เริ่มลูปแม่มด
    public void startLoop() {
        if (isLooping) return; // ถ้ากำลังลูปอยู่แล้ว ให้ข้าม
        isLooping = true;
        handler.post(witchLoopRunnable);
    }

    // หยุดลูปแม่มด
    public void stopLoop() {
        isLooping = false;
        handler.removeCallbacks(witchLoopRunnable);
    }

    // ตั้งสถานะ Pause
    public void pauseLoop() {
        isPaused = true;

    }

    // ตั้งสถานะ Resume
    public void resumeLoop() {
        isPaused = false;
        if (isLooping) { // ถ้ายังอยู่ในสถานะลูป ให้เริ่มใหม่
            handler.post(witchLoopRunnable);
        }
    }

    // อนิเมชั่นตอบถูก/ผิด
    public void showFeedbackAnimation(boolean isCorrect, Runnable onComplete) {
        stopLoop();

        if (isCorrect) {
            String[] materials = {"bone", "leaf", "mushroom"};
            int randomIndex = (int) (Math.random() * materials.length);
            String selectedMaterial = materials[randomIndex];

            int resource01 = context.getResources().getIdentifier(selectedMaterial + "01", "drawable", context.getPackageName());
            int resource02 = context.getResources().getIdentifier(selectedMaterial + "02", "drawable", context.getPackageName());

            witchImageView.setImageResource(resource01);
            witchImageView.postDelayed(() -> witchImageView.setImageResource(resource02), 350);
            witchImageView.postDelayed(() -> witchImageView.setImageResource(R.drawable.right03), 500); // ภาพอนิเมชันจบ
            witchImageView.postDelayed(onComplete, 750); // เรียก callback หลังจบอนิเมชั่น
        } else {
            witchImageView.setImageResource(R.drawable.snake01);
            witchImageView.postDelayed(() -> witchImageView.setImageResource(R.drawable.snake02), 400);
            witchImageView.postDelayed(() -> witchImageView.setImageResource(R.drawable.wrong03), 800);
            witchImageView.postDelayed(() -> {
                witchImageView.setImageResource(R.drawable.wrong04);
                onComplete.run(); // เรียก callback เมื่ออนิเมชั่นผิดจบ
            }, 1500);
        }
    }

    // Runnable สำหรับลูปแม่มด
    private final Runnable witchLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (isLooping  && !isPaused) {
                witchState = (witchState % 3) + 1;
                updateWitchImage(witchState);
                handler.postDelayed(this, 300);
            }
        }
    };

    private void updateWitchImage(int state) {
        int resource;
        switch (state) {
            case 1:
                resource = R.drawable.witch01;
                break;
            case 2:
                resource = R.drawable.witch02;
                break;
            case 3:
                resource = R.drawable.witch03;
                break;
            default:
                resource = R.drawable.witch01;
        }
        witchImageView.setImageResource(resource);
    }
}




