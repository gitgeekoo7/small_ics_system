package com.cesecsh.small_ics_system.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class Reactor implements Runnable {

    public final Selector selector;
    public final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {

        // 创建选择器
        selector = Selector.open();

        // 打开监听信道
        serverSocketChannel = ServerSocketChannel.open();
        // 与本地端口绑定
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        serverSocketChannel.socket().bind(inetSocketAddress);
        // 设置为非阻塞模式,只有非阻塞信道才可以注册选择器,否则异步IO就无法工作
        serverSocketChannel.configureBlocking(false);

        // 向selector注册该channel
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 利用selectionKey的attache功能绑定Acceptor回调对象
        selectionKey.attach(new Acceptor(this));
    }

    @Override
    public void run() {
        // 阻塞等待某信道就绪
        try {
            while (!Thread.interrupted()){

                //获取通道内是否有选择器的关心事件
                int num = selector.select();
                if(num<1)
                    continue;

                // 取得已就绪事件的key集合，遍历每一个注册的通道
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()){
                    SelectionKey selectionKey = it.next();
                    // 根据事件的key进行调度
                    dispatch(selectionKey);
                    // 删除处理过的事件
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) (key.attachment());
        if (r != null){
            r.run();    //回调事件对应的处理流程
//            new Thread(temp).start();
            log.info("Reactor.dispatch()...................");
        }
    }
}
