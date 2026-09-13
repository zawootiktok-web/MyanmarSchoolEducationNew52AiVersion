package com.mmschooledu.MyanmarSpelling.fragment;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.mmschooledu.R;


public class QuizFragment extends Fragment {
    private ImageButton settingBtn;
    private Button basicbtn, advancebtn;
    private int basicBestScore = 0; // Best Score ของ Basic Mode
    private int advanceBestScore = 0; // Best Score ของ Advance Mode
    private boolean isBackPressedOnce = false; // ตัวแปรตรวจสอบการกด Back ครั้งแรก
    private Handler handler = new Handler(); // ตัวจัดการเวลา
    private Runnable resetBackPressedFlag; // Task สำหรับรีเซ็ตสถานะการกด Back

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false); // Use a fragment layout

        // เริ่มเล่นเพลงพื้นหลังเมื่อเปิดแอป
        SoundManager.playBackgroundMusic(getContext(), R.raw.themesong);

        // กำหนดปุ่ม
        basicbtn = view.findViewById(R.id.basicbtn);
        advancebtn = view.findViewById(R.id.advancebtn);
        settingBtn = view.findViewById(R.id.settingbtn);

        // เมื่อกดปุ่ม Basic Mode
        basicbtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GameActivity.class); // เปิด GameActivity
            intent.putExtra("bestScore", basicBestScore); // ส่งค่า Best Score ของ Basic Mode
            startActivityForResult(intent, 1); // ใช้ requestCode 1 สำหรับ Basic Mode
        });

        // เมื่อกดปุ่ม Advance Mode
        advancebtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AdvanceActivity.class); // เปิด AdvanceActivity
            intent.putExtra("bestScore", advanceBestScore); // ส่งค่า Best Score ของ Advance Mode
            startActivityForResult(intent, 2); // ใช้ requestCode 2 สำหรับ Advance Mode
        });

        // การตั้งค่าปุ่ม Setting
        settingBtn.setOnClickListener(v -> showCustomDialog());

        // จัดการปุ่ม Back ด้วย onBackPressedDispatcher
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleBackPress();
            }
        });

        // กำหนด Task สำหรับรีเซ็ตสถานะ
        resetBackPressedFlag = () -> isBackPressedOnce = false;

        return view;
    }

    private void handleBackPress() {
        if (isBackPressedOnce) {
            // กด Back ติดกันสองครั้ง จะออกจากแอปทันที
            requireActivity().finish();
            return;
        }

        isBackPressedOnce = true;

        // ตั้งเวลาสำหรับรีเซ็ตสถานะการกด Back หลังจาก 1 วินาที
        handler.postDelayed(resetBackPressedFlag, 1000);

        // แสดงไดอะล็อกเมื่อกด Back ครั้งแรก
        showExitConfirmationDialog();
    }

    // รับค่าผลลัพธ์จากกิจกรรม
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            int newBestScore = data.getIntExtra("newBestScore", 0); // รับค่า Best Score ใหม่

            if (requestCode == 1) { // Basic Mode
                basicBestScore = Math.max(basicBestScore, newBestScore); // อัปเดต Basic Best Score
            } else if (requestCode == 2) { // Advance Mode
                advanceBestScore = Math.max(advanceBestScore, newBestScore); // อัปเดต Advance Best Score
            }
        }
    }

    private void showCustomDialog() {
        // โหลด layout ที่เราสร้างขึ้น
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_setting, null); // custom_dialog.xml คือไฟล์ที่คุณสร้างไว้

        // สร้าง Dialog พร้อมใช้สไตล์โปร่งใส
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.TransparentDialog);
        builder.setView(dialogView);

        // เข้าถึง View ใน Dialog
        ImageButton soundBtn = dialogView.findViewById(R.id.soundbtn);
        ImageButton contactBtn = dialogView.findViewById(R.id.contactbtn);

        // ตั้งค่าไอคอนของปุ่มเสียงตามสถานะปัจจุบัน
        updateSoundButtonIcon(soundBtn);

        soundBtn.setOnClickListener(v -> {
            SoundManager.toggleSound();
            updateSoundButtonIcon(soundBtn);
        });

        contactBtn.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cs.sci.tu.ac.th/"));
            startActivity(browserIntent);
        });

        // สร้างและแสดง Dialog
        AlertDialog dialog = builder.create();

        // ตั้งค่าให้โปร่งใสอย่างสมบูรณ์
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        dialog.show();
    }

    private void updateSoundButtonIcon(ImageButton soundButton) {
        if (SoundManager.isSoundOn()) {
            soundButton.setImageResource(R.drawable.soundopen);
        } else {
            soundButton.setImageResource(R.drawable.soundclose);
        }
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Exit Confirmation");
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            handler.removeCallbacks(resetBackPressedFlag); // ยกเลิกการรีเซ็ตสถานะ
            requireActivity().finish(); // ออกจากแอป
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
            isBackPressedOnce = false; // รีเซ็ตสถานะเมื่อเลือก "No"
        });

        AlertDialog exitDialog = builder.create();
        exitDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        // หยุดเพลงชั่วคราวเมื่อออกจาก Fragment
        SoundManager.pauseBackgroundMusic();
    }

    @Override
    public void onResume() {
        super.onResume();
        // เล่นเพลงต่อเมื่อกลับมาโฟกัส และเสียงยังเปิดอยู่
        if (SoundManager.isSoundOn()) {
            SoundManager.resumeBackgroundMusic();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(resetBackPressedFlag); // ลบ Task เมื่อ Fragment ถูกทำลาย
    }
}
