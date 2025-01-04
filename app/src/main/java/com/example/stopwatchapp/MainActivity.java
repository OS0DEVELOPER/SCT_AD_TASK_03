package com.example.stopwatchapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView tvTimer;
    private Button btnStart, btnPause, btnReset;
    private Handler handler = new Handler();
    private long startTime = 0L;
    private long elapsedTime = 0L;
    private boolean isRunning = false;

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run(){
            if (isRunning) {
                long currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime;
                int seconds = (int) (elapsedTime / 1000);
                int minutes = seconds / 60;
                int hours = minutes / 60;
                seconds = seconds % 60;
                minutes = minutes % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                tvTimer.setText(time);
                handler.postDelayed(this, 1000);

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis() - elapsedTime;
                    isRunning = true;
                    handler.post(timerRunnable);
                }
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                elapsedTime = 0L;
                tvTimer.setText("00:00:00");
            }
        });
    }
}