package com.ashishdake.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ashishdake.countdowntime.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    TextView text;
    EditText time;
    CountDownTimer timer;
    Button start,stop,pause,resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        time = findViewById(R.id.time);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        pause = findViewById(R.id.pause);
        resume = findViewById(R.id.resume);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Time cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                timer.setTime(Integer.valueOf(time.getText().toString()));
                timer.startTimer();

                time.setEnabled(false);
                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
                resume.setVisibility(View.GONE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.stopTimer();

                time.setEnabled(true);
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
                pause.setVisibility(View.GONE);
                resume.setVisibility(View.GONE);
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.resumeTimer();

                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
                resume.setVisibility(View.GONE);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.pauseTimer();

                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                resume.setVisibility(View.VISIBLE);
            }
        });

        timer = new CountDownTimer() {
            @Override
            public void onTimerTick(long timeRemainingInSeconds) {
                String finalhours = "";
                String finalminute = "";
                String finalsecond = "";

                long hours = timeRemainingInSeconds / (60 * 60);
                long minutes = timeRemainingInSeconds / 60 % 60;
                long seconds = timeRemainingInSeconds % 60;

                if(hours < 10){
                    finalhours = "0" + hours;
                }
                else {
                    finalhours = String.valueOf(hours);
                }
                if(minutes < 10){
                    finalminute = "0" + minutes;
                }
                else {
                    finalminute = String.valueOf(minutes);
                }
                if(seconds < 10){
                    finalsecond = "0" + seconds;
                }
                else {
                    finalsecond = String.valueOf(seconds);
                }

                text.setText(finalhours + ":" + finalminute + ":" + finalsecond);
            }

            @Override
            public void onTimerFinish() {
                text.setText("00:00:00");

                time.setEnabled(true);
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
                pause.setVisibility(View.GONE);
                resume.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, "Time finished", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
