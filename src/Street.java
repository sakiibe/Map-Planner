import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.List;

public class Street {
    private Point start;
    private Point end;
    private String streetID;
    private double distance;
    private int legNumber;


    public Street() {

    }

    public Street(Point start, Point end, String streetID, int legNumber) {
        this.start = start;
        this.end = end;
        this.streetID = streetID;
        this.distance = start.distanceTo(end);
        this.legNumber = legNumber;
    }

    public Street(Point start, Point end, String streetID, int legNumber, double distance) {
        this.start = start;
        this.end = end;
        this.streetID = streetID;
        this.distance = distance;
        this.legNumber = legNumber;
    }

    public String getStreetID() {
        return streetID;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public double getDistance() {
        return distance;
    }


    public void addNeighbour(Street comparedStreet, ArrayList<ArrayList<Edge>> adjacencyList, int degrees) {
        TurnDirection turn;
        //if the intersection is between this street's start and compared street's start point

        if (this.start.equals(comparedStreet.getStart())) {
            //add edge from this street to compared street
            turn = this.end.turnType(start, comparedStreet.end, degrees);
            adjacencyList.get(this.legNumber).add(new Edge(comparedStreet.legNumber, comparedStreet.getStreetID(), turn, comparedStreet.getDistance()));

            //add edge from compared street to this street
            turn = comparedStreet.end.turnType(start, this.end, degrees);
            adjacencyList.get(comparedStreet.legNumber).add(new Edge(this.legNumber, this.getStreetID(),turn, this.getDistance()));

        }
        //if the intersection is between this street's start and compared street's end point
        else if (this.start.equals(comparedStreet.getEnd())) {
            //add edge from this street to compared street
            turn = this.end.turnType(this.start, comparedStreet.start, degrees);
            adjacencyList.get(this.legNumber).add(new Edge(comparedStreet.legNumber, comparedStreet.getStreetID(), turn, comparedStreet.getDistance()));

            //add edge from compared street to this street
            turn = comparedStreet.start.turnType(comparedStreet.end, this.end, degrees);
            adjacencyList.get(comparedStreet.legNumber).add(new Edge(this.legNumber, this.getStreetID(),turn, this.getDistance()));

        }
        //if the intersection is between this street's end and compared street's start point
        else if (this.end.equals(comparedStreet.getStart())) {
            //add edge from this street to compared street
            turn = this.start.turnType(this.end, comparedStreet.end, degrees);
            adjacencyList.get(this.legNumber).add(new Edge(comparedStreet.legNumber, comparedStreet.getStreetID(), turn, comparedStreet.getDistance()));

            //add edge from compared street to this street
            turn= comparedStreet.end.turnType(comparedStreet.start, this.start, degrees);
            adjacencyList.get(comparedStreet.legNumber).add(new Edge(this.legNumber, this.getStreetID(),turn, this.getDistance()));
        }
        //if the intersection is between this street's end and compared street's end point
        else if (this.end.equals(comparedStreet.getEnd())) {
            //add edge from this street to compared street
            turn= this.start.turnType(this.end,comparedStreet.end, degrees);
            adjacencyList.get(this.legNumber).add(new Edge(comparedStreet.legNumber, comparedStreet.getStreetID(), turn, comparedStreet.getDistance()));

            //add edge from compared street to this street
            turn= comparedStreet.start.turnType(comparedStreet.end, this.start, degrees);
            adjacencyList.get(comparedStreet.legNumber).add(new Edge(this.legNumber, this.getStreetID(),turn, this.getDistance()));
        }
    }

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
