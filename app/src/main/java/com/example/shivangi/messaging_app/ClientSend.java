package com.example.shivangi.messaging_app;

import java.io.IOException;
import java.net.*;
import android.util.Log;

public class ClientSend implements Runnable {
    @Override
    public void run() {
        try {
            DatagramSocket udpSocket = new DatagramSocket(4445);
            InetAddress serverAddr = InetAddress.getByName("localhost");
            byte[] buf = ("The String to Send").getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr, 4445);
            udpSocket.send(packet);
        } catch (SocketException e) {
            Log.e("Udp:", "Socket Error:", e);
        } catch (IOException e) {
            Log.e("Udp Send:", "IO Error:", e);
        }
    }
}