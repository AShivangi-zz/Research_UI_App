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
            udpSocket.setSoTimeout(5000); //timeout 5s
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
        return text;
    }

    @Override
    public void run() {
        //Creating Socket
        connect();
        while (run) { //~~~~~~~~~~~~~~~~how to break this loop?
            try {
                getData();
            } catch (IOException e) {
                Log.e("UDP client has IOExcept", "error: ", e);
            }
        }
    }
}