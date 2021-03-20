package com.example.mywallet.adapters.TableViewAdapter_package;

import com.evrencoskun.tableview.sort.ISortableModel;

public class CellModel implements ISortableModel {
    private String mId;
    private Object mData;

    public CellModel(String mId, Object mData) {
        this.mId = mId;
        this.mData = mData;
    }

    public Object getData() {
        return mData;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public Object getContent() {
        return mData;
    }

}
