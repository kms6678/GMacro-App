package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MacroListActivity extends Activity implements View.OnClickListener{

    private MySQLiteOpenHelper helper;
    String dbName = "st_file.db";
    private SQLiteDatabase db;
    int dbVersion = 1; // 데이터베이스 버전

    int macroCnt = 0;
    private ListView m_ListView;
    private ArrayAdapter<String> m_Adapter;

    Button btnDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macro_list);

        GlobalVal myApp = (GlobalVal) getApplication();

        btnDel = (Button)findViewById(R.id.삭제버튼);
        btnDel.setOnClickListener(this);

        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성
        //m_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        m_Adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simpleitem);
        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listview);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        // ListView 모드 변경
        m_ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
            macroCnt++;
            flag = 1;
            //int id = c.getInt(0);
            String desc = c.getString(2);
            //String option = c.getString(3);
            //String temp = "ID : " + id + ", 이름 : " + desc + ", 입력 개수 : " + option;
            m_Adapter.add(desc);
        }
        if (flag == 0){
            finish();
            Toast. makeText(MacroListActivity.this, "등록된 매크로가 없습니다!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        GlobalVal myApp = (GlobalVal) getApplication();
        if( v == btnDel ){
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MacroListActivity.this);
            alert_confirm.setMessage("선택된 G-Macro를 삭제하시겠습니까?").setCancelable(false).setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'YES'
                            for(int ii = 0; ii < macroCnt; ii++){
                                if(m_ListView.isItemChecked(ii)) {
                                    String temp = m_Adapter.getItem(ii);
                                    Log.i("템프값", temp);
                                    String query = "delete from mytable2 where description = '" + temp +"'";
                                    db.execSQL(query);
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

}
