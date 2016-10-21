package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowProcessListActivity extends Activity {

    private ArrayList<AppInfo> mListData = new ArrayList<AppInfo>();

    private static final String TAG = ShowProcessListActivity.class.getSimpleName();
    // 메뉴 KEY
    private final int MENU_DOWNLOAD = 0;
    private final int MENU_ALL = 1;
    private int MENU_MODE = MENU_DOWNLOAD;

    private PackageManager pm;

    private View mLoadingContainer;
    private ListView mListView = null;
    private IAAdapter mAdapter = null;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_process_list);

        mLoadingContainer = findViewById(R.id.loading_container);
        mListView = (ListView) findViewById(R.id.listView1);

        mAdapter = new IAAdapter(ShowProcessListActivity.this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(ListViewExampleClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 작업 시작
        startTask();
    }

    /**
     * 작업 시작
     */
    private void startTask() {
        new AppTask().execute();
    }

    /**
     * 로딩뷰 표시 설정
     *
     * @param isView
     *            표시 유무
     */
    private void setLoadingView(boolean isView) {
        if (isView) {
            // 화면 로딩뷰 표시
            mLoadingContainer.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            // 화면 어플 리스트 표시
            mListView.setVisibility(View.VISIBLE);
            mLoadingContainer.setVisibility(View.GONE);
        }
    }

    /**
     * List Fast Holder
     *
     * @author nohhs
     */
    private class ViewHolder {
        // App Icon
        public ImageView mIcon;
        // App Name
        public TextView mName;
        // App Package Name
        public TextView mPacakge;
    }

    /**
     * List Adapter
     *
     * @author nohhs
     */
    private class IAAdapter extends BaseAdapter {
        private Context mContext = null;

        private List<ApplicationInfo> mAppList = null;

        public IAAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public int getCount() {
            return mListData.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_layout, null);

                holder.mIcon = (ImageView) convertView
                        .findViewById(R.id.app_icon);
                holder.mName = (TextView) convertView
                        .findViewById(R.id.app_name);
                holder.mPacakge = (TextView) convertView
                        .findViewById(R.id.app_package);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AppInfo data = mListData.get(position);

            if (data.mIcon != null) {
                holder.mIcon.setImageDrawable(data.mIcon);
            }

            holder.mName.setText(data.mAppNaem);
            holder.mPacakge.setText(data.mAppPackge);

            return convertView;
        }

        /**
         * 어플리케이션 리스트 작성
         */
        public void rebuild() {
            if (mAppList == null) {

                Log.d(TAG, "Is Empty Application List");
                // 패키지 매니저 취득
                pm = ShowProcessListActivity.this.getPackageManager();

                // 설치된 어플리케이션 취득
                mAppList = pm
                        .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
                                | PackageManager.GET_DISABLED_COMPONENTS);
            }

            AppInfo.AppFilter filter;
            switch (MENU_MODE) {
                case MENU_DOWNLOAD:
                    filter = AppInfo.THIRD_PARTY_FILTER;
                    break;
                default:
                    filter = null;
                    break;
            }

            if (filter != null) {
                filter.init();
            }

            // 기존 데이터 초기화
            mListData.clear();

            AppInfo addInfo = null;
            ApplicationInfo info = null;
            for (ApplicationInfo app : mAppList) {
                info = app;

                if (filter == null || filter.filterApp(info)) {
                    // 필터된 데이터

                    addInfo = new AppInfo();
                    // App Icon
                    addInfo.mIcon = app.loadIcon(pm);
                    // App Name
                    addInfo.mAppNaem = app.loadLabel(pm).toString();
                    // App Package Name
                    addInfo.mAppPackge = app.packageName;

                    addInfo.mAppClass = app.name;

                    mListData.add(addInfo);
                }
            }

            // 알파벳 이름으로 소트(한글, 영어)
            Collections.sort(mListData, AppInfo.ALPHA_COMPARATOR);
        }
    }

    /**
     * 작업 태스크
     * @author nohhs
     */
    private class AppTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // 로딩뷰 시작
            setLoadingView(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // 어플리스트 작업시작
            mAdapter.rebuild();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // 어댑터 갱신
            mAdapter.notifyDataSetChanged();

            // 로딩뷰 정지
            setLoadingView(false);
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_DOWNLOAD, 1, R.string.menu_download);
        menu.add(0, MENU_ALL, 2, R.string.menu_all);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (MENU_MODE == MENU_DOWNLOAD) {
            menu.findItem(MENU_DOWNLOAD).setVisible(false);
            menu.findItem(MENU_ALL).setVisible(true);
        } else {
            menu.findItem(MENU_DOWNLOAD).setVisible(true);
            menu.findItem(MENU_ALL).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == MENU_DOWNLOAD) {
            MENU_MODE = MENU_DOWNLOAD;
        } else {
            MENU_MODE = MENU_ALL;
        }

        startTask();

        return true;
    }

    public AdapterView.OnItemClickListener ListViewExampleClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parentView, final View clickedView, final int position, long id)
        {
            final GlobalVal myApp = (GlobalVal) getApplication();
            // 팝업 띄우기
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ShowProcessListActivity.this);
            alert_confirm.setTitle("Process Event");
            alert_confirm.setMessage("종료 or 실행?");

            final EditText status = new EditText(ShowProcessListActivity.this);
            alert_confirm.setView(status);
            alert_confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    final String processStatus = status.getText().toString();
                    String cmd;
                    AppInfo tempAppInfo = mListData.get(position);
                    PackageManager pm = getPackageManager();
                    Intent launch = pm.getLaunchIntentForPackage(tempAppInfo.mAppPackge);

                    String tempClassName = launch.getComponent().getClassName().substring(tempAppInfo.mAppPackge.length());
                    String log = tempAppInfo.mAppNaem;

                    if (processStatus.equals("종료")) {
                        cmd = "PROCESSKILL|" + tempAppInfo.mAppPackge ;
                        myApp.setArrLog(myApp.getEventCnt() - 1, log + " 앱 종료");
                    } else {    // 실행
                        cmd = "PROCESSRUN|" + tempAppInfo.mAppPackge + "|" + tempClassName;
                        myApp.setArrLog(myApp.getEventCnt() - 1, log + " 앱 실행");
                    }

                    myApp.setEventCmdList(myApp.getEventCnt() - 1, cmd);
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
    };
}
