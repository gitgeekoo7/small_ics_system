package com.cesecsh.small_ics_system.util;

import lombok.Getter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 分组统计类型
 */
@Getter
public enum AddupGroupByType {
    DAY("本日", "hour(create_time)"),
    WEEK("本周", "dayofweek(create_time)"),
    MONTH("本月", "dayofmonth(create_time)"),
    YEAR("本年度", "month(create_time)"),
    QUARTER("季度", "quarter(create_time)");

    AddupGroupByType(String name, String group) {
        this.name = name;
        this.group = group;
    }

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private String name;
    private String group;

    public String getStartTime(Date date) {
        switch (this) {
            case DAY:
                date = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
                break;
            case WEEK:
                Calendar calendar = DateUtils.toCalendar(date);
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                date = DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);
                break;
            case MONTH:
                date = DateUtils.truncate(date, Calendar.MONTH);
                break;
            case YEAR:
            case QUARTER:
                date = DateUtils.truncate(date, Calendar.YEAR);
                break;
        }
        return DateFormatUtils.format(date, PATTERN);
    }
}
