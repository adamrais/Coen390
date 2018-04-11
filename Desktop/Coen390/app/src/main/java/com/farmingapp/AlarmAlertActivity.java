/*
 */

package com.farmingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class AlarmAlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(">>>>>>>>","oncreate");
		requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
		wakeUpAndUnlock();
		int type = getIntent().getIntExtra("type",0);
		String tile ="";
		String content="";
		if(type==AlarmsSetting.ALARM_SETTING_TYPE_IN){
			tile = "Water the plant";
			content = "It is time to water your plant!";
		}else if( type == AlarmsSetting.ALARM_SETTING_TYPE_OUT){
			tile = "Pesticide";
			content = "It is time to kill the bugs!";
		}else{
			finish();
		}

		new AlertDialog.Builder(AlarmAlertActivity.this).setIcon(R.drawable.icon_switch)
				.setTitle(tile).setMessage(content).setCancelable(false)
				.setPositiveButton("I am going to do it", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						finish();
//						System.exit(0);
//						android.os.Process.killProcess(android.os.Process
//								.myPid());
					}
				}).show();
		notificationVibrator();
		notificationRing();
	}

	public  void wakeUpAndUnlock(){
		KeyguardManager km= (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		//
		kl.disableKeyguard();
		//
		PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
		//
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
		//
		wl.acquire();
		//
		wl.release();
	}

	private MediaPlayer mediaPlayer = new MediaPlayer();
	public Vibrator vibrator;
	/**
	 * shake
	 */
	private void notificationVibrator() {

			if (vibrator == null) {
				vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
			}
			vibrator.vibrate(new long[]{500, 50, 50, 1000, 50}, -1);
	}

	/**
	 * Ring
	 */
	private void notificationRing() {

		if (mediaPlayer == null)
			mediaPlayer = new MediaPlayer();
		if (mediaPlayer.isPlaying())
			return;

		try {

			Uri uri = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_ALARM);
			mediaPlayer.stop();
			mediaPlayer.reset();
			mediaPlayer.setDataSource(this, uri);
			mediaPlayer.prepare();
		} catch (Exception e) {

		}
		mediaPlayer.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaPlayer.stop();
	}

//	@Override
//	public void onAttachedToWindow() {
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//		super.onAttachedToWindow();
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			// Volume keys and camera keys dismiss the alarm
			case KeyEvent.KEYCODE_BACK:
			case KeyEvent.KEYCODE_MENU:
			case KeyEvent.KEYCODE_FOCUS:
				return true;
			default:
				break;
		}
		return super.onKeyDown(keyCode, event);
	}
//
//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		// Do this on key down to handle a few of the system keys.
//		switch (event.getKeyCode()) {
//			// Volume keys and camera keys dismiss the alarm
//			case KeyEvent.KEYCODE_VOLUME_UP:
//			case KeyEvent.KEYCODE_BACK:
//			case KeyEvent.KEYCODE_HOME:
//			case KeyEvent.KEYCODE_MENU:
//			case KeyEvent.KEYCODE_VOLUME_DOWN:
//			case KeyEvent.KEYCODE_CAMERA:
//			case KeyEvent.KEYCODE_FOCUS:
//				return true;
//			default:
//				break;
//		}
//		return super.dispatchKeyEvent(event);
//	}
}
