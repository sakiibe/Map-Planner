import java.util.ArrayList;
import java.util.List;

/**
 * capture a street in map
 */
public class Street {
    private Point start;
    private Point end;
    private String streetID;
    private double distance;
    private int legNumber;
    private List<Street> startNeighbour;
    private List<Street> endNeighbour;

    /**
     * dummy street object constructor
     */
    public Street() {

    }

    /**
     * instantiate a street object
     * @param start -- Starting point of this street
     * @param end -- Ending point of this street
     * @param streetID -- Name of the street
     * @param legNumber -- leg number associated with this street
     */
    public Street(Point start, Point end, String streetID, int legNumber) {
        this.start = start;
        this.end = end;
        this.streetID = streetID;
        this.distance = start.distanceTo(end);
        this.legNumber = legNumber;
        this.startNeighbour = new ArrayList<>();
        this.endNeighbour = new ArrayList<>();
    }

    /**
     * Get this street's name
     * @return -- the name of this street
     */

    public String getStreetID() {
        return streetID;
    }

    /**
     * Get this street's starting point
     * @return -- starting point
     */
    public Point getStart() {
        return start;
    }

    /**
     * Get this street's ending point
     * @return -- ending point
     */
    public Point getEnd() {
        return end;
    }

    /**
     * Get the distance from start point to end point of this street
     * @return -- distance
     */
    public double getDistance() {
        return distance;
    }

    public void addStartNeighbour(Street neighbourStreet) {
        startNeighbour.add(neighbourStreet);
    }

    public void addEndNeighbour(Street neighbourStreet) {
        endNeighbour.add(neighbourStreet);
    }

    public List<Street> getStartNeighbour() {
        return startNeighbour;
    }

    public List<Street> getEndNeighbour() {
        return endNeighbour;
    }

    /**
     *Add a connection to this street if both street share a common intersection
     * @param comparedStreet -- Street being compared
     * @param adjacencyList -- Adjacency list of the map
     * @param degrees -- degree tolerance for turns
     */
    public void addNeighbour(Street comparedStreet, ArrayList<ArrayList<Edge>> adjacencyList, int degrees) {
        TurnDirection turn;
        //if the common intersection is on this street's start and compared street's start point

        if (this.start.equals(comparedStreet.getStart())) {
            //add edge from this street to compared street
            turn = this.end.turnType(start, comparedStreet.end, degrees);
            adjacencyList.get(this.legNumber).add(new Edge(comparedStreet.legNumber, comparedStreet.getStreetID(), turn, comparedStreet.getDistance(),comparedStreet.start));

            //add edge from compared street to this street
            turn = comparedStreet.end.turnType(start, this.end, degrees);
            adjacencyList.get(comparedStreet.legNumber).add(new Edge(this.legNumber, this.getStreetID(),turn, this.getDistance(),this.start));

            //assign neighbours according to their common intersection
            this.addStartNeighbour(comparedStreet);
            comparedStreet.addStartNeighbour(this);

        }
        //if the common intersection is on this street's start and compared street's end point
        else if (this.start.equals(comparedStreet.getEnd())) {
            //add edge from this street to compared street
            turn = this.end.turnType(this.start, comparedStreet.start, degrees);
            adjacencyList.get(this.legNumber).add(new Edge(comparedStreet.legNumber, comparedStreet.getStreetID(), turn, comparedStreet.getDistance(),comparedStreet.end));

            //add edge from compared street to this street
            turn = comparedStreet.start.turnType(comparedStreet.end, this.end, degrees);
            adjacencyList.get(comparedStreet.legNumber).add(new Edge(this.legNumber, this.getStreetID(),turn, this.getDistance(),this.start));

            //assign neighbours according to their common intersection
            this.addStartNeighbour(comparedStreet);
            comparedStreet.addEndNeighbour(this);

        }
        //if the common intersection is on this street's end and compared street's start point
        else if (this.end.equals(comparedStreet.getStart())) {
            //add edge from this street to compared street
            turn = this.start.turnType(this.end, comparedStreet.end, degrees);
            adjacencyList.get(this.legNumber).add(new Edge(comparedStreet.legNumber, comparedStreet.getStreetID(), turn, comparedStreet.getDistance(),comparedStreet.start));

            //add edge from compared street to this street
            turn= comparedStreet.end.turnType(comparedStreet.start, this.start, degrees);
            adjacencyList.get(comparedStreet.legNumber).add(new Edge(this.legNumber, this.getStreetID(),turn, this.getDistance(),this.end));

            //assign neighbours according to their common intersection
            this.addEndNeighbour(comparedStreet);
            comparedStreet.addStartNeighbour(this);

        }
        //if the common intersection is on this street's end and compared street's end point
        else if (this.end.equals(comparedStreet.getEnd())) {
            //add edge from this street to compared street
            turn= this.start.turnType(this.end,comparedStreet.end, degrees);
            adjacencyList.get(this.legNumber).add(new Edge(comparedStreet.legNumber, comparedStreet.getStreetID(), turn, comparedStreet.getDistance(),comparedStreet.end));

            //add edge from compared street to this street
            turn= comparedStreet.start.turnType(comparedStreet.end, this.start, degrees);
            adjacencyList.get(comparedStreet.legNumber).add(new Edge(this.legNumber, this.getStreetID(),turn, this.getDistance(),this.end));

            //assign neighbours according to their common intersection
            this.addEndNeighbour(comparedStreet);
            comparedStreet.addEndNeighbour(this);
        }
    }

    /**
     * compute number of common intersection between streets
     * @param comparedStreet -- Street being compared
     * @return -- number of common intersecting points between streets
     */
    public int checkNeighbour(Street comparedStreet) {
        /* counts common intersection. If,
        commonPoint=0, comparedStreet isn't connected to this street
        commonPoint=1, comparedStreet is connected to this street
        commontPoint>1, the same start and end point is given with different name
         */
        int commonPoint = 0;

        //if the intersection is between this street's start and compared street's start point
        if (this.start.equals(comparedStreet.getStart())) {

            commonPoint++;
        }
        //if the intersection is between this street's start and compared street's end point
        if (this.start.equals(comparedStreet.getEnd())) {
            commonPoint++;
        }
        //if the intersection is between this street's end and compared street's start point
        else if (this.end.equals(comparedStreet.getStart())) {
            commonPoint++;
        }
        //if the intersection is between this street's end and compared street's end point
        else if (this.end.equals(comparedStreet.getEnd())) {
            commonPoint++;
        }
        return commonPoint;
    }

    /**
     * Get this steet's leg number
     * @return -- leg number of this street
     */
    public int getLegNumber() {
        return legNumber;
    }

    @Override
    public String toString() {
        return "Legnumber: " + legNumber + " Street ID: " + streetID;
    }

    @Override
    public boolean equals(Object comparedObject) {
        if (this == comparedObject) {
            return true;
        }
        if (!(comparedObject instanceof Street)) {
            return false;
        }

        Street comparedTable = (Street) comparedObject;

        //only need to check if streetId are the same

        if (this.streetID.equals(((Street) comparedObject).streetID)) {
            return true;
        }

        return false;
    }

}
