package com.lagousocket.socketdemo.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br>
 * 〈socket客户端〉
 *
 * @author 商玉
 * @create 2021/12/7
 * @since 1.0.0
 */
public class socketClient {

    public static void main(String[] args) throws IOException {
        while (true){
        //    创建socket对象
            Socket socket = new Socket("127.0.0.1", 9999);
            //创建发送消息的类
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("请输入：");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            outputStream.write(s.getBytes());
        //    从连接中取出输入流并接收回话
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            System.out.println("老板说："+new String(bytes,0,read).trim());
            socket.close();
        }
    }
}
