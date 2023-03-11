/**
 * capture a leg for a route
 */

public class Leg {
    private String streetID;
    private TurnDirection turn;
    private int legNumber;
    public Leg (TurnDirection turn, String streetTurnedOnto, int legNumber){
        this.streetID=streetTurnedOnto;
        this.turn=turn;
        this.legNumber=legNumber;
    }

    /**
     * get street name of the leg
     * @return -- street id of leg
     */
    public String getStreetID() {
        return streetID;
    }

    /**
     * Get turn direction of the leg
     * @return - turn direction of leg
     */
    public TurnDirection getTurn() {
        return turn;
    }

    /**
     * get leg number of the leg
     * @return leg number of leg
     */
    public int getLegNumber() {
        return legNumber;
    }

    @Override
    public String toString() {
        return "Leg{" +
                "streetID='" + streetID + '\'' +
                ", turn=" + turn +
                ", legNumber=" + legNumber +
                '}';
    }
}
