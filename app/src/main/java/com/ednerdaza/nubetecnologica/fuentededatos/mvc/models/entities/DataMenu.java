package com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class DataMenu implements Serializable {
    public int id;
    public String kind;
    public String title;
    public ArrayList<DataList> dataList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DataList> dataList) {
        this.dataList = dataList;
    }
}
