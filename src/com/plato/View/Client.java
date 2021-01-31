package View;


        import java.io.*;
        import java.net.*;
        import java.util.Scanner;

public class Client {
    public static void run () throws IOException {

        System.out.println("client started");
        try {
            Scanner scanner = new Scanner(System.in);


            InetAddress ip = InetAddress.getByName("localhost");


            Socket socket = new Socket(ip, 5056);

            // obtaining input and out streams
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            // the following loop performs the exchange of
            // information between client and client handler
            while (true) {
                // System.out.println(dis.readUTF());
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);
                // If client sends exit,close this connection
                // and then break from the while loop
                if (tosend.equals("Exit")) {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by client
                // String received = dis.readUTF();
                // System.out.println(received);
            }

            // closing resources
            scn.close();
            // dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
