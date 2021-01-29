package View.GameRelated.BattleSea;

import java.io.IOException;
import java.net.Socket;

public class BattleSeaClient {
    public static void main(String[] args) {

    }
    static class BattleSeaClientImpl {
        private Socket socket;

        public void run(){
            try{
                socket = new Socket("127.0.0.1",6666);
            }
            catch (IOException e) {
                System.err.println("Error BattleSea Client");
            }

        }

    }
}
