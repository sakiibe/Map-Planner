public class Leg {

    private String streetID;
    private TurnDirection turn;
    private int legNumber;
    public Leg (TurnDirection turn, String streetTurnedOnto, int legNumber){
        this.streetID=streetTurnedOnto;
        this.turn=turn;
        this.legNumber=legNumber;
    }

    public String getStreetID() {
        return streetID;
    }

    public TurnDirection getTurn() {
        return turn;
    }

    public int getLegNumber() {
        return legNumber;
    }

    @Override
    public String toString() {
        return "Leg{" +
                "streetID='" + streetID + '\'' +
                ", turn=" + turn +
                '}';
    }
}
