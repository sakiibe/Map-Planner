public class Leg {

    private String streetID;
    private TurnDirection turn;
    public Leg (TurnDirection turn, String streetTurnedOnto){
        this.streetID=streetTurnedOnto;
        this.turn=turn;
    }

    public String getStreetID() {
        return streetID;
    }

    public TurnDirection getTurn() {
        return turn;
    }
}
