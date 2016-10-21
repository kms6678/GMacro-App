package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MugicEventActivity extends Activity implements View.OnClickListener{

    Button btnStart;
    Button btnEnd;
    Button btnNext;
    Button btnRestart;
    Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mugic_event);

        btnStart = (Button) findViewById(R.id.시작버튼);
        btnEnd = (Button) findViewById(R.id.종료버튼);
        btnNext = (Button) findViewById(R.id.다음버튼);
        btnRestart = (Button)findViewById(R.id.다시버튼);
        btnStop = (Button)findViewById(R.id.일시정지버튼);

        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnRestart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    public void onClick(View v) {
        final GlobalVal myApp = (GlobalVal) getApplication();

        if (v == btnStart) {
            // 음악 시작 이벤트
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MugicEventActivity.this);
            alert_confirm.setTitle("Mugic Start Event");
            alert_confirm.setMessage("노래 시작 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt() - 1, "MUGICSTART");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "노래 시작 이벤트 발생");
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
        } else if (v == btnEnd) {
            // 음악 종료 이벤트
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MugicEventActivity.this);
            alert_confirm.setTitle("Mugic End Event");
            alert_confirm.setMessage("음악 종료 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "MUGICEND");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "음악 종료 이벤트 발생");
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
        } else if (v == btnNext) {
            // 다음 음악 시작 이벤트
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MugicEventActivity.this);
            alert_confirm.setTitle("Mugic Next Event");
            alert_confirm.setMessage("다음 음악 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "MUGICNEXT");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "다음 음악 이벤트 발생");
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
        } else if (v == btnRestart){
            // 음악 재시작 이벤트
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MugicEventActivity.this);
            alert_confirm.setTitle("Mugic Restart Event");
            alert_confirm.setMessage("음악 다시 재생 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "MUGICRESTART");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "음악 다시 재생 이벤트 발생");
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
        } else if( v == btnStop ){
            // 음악 일시정지 이벤트
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MugicEventActivity.this);
            alert_confirm.setTitle("Mugic Stop Event");
            alert_confirm.setMessage("음악 일시정지 이벤트를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "MUGICSTOP");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "음악 일시정지 이벤트 발생");
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
        }
    }
}
