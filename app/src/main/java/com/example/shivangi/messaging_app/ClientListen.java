package com.example.shivangi.messaging_app;

import java.io.IOException;
import java.net.*;
import android.util.Log;


public class ClientListen implements Runnable {

    static DatagramSocket udpSocket;
    private boolean run = true;
    private byte[] message = new byte[6];

    public boolean connect(){
        boolean isAlive = false;
        try {
            udpSocket = new DatagramSocket(4488);
           // udpSocket.setSoTimeout(10000); //timeout 10s
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
        DatagramPacket packet = new DatagramPacket(message,message.length);
        Log.i("UDP client: ", "about to wait to receive");
        udpSocket.receive(packet);
        String text = new String(message, 0, packet.getLength());
        Log.d("Received data", text);
        Main.newMsg = true;
        return text;
    }

    @Override
    public void run() {
        //Creating Socket
        connect();
        while (run) { //~~~~~~~~~~~~~~~~how to break this loop?
            try {
                Main.msg = getData();
            } catch (IOException e) {
                Log.e("UDP client has IOExcept", "error: ", e);
            }
        }
    }
}