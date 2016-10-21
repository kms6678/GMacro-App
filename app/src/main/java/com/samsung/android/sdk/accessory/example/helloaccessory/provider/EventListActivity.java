package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EventListActivity extends Activity implements View.OnClickListener{

    private ListView m_ListView;
    private ArrayAdapter<String> m_Adapter;
    Button btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        GlobalVal myApp = (GlobalVal) getApplication();

        btnDel = (Button)findViewById(R.id.삭제버튼);
        btnDel.setOnClickListener(this);
        // 리스트 뷰를 통해 이 때까지 축적한 스크립트 파일 Log 형태로 보여주기!
        // 예) 1. x1, y1 좌표에 터치 이벤트.
        //     2. 2초간 슬립.
        //     3. x2, y2 좌표에 터치 이벤트.

        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성
        //m_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        m_Adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.simpleitem);
        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listview);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        // ListView 모드 변경
        m_ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // ListView에 아이템 추가
        if(myApp.getEventCnt() == 1){
            Toast.makeText(getApplicationContext(), "등록된 이벤트가 없습니다!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            for(int ii = 0; ii < myApp.getEventCnt()-1; ii++){
                m_Adapter.add(myApp.getArrLog(ii));
                Log.i("로그확인3", myApp.getArrLog(ii));
            }
        }
    }

    public void onClick(View v) {
        final GlobalVal myApp = (GlobalVal) getApplication();
        if( v == btnDel ){
            final int limit = myApp.getEventCnt()-1;
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(EventListActivity.this);
            alert_confirm.setMessage("선택된 이벤트를 삭제하시겠습니까?").setCancelable(false).setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'YES'
                            for(int ii = limit-1; ii >= 0; ii--){
                                if(m_ListView.isItemChecked(ii)) {
                                    myApp.deleteEvent(ii);
                                    if(myApp.getArrLog(ii).equals("기어로 부터 받은 데이터 삽입")){
                                        myApp.decreaseInputDataCnt();
                                    }
                                    myApp.deleteLog(ii);
                                    myApp.setEventCnt(myApp.getEventCnt() - 1);
                                }
                            }
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
        }
    }
    private void refresh() {
        m_Adapter.notifyDataSetChanged() ;
    }

}
