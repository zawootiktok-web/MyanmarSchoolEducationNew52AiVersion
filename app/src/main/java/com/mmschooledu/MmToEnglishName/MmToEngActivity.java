package com.mmschooledu.MmToEnglishName;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mmschooledu.R;


public class MmToEngActivity extends AppCompatActivity {
    Toolbar tb;
    private boolean myanmarInput = true;
    private EditText editText;
    private TextView resultTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmengname_main);

        tb = (Toolbar) findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.editText);
        resultTextView = findViewById(R.id.resultTextView);

        Button toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSwitch();
            }
        });

        updateUI();

        // Add TextWatcher to auto-convert when the text changes
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Auto-convert after text changes
                convert();
            }
        });
    }

    private void toggleSwitch() {
        myanmarInput = !myanmarInput;
        resultTextView.setText("");
        editText.getText().clear();
        updateUI();
    }

    private void updateUI() {
        TextView languageTextView = findViewById(R.id.languageTextView);
        languageTextView.setText(myanmarInput ? "Myanmar" : "English");

        TextView oppositeLanguageTextView = findViewById(R.id.oppositeLanguageTextView);
        oppositeLanguageTextView.setText(myanmarInput ? "English" : "Myanmar");

        String hintText = myanmarInput ? "Enter Myanmar" : "Enter English";
        editText.setHint(hintText);
    }

    public void convert() {
        String text = editText.getText().toString();
        String convertedText = myanmarInput ? MyanmarNameConverter.mm2en(text) : MyanmarNameConverter.en2mm(text);
        resultTextView.setText(convertedText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.lesson) {
//            //toggleSwitch();
//
//            Intent i = new Intent(MmToEngActivity.this, BasicGrammarViewer.class);
//            startActivity(i);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    // ... (existing code)
//}


        int id = item.getItemId();
        if (id == android.R.id.home) {
            //mDrawerLayout.openDrawer(GravityCompat.START);
            //return true;
            finish();
        } else if (item.getItemId() == R.id.lessonmm) {
            Intent i = new Intent(MmToEngActivity.this, BasicGrammarViewer.class);
           startActivity(i);

        }


        return super.onOptionsItemSelected(item);
    }}