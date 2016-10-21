package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.*;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class CurveDragActivity extends Activity {

    float x1 = 0, x2 = 0, y1 = 0, y2 = 0;
    int flag = 0;
    final int REQ_CODE_SELECT_IMAGE=100;
    java.lang.Process process = null;
    Runtime eee = Runtime.getRuntime();

    //    GlobalVal myApp = (GlobalVal) getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve_drag);

        eee = Runtime.getRuntime();

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
                    eee.exec(new String[]{"su", "-c", "adb shell & getevent | grep /dev/input/event2: > /storage/extSdCard/1234/gmacro.txt"});
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
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        final GlobalVal myApp = (GlobalVal) getApplication();
       if(event.getAction() == MotionEvent.ACTION_UP) {
            String intentLog = "";
            String intentCmd = "";

            // 전달할 데이터 삽입
            // EventList에서 볼 Log 와 내부 DB 커맨드 라인에 저장될 Cmd 저장.
            intentLog = "패턴 이벤트";
            Log.i("로그확인", intentLog);
            intentCmd = "PATTERN";
            // 데이터 변경
            myApp.setEventCmdList(myApp.getEventCnt()-1, intentCmd);
            Log.i("로그확인1", myApp.getEventCmdList(myApp.getEventCnt() - 1));
            myApp.setArrLog(myApp.getEventCnt() - 1, intentLog);
            Log.i("로그확인2", myApp.getArrLog(myApp.getEventCnt() - 1));
            myApp.setEventCnt(myApp.getEventCnt()+1);
            //eee.exit(1);
           finish();
        }
        return true;
    }
}

