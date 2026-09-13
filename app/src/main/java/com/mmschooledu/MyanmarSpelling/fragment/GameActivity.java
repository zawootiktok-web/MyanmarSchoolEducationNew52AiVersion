package com.mmschooledu.MyanmarSpelling.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.mmschooledu.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class GameActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private List<Integer> usedIds = new ArrayList<>();
    private TextView vocabTextView;
    private Button button1, button2;
    private ImageButton pauseButton;
    private AlertDialog pauseDialog, gameOverDialog;
    private ProgressBar timerProgressBar;

    private CountDownTimer countDownTimer;
    private static final int TIMER_DURATION = 7000; // 7 seconds
    private static final int TIMER_INTERVAL = 50;

    private long remainingTime = TIMER_DURATION;
    private int currentScore = 0;
    private int bestScore = 0;
    private boolean isPaused = false;
    private boolean isExiting = false;

    private WitchAnimationManager witchAnimationManager;
    private BackgroundManager backgroundManager;

    private boolean isFirstWord = true;
    private boolean isGameOverDialogShowing = false;
    private boolean isPauseDialogShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences preferences = getSharedPreferences("WordWizPrefs", MODE_PRIVATE);
        bestScore = preferences.getInt("best_score_basic", 0);

        dbHelper = new DatabaseHelper(this);

        vocabTextView = findViewById(R.id.vocab);
        button1 = findViewById(R.id.vocabbtn1);
        button2 = findViewById(R.id.vocabbtn2);
        pauseButton = findViewById(R.id.pausebtn);
        timerProgressBar = findViewById(R.id.timerProgressBar);
        timerProgressBar.setMax(TIMER_DURATION);
        timerProgressBar.setProgress(TIMER_DURATION);

        ImageView witchImageView = findViewById(R.id.witchImageView);
        witchAnimationManager = new WitchAnimationManager(this, witchImageView);

        View mainView = findViewById(R.id.main);
        backgroundManager = new BackgroundManager(mainView, new int[]{
                R.drawable.bg_night,
                R.drawable.bg_morning,
                R.drawable.bg_evening
        });

        resetGameState();
        showNextWord();

        pauseButton.setOnClickListener(v -> togglePauseDialog());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                togglePauseDialog();
            }
        });
    }

    private void togglePauseDialog() {
        if (pauseDialog != null && pauseDialog.isShowing()) {
            pauseDialog.dismiss();
            isPaused = false;
            witchAnimationManager.resumeLoop();
            startCountdownTimer(remainingTime);
        } else {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            witchAnimationManager.pauseLoop();
            showPauseDialog();
        }
    }

    private void showPauseDialog() {
        if (isPauseDialogShowing || isGameOverDialogShowing || isExiting) return;
        isPauseDialogShowing = true;

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_pause, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this, R.style.TransparentDialog);
        builder.setView(dialogView);

        pauseDialog = builder.create();

        ImageButton resumeButton = dialogView.findViewById(R.id.resumebtn);
        ImageButton exitButton = dialogView.findViewById(R.id.exitbtn);

        resumeButton.setOnClickListener(v -> {
            isPauseDialogShowing = false;
            pauseDialog.dismiss();
            isPaused = false;
            startCountdownTimer(remainingTime);
            witchAnimationManager.resumeLoop();
        });

        exitButton.setOnClickListener(v -> {
            isPauseDialogShowing = false;
            isExiting = true;
            pauseDialog.dismiss();
            finish();
        });

        pauseDialog.show();
        pauseDialog.setCancelable(false);
        isPaused = true;
    }



    private void resetGameState() {
        currentScore = 0;
        usedIds.clear();
        isFirstWord = true;
        backgroundManager.resetBackground();
        remainingTime = TIMER_DURATION;
        timerProgressBar.setProgress(TIMER_DURATION);
    }

    private void startCountdownTimer(long timeToRun) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(timeToRun, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                timerProgressBar.setProgress((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerProgressBar.setProgress(0);
                endGame();
            }
        };
        countDownTimer.start();
    }

    private void resetCountdownTimer() {
        remainingTime = TIMER_DURATION;
        timerProgressBar.setProgress(TIMER_DURATION);
        startCountdownTimer(remainingTime);
    }

    private void showNextWord() {
        if (!isFirstWord) {
            backgroundManager.updateBackground();
        } else {
            isFirstWord = false;
        }

        resetCountdownTimer();
        witchAnimationManager.startLoop();

        button1.setEnabled(true);
        button2.setEnabled(true);

        String tableName = "Vocab1";
        List<String[]> wordList = dbHelper.getRandomWords(tableName, usedIds);

        if (wordList.isEmpty()) {
            endGame();
            return;
        }

        Collections.shuffle(wordList);
        String[] word = wordList.get(0);
        int wordId = Integer.parseInt(word[0]);

        usedIds.add(wordId);
        String englishWord = word[1];
        String correctTranslation = word[2];
        String incorrectTranslation = word[3];

        vocabTextView.setText(englishWord);

        List<String> options = new ArrayList<>();
        options.add(correctTranslation);
        options.add(incorrectTranslation);
        Collections.shuffle(options);

        button1.setText(options.get(0));
        button2.setText(options.get(1));

        button1.setOnClickListener(v -> checkAnswer(options.get(0), correctTranslation));
        button2.setOnClickListener(v -> checkAnswer(options.get(1), correctTranslation));
    }

    private void checkAnswer(String selectedAnswer, String correctAnswer) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        button1.setEnabled(false);
        button2.setEnabled(false);

        if (selectedAnswer.equals(correctAnswer)) {
            currentScore++;
            witchAnimationManager.showFeedbackAnimation(true, this::showNextWord);
        } else {
            witchAnimationManager.showFeedbackAnimation(false, this::endGame);
        }
    }

    private void endGame() {
        witchAnimationManager.stopLoop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        SharedPreferences.Editor editor = getSharedPreferences("WordWizPrefs", MODE_PRIVATE).edit();
        if (currentScore > bestScore) {
            bestScore = currentScore;
            editor.putInt("best_score_basic", bestScore);
        }
        editor.apply();

        showGameOverDialog();
    }

    private void showGameOverDialog() {
        if (isGameOverDialogShowing) return;
        isGameOverDialogShowing = true;

        if (pauseDialog != null && pauseDialog.isShowing()) {
            pauseDialog.dismiss();
        }

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_game, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.TransparentDialog);
        builder.setView(dialogView);

        TextView scoreView = dialogView.findViewById(R.id.gamescore);
        TextView bestScoreView = dialogView.findViewById(R.id.best_score);
        ImageButton restartButton = dialogView.findViewById(R.id.restartbtn);
        ImageButton homeButton = dialogView.findViewById(R.id.homebtn);

        scoreView.setText(String.valueOf(currentScore));
        bestScoreView.setText(String.valueOf(bestScore));

        restartButton.setOnClickListener(v -> restartGame());
        homeButton.setOnClickListener(v -> returnToHome());

        gameOverDialog = builder.create();
        gameOverDialog.setCancelable(false);
        gameOverDialog.show();
    }

    private void restartGame() {
        if (gameOverDialog != null && gameOverDialog.isShowing()) {
            gameOverDialog.dismiss();
        }
        resetGameState();
        showNextWord();
    }

    private void returnToHome() {
        if (gameOverDialog != null && gameOverDialog.isShowing()) {
            gameOverDialog.dismiss();
        }
        finish();
    }
}



