package com.example.shivangi.messaging_app;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import android.util.Log;



public class ClientListen implements Runnable {

    private DatagramSocket udpSocket;
    private boolean run = true;
    private byte[] message = new byte[6];

    private Context mcontext;

    public ClientListen(Context context){
        mcontext = context;
    }
    @Override
    public void run() {
        File logFile2 = new File(mcontext.getExternalCacheDir(), "RiSA2S_log2.txt");
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(logFile2, true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (run) {
            try {
                udpSocket = new DatagramSocket(4488);
                udpSocket.setSoTimeout(10000); //timeout 10s
                DatagramPacket packet = new DatagramPacket(message,message.length);
                Log.i("UDP client: ", "about to wait to receive");
                udpSocket.receive(packet);
                String text = new String(message, 0, packet.getLength());
                //

                out.print("Received data "+ text);

                //
                Log.d("Received data", text);
            }catch (IOException e) {
                //

                out.print("UDP client has IOExcept "+ " error: "+ e);

                //
                Log.e("UDP client has IOExcept", "error: ", e);
                run = false;
            }finally {
                out.flush();
                out.close();
            }
        }
    }
}