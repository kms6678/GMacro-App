package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import junit.framework.Test;

import java.io.IOException;

public class HWActivity extends Activity implements View.OnClickListener {

    Button btnScreen;
    Button btnHomeKey;
    Button btnBackKey;
    Button btnPowerKey;
    Button btnVolUp;
    Button btnVolDown;
    Button btnMugic;
    Button btnProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw);

        btnScreen = (Button) findViewById(R.id.스크린샷버튼);
        btnHomeKey = (Button) findViewById(R.id.홈버튼);
        btnBackKey = (Button) findViewById(R.id.백버튼);
        btnPowerKey = (Button)findViewById(R.id.전원버튼);
        btnVolUp = (Button)findViewById(R.id.볼륨업);
        btnVolDown = (Button)findViewById(R.id.볼륨다운);
        btnMugic = (Button)findViewById(R.id.뮤직버튼);
        btnProcess = (Button)findViewById(R.id.프로세스버튼);

        btnScreen.setOnClickListener(this);
        btnHomeKey.setOnClickListener(this);
        btnBackKey.setOnClickListener(this);
        btnPowerKey.setOnClickListener(this);
        btnVolUp.setOnClickListener(this);
        btnVolDown.setOnClickListener(this);
        btnMugic.setOnClickListener(this);
        btnProcess.setOnClickListener(this);
    }

    public void onClick(View v) {
        final GlobalVal myApp = (GlobalVal) getApplication();

        if (v == btnScreen) {
            // 스크린샷 이벤트 발생
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(HWActivity.this);
            alert_confirm.setTitle("Screen Shot Event");
            alert_confirm.setMessage("스크린샷 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt() - 1, "SCREEN");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "스크린 샷 이벤트 발생");
                    myApp.setEventCnt(myApp.getEventCnt() + 1);
                    finish();
                }

            });
            alert_confirm.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
        } else if (v == btnHomeKey) {
            // 홈 키 눌림 이벤트 발생
// 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(HWActivity.this);
            alert_confirm.setTitle("Insert Home Key Event");
            alert_confirm.setMessage("홈 키 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "HOMEKEY");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "홈 키 이벤트 발생");
                    myApp.setEventCnt(myApp.getEventCnt() + 1);
                    finish();
                }

            });
            alert_confirm.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
        } else if (v == btnBackKey) {
            // 뒤로가기 키 눌림 이벤트 발생
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(HWActivity.this);
            alert_confirm.setTitle("Insert Back Key Event");
            alert_confirm.setMessage("뒤로가기 키 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "BACKKEY");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "뒤로가기 키 이벤트 발생");
                    myApp.setEventCnt(myApp.getEventCnt() + 1);
                    finish();
                }

            });
            alert_confirm.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
        } else if (v == btnPowerKey){
            // 뒤로가기 키 눌림 이벤트 발생
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(HWActivity.this);
            alert_confirm.setTitle("Insert Power Key Event");
            alert_confirm.setMessage("전원 키 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "POWERKEY");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "전원 키 이벤트 발생");
                    myApp.setEventCnt(myApp.getEventCnt() + 1);
                    finish();
                }

            });
            alert_confirm.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
        } else if( v == btnVolUp ){
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(HWActivity.this);
            alert_confirm.setTitle("Insert Volume Up Event");
            alert_confirm.setMessage("볼륨 업 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "VOLUP");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "볼륨 업 이벤트 발생");
                    myApp.setEventCnt(myApp.getEventCnt() + 1);
                    finish();
                }

            });
            alert_confirm.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
        } else if( v == btnVolDown ){
// 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(HWActivity.this);
            alert_confirm.setTitle("Insert Volume Down Event");
            alert_confirm.setMessage("볼륨 다운 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "VOLDOWN");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "볼륨 다운 이벤트 발생");
                    myApp.setEventCnt(myApp.getEventCnt() + 1);
                    finish();
                }

            });
            alert_confirm.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
        } else if( v == btnMugic ){
            // 뮤직 이벤트 설정 액티비티로 전환
            Intent intent = new Intent(HWActivity.this, MugicEventActivity.class);
            startActivity(intent);
        } else if( v == btnProcess ){
            // 프로세스 실행 이벤트 액티비티로 전환
            Intent intent = new Intent(HWActivity.this, ShowProcessListActivity.class);
            startActivity(intent);
        }
    }
}
