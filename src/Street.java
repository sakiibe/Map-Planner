import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.List;

public class Street {
    private Point start;
    private Point end;
    private String streetID;
    private double distance;
    private int legNumber;
    private List<Street> startNeighbour;
    private List<Street> endNeighbour;

    public Street() {

    }

    public Street(Point start, Point end, String streetID, int legNumber) {
        this.start = start;
        this.end = end;
        this.streetID = streetID;
        this.distance = start.distanceTo(end);
        this.startNeighbour = new ArrayList<>();
        this.endNeighbour = new ArrayList<>();
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

    public void addStartNeighbour(Street neighbourStreet) {
        startNeighbour.add(neighbourStreet);
    }

    public void addEndNeighbour(Street neighbourStreet) {
        endNeighbour.add(neighbourStreet);
    }

    /**
     * @return
     */
//    public void getNeighbour(Street connectedStreet) {
//
//    }
    public void getNeighbour(Street connectedStreet, ArrayList<ArrayList<Leg>> adjacencyList, ) {
        //if the intersection is between this street's start and compared street's start point
        if (this.start.equals(connectedStreet.getStart())) {
            Node Leg= new Leg()
        }
        //if the intersection is between this street's start and compared street's end point
        else if (this.start.equals(connectedStreet.getEnd())) {
            this.addStartNeighbour(connectedStreet);
            connectedStreet.addEndNeighbour(this);
        }
        //if the intersection is between this street's end and compared street's start point
        else if (this.end.equals(connectedStreet.getStart())) {
            this.addEndNeighbour(connectedStreet);
            connectedStreet.addStartNeighbour(this);
        }
        //if the intersection is between this street's end and compared street's end point
        else if (this.end.equals(connectedStreet.getEnd())) {
            this.addEndNeighbour(connectedStreet);
            connectedStreet.addEndNeighbour(this);
        }
//    }

    public List<Street> getStartNeighbour() {
        return startNeighbour;
    }

    public List<Street> getEndNeighbour() {
        return endNeighbour;
    }

    @Override
    public String toString() {
        return "Street ID: " + streetID;
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
