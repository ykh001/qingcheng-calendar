package com.ykh.calendar.entry;

/**
 * Created by 11297 on 2017-11-04.
 */

public class HistoryData {
    private String title;
    private String img;
    private String date;

    public HistoryData() {
    }

    public HistoryData(String title, String img, String date) {
        this.title = title;
        this.img = img;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HistoryData{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
