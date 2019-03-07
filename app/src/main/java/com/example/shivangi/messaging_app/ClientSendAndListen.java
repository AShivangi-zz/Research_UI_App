package com.example.shivangi.messaging_app;

import java.io.IOException;
import java.net.*;
import android.util.Log;

public class ClientSendAndListen implements Runnable {
    @Override
    public void run() {
        boolean run = true;
        try {
            DatagramSocket udpSocket = new DatagramSocket(4445);
            InetAddress serverAddr = InetAddress.getByName("localhost");
            byte[] buf = ("FILES").getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr, 4445);
            udpSocket.send(packet);
            while (run) {
                try {
                    byte[] message = new byte[8000];
                    DatagramPacket p = new DatagramPacket(message,message.length);
                    Log.i("UDP client: ", "about to wait to receive");
                    udpSocket.setSoTimeout(10000);
                    udpSocket.receive(p);
                    String text = new String(message, 0, p.getLength());
                    Log.d("Received text", text);
                } catch (IOException e) {
                    Log.e("UDP client has IOExcept", "error: ", e);
                    run = false;
                    udpSocket.close();
                }
            }
        } catch (SocketException e) {
            Log.e("Socket Open:", "Error:", e);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}