package com.cesecsh.small_ics_system.socket.util;

import com.cesecsh.small_ics_system.socket.data.SlectionKeyData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IcsMsgUtil {

    /**
     * ICS重启
     *
     * @param serial 序列号
     *
     * @return true:下发成功 / false:下发失败
     */
    public static boolean icsChongQi(String serial) {
        try {
            return SlectionKeyData.getInstance().getKeySendMsg(serial,
                    "7F0000000000000008000000" + serial + "000000000F0F0F0F00000000000000000000000000000000000000000000000023232323");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 网络校时
     *
     * @param serial 序列号
     *
     * @return true:下发成功 / false:下发失败
     */
    public static boolean wangLuoJiaoShi(String serial) {
        try {
            return SlectionKeyData.getInstance().getKeySendMsg(serial,
                    "7F0000000000000008000000" + serial + "000000000300000000000000000000000000000000000000000000000000000023232323" + MyTime.currentICSTime());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改IP
     *
     * @param serial      序列号
     * @param serviceIp   服务器ip
     * @param clientIp    客户端ip
     * @param gateway     网关
     * @param subnetMask  子网掩码
     * @return
     */
    public static boolean changeIcsIp(String serial, String serviceIp, String clientIp, String gateway, String subnetMask) {
        try {
            return SlectionKeyData.getInstance().getKeySendMsg(serial,
                    changeIp(serial, serviceIp, clientIp, gateway, subnetMask));
        } catch (Exception e) {
            return false;
        }
    }
    private static String changeIp(String serial, String serviceIp, String clientIp, String gateway, String subnetMask) {
        return "7F000000"
                + "00000000"
                + "08000000"
                + serial
                + "00000000"
                + "08040000"
                + "00000000"
                + "0000000000000000000000000000000000000000"
                + "23232323"
                + strNumberTo1bStringHex(serviceIp.split("\\."))
                + strNumberTo1bStringHex(clientIp.split("\\."))
                + strNumberTo1bStringHex(gateway.split("\\."))
                + strNumberTo1bStringHex(subnetMask.split("\\."));
    }
    private static String strNumberTo1bStringHex(String[] numByte) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0, ii = numByte.length; i < ii; i++) {
            String str = Integer.toHexString(Integer.valueOf(numByte[i])).toUpperCase();
            if (1 == str.length())
                strBuf.append("0" + str);
            else
                strBuf.append(str);
        }
        return strBuf.toString();
    }
}