//package mm.pndaza.thupyadictionary.fragment;
//
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.activity.OnBackPressedCallback;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import mm.pndaza.thupyadictionary.R;
//
//public class GameActivity extends AppCompatActivity {
//
//    private DatabaseHelper dbHelper;
//    private List<Integer> usedIds = new ArrayList<>();
//    private TextView vocabTextView;
//    private Button button1, button2;
//    private ImageButton pauseButton;
//    private AlertDialog pauseDialog;
//    private ProgressBar timerProgressBar;
//
//    private CountDownTimer countDownTimer;
//    private static final int TIMER_DURATION = 70000; // 7 seconds
//    private static final int TIMER_INTERVAL = 50;   // Update every 50ms
//
//    private long remainingTime = TIMER_DURATION; // เวลาที่เหลือเริ่มต้น
//    private int currentScore = 0;
//    private int basicBestScore = 0;
//    private boolean isPaused = false;
//    private boolean isExiting = false;
//
//    private WitchAnimationManager witchAnimationManager; // จัดการอนิเมชั่นแม่มด
//    private BackgroundManager backgroundManager;         // จัดการพื้นหลัง
//
//    private boolean isFirstWord = true; // ตัวแปรควบคุมพื้นหลังครั้งแรก
//    private boolean isGameOverDialogShowing = false;
//    private boolean isPauseDialogShowing = false;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_game);
//
//        // โหลดคะแนนสูงสุดสำหรับ basic mode
//        SharedPreferences preferences = getSharedPreferences("WordWizPrefs", MODE_PRIVATE);
//        basicBestScore = preferences.getInt("best_score_basic", 0);
//
//        // เชื่อมฐานข้อมูล
//        dbHelper = new DatabaseHelper(this);
//
//        // เชื่อม UI
//        vocabTextView = findViewById(R.id.vocab);
//        button1 = findViewById(R.id.vocabbtn1);
//        button2 = findViewById(R.id.vocabbtn2);
//        pauseButton = findViewById(R.id.pausebtn);
//        timerProgressBar = findViewById(R.id.timerProgressBar);
//        timerProgressBar.setMax(TIMER_DURATION);
//        timerProgressBar.setProgress(TIMER_DURATION);
//
//        // ตั้งค่าผู้จัดการแม่มดและพื้นหลัง
//        ImageView witchImageView = findViewById(R.id.witchImageView);
//        witchAnimationManager = new WitchAnimationManager(this, witchImageView);
//
//        View mainView = findViewById(R.id.main);
//        backgroundManager = new BackgroundManager(mainView, new int[]{
//                R.drawable.bg_night,   // กลางคืน
//                R.drawable.bg_morning, // ตอนเช้า
//                R.drawable.bg_evening  // ตอนเย็น
//        });
//
//        // เริ่มต้นสถานะเกม
//        resetGameState();
//        showNextWord();
//
//        pauseButton.setOnClickListener(v -> togglePauseDialog());
//
//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                togglePauseDialog();
//            }
//        });
//    }
//
//    private void resetGameState() {
//        currentScore = 0;
//        usedIds.clear();
//        isFirstWord = true; // รีเซ็ตสถานะคำแรก
//        backgroundManager.resetBackground(); // รีเซ็ตพื้นหลังเป็น bg_night
//        remainingTime = TIMER_DURATION;
//        timerProgressBar.setProgress(TIMER_DURATION);
//    }
//
//    private void startCountdownTimer(long timeToRun) {
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//
//        countDownTimer = new CountDownTimer(timeToRun, TIMER_INTERVAL) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                remainingTime = millisUntilFinished;
//                timerProgressBar.setProgress((int) millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                timerProgressBar.setProgress(0);
//                endGame();
//            }
//        };
//        countDownTimer.start();
//    }
//
//    private void resetCountdownTimer() {
//        remainingTime = TIMER_DURATION;
//        timerProgressBar.setProgress(TIMER_DURATION);
//        startCountdownTimer(remainingTime);
//    }
//
//    private void showNextWord() {
//        if (!isFirstWord) {
//            backgroundManager.updateBackground(); // เปลี่ยนพื้นหลังเมื่อไม่ใช่ครั้งแรก
//        } else {
//            isFirstWord = false;
//        }
//
//        resetCountdownTimer();
//        witchAnimationManager.startLoop();
//
//        // เปิดใช้งานปุ่มใหม่สำหรับคำถามถัดไป
//        button1.setEnabled(true);
//        button2.setEnabled(true);
//
//        String tableName = "Vocab1";
//        List<String[]> wordList = dbHelper.getRandomWords(tableName, usedIds);
//
//        if (wordList.isEmpty()) {
//            endGame();
//            return;
//        }
//
//        Collections.shuffle(wordList);
//        String[] word = wordList.get(0);
//        int wordId = Integer.parseInt(word[0]);
//
//        usedIds.add(wordId);
//        String englishWord = word[1];
//        String correctTranslation = word[2];
//        String incorrectTranslation = word[3];
//
//        vocabTextView.setText(englishWord);
//
//        List<String> options = new ArrayList<>();
//        options.add(correctTranslation);
//        options.add(incorrectTranslation);
//        Collections.shuffle(options);
//
//        button1.setText(options.get(0));
//        button2.setText(options.get(1));
//
//        button1.setOnClickListener(v -> checkAnswer(options.get(0), correctTranslation));
//        button2.setOnClickListener(v -> checkAnswer(options.get(1), correctTranslation));
//    }
//
//    private void checkAnswer(String selectedAnswer, String correctAnswer) {
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//
//        // ปิดการใช้งานปุ่มหลังจากการตอบ
//        button1.setEnabled(false);
//        button2.setEnabled(false);
//
//
//        if (selectedAnswer.equals(correctAnswer)) {
//            currentScore++;
//            witchAnimationManager.showFeedbackAnimation(true, this::showNextWord);
//        } else {
//            witchAnimationManager.showFeedbackAnimation(false, this::endGame);
//        }
//    }
//
//    private void endGame() {
//        witchAnimationManager.stopLoop();
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//
//        // บันทึกคะแนนสูงสุดของ
//        SharedPreferences.Editor editor = getSharedPreferences("WordWizPrefs", MODE_PRIVATE).edit();
//        if (currentScore > basicBestScore) {
//            basicBestScore = currentScore;
//            editor.putInt("best_score_basic", basicBestScore);
//        }
//        editor.apply();
//
//        showGameOverDialog();
//    }
//
//    private void showGameOverDialog() {
//        if (isGameOverDialogShowing) return;
//        isGameOverDialogShowing = true;
//
//        if (pauseDialog != null && pauseDialog.isShowing()) {
//            pauseDialog.dismiss();
//        }
//
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_gameover, null);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.TransparentDialog);
//        builder.setView(dialogView);
//
//        TextView scoreView = dialogView.findViewById(R.id.score);
//        TextView bestScoreView = dialogView.findViewById(R.id.best_score);
//        ImageButton restartButton = dialogView.findViewById(R.id.restartbtn);
//        ImageButton homeButton = dialogView.findViewById(R.id.homebtn);
//
//        scoreView.setText(String.valueOf(currentScore));
//        bestScoreView.setText(String.valueOf(basicBestScore));
//
//        restartButton.setOnClickListener(v -> {
//            resetGameState();
//            Intent restartIntent = new Intent(GameActivity.this, GameActivity.class);
//            restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(restartIntent);
//            finish();
//        });
//
//        homeButton.setOnClickListener(v -> {
//            Intent homeIntent = new Intent(GameActivity.this, QuizFragment.class);
//            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(homeIntent);
//            finish();
//        });
//
//        AlertDialog gameOverDialog = builder.create();
//        gameOverDialog.setCancelable(false);
//        gameOverDialog.show();
//
//    }
//
//    private void togglePauseDialog() {
//        if (pauseDialog != null && pauseDialog.isShowing()) {
//            pauseDialog.dismiss();
//            isPaused = false;
//            witchAnimationManager.resumeLoop();
//            startCountdownTimer(remainingTime);
//
//        } else {
//            if (countDownTimer != null) {
//                countDownTimer.cancel();
//            }
//            witchAnimationManager.pauseLoop();
//            showPauseDialog();
//        }
//    }
//
//    private void showPauseDialog() {
//        if (isPauseDialogShowing || isGameOverDialogShowing || isExiting) return; // ห้ามแสดงซ้ำ
//        isPauseDialogShowing = true;
//
//        if (pauseDialog != null && pauseDialog.isShowing()) return; // ตรวจสอบ Dialog เดิม
//
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_pause, null);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this, R.style.TransparentDialog);
//        builder.setView(dialogView);
//
//        pauseDialog = builder.create();
//
//        ImageButton soundButton = dialogView.findViewById(R.id.soundbtn);
//        ImageButton resumeButton = dialogView.findViewById(R.id.resumebtn);
//        ImageButton exitButton = dialogView.findViewById(R.id.exitbtn);
//
//        updateSoundButtonIcon(soundButton);
//
//        soundButton.setOnClickListener(v -> {
//            SoundManager.toggleSound();
//            updateSoundButtonIcon(soundButton);
//            if (SoundManager.isSoundOn()) {
//                SoundManager.resumeBackgroundMusic();
//            } else {
//                SoundManager.pauseBackgroundMusic();
//            }
//        });
//
//        resumeButton.setOnClickListener(v -> {
//            isPauseDialogShowing = false;
//            pauseDialog.dismiss();
//            pauseDialog = null; // เคลียร์ Dialog
//            isPaused = false;
//            startCountdownTimer(remainingTime);
//            witchAnimationManager.resumeLoop();
//        });
//
//        exitButton.setOnClickListener(v -> {
//            isPauseDialogShowing = false;
//            isExiting = true;
//            pauseDialog.dismiss();
//            finish();
//        });
//
//        pauseDialog.show();
//        pauseDialog.setCancelable(false);
//        isPaused = true;
//    }
//
//
//
//
//    private void updateSoundButtonIcon(ImageButton soundButton) {
//        if (SoundManager.isSoundOn()) {
//            soundButton.setImageResource(R.drawable.soundopen);
//        } else {
//            soundButton.setImageResource(R.drawable.soundclose);
//        }
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        if (!hasFocus) {
//            if (isExiting) return; // ถ้ากำลังจะปิด Activity ให้ข้ามการแสดง Dialog
//
//            if (countDownTimer != null) countDownTimer.cancel();
//            witchAnimationManager.pauseLoop();
//            isPaused = true;
//
//            if (!isPauseDialogShowing && !isGameOverDialogShowing) {
//                showPauseDialog(); // แสดง Dialog
//            }
//        } else {
//            if (isExiting) return; // ถ้ากำลังจะปิด Activity ให้ข้ามการแสดง Dialog
//
//            if (SoundManager.isSoundOn()) {
//                SoundManager.resumeBackgroundMusic();
//            }
//            if (!isPauseDialogShowing && !isGameOverDialogShowing && isPaused) {
//                witchAnimationManager.resumeLoop();
//                startCountdownTimer(remainingTime);
//                isPaused = false;
//            }
//        }
//    }
//
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (isExiting) return; // ถ้ากำลังจะปิด Activity ให้ข้ามการแสดง Dialog
//
//        // หยุด Timer
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//
//        // หยุด Witch Animation
//        witchAnimationManager.pauseLoop();
//        SoundManager.pauseBackgroundMusic(); // หยุดเพลง
//
//
//
//        // ตั้งสถานะ pause
//        isPaused = true;
//
//        // แสดง Pause Dialog ถ้ายังไม่ได้แสดง
//        if (pauseDialog == null || !pauseDialog.isShowing()) {
//            showPauseDialog();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        SoundManager.resumeBackgroundMusic();
//
//        if (isPaused) {
//            showPauseDialog();
//        } else {
//            // เริ่ม Timer ใหม่
//            startCountdownTimer(remainingTime);
//            // เริ่ม Witch Animation อีกครั้ง
//
//            witchAnimationManager.resumeLoop();
//        }
//        // เล่นเพลงต่อจากเดิม
//        SoundManager.resumeBackgroundMusic();
//    }
//
//
//}