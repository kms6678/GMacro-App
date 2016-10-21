package com.samsung.android.sdk.accessory.example.helloaccessory.provider;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by KCH on 2016-02-25.
 */
public class GlobalVal extends Application {
    private int eventCnt;
    private String eventCmd;
    private ArrayList<String> eventCmdList;
    private String macroCmd;
    private ArrayList<String> arrLog;
    // 데이터 입력 개수 체크하는 글로벌 변수
    private int inputDataCnt;

    public void resetData(){
        eventCnt = 1;
        inputDataCnt= 0;
        eventCmd = "";
        macroCmd = "";
        if(eventCmdList!=null) {
            eventCmdList.clear();
        }else {
            eventCmdList = new ArrayList<String>();
        }
        if(arrLog!=null) {
            arrLog.clear();
        }else {
            arrLog = new ArrayList<String>();
        }
    }
    public void setEventCnt(int val){
        eventCnt = val;
    }
    public int getEventCnt(){
        return eventCnt;
    }

    public void increaseInputDataCnt(){
        inputDataCnt++;
    }
    public int getInputDataCnt(){
        return inputDataCnt;
    }
    public void decreaseInputDataCnt(){
        inputDataCnt--;
    }

    public void setEventCmd(String val){
        eventCmd = val;
    }
    public String getEventCmd(){
        return eventCmd;
    }

    // cmdList로 부터 받아 올 수 있게 변경해야함.
    public void setMacroCmd(String val){
        if(macroCmd == ""){
            macroCmd = val;
        }
        else{
            macroCmd += "," + val;
        }
    }
    public String getMacroCmd(){
        return macroCmd;
    }

    public void setArrLog(int idx, String val){
        arrLog.add(idx, val);
    }
    public String getArrLog(int idx){
        return arrLog.get(idx);
    }
    public void deleteLog(int idx){
        // 뒤에 내용이 자동으로 넘어가나?
        arrLog.remove(idx);
    }

    public void setEventCmdList(int idx, String val){
        eventCmdList.add(idx, val);
    }
    public String getEventCmdList(int idx){
        return eventCmdList.get(idx);
    }
    public void deleteEvent(int idx){
        // 뒤에 내용이 자동으로 넘어가나?
        eventCmdList.remove(idx);
    }
}
