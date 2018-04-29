package com.pdfdocumentreader;

import java.io.Serializable;

public class FileModel implements Serializable {
    String name, path, size;

    public FileModel(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public FileModel(String name, String path, String size) {
        this.name = name;
        this.path = path;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
