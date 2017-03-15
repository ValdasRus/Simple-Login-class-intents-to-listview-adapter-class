package com.example.valdas.loginas;

import android.graphics.Bitmap;

public class RowItem {
    private Bitmap imageId;
    private String title;

    public RowItem(Bitmap imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }
    public Bitmap getImageId() {
        return imageId;
    }
    public String getTitle() {
        return title;
    }
    @Override
    public String toString() {
        return title + "\n";
    }
}