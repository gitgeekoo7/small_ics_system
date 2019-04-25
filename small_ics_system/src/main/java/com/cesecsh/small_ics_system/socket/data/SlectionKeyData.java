package com.cesecsh.small_ics_system.socket.data;

import com.cesecsh.small_ics_system.socket.util.BinaryConversion;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class SlectionKeyData {
    private Map<String,SelectionKey> keyMap;

    private SlectionKeyData() {
        keyMap = new HashMap<>();
    }

    private static SlectionKeyData instance = new SlectionKeyData();
    public static SlectionKeyData getInstance(){
        return instance;
    }

    public void putKey(String serial,SelectionKey key){
        keyMap.put(serial,key);
    }

    public void removeKey(String serial){
        keyMap.remove(serial);
    }

    public void getKeySendMsg(String serial,String msg) throws IOException {
        if (!keyMap.get(serial).isValid()){
            keyMap.remove(serial);
            return;
        }
        // 创建发送数据的缓存区并写入数据
        ByteBuffer outputBuffer = ByteBuffer.allocate(300);
        outputBuffer.put(BinaryConversion.hexStringToByteArray(msg.toUpperCase()));
        outputBuffer.flip();

        // 向客户端发送反馈数据
        SocketChannel sc = (SocketChannel) keyMap.get(serial).channel();
        sc.write(outputBuffer);
    }
}
