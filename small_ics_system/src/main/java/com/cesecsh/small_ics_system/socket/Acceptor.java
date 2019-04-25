package com.cesecsh.small_ics_system.socket;

import com.cesecsh.small_ics_system.socket.data.SlectionKeyData;
import com.cesecsh.small_ics_system.socket.util.BinaryConversion;
import com.cesecsh.small_ics_system.socket.work.TcpMsgWork;
import com.cesecsh.small_ics_system.vo.TbIcsSocketVo;
import com.cesecsh.small_ics_system.web.socket.IcsService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

@Slf4j
public class Acceptor implements Runnable {

    private Reactor reactor;

    public Acceptor(Reactor reactor) {
        this.reactor = reactor;
    }

    @Override
    public void run() {
        try {
            // 接受client连接请求
            SocketChannel sc = reactor.serverSocketChannel.accept();
            log.info(sc.socket().getRemoteSocketAddress().toString() + " is connected.");

            if (sc == null)
                return;
            sc.configureBlocking(false);
            // SocketChannel向selector注册一个OP_READ事件，然后返回该通道的key
            SelectionKey sk = sc.register(reactor.selector, SelectionKey.OP_READ);
            // 使一个阻塞住的selector操作立即返回
            reactor.selector.wakeup();

            if (read(sc,sk)){
                // 通过key为新的通道绑定一个回调的TCPHandler对象
                sk.attach(new TCPHandler(sk, sc));
            } else {
                closeChannel(sc,sk);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
    private synchronized boolean read(SocketChannel sc, SelectionKey sk){
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
                return send(sc,sk,process(sk,bt)); // 进一步处理获取的数据
            }   return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String process(SelectionKey sk, byte[] byteArray) throws IOException {
        log.info(">>" + BinaryConversion.byteArrayToStringHex1(byteArray, 0, byteArray.length));

        String serial = BinaryConversion.byteArrayToStringHex1(byteArray, 12, 4);
        if (serial == null) return null;

        SocketChannel channel = (SocketChannel) sk.channel();

        TbIcsSocketVo icsSocketVo = new TbIcsSocketVo();
        icsSocketVo.setSerial(serial);
        icsSocketVo.setVersion(BinaryConversion.byteArrayToStringHex1(byteArray,4,4));
        icsSocketVo.setIp(channel.getRemoteAddress().toString().split(":")[0].substring(1));
        icsSocketVo.setServerIp(channel.getLocalAddress().toString().split(":")[0].substring(1));

        IcsService.saveIcs(icsSocketVo);
        SlectionKeyData.getInstance().putKey(serial,sk);

        return BinaryConversion.byteArrayToStringHex1(byteArray, 12, 4) + BinaryConversion.byteArrayToStringHex1(byteArray, 20, 4);
    }
    //必须调用完后flip()才可以调用此方法
    public static byte[] conver(ByteBuffer byteBuffer){
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

    private synchronized boolean send(SocketChannel sc, SelectionKey sk, String msg) {
        try {
            // 创建发送数据的缓存区并写入数据
            ByteBuffer outputBuffer = ByteBuffer.allocate(300);

            outputBuffer.put(BinaryConversion.hexStringToByteArray(msg.toUpperCase()));
            outputBuffer.flip();
            // 向客户端发送反馈数据
            sc.write(outputBuffer);

            // 通过key改变通道注册的事件类型
            sk.interestOps(SelectionKey.OP_READ);
            sk.selector().wakeup();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void closeChannel(SocketChannel sc, SelectionKey sk) {
        try {
            sk.cancel();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
