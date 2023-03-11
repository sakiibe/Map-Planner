/**
 * capture connection of streets (edges) in the map (graph)
 */
public class Edge {
    private int legNumber;
    private String streedID;
    private TurnDirection turn;
    private double distance;
    private Point entryPoint;

    /**
     * instantiate an Edge object
     * @param legNumber -- leg number of this edge
     * @param streedID -- street name of this edge
     * @param turn -- the direction of turn selecting this edge would result in
     * @param distance -- length of this edge
     * @param entryPoint -- Point used to enter this edge from previous edge
     */
    public Edge(int legNumber, String streedID, TurnDirection turn, double distance, Point entryPoint){
        this.legNumber= legNumber;
        this.streedID=streedID;
        this.turn=turn;
        this.distance=distance;
        this.entryPoint=entryPoint;
    }

    /**
     * Get leg number of this edge
     * @return -- leg number of this edge
     */
    public int getLegNumber() {
        return legNumber;
    }

    /**
     * Get the turn direction used to enter this edge
     * @return -- direction of turn
     */
    public TurnDirection getTurn() {
        return turn;
    }

    /**
     * Get the distance from one point of this edge to another
     * @return -- distance of edge
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Get the street id associated with this edge
     * @return -- street id of this edge
     */
    public String getStreedID() {
        return streedID;
    }

    /**
     * Get the point used to enter this edge from previous edge
     * @return -- entry point of this edge
     */
    public Point getEntryPoint() {
        return entryPoint;
    }
}
