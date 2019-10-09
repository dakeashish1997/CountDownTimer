package com.ashishdake.countdowntime;

public interface CountDownTimerListener {

   void onTimerTick(long timeRemaining);

   void onTimerFinish();

}
