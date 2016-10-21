package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// 메뉴에서 뒤로가기 키로 메인 메뉴로 돌아갔을 때 전역 변수 초기화 해줘야함.

public class MenuActivity extends Activity implements View.OnClickListener {

    // DB 설정
    private MySQLiteOpenHelper helper;
    String dbName = "st_file.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;

    GlobalVal myApp = (GlobalVal) getApplication();

    Button btnTouch;
    Button btnLongTouch;
    Button btnDrag;
    Button btnEventList;
    Button btnSavaFile;
    Button btnHW;
    Button btnTime;
    Button btnData;
    String saveResultData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        helper = new MySQLiteOpenHelper(
                this,  // 현재 화면의 제어권자
                dbName,// db 이름
                null,  // 커서팩토리-null : 표준커서가 사용됨
                dbVersion);

        btnTouch = (Button)findViewById(R.id.터치버튼);
        btnLongTouch = (Button)findViewById(R.id.롱터치버튼);
        btnDrag = (Button)findViewById(R.id.드래그버튼);
        btnEventList = (Button)findViewById(R.id.리스트버튼);
        btnSavaFile = (Button)findViewById(R.id.저장버튼);
        btnTime = (Button)findViewById(R.id.시간버튼);
        btnData = (Button)findViewById(R.id.데이터버튼);
        btnHW = (Button)findViewById(R.id.하드웨어버튼);

        btnTouch.setOnClickListener(this);
        btnLongTouch.setOnClickListener(this);
        btnDrag.setOnClickListener(this);
        btnEventList.setOnClickListener(this);
        btnSavaFile.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnData.setOnClickListener(this);
        btnHW.setOnClickListener(this);
    }

    // Button Click Event
    // 갤러리에서 이미지를 가져오는 부분 - 코드 중복 - 제거하자!!!!
    public void onClick(View v) {
        final GlobalVal myApp = (GlobalVal) getApplication();
        if( v == btnTouch ) {
            // 터치 이벤트를 등록할 수 있는 액티비티로 전환
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MenuActivity.this);
            alert_confirm.setTitle("Touch Event");
            alert_confirm.setMessage("갤러리에서 이미지를 가져오시겠습니까?");
            alert_confirm.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'YES'
                            //finish();
                            Intent intent = new Intent(MenuActivity.this, TouchActivity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'No'
                            return;
                        }
                    });
            alert_confirm.show();
        } else if( v == btnLongTouch ){
            // 롱 터치 이벤트를 등록할 수 있는 액티비티로 전환
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MenuActivity.this);
            alert_confirm.setTitle("Long Touch Event");
            alert_confirm.setMessage("갤러리에서 이미지를 가져오시겠습니까?");
            alert_confirm.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'YES'
                            //finish();
                            Intent intent=new Intent(MenuActivity.this, LongTouchEventActivity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'No'
                            return;
                        }
                    });
            alert_confirm.show();
        } else if( v == btnDrag ) {
            // 드래그 이벤트를 등록할 수 있는 액티비티로 전환
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MenuActivity.this);
            alert_confirm.setTitle("Drag Event");
            alert_confirm.setMessage("갤러리에서 이미지를 가져오시겠습니까?");
            alert_confirm.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'YES'
                            //finish();
                            Intent intent=new Intent(MenuActivity.this, DragEventActivity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'No'
                            return;
                        }
                    });
            alert_confirm.show();
        }else if( v == btnEventList ){
            // 등록된 이벤트를 보여주는 액티비티로 전환
            Intent intent=new Intent(MenuActivity.this, EventListActivity.class);
            startActivity(intent);
        } else if( v == btnSavaFile ){
            // 매크로 이름, 기어로부터 받아야할 인풋 개수 입력받기

            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MenuActivity.this);
            alert_confirm.setTitle("매크로 등록");
            alert_confirm.setMessage("매크로 이름을 입력해주세요.");
            if(myApp.getEventCnt()-1 == 0){
                Toast. makeText(MenuActivity.this, "등록된 이벤트가 없어 저장할 수 없습니다!", Toast.LENGTH_SHORT ).show();
            } else{
                final EditText macroName = new EditText(this);
                alert_confirm.setView(macroName);
                alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final String macro_name = macroName.getText().toString();
                        Log.i("name", macro_name);

                        // 내부 디비에 Global -> macroCmd 저장하기
                        String commandLine = "";
                        for (int ii = 0; ii < myApp.getEventCnt()-1; ii++) {
                            commandLine += myApp.getEventCmdList(ii);
                            if (ii + 1 != myApp.getEventCnt()) {
                                commandLine += ",";
                            }
                        }
                        // DB에 데이터 등록하기
                        // macgoCmd 와 위에 입력 받은 2개의 데이터 저장하기
                        db = helper.getWritableDatabase(); // 읽고 쓸수 있는 DB
                        String db_data = "insert into mytable2 (command, description, option) values(" + "'" + commandLine + "', '" + macro_name +  "', '" + myApp.getInputDataCnt() + "');";
                        db.execSQL(db_data);
                        Toast.makeText(getApplicationContext(), "매크로 저장 완료.", Toast.LENGTH_SHORT).show();
                        myApp.resetData();
                        finish();
                        //Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                        //startActivity(intent);
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
        } else if( v == btnTime ){
            // 시간 주기
            // 다이얼 로그 창을 통해 시간만 입력 받은 후 내부 DB에 저장해 놓기
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MenuActivity.this);
            alert_confirm.setTitle("Insert Delay Time");
            alert_confirm.setMessage("딜레이할 시간을 입력해주세요.");

            final EditText macroTime = new EditText(this);
            alert_confirm.setView(macroTime);
            macroTime.setInputType(0x00000002);
            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    final String macro_time = macroTime.getText().toString();
                    Log.i("name", macro_time);
                    String intentCmd = "TIME|"+ macro_time;  // TIME | 딜레이할 시간
                    // 데이터 변경
                    myApp.setEventCmdList(myApp.getEventCnt() - 1, intentCmd);
                    myApp.setArrLog(myApp.getEventCnt() - 1, macro_time + "초만큼 딜레이");
                    myApp.setEventCnt(myApp.getEventCnt() + 1);
                    Toast. makeText(MenuActivity.this, "딜레이 시간 지정 이벤트 저장 완료!", Toast.LENGTH_SHORT ).show();

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
        } else if( v == btnData ){
            // 데이터 넣기
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MenuActivity.this);
            alert_confirm.setTitle("Insert Input Data");
            alert_confirm.setMessage("데이터를 삽입하시겠습니까?");

            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // 데이터 변경
                    myApp.setEventCmdList(myApp.getEventCnt()-1, "INPUT");
                    myApp.setArrLog(myApp.getEventCnt() - 1, "기어로 부터 받은 데이터 삽입");
                    myApp.setEventCnt(myApp.getEventCnt() + 1);
                    myApp.increaseInputDataCnt();
                    Toast. makeText(MenuActivity.this, "데이터 삽입 이벤트 저장 완료!", Toast.LENGTH_SHORT ).show();
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
        } else if( v == btnHW ){
            // 하드웨어 설정 액티비티로 전환
            Intent intent = new Intent(MenuActivity.this, HWActivity.class);
            startActivity(intent);
        }
    }

    // back key 처리
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        final GlobalVal myApp = (GlobalVal) getApplication();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // 팝업 띄우기
                // 밑에 라인 체크하기
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MenuActivity.this);
                alert_confirm.setMessage("이벤트가 초기화됩니다. 그래도 메인 화면으로 돌아가시겠습니까?").setCancelable(false).setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'YES'
                                myApp.resetData();
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
