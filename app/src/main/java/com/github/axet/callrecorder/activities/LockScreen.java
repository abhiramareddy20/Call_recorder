package com.github.axet.callrecorder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.github.axet.callrecorder.R;

import java.util.List;
import java.util.concurrent.locks.Lock;

import io.paperdb.Paper;

public class LockScreen extends AppCompatActivity {

    String save_pattern_key = "pattern_code";
    PatternLockView mpatternLockView;
    String  final_pattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        if(save_pattern != null && !save_pattern.equals(null))
        {
            setContentView(R.layout.pattern_screen);
            mpatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_view);
            mpatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mpatternLockView,pattern);
                    if(final_pattern.equals(save_pattern)) {
                        Intent i = new Intent(LockScreen.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LockScreen.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCleared() {

                }
            });
        }
        else
        {
            setContentView(R.layout.activity_lock_screen);
            mpatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_view);
            mpatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mpatternLockView,pattern);
                }

                @Override
                public void onCleared() {

                }
            });

            Button btnsetup = (Button) findViewById(R.id.set_pattern);
            btnsetup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(save_pattern_key,final_pattern);
                    finish();
                }
            });
        }
    }
}