/*

 */

package com.farmingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmOpreation {


    public static void cancelAlert(Context context, int type) {
//        Log.e("<<<<<<<<<<<<<<<<<", "cancelAlert");
        AlarmManager mAlarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmsSetting.ALARM_ALERT_ACTION);
        intent.putExtra("type", type);
        intent.setClass(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, type, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        mAlarmManager.cancel(pi);

    }

    public static void enableAlert(Context context, int type, AlarmsSetting alarmsSetting) {
//        Log.e("<<<<<<<<<<<<<<<<<", "enableAlert");
        if(type==AlarmsSetting.ALARM_SETTING_TYPE_IN && !alarmsSetting.isInEnble()){
            return ;
        }else if(type==AlarmsSetting.ALARM_SETTING_TYPE_OUT && !alarmsSetting.isOutEnble()){
            return;
        }
        AlarmManager mAlarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        int hours = 0,minute=0,dayOfweek=0;
        if(type==AlarmsSetting.ALARM_SETTING_TYPE_IN){
            hours = alarmsSetting.getInHour();
            minute=alarmsSetting.getInMinutes();
            dayOfweek = alarmsSetting.getInDays();
        }else if(type==AlarmsSetting.ALARM_SETTING_TYPE_OUT){
            hours = alarmsSetting.getOutHour();
            minute=alarmsSetting.getOutMinutes();
            dayOfweek=alarmsSetting.getOutDays();
        }
        Calendar mCalendar = cacluteNextAlarm(hours, minute, dayOfweek);
//        Log.e("<<<<<<<<<<<<<<<<<", "alarmsSetting" + alarmsSetting.getInHour() + "-" + alarmsSetting.getInMinutes());
//        Log.e("<<<<<<<<<<<<<<<<<", " mCalendar" + mCalendar.get(Calendar.DAY_OF_WEEK));
        if (mCalendar.getTimeInMillis() < System.currentTimeMillis()) {
            Log.e("!!!!!!!!!!!","setAlarm FAIL:Set time cannot less than current time，This"+mCalendar.getTimeInMillis()+"is invalid");
            return;
        }

        Intent intent = new Intent(AlarmsSetting.ALARM_ALERT_ACTION);
        intent.putExtra("type", type);
        intent.setClass(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, type, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
        alarmsSetting.setNextAlarm(mCalendar.getTimeInMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/——HH/mm/ss/SSS");
        Log.e("#########Alert#######", "alarmsSetting.getNextAlarm()" + formatter.format(new Date(alarmsSetting.getNextAlarm())));
    }

    public static Calendar cacluteNextAlarm(int hour,int minute,int dayOfweek){
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.set(Calendar.HOUR_OF_DAY,hour);
        mCalendar.set(Calendar.MINUTE, minute);
        int differDays = getNextAlarmDifferDays(dayOfweek,mCalendar.get(Calendar.DAY_OF_WEEK), mCalendar.getTimeInMillis());
        int nextYear = getNextAlarmYear(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.DAY_OF_YEAR), mCalendar.getActualMaximum(Calendar.DAY_OF_YEAR), differDays);
        int nextMonth = getNextAlarmMonth(mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), mCalendar.getActualMaximum(Calendar.DATE), differDays);
        int nextDay = getNextAlarmDay(mCalendar.get(Calendar.DAY_OF_MONTH), mCalendar.getActualMaximum(Calendar.DATE), differDays);
        mCalendar.set(Calendar.YEAR,nextYear);
        mCalendar.set(Calendar.MONTH, nextMonth % 12);
        mCalendar.set(Calendar.DAY_OF_MONTH, nextDay);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
        return mCalendar;
    }


    //
    private static int getNextAlarmDifferDays(int data, int currentDayOfWeek,long timeInMills){
        int nextDayOfWeek =  getNextDayOfWeek(data, currentDayOfWeek,timeInMills);
        return currentDayOfWeek<=nextDayOfWeek?(nextDayOfWeek-currentDayOfWeek):(7 - currentDayOfWeek + nextDayOfWeek);
    }


    //
    private static int getNextAlarmYear(int year,int dayOfYears, int actualMaximum, int differDays) {
        int temp = actualMaximum-dayOfYears-differDays;
        return temp >= 0?year:year+1;
    }

    //
    private static int getNextAlarmMonth(int month,int dayOfMonth,int actualMaximum, int differDays) {
        int temp = actualMaximum-dayOfMonth-differDays;
        return temp >= 0?month:month+1;
    }

    //
    private static int getNextAlarmDay(int thisDayOfMonth, int actualMaximum, int differDays) {
        int temp = actualMaximum - thisDayOfMonth-differDays;
        if (temp<0){
            return thisDayOfMonth + differDays - actualMaximum;
        }
        return thisDayOfMonth + differDays;
    }

    //
    private static int getNextDayOfWeek(int data, int cWeek,long timeInMillis) {
        int tempBack = data >> cWeek - 1;
        int tempFront = data ;

        if(tempBack%2==1){
            if(System.currentTimeMillis()<timeInMillis)  return cWeek;
        }
        tempBack = tempBack>>1;
        int m=1,n=0;
        while (tempBack != 0) {
            if (tempBack % 2 == 1 ) return cWeek + m;
            tempBack = tempBack / 2;
            m++;
        }
        while(n<cWeek){
            if (tempFront % 2 == 1)  return n+1;
            tempFront =tempFront/2;
            n++;
        }
        return 0;
    }
}
