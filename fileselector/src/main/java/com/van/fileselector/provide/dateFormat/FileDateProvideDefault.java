package com.van.fileselector.provide.dateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileDateProvideDefault extends FileDateProvide {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private Date date = new Date();

    @Override
    public String formatDate(long modifyTime) {
        date.setTime(modifyTime);
        return format.format(date);
    }
}
