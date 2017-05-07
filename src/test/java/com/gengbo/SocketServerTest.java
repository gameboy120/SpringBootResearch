package com.gengbo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * Created  2017/1/5-14:46
 * Author : gengbo
 */
public class SocketServerTest {
    public static void main(String[] args) {
        new Thread(() -> {
            ServerSocket server = null;
            try {

                server = new ServerSocket(10000);
                System.out.println("server accept request");

                while (true) {
                    Socket accept = server.accept();
                    byte[] data = new byte[80];
                    accept.getInputStream().read(data);
                    System.out.println(new String(data));
                    accept.getOutputStream().write("耿博".getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                ServerSocketChannel channel = ServerSocketChannel.open();
                channel.configureBlocking(false);
                channel.socket().bind(new InetSocketAddress(9876));
                System.out.println("channel start");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
//        connectSocket();
//        while (true) {
//
//        }
    }

//    public static void connectSocket() {
//        try {
//            Socket client = new Socket("127.0.0.1", 10000);
//            client.getOutputStream().write("啦啦啦".getBytes());
//            byte[] data = new byte[100];
//            client.getInputStream().read(data);
//            System.out.println(new String(data));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
