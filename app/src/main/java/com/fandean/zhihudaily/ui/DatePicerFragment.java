package com.fandean.zhihudaily.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.fandean.zhihudaily.R;

import java.util.GregorianCalendar;

/**
 * Created by fan on 17-6-22.
 */

public class DatePicerFragment extends AppCompatDialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //方法一：
//        DatePicker mDatePicker = new DatePicker(getActivity());
        //如果需要在设备配置变更后，保留所选日期，需要设置ID
//        mDatePicker.setId();
        //方法二：
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);
        final DatePicker mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
//        mDatePicker.init(); 初始化，传入年月日参数
        mDatePicker.setMaxDate(GregorianCalendar.getInstance().getTimeInMillis());
        //知乎日报诞生于 2013 年 5 月 19 (月份以0开始)
        GregorianCalendar calendar = new GregorianCalendar(2013,4,19);
        mDatePicker.setMinDate(calendar.getTimeInMillis());
        //设置当前显示的时间

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("选择某天的知乎日报")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                GregorianCalendar calendar = new GregorianCalendar(year,month,day);
                                sendResult(Activity.RESULT_OK, calendar);
                            }
                        })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

    }

    //TODO 传入要显示的日期值
    // datePicker.updateDate(year, month, day);
    //datepicker.init(xxyear, xxmonth, xxday, null);


    public static final String EXTRA_DATE = "com.fandean.zhihudaily.date";

    private void sendResult(int resultCode, GregorianCalendar calendar){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,calendar);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
