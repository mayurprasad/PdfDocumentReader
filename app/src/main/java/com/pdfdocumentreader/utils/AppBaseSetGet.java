package com.pdfdocumentreader.utils;

/**
 * Created by mayur.p on 29/4/2018.
 */

public class AppBaseSetGet {
    int layoutResId;
    String toolbarTitle;

    public AppBaseSetGet(int layoutResId, String toolbarTitle) {
        this.layoutResId = layoutResId;
        this.toolbarTitle = toolbarTitle;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public String getToolbarTitle() {
        return toolbarTitle;
    }
}
