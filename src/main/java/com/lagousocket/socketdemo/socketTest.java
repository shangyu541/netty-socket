package com.lagousocket.socketdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 * 〈一句话功能简述〉<br>
 * 〈socketf服务端代码实现〉
 *
 * @author 商玉
 * @create 2021/12/7
 * @since 1.0.0
 */
public class socketTest {

    public static void main(String[] args) throws IOException {
        //创建一个线程池，如果有客户端连接就创建一个线程，与之通信
        ExecutorService executorService = Executors.newCachedThreadPool();
        //创建serverSocket对象
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务器已启动");
        while (true) {
            Socket accept = serverSocket.accept();
            System.out.println("有客户端连接");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handle(accept);
                }
            });
        }
    }

    private static void handle(Socket accept) {
        try {
            System.out.println("线程id:" + Thread.currentThread().getId() + "  线程名称:" + Thread.currentThread().getName());
            //    从连接中取出输入流来接收消息
            InputStream inputStream = accept.getInputStream();
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            System.out.println("客户端：" + new String(bytes, 0, read));
            //    连接中取出输出流并回话
            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("没钱".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                accept.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
