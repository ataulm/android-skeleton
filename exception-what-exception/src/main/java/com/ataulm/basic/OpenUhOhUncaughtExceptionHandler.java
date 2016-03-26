package com.ataulm.basic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

public class OpenUhOhUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final Context context;
    private final AlarmManager alarmManager;

    public static Thread.UncaughtExceptionHandler newInstance(Context context) {
        Context applicationContext = context.getApplicationContext();
        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);

        return new OpenUhOhUncaughtExceptionHandler(applicationContext, alarmManager);
    }

    private OpenUhOhUncaughtExceptionHandler(Context context, AlarmManager alarmManager) {
        this.context = context;
        this.alarmManager = alarmManager;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        PendingIntent pendingIntent = createPendingIntentToOpenUhOhActivity();
        setAlarmToFire(pendingIntent);

        Process.killProcess(Process.myPid());
    }

    private PendingIntent createPendingIntentToOpenUhOhActivity() {
        Intent intent = new Intent(context, UhOhActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    private void setAlarmToFire(PendingIntent pendingIntent) {
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis(), pendingIntent);
    }

}
