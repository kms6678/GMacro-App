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

public class TouchActivity extends Activity {

    final int REQ_CODE_SELECT_IMAGE=100;
//    GlobalVal myApp = (GlobalVal) getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

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

            final float x = event.getX();
            final float y = event.getY();
            String msg = "좌표 : (" + x + " ,  " + y + ") 위치에 터치 이벤트 발생!";
            Toast. makeText(TouchActivity.this, msg, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(TouchActivity.this);
            alert_confirm.setMessage("터치 이벤트를 저장하시겠습니까?").setCancelable(false).setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'YES'
                            // 데이터 저장하기 -> 내부 DB -> SQL
                            // 좌표 저장 후 로그도 따로 저장해놓기! -> 이벤트 리스트에서 확인 가능하게끔.
                            Toast. makeText(TouchActivity.this, "터치 이벤트 저장 완료!", Toast.LENGTH_SHORT ).show();
                            finish();
                            //Intent intent = new Intent(TouchActivity.this, MenuActivity.class);

                            String intentLog = "";
                            String intentCmd = "";
                            // 전달할 데이터 삽입
                            // EventList에서 볼 Log 와 내부 DB 커맨드 라인에 저장될 Cmd 저장.
                            intentLog = "(" + Float.toString(x) + ", " + Float.toString(y) + ")" + " 좌표에 터치 이벤트";
                            Log.i("로그확인", intentLog);
                            intentCmd = "TOUCH|"+ Float.toString(x) + "|" + Float.toString(y);  // 0 구분자 x좌표 구분자 y좌표
                            // 데이터 변경
                            myApp.setEventCmdList(myApp.getEventCnt()-1, intentCmd);
                            myApp.setArrLog(myApp.getEventCnt()-1, intentLog);
                            //myApp.setMacroCmd(intentCmd);
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
        return false;
    }
}
