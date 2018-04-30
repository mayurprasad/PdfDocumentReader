package com.pdfdocumentreader.utils;

import java.io.File;

/**
 * Created by mayur.p on 29/4/2018.
 */

public class UtilsFunction {

    public static String getFileSize(File file) {
        // Get length of file in bytes
        long fileSizeInBytes = file.length();
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        return (fileSizeInMB > 1) ? "" + fileSizeInMB + " MB" : "" + fileSizeInKB + " KB";
    }

    public static boolean isNullString(String check_str) {
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

    public static String isNullStringReturnBlank(String check_str) {
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
}
