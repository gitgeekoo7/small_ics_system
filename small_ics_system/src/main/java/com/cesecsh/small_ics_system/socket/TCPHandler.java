package com.cesecsh.small_ics_system.socket;

import com.cesecsh.small_ics_system.socket.util.BinaryConversion;
import com.cesecsh.small_ics_system.socket.work.TcpMsgWork;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TCPHandler implements Runnable {

    private final SelectionKey sk;
    private final SocketChannel sc;

    private Lock doLock;

    public TCPHandler(SelectionKey sk, SocketChannel sc) {
        this.sk = sk;
        this.sc = sc;
        this.doLock = new ReentrantLock();
    }

    @Override
    public void run() {
        if(!read())
            closeChannel();
    }

    private synchronized boolean read(){
        try {
            // 创建一个读取通道数据的缓冲区
            ByteBuffer inputBuffer = ByteBuffer.allocate(300);
            inputBuffer.clear();

            int sum = 0;
            while(sum<300){
                // 读取数据
                int numBytes = sc.read(inputBuffer);
                sum+=numBytes;
                if (numBytes == -1){
                    log.error("读取数据：[Warning!] A client has been closed.");
                    return false;
                }
            }

            byte[] bt = conver(inputBuffer);
            if(bt!=null && bt.length==300){
                sk.interestOps(SelectionKey.OP_WRITE);
                sk.selector().wakeup();
                return send(process(bt));
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //必须调用完后flip()才可以调用此方法
    private byte[] conver(ByteBuffer byteBuffer){
        byteBuffer.flip();
        int len = byteBuffer.limit() - byteBuffer.position();
        byte[] bytes = new byte[len];

        if(byteBuffer.isReadOnly()){
            return null;
        }else {
            byteBuffer.get(bytes);
        }
        return bytes;
    }
    private String process(byte[] byteArray) {
        // 判断序列号是否为空
        if (null == BinaryConversion.byteArrayToStringHex1(byteArray, 12, 4))
            return null;
        log.info(BinaryConversion.byteArrayToStringHex1(byteArray, 12, 4)
                + " >> " + BinaryConversion.byteArrayToStringHex1(byteArray, 0, byteArray.length));

        return TcpMsgWork.getInstance().submit(byteArray);
    }

    private boolean send(String msg) {
        try {
            // 创建发送数据的缓存区并写入数据
            ByteBuffer outputBuffer = ByteBuffer.allocate(300);
            outputBuffer.put(BinaryConversion.hexStringToByteArray(msg.toUpperCase()));
            outputBuffer.flip();

            doLock.lock();
            // 向客户端发送反馈数据
            sc.write(outputBuffer);
            // 通过key改变通道注册的事件类型
            sk.interestOps(SelectionKey.OP_READ);
            sk.selector().wakeup();
            doLock.unlock();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void closeChannel() {
        try {
            log.error("[Warning!] A client has been closed.");
            sk.cancel();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
