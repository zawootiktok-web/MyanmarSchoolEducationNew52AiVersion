package com.mmschooledu.MyanmarSpelling.fragment;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private static boolean isSoundOn = true; // ควบคุมสถานะเสียง
    private static MediaPlayer mediaPlayer;

    // เช็คสถานะเสียง
    public static boolean isSoundOn() {
        return isSoundOn;
    }

    // สลับสถานะเสียง
    public static void toggleSound() {
        isSoundOn = !isSoundOn;
        if (!isSoundOn) {
            pauseBackgroundMusic(); // หยุดเสียงเมื่อปิด
        } else {
            resumeBackgroundMusic(); // เล่นเสียงต่อเมื่อเปิด
        }
    }

    // เล่นเพลงพื้นหลัง
    public static void playBackgroundMusic(Context context, int musicResId) {
        if (mediaPlayer == null) {
            // ถ้า MediaPlayer ยังไม่ถูกสร้าง ให้สร้างใหม่
            mediaPlayer = MediaPlayer.create(context, musicResId);
            mediaPlayer.setLooping(true); // ให้เพลงเล่นวนลูป
        }
        if (isSoundOn && !mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // เริ่มเล่นเพลง
        }
    }

    public static void pauseBackgroundMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // เล่นเพลงต่อ
    public static void resumeBackgroundMusic() {
        if (mediaPlayer != null && isSoundOn && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
}


