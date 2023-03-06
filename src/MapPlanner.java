import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapPlanner {

    /**
     * Create the Map Planner object.  The degrees provided tell us how much deviation from straight-forward
     * is needed to identify an actual turn in a route rather than a straight-on driving.
     *
     * @param degrees
     */
    private int degrees;
    private ArrayList<ArrayList<Leg>> adjacencyList;
    private ArrayList<Street> streets;
    private Map<Point, Integer> points;
    private int legNumber;
    private Location depot;

    public MapPlanner(int degrees) {
        this.degrees = degrees;
        this.adjacencyList = new ArrayList<>();
        this.legNumber = 0;
        this.points = new HashMap<>();
        this.streets=new ArrayList<>();
    }

    /**
     * Identify the location of the depot.  That location is used as the starting point of any route request
     * to a destiation
     *
     * @param depot -- the street ID and side of the street (left or right) where we find the depot
     * @return -- true if the depot was set.  False if there was a problem in setting the depot location.
     */
    public Boolean depotLocation(Location depot) {

        if (streets.contains(depot.getStreetId())) {
            this.depot=depot;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add a street to our map of the city.  The street is identified by the unique street id.
     * Although the parameters indicate a start and an end to the street, the street is bi-directional.
     * The start and end are just relevant when identifying the side of the street for some location.
     * <p>
     * Street coordinates are in metres.
     * <p>
     * Streets that share coordinates of endpoints meet at an intersection and you can drive from one street to the
     * other at that intersection.
     *
     * @param streetId -- unique identifier for the street.
     * @param start    -- coordinates of the starting intersection for the street
     * @param end      -- coordinates of the ending entersection for the street
     * @return -- true if the street could be added.  False if the street isn't available in the map.
     */
    public Boolean addStreet(String streetId, Point start, Point end) {
        //check if start point and end point is the same
        if (start.equals(end)) {
            return false;
        }
        //check if street with the same name is already added
        if (streets.contains(streetId)) {
            return false;
        }
        //if the street already exists with a different name
        if (points.containsKey(start) && points.containsKey(end)) {
            return false;
        }
        //add street into list
        Street newStreet= new Street(start,end,streetId, legNumber);
        adjacencyList.add(new ArrayList<>());

        for (Street street:streets){
            street.getNeighbour(newStreet, adjacencyList);
        }


        streets.add(newStreet);
        return true;


    }

    /**
     * Given a depot location, return the street id of the street that is furthest away from the depot by distance,
     * allowing for left turns to get to the street.
     */
    public String furthestStreet() {
        return null;
    }

    /**
     * Compute a route to the given destination from the depot, given the current map and not allowing
     * the route to make any left turns at intersections.
     *
     * @param destination -- the destination for the route
     * @return -- the route to the destination, or null if no route exists.
     */
    public Route routeNoLeftTurn(Location destination) {





        return null;
    }

    public Route routeNoLeftTurn(int depot, int destination, ArrayList<Street> streets) {

        int[] distance= new int[legNumber];

        return null;

    }

    public ArrayList<Street> getStreetList() {
        return this.streets;
    }
}
