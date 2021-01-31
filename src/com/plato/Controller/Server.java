package Controller;

        import java.io.*;
        import java.text.*;
        import java.util.*;
        import java.net.*;


public class Server {
    public static void run () throws IOException {
        System.out.println("server started");
        ServerSocket serverSocket = new ServerSocket(5056);


        while (true) {
            Socket socket = null;

            try {

                socket = serverSocket.accept();

                System.out.println("A new client has connected : " + socket);


                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                DataOutputStream dataOutputStream =new DataOutputStream(socket.getOutputStream());

               // System.out.println("Assigning new thread for this client");


                Thread thread = new ClientHandler(socket, dataInputStream);

                thread.start();

            } catch (Exception e) {
                socket.close();
                System.out.println("socket closed due to exception");
                e.printStackTrace();
            }
        }
    }
}


class ClientHandler extends Thread {

    final Socket socket;
    final DataInputStream dataInputStream;


    public ClientHandler(Socket socket, DataInputStream dataInputStream1) {
        this.socket = socket;
        this. dataInputStream= dataInputStream1;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String received = this.dataInputStream.readUTF();
                System.out.println(received);
                if (!received.equals("Exit")) {
                    continue;
                }

                System.out.println("Client " + this.socket + " sends exit...");
                System.out.println("Closing this connection.");
                this.socket.close();
                System.out.println("Connection closed");
            } catch (IOException exception) {
                exception.printStackTrace();
                continue;
            }

            try {
                this.dataInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }
    }
}

    }
}
