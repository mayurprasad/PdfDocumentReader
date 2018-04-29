package com.pdfdocumentreader.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by mayur.p on 7/27/2017.
 */

public class UtilsFunction {

    public boolean isNullString(String check_str) {
        boolean isNull = false;
        if (check_str != null) {
            if (check_str.trim().equals("null") || check_str.trim().equals("")) {
                isNull = true;
            }
        } else {
            isNull = true;
        }
        return isNull;
    }

    public String isNullStringReturnBlank(String check_str) {
        String str = check_str;
        boolean isNull = false;
        if (check_str != null) {
            if (check_str.trim().equals("null") || check_str.trim().equals("")) {
                str = "";
            }
        } else {
            str = "";
        }
        return str;
    }

    public void closeKeyboard(Activity activity) {
        try {
            // Check if no view has focus:
            View ketView = activity.getCurrentFocus();
            if (ketView != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ketView.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
