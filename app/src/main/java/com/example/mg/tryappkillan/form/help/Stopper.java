package com.example.mg.tryappkillan.form.help;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mg.tryappkillan.form.StartActivity;
import com.example.mg.tryappkillan.interf.ITimerActivity;

import java.net.URL;

/**
 * Created by mg on 08/01/17.
 */

public class Stopper extends   Thread {

    private  long mStartTime;
    private volatile int since;
    public Handler handler;
    private TextView tv;

    private Activity activity;
    private ITimerActivity context;

    /*
    * Constants
    * */
        public static final int MILISEC_TO_MINUTE = 60000;
        public static final int MILISEC_TO_HOUR = 3600000;

    /*
    * Flags
    * */
         private boolean mIsRunning;

        public Stopper(ITimerActivity context){
           this.context = context;
           //  activity = (Activity)context;
            handler = new Handler(Looper.getMainLooper());

        }
        public void start(){
            mStartTime = System.currentTimeMillis();
            mIsRunning = true;


        }
        public void mstop(){
            mIsRunning = false;
        }



        @Override
        public void run () {
            while (mIsRunning) {
                        //    Log.e("LOG","ACTIVE");
                        //  Thread.sleep(1000);
                        since = (int)(System.currentTimeMillis() - mStartTime);
                        int hours =  (since / MILISEC_TO_HOUR) % 24; // ms -> h; 360 000= 1[h]
                        int minutes =  (since / MILISEC_TO_MINUTE) % 60;// ms -> min; 60 000[ms] = 1[min]
                        int seconds =  (since / 1000) % 60; //  ms -> s ;1 000[ms] = 1[s] -> (1000 [ms] /1000)%60 = 1[s]
                        int ms =  since % 1000;

                context.updateTimerText(String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, ms));
                //((StartActivity) context).updateTimerText(String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, ms));
            }
        }
    public void getStooperTextView(TextView textView)
    {
        this.tv = textView;
    }


    public int getSince() {
        return since;
    }
}



