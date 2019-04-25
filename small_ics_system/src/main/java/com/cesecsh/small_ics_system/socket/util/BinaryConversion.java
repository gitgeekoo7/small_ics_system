package com.cesecsh.small_ics_system.socket.util;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLEncoder;

/**
 * 进制转换
 */
public class BinaryConversion {
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String byteArrayToStringHex1(byte[] byteArray, int begin, int count) {
        try {
            StringBuffer r = new StringBuffer(count);
            for (int i = 0; i < count; i++) {
                r.append(hexCode[(byteArray[begin + i] >> 4) & 0xf]);
                r.append(hexCode[(byteArray[begin + i] & 0xf)]);
            }
            return r.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * byte[4] �? 整数
     * 高低位互�?
     *
     * @param bt  数组�?
     * @param num 数组截取起始下标
     * @return long
     */
    public static long byteToLong14(byte[] bt, int num) {
        long number = ((long) bt[num + 13] & 0xff)<<13*8;
        num += 1;
        for(int i=12; i>=0; i--){
            number = number | ((long) bt[num + i] & 0xff)<<i*8;
        }
        return number;
    }

    /**
     * byte[4] �? 整数
     * 高低位互�?
     *
     * @param bt  数组�?
     * @param num 数组截取起始下标
     * @return long
     */
    public static long byteToLong4(byte[] bt, int num) {
        return ((long) bt[num + 3] & 0xff) << 24 | ((long) bt[num + 2] & 0xff) << 16 | ((long) bt[num + 1] & 0xff) << 8 | bt[num] & 0xff;
    }
    public static String byteToLong4toIc(byte[] bt, int num) {
        String str = "0000000000"+(((long) bt[num + 3] & 0xff) << 24 | ((long) bt[num + 2] & 0xff) << 16 | ((long) bt[num + 1] & 0xff) << 8 | bt[num] & 0xff);
        return str.substring(str.length()-10);
    }

    /**
     * byte[2] �? 整数
     * 高低位互�?
     *
     * @param bt  数组�?
     * @param num 数组截取起始下标
     * @return long
     */
    public static long byteToLong2(byte[] bt, int num) {
        return ((long) bt[num + 1] & 0xff) << 8 | bt[num] & 0xff;
    }

    /**
     * byte[1] �? 整数
     *
     * @param bt  数组�?
     * @param num 数组截取起始下标
     * @return long
     */
    public static long byteToLong1(byte[] bt, int num) {
        return bt[num] & 0xff;
    }

    /**
     * 16进制字符�? �? 字符串字节数�?
     *
     * @param hexString �?16进制字符�?
     * @return byte[]
     */
    public static byte[] hexStringToByteArray(String hexString) {
        byte backByte[] = new byte[hexString.length() / 2];
        for (int i = 0; i < backByte.length; i++) {
            backByte[i] = (byte) Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16);
        }
        return backByte;
    }

    /**
     * 字符串转换成�?16进制(无需Unicode编码)
     *
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(hexCode[bit]);
            bit = bs[i] & 0x0f;
            sb.append(hexCode[bit]);
        }
        return sb.toString().trim();
    }

    /**
     * 字符串转换成�?16进制(无需Unicode编码)
     *
     * @param str
     * @return
     */
    public static String str2HexStrForUtf8toGBK(String str) {
        StringBuffer strBufer = new StringBuffer();
        char[] myBuffer = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
            if (ub == UnicodeBlock.BASIC_LATIN) {
                //英文、数�?
                byte[] bs = str.substring(i, i + 1).getBytes();
                int bit;
                for (int z = 0; z < bs.length; z++) {
                    bit = (bs[z] & 0x0f0) >> 4;
                    strBufer.append(hexCode[bit]);
                    bit = bs[z] & 0x0f;
                    strBufer.append(hexCode[bit]);
                }
            } else {
                //汉字
                try {
                    strBufer.append(URLEncoder.encode(str.substring(i, i + 1), "gbk").replaceAll("%", ""));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return strBufer.toString();
    }

    /**
     * StringInt �? 16进制字符�?
     *
     * @return 16进制字符�?
     */
    public static String longNumberToStringHex(String intNum) {
        String str = Integer.toHexString(Integer.valueOf(intNum)).toUpperCase();
        if (str.length() != 2) {
            return "0" + str;
        } else {
            return str;
        }
    }

    /**
     * StringInt �? 16进制字符�?
     *
     * @return 16进制字符�?
     */
    public static String longNumberTo2bStringHex(String intNum) {
        String str = Integer.toHexString(Integer.valueOf(intNum)).toUpperCase();
        switch (str.length()) {
            case 1: {
                return "0" + str + "00";
            }
            case 2: {
                return str + "00";
            }
            case 3: {
                return str.substring(1) + "0" + str.substring(0, 1);
            }
            case 4: {
                return str.substring(2) + str.substring(0, 2);
            }
        }
        return "0000";
    }

    /**
     * byte[]  ? String
     *
     * @param byteArray 数组 ?
     * @param begin     起始下标
     * @param count     循环次数
     * @return String
     */
    public static String byteArrayToStr(byte[] byteArray, int begin, int count) {
        byte[] newByte = new byte[count];
        System.arraycopy(byteArray, begin, newByte, 0, count);
        if (newByte == null) {
            return "无数据";
        }
        try {
            return new String(newByte,"gbk").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            for(int i=0,ii=newByte.length; i<ii; i++) {
                System.out.print(newByte[i]+",");
            }
            System.out.println(byteArrayToStringHex1(newByte,0,newByte.length));
            System.out.println(new String(newByte).trim());
            return "数据异常";
        }
    }
}
