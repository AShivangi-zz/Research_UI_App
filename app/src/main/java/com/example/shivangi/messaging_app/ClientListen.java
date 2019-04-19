package com.example.shivangi.messaging_app;

import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;


public class ClientListen implements Runnable {

    static DatagramSocket udpSocket;
    private boolean run = true;
    private byte[] message = new byte[6];

    public boolean connect(){
        boolean isAlive = false;
        try {
            udpSocket = new DatagramSocket(4488); //5050 for testing
            isAlive = true;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return isAlive;
    }

    public boolean disconnect() {
        if(udpSocket!= null) {
            udpSocket.disconnect();
            return udpSocket.isClosed();
        }
        return true;
    }

    public String getData() throws IOException {
        DatagramPacket packet = new DatagramPacket(message, message.length);
        Log.d("UDP client: ", "about to wait to receive");
        udpSocket.receive(packet);
        String text = new String(message, 0, packet.getLength());
        Log.d("Received data", text);
        EventBus.getDefault().post(new ServerEvent(text));
        return text;
    }

    @Override
    public void run() {
        //Creating Socket
        connect();
        while (run) {
            try {
                Main.msg = getData();
            } catch (IOException e) {
                Log.e("UDP client has IOExcept", "error: ", e);
            }
        }
    }
}