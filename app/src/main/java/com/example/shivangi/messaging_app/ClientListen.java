package com.example.shivangi.messaging_app;

//import android.content.Context;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
import java.net.*;

//import android.support.v7.app.AppCompatActivity;
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
//        File logFile2 = new File(mcontext.getExternalCacheDir(), "RiSA2S_log2.txt");
//        PrintWriter out = null;
//        try {
//            out = new PrintWriter(new BufferedWriter(new FileWriter(logFile2, true)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //Creating Socket
        connect();
//
//        int n = 0;
//        int nRecCounter = 0;
        while (run) { //~~~~~~~~~~~~~~~~how to break this loop?
//            if(n > 10)
//                run = false;
//            n +=1;
            try {
                //out.println("Waiting for Data...");

                getData();
//                out.print("Received data "+ text);
//                nRecCounter +=1;
//                if(nRecCounter >=5)
//                    run = false;

            }catch (IOException e) {
                Log.e("UDP client has IOExcept", "error: ", e);
            }
        }

        //doesn't reach this yet - remove later
//        try {
//            Thread.sleep(10000);
//            Log.e("UDP Client", "Flushing!");
//            out.flush();
//            out.close();
//            Log.e("UDP Client", "Flushing done!");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}