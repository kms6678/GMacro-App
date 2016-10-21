package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button btnInsMacro;
	Button btnCheckMacro;
	Button btnExit;
	Button btnNoti;
	Button btnRotary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 스플래시 화면 띄우기
		startActivity(new Intent(this, SplashActivity.class));

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 전역 변수 코드
		GlobalVal myApp = (GlobalVal) getApplication();
		myApp.resetData();

		btnInsMacro = (Button) findViewById(R.id.추가버튼);
		btnCheckMacro = (Button) findViewById(R.id.확인버튼);
		btnExit = (Button) findViewById(R.id.종료버튼);
		btnNoti = (Button) findViewById(R.id.노티피버튼);
		btnRotary = (Button) findViewById(R.id.즐겨찾기버튼);

		btnInsMacro.setOnClickListener(this);
		btnCheckMacro.setOnClickListener(this);
		btnExit.setOnClickListener(this);
		btnNoti.setOnClickListener(this);
		btnRotary.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v == btnInsMacro) {
			Intent intent = new Intent(MainActivity.this, MenuActivity.class);
			startActivity(intent);
		} else if (v == btnCheckMacro) {
			// 등록된 매크로를 보여주는 액티비티로 전환
			Intent intent = new Intent(MainActivity.this, MacroListActivity.class);
			startActivity(intent);

		} else if (v == btnExit) {
			// 팝업 띄우기
			// 밑에 라인 체크하기
			AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
			alert_confirm.setMessage("G-Macro를 종료하시겠습니까?").setCancelable(false).setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 'YES'
							finish();
						}
					}).setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 'No'
							return;
						}
					});
			AlertDialog alert = alert_confirm.create();
			alert.show();
		} else if (v == btnNoti) {
			Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
			startActivity(intent);
		} else if (v == btnRotary) {
			Intent intent = new Intent(MainActivity.this, RotaryActivity.class);
			startActivity(intent);
		}
	}

	// back key 처리
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				// 팝업 띄우기
				// 밑에 라인 체크하기
				AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
				alert_confirm.setMessage("G-Macro를 종료하시겠습니까?").setCancelable(false).setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// 'YES'
								finish();
							}
						}).setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// 'No'
								return;
							}
						});
				AlertDialog alert = alert_confirm.create();
				alert.show();
				break;
		}
		return true;
	}
}

//C:\Users\KCH\Downloads\android-sdk-windows\platform-tools
