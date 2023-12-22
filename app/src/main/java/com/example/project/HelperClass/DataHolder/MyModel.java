package com.example.project.HelperClass.DataHolder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project.DataTable;
import com.example.project.HelperClass.Downloads.ResponseDjango;

import java.util.ArrayList;
import java.util.List;

public class MyModel extends ViewModel {
    private MutableLiveData<List<DataTable>> textLiveData = new MutableLiveData<>();
    private List<DataTable> dataTables;
    private MutableLiveData<DataTable> dataTable=new MutableLiveData<DataTable>();
    private MutableLiveData<Integer> DeletePositionInPending=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> responseDjangoMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> responseComponentMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> responseScheduleMutableLiveData=new MutableLiveData<>();

    public LiveData<List<DataTable>> getTextLiveData() {
        return textLiveData;
    }

    public int updateText(List<DataTable> activityData) {
        textLiveData.setValue(activityData);
        return 1;
    }
    public void setData(List<DataTable> dataTables){
        this.dataTables=dataTables;
    }
    public List<DataTable> getDataTables(){
        return dataTables;
    }

    public MutableLiveData<DataTable> getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable.setValue(dataTable);
    }

    public MutableLiveData<Integer> getDeletePositionInPending() {
        return DeletePositionInPending;
    }

    public void setDeletePositionInPending(Integer deletePositionInPending) {
        DeletePositionInPending.setValue(deletePositionInPending);
    }

    public MutableLiveData<ArrayList<String>> getResponseDjangoMutableLiveData() {
        return responseDjangoMutableLiveData;
    }

    public void setResponseDjangoMutableLiveData(ArrayList<String> responseDjangoMutableLiveData) {
        this.responseDjangoMutableLiveData.setValue( responseDjangoMutableLiveData);
    }

    public MutableLiveData<ArrayList<String>> getResponseComponentMutableLiveData() {
        return responseComponentMutableLiveData;
    }

    public void setResponseComponentMutableLiveData(ArrayList<String> responseComponentMutableLiveData) {
        this.responseComponentMutableLiveData.setValue(responseComponentMutableLiveData);
    }

    public MutableLiveData<ArrayList<String>> getResponseScheduleMutableLiveData() {
        return responseScheduleMutableLiveData;
    }

    public void setResponseScheduleMutableLiveData(ArrayList<String> responseScheduleMutableLiveData) {
        this.responseScheduleMutableLiveData.setValue(responseScheduleMutableLiveData);
    }
}
