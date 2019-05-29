package com.hebeu.meet.fragment;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;

import com.hebeu.meet.R;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

public class Util {

//    年月日
    static void setYearDate(final Activity mContext, final DateListener dateListener) {
        DatePicker picker = new DatePicker(mContext);
//        DateTimePicker picker = new DateTimePicker(mContext);//具体到时分，这样报错
        //获取当前时间
        Time time = new Time();
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int monthDay = time.monthDay;
        int hour = time.hour;
        int minute = time.minute;
        //设置样式
        picker.setLabel("年", "月", "日");
        //设置取消按钮文字颜色
        picker.setCancelTextColor(ContextCompat.getColor(mContext, R.color.gray_text));
        //设置取消按钮文字
        picker.setCancelText("取消");
        //设置取消按钮文字大小
        picker.setCancelTextSize(18);
        //设置顶部标题栏下划线颜色
        picker.setTopLineColor(ContextCompat.getColor(mContext, R.color.white));
        // 设置顶部标题栏颜色
        picker.setTopBackgroundColor(ContextCompat.getColor(mContext, R.color.title));
        //设置分割线颜色
        picker.setDividerColor(ContextCompat.getColor(mContext, R.color.line_gray));
        //设置确定按钮文字颜色
        picker.setSubmitTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        //设置确定按钮文字
        picker.setSubmitText("确定");
        picker.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        picker.setSubmitTextSize(18);
        //------------------ end---------------
        //设置时间区间
        picker.setRange(2000, 2020);
        //设置默认显示时间
        picker.setSelectedItem(year, month + 1, monthDay);
        //加入动画
        picker.setAnimationStyle(R.style.Animation_CustomPopup);
        //回传数据
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                dateListener.setYearDate(year, month, day);
            }
        });
        picker.show();

    }


    //    选择类型
    static void setTypeName(final Activity mContext, final TypeListener typeListener) {
        OptionPicker picker = new OptionPicker(mContext, new String[]{
                "约吃饭", "约跑步","约逛街","约学习","约游戏","约其他"
        });
        picker.setOffset(2);
        picker.setSelectedIndex(1);//默认选中项
        picker.setTextSize(15);
        //设置样式
        //设置取消按钮文字颜色
        picker.setCancelTextColor(ContextCompat.getColor(mContext, R.color.gray_text));
        //设置取消按钮文字
        picker.setCancelText("取消");
        //设置取消按钮文字大小
        picker.setCancelTextSize(18);
        //设置顶部标题栏下划线颜色
        picker.setTopLineColor(ContextCompat.getColor(mContext, R.color.white));
        // 设置顶部标题栏颜色
        picker.setTopBackgroundColor(ContextCompat.getColor(mContext, R.color.title));
        //设置分割线颜色
        picker.setDividerColor(ContextCompat.getColor(mContext, R.color.line_gray));
        //设置确定按钮文字颜色
        picker.setSubmitTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        //设置确定按钮文字
        picker.setSubmitText("确定");
        picker.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        picker.setSubmitTextSize(18);
        // -------------end---------------
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String typename) {
                typeListener.setTypeName(typename);
            }
        });
        picker.show();
    }
    //选择性别
    static void setSexName(final Activity mContext, final SexListener sexListener) {
        OptionPicker picker = new OptionPicker(mContext, new String[]{
                "男", "女","不限"
        });
        picker.setOffset(2);
        picker.setSelectedIndex(1);
        picker.setTextSize(15);
        //设置样式
        //设置取消按钮文字颜色
        picker.setCancelTextColor(ContextCompat.getColor(mContext, R.color.gray_text));
        //设置取消按钮文字
        picker.setCancelText("取消");
        //设置取消按钮文字大小
        picker.setCancelTextSize(18);
        //设置顶部标题栏下划线颜色
        picker.setTopLineColor(ContextCompat.getColor(mContext, R.color.white));
        // 设置顶部标题栏颜色
        picker.setTopBackgroundColor(ContextCompat.getColor(mContext, R.color.title));
        //设置分割线颜色
        picker.setDividerColor(ContextCompat.getColor(mContext, R.color.line_gray));
        //设置确定按钮文字颜色
        picker.setSubmitTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        //设置确定按钮文字
        picker.setSubmitText("确定");
        picker.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        picker.setSubmitTextSize(18);
        // ---------------------end-----------------
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String sexname) {
                sexListener.setSexName(sexname);
            }
        });
        picker.show();
    }


}
