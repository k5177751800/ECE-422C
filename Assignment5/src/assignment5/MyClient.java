package assignment5;
import java.io.*;
import java.net.*;
import java.util.*;

public class MyClient {
    static volatile boolean isclosed = false;

    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 6666);
             DataOutputStream dout = new DataOutputStream(s.getOutputStream());
             DataInputStream dis = new DataInputStream(s.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            Thread send = new Thread(() -> {
                while (!isclosed) {
                    String mine = scanner.nextLine();
                    try {
                        if (mine.equals("N")) {
                            isclosed = true;
                            dout.writeUTF(mine);
                            return;
                        }
                        //if(isclosed) { System.exit(0); }
                        if(!isclosed) {
                            dout.writeUTF(mine);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread read = new Thread(() -> {
                try {
                    while (!isclosed) {
                        try{
                            String mine = dis.readUTF();
                            if (mine.equals("exit")) {
                                System.out.println("We have a Winner!");
                                isclosed = true;
                                System.out.println("please return");
                                System.exit(0);
                                send.interrupt();
                            }
                            System.out.println(mine);
                        }catch(EOFException e){
                           return;

                        }
                    }
                } catch (SocketException se) {
                    // SocketException will be thrown when the socket is closed
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        dis.close();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            send.start();
            read.start();
            try {
                send.join();
                read.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupt");
            }
           dout.flush();
           dout.close();

            s.close();

            send.interrupt();
            read.interrupt();
//
//            System.out.println(send.isAlive());
//            System.out.println(read.isAlive());

        } catch (Exception e) {
            System.out.println(e);

        }
    }
}
