package com.ykh.calendarview.listener;

import android.view.View;

import com.ykh.calendarview.DateBean;

/**
 * 日期点击接口
 */
public interface OnMonthItemClickListener {
    /**
     * @param view
     * @param date
     */
    void onMonthItemClick(View view, DateBean date);
}
