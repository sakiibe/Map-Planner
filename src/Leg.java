public class Leg {
    private int legNumber;
    private double distance;
    private String streetID;
    private Point vertex;
    private TurnDirection turn;
    Leg (int legNumber, double distance, String streetID, Point vertex){
        this.legNumber=legNumber;
        this.distance=distance;
        this.streetID=streetID;
        this.vertex=vertex;
    }

    public String getStreetID() {
        return streetID;
    }
}
