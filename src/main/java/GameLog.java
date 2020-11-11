public class GameLog{

   //constructor
    public GameLog(int logID){
        this.logID = logID;
    }

    //attributes
    private int playedCount = 0;
    private int winsCount = 0;
    private int logID;
    private double point = 0;

    //methods

    public void showBoard(int gameID , int logID){}   //TODO : is name needed?

    //setters and getters
    public int getPlayedCount() {
        return playedCount;
    }

    public void setPlayedCount(int playedCount) {
        this.playedCount = playedCount;
    }

    public int getWinsCount() {
        return winsCount;
    }

    public void setWinsCount(int winsCount) {
        this.winsCount = winsCount;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

}
