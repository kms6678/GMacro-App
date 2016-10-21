package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RotaryActivity extends Activity {

    private MySQLiteOpenHelper helper;
    String dbName = "st_file.db";
    private SQLiteDatabase db;
    int dbVersion = 1; // 데이터베이스 버전

    RbPreference pref;

    private ListView m_ListView;
    private ArrayAdapter<String> m_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotary);

        pref = new RbPreference(getApplicationContext());

        GlobalVal myApp = (GlobalVal) getApplication();

        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성
        //m_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        m_Adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simpleitem1);
        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listview);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        // ListView 아이템 터치 시 이벤트 추가 -> 삭제 및 수정 가능하도록?
        m_ListView.setOnItemClickListener(onClickListItem);

        // 내부 DB에서 매크로 정보를 가져와 Add 시키기
        helper = new MySQLiteOpenHelper(
                this,  // 현재 화면의 제어권자
                dbName,// db 이름
                null,  // 커서팩토리-null : 표준커서가 사용됨
                dbVersion);       // 버전

        db = helper.getWritableDatabase(); // 읽고 쓸수 있는 DB

        Cursor c = db.rawQuery("select * from mytable2;", null);
        int flag = 0;
        while (c.moveToNext())
        {
            flag = 1;
            //int id = c.getInt(0);
            String desc = c.getString(2);
            //String option = c.getString(3);
            //String temp = "ID : " + id + ", 이름 : " + desc + ", 입력 개수 : " + option;
            m_Adapter.add(desc);
        }
        if (flag == 0){
            finish();
            Toast. makeText(RotaryActivity.this, "등록된 매크로가 없습니다!", Toast.LENGTH_SHORT).show();
        }
    }

    // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
            final CharSequence[] event = {"오른쪽 휠", "왼쪽 휠"};
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(RotaryActivity.this);
            alt_bld.setIcon(R.drawable.icon);
            alt_bld.setTitle("Select a Rotary").setItems(event, new DialogInterface.OnClickListener(){    // 목록 클릭시 설정
                    public void onClick(DialogInterface dialog, int index){
                    if( index == 1 ){
                        String temp = m_Adapter.getItem(arg2);
                        pref.put("9999", temp);
                        Toast.makeText(RotaryActivity.this, "해당 매크로가 왼쪽 휠 로터리로 등록되었스니다.", Toast.LENGTH_SHORT).show();
                    }else if (index == 0 ){
                        String temp = m_Adapter.getItem(arg2);
                        pref.put("8888", temp);
                        Toast.makeText(RotaryActivity.this, "해당 매크로가 오른쪽 휠 로터리로 등록되었스니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AlertDialog alert = alt_bld.create();
            alert.show();
/*
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(RotaryActivity.this);
            // 이거 입력 대신 선택할 수 있게 바꾸기
            alert_confirm.setTitle("G-Macro Rotary");
            alert_confirm.setMessage("설정할 Rotary 번호를 입력해주세요");

            final EditText macroRotary = new EditText(RotaryActivity.this);
            alert_confirm.setView(macroRotary);
            macroRotary.setInputType(0x00000002);
            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    final String macro_rotary = macroRotary.getText().toString();
                    Log.i("name", macro_rotary);
                    if (macro_rotary.equals("9999")) {
                        String temp = m_Adapter.getItem(arg2);
                        pref.put("9999", temp);
                    } else if (macro_rotary.equals("8888")) {
                        String temp = m_Adapter.getItem(arg2);
                        pref.put("8888", temp);
                    } else {
                        Toast.makeText(RotaryActivity.this, "Rotarty 번호를 잘못입력하였습니다!!!", Toast.LENGTH_SHORT).show();
                    }
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
            alert.show();*/
        }

    };
}
