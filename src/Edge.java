public class Edge {
    private int legNumber;
    private String streedID;
    private TurnDirection turn;
    private double distance;

    public Edge(int legNumber, String streedID, TurnDirection turn, double distance){
        this.legNumber= legNumber;
        this.streedID=streedID;
        this.turn=turn;
        this.distance=distance;
    }

    public int getLegNumber() {
        return legNumber;
    }

    public TurnDirection getTurn() {
        return turn;
    }

    public double getDistance() {
        return distance;
    }

    public String getStreedID() {
        return streedID;
    }
}
