package View.GameRelated.Reversi;

import java.io.IOException;
import java.net.Socket;

public class ReversiClient {

    static class BattleSeaClientImpl {
        private Socket socket;

        public void run(){
            try{
                socket = new Socket("127.0.0.1",6666);
            }
            catch (IOException e) {
                System.err.println("Error Reversi Client");
            }

        }

    }
}
