package com.cesecsh.small_ics_system.socket.work;

import com.cesecsh.small_ics_system.socket.util.BinaryConversion;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class TcpMsgWork {

    private ExecutorService service;
    private TcpMsgWork() { service = Executors.newCachedThreadPool(); }

    private static TcpMsgWork instance = new TcpMsgWork();
    public static TcpMsgWork getInstance() { return instance; }

    public String submit(byte[] tcpMsg){
        service.submit(new Runnable() {
            @Override
            public void run() {
                byte[] msg = new byte[(int) BinaryConversion.byteToLong4(tcpMsg, 8)];
                doWork(BinaryConversion.byteArrayToStringHex1(tcpMsg, 12, 4),
                        subBytes(msg,tcpMsg));
            }
        });
        // 返回序列号+功能码
        return BinaryConversion.byteArrayToStringHex1(tcpMsg, 12, 4)
                + BinaryConversion.byteArrayToStringHex1(tcpMsg, 20, 4);
    }

    private void doWork(String serial, byte[] msg){
        switch(BinaryConversion.byteArrayToStringHex1(msg, 16, 4)
                + BinaryConversion.byteArrayToStringHex1(msg, 20, 4)){
            // 传感器状态或值
            case "01000000":{
                cut01000000(serial,msg);
                break;
            }
            // 上传定时传感器值
            case "12010000":{
                cut12010000(serial,msg);
                break;
            }
            default:
                break;
        }
    }
    private byte[] subBytes(byte[] msg, byte[] src) {
        try {
            System.arraycopy(src, 52, msg, 0, msg.length);
            return msg;
        } catch (Exception e) {
            return null;
        }
    }

    private static void cut01000000(String serial, byte[] byteArray){
        try {
            int size = (int)(5+BinaryConversion.byteToLong1(byteArray,4));
            //通道编号
            String address = BinaryConversion.byteArrayToStringHex1(byteArray, 0, size);
            // 设备值 "0203"温度湿度，特殊处理
            String value = "0203".equals(BinaryConversion.byteArrayToStringHex1(byteArray, size, 2))
                    ? BinaryConversion.byteToLong2(byteArray, size+2)/10+","+BinaryConversion.byteToLong2(byteArray, size+4)/10
                    : String.valueOf(BinaryConversion.byteToLong4(byteArray, size+2));
            switch(BinaryConversion.byteArrayToStringHex1(byteArray, size, 2)){
                //双联开关灯
                case "0102":
                //PWM调光灯
                case "0202":
                //档位调光灯
                case "0302":
                //普通灯
                case "0402":
                //485通道电能采集
                case "0303":{
                    // TODO Auto-generated catch block
                    // 此处固定电阻=10，变比=2000
                    break;
                }
                //门禁状态
                case "0501":
                //温度湿度传感器
                case "0203":
                //空气质量
                case "0403":
                //光照感应
                case "0603":
                //风扇状态
                case "0903":
                //AD通道值
                case "FFFF":{
                    // TODO Auto-generated catch block
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return;
        }
    }

    private static void cut12010000(String serial, byte[] byteArray) {
        try {
            for(int i=0; i<(byteArray.length-4)/12; i++){
                byte[] newByte = new byte[12];
                System.arraycopy(byteArray, (i*12+4), newByte, 0, 12);
                try {
                    cut01000000(serial,newByte);
                } catch (Exception e) {
                    log.info("[TODO cut12010000 |> Serial:"+serial+" |传感器值解析失败_Data:"+BinaryConversion.byteArrayToStringHex1(newByte, 0, newByte.length)+"]");
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.info("[TODO cut12010000 |> Serial:"+serial+" |处理异常_Data:"+BinaryConversion.byteArrayToStringHex1(byteArray, 0, byteArray.length)+"]");
            return;
        }
    }
}
