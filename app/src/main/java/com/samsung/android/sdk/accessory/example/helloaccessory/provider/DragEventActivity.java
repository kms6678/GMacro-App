package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

// Flag를 통해 2번 찍은 후 넘어가기?

public class DragEventActivity extends Activity {

    float x1 = 0, x2 = 0, y1 = 0, y2 = 0;
    int flag = 0;
    final int REQ_CODE_SELECT_IMAGE=100;

    //    GlobalVal myApp = (GlobalVal) getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_event);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();
        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView)findViewById(R.id.캡쳐이미지);
                    //배치해놓은 ImageView에 set
                    image.setImageBitmap(image_bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final GlobalVal myApp = (GlobalVal) getApplication();
        // TODO Auto-generated method stub
        super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_DOWN ){

            if(flag == 0){
                x1 = event.getX();
                y1 = event.getY();
                // 좌표 위치 정보 출력
                String msg = "좌표 : (" + x1 + " ,  " + y1 + ") 위치에 시작 위치 설정!";
                Toast. makeText(DragEventActivity.this, msg, Toast.LENGTH_SHORT).show();
                flag++;
            } else if(flag == 1){
                x2 = event.getX();
                y2 = event.getY();
                flag = 0;
                // 좌표 위치 정보 출력
                String msg = "좌표 : (" + x2 + " ,  " + y2 + ") 위치에 끝 위치 설정!";
                Toast. makeText(DragEventActivity.this, msg, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(DragEventActivity.this);
                alert_confirm.setMessage("드래그 이벤트를 저장하시겠습니까?").setCancelable(false).setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'YES'
                                Toast. makeText(DragEventActivity.this, "드래그 이벤트 저장 완료!", Toast.LENGTH_SHORT ).show();
                                finish();
                                //Intent intent = new Intent(DragEventActivity.this, MenuActivity.class);

                                String intentLog = "";
                                String intentCmd = "";

                                // 전달할 데이터 삽입
                                // EventList에서 볼 Log 와 내부 DB 커맨드 라인에 저장될 Cmd 저장.
                                intentLog = "시작점 : (" + Float.toString(x1) + "," + Float.toString(y1) + ")부터 끝점 : (" + Float.toString(x2) + "," + Float.toString(y2) + ")까지 드래그 이벤트";
                                Log.i("로그확인", intentLog);
                                intentCmd = "DRAG|"+ Float.toString(x1) + "|" + Float.toString(y1) + "|" + Float.toString(x2) + "|" + Float.toString(y2);
                                // 데이터 변경
                                myApp.setEventCmdList(myApp.getEventCnt()-1, intentCmd);
                                myApp.setArrLog(myApp.getEventCnt()-1, intentLog);
                                myApp.setEventCnt(myApp.getEventCnt()+1);

                                //startActivity(intent);
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
            }
        }
        return false;
    }
}
