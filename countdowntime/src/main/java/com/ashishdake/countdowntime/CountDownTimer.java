package com.ashishdake.countdowntime;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

public abstract class CountDownTimer implements CountDownTimerListener {

    private static final int INTERVAL = 1000;
    private static final int MSG = 1;

    private boolean isRunning;

    private boolean isPaused;

    private long time;
    private long localTime;
    private long interval;
    private Handler handler;

    public CountDownTimer() {
        init(0, INTERVAL);
    }

    private void init(long time, long interval) {
        setTime(time);
        setInterval(interval);
        initTimer();
    }

    @SuppressLint("HandlerLeak")
    private void initTimer() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == MSG) {
                    if (!isPaused) {
                        if (localTime <= time) {
                            onTimerTick((time - localTime)/1000);
                            localTime += interval;
                            sendMessageDelayed(handler.obtainMessage(MSG), interval);
                        } else{
                            stopTimer();
                        }
                    }
                }
            }
        };
    }

    public void startTimer() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        isPaused = false;
        localTime = 0;
        handler.sendMessage(handler.obtainMessage(MSG));
    }

    public void stopTimer() {
        isRunning = false;
        handler.removeMessages(MSG);
        onTimerFinish();
    }

    private synchronized void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public synchronized void pauseTimer() {
        setPaused(true);
    }

    public synchronized void resumeTimer() {
        setPaused(false);

        handler.sendMessage(handler.obtainMessage(MSG));
    }

    public void setTime(long timeInSeconds) {
        long timeInMillis = 0;
        timeInMillis = timeInSeconds * 1000;
        if (isRunning) {
            return;
        }
        if (this.time <= 0) {
            if (timeInMillis < 0) {
                timeInMillis *= -1;
            }
        }
        this.time = timeInMillis;
    }

    public void setInterval(long intervalInMillis) {
        if (isRunning) {
            return;
        }
        if (this.interval <= 0) {
            if (intervalInMillis < 0) {
                intervalInMillis *= -1;
            }
        }
        this.interval = intervalInMillis;
    }
}
