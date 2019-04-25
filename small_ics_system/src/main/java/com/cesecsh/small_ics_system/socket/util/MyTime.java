package com.cesecsh.small_ics_system.socket.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class MyTime {

    static public String currentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date();
        String date = df.format(dt);
        return date;
    }

    static public String currentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        String date = df.format(dt);
        return date;
    }

    static public String currentUTC() {
        long time = System.currentTimeMillis();
        return String.valueOf(time);
    }

    static public String timeStringToUTCString(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(str).getTime() / 1000 + "";
        } catch (ParseException e) {
            System.out.println(str);
            System.err.println("[Time:" + MyTime.currentTime() + " -- ERR:时间字符串转换utc时间异常!]");
            return "0000-00-00 00:00:00";
        }
    }

    static public String utcTimeToTimeString(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(new Long(str) * 1000));
    }

    static public String periodOfValidityDate(int restDay) {
        Calendar cal = Calendar.getInstance();// 使用默认时区和语�?环境获得当前时间
        cal.add(Calendar.DAY_OF_MONTH, +restDay);// 取当前时间往后restDay天的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(cal.getTime());
        return date;
    }

    public static String hexTimeToStringTime(String str) {
        StringBuffer sbf = new StringBuffer();
        StringTokenizer st = new StringTokenizer(str, "|");
        str = currentDate().substring(0, 2);
        while (st.hasMoreElements()) {
            sbf.append(st.nextToken());
            if (sbf.length() < 2) {
                str += "0" + sbf.toString();
            } else {
                str += sbf.toString();
            }
            sbf.setLength(0);
        }
        return str.substring(0, 4)
                + "-" + str.substring(4, 6)
                + "-" + str.substring(6, 8)
                + " " + str.substring(8, 10)
                + ":" + str.substring(10, 12)
                + ":" + str.substring(12, 14);
    }

    public static String currentICSTime() {
        String yyyy = new SimpleDateFormat("yyyy").format(new Date());
        String mm = new SimpleDateFormat("MM").format(new Date());
        String dd = new SimpleDateFormat("dd").format(new Date());
        String time = new SimpleDateFormat("HHmmss").format(new Date());
        StringBuffer sbf = new StringBuffer();
        sbf.append(BinaryConversion.longNumberToStringHex(yyyy.substring(0, 2)) + BinaryConversion.longNumberToStringHex(yyyy.substring(2)));
        sbf.append(BinaryConversion.longNumberToStringHex(mm));
        sbf.append(BinaryConversion.longNumberToStringHex(dd));
        sbf.append(BinaryConversion.longNumberToStringHex(time.substring(0, 2)) + BinaryConversion.longNumberToStringHex(time.substring(2, 4)) + BinaryConversion.longNumberToStringHex(time.substring(4)));
//        int iWeek = (Integer.valueOf(dd) + 2 * Integer.valueOf(mm) + 3 * (Integer.valueOf(mm) + 1) / 5 + Integer.valueOf(yyyy) + Integer.valueOf(yyyy) / 4 - Integer.valueOf(yyyy) / 100 + Integer.valueOf(yyyy) / 400) % 7;
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case 1: {
                sbf.append("07");
                break;
            }
            case 2: {
                sbf.append("01");
                break;
            }
            case 3: {
                sbf.append("02");
                break;
            }
            case 4: {
                sbf.append("03");
                break;
            }
            case 5: {
                sbf.append("04");
                break;
            }
            case 6: {
                sbf.append("05");
                break;
            }
            case 7: {
                sbf.append("06");
                break;
            }
        }
        return sbf.toString();
    }
}
