package assignment5;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class MyServer {
    static ArrayList<ClientHandler> addedclient;
    static Socket s;
    static ServerSocket ss;
    static volatile boolean finish;
    static String secreteCode;
    public static int i;


    public static void main(String[] args) {
        try {
            ss = new ServerSocket(6666); //port 6666 needed
            ss.setSoTimeout(1000);//timeout
            secreteCode = SecretCodeGenerator.getInstance().getNewSecretCode();
            addedclient = new ArrayList<>();
            System.out.println("reached");
            while (true) {
                try {
                    s = ss.accept();//establishes connection
                    System.out.println("New Client connected: ");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                    ClientHandler client = new ClientHandler(s, dis, dout, secreteCode, "Client " + i);
                    addedclient.add(client);

                    Thread t = new Thread(client);

                    t.start();
                } catch (SocketTimeoutException timeOut) {
                    if (finish == true) {
                        for (ClientHandler a : addedclient) {
                            if (!a.s.isClosed()) {
                                a.dout.writeUTF("exit");
                            }
                        }
                        break;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
    class ClientHandler implements Runnable{

        public String name;
        final DataInputStream dis;
        final DataOutputStream dout;
        Socket s;
        final String secreteCode;
        final Game mM;
        int numberGuesses = GameConfiguration.guessNumber; //12
        public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dout, String secreteCode, String name){
            this.s = s;
            this.dis = dis;
            this.dout = dout;
            this.secreteCode = secreteCode;
            mM = new Game(true, secreteCode);
        }
        @Override
        public void run(){
            String received;
            try{
                dout.writeUTF(GameConfiguration.welcomeMessage);
            }catch (IOException e){};
            while (true){
                try{
                    if (s.isClosed()){
                        System.out.println("Socket is closed");
                        break;
                    }
                    received = dis.readUTF(); //data input from client
                    if (received.equals("Y")){
                        dout.writeUTF(mM.startGame());
                    }else if (received.equals("N")){
                        break;
                    }else if(received.equals(this.secreteCode)){
                        MyServer.finish = true;
                        System.out.println("reached");
                    }else{
                        String correct = mM.runGame(received);
                        if (mM.getNumberGuesses() == 0){
                            dout.writeUTF(correct);
                            break;
                        }else{
                            dout.writeUTF(correct);

                        }
                    }
                }catch (IOException e){
                    if(s.isClosed()){
                        System.out.println("client disconnected");
                    }else{
                        System.out.println("IO in handler" + e.getMessage());
                    }
                    break;
                }

            }
            System.out.println(this.name + " diconnected.");
            disconnect();
        }
        public void disconnect(){
            System.out.println("disconnecting");
            try{
                if (s != null && s.isClosed()){
                    s.close();
                }if (dis != null){
                    dis.close();;
                }if(dout != null){
                    dout.close();
                }
            }catch (IOException e){
                System.out.println("Error client not connected" + e.getMessage());
            }
            return;
        }

    }

