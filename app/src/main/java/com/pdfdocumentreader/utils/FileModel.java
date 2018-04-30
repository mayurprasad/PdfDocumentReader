package com.pdfdocumentreader.utils;

import android.graphics.Bitmap;

import java.io.Serializable;

public class FileModel implements Serializable {
    String name, path, size;
    Bitmap bitmap;

    public FileModel(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public FileModel(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public FileModel(String name, String path, String size) {
        this.name = name;
        this.path = path;
        this.size = size;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getSize() {
        return size;
    }
}
