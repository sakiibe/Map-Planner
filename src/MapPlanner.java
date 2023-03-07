import javafx.util.Pair;

import java.util.*;

public class MapPlanner {

    /**
     * Create the Map Planner object.  The degrees provided tell us how much deviation from straight-forward
     * is needed to identify an actual turn in a route rather than a straight-on driving.
     *
     * @param degrees
     */
    private int degrees;
    private ArrayList<ArrayList<Street>> adjacencyList;
    private ArrayList<Street> streets;
    private Map<Point, Integer> points;
    private int legNumber;
    private Location depot;

    public MapPlanner(int degrees) {
        this.degrees = degrees;
        this.adjacencyList = new ArrayList<>();
        this.legNumber = 0;
        this.points = new HashMap<>();
        this.streets = new ArrayList<>();
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
            this.depot = depot;
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
        for (Street street:streets){
            if (street.getStreetID().equals(streetId)){
                return false;
            }
        }
        //records intersecting points
        int maxIntersection = 0;

        //create new street
        Street newStreet = new Street(start, end, streetId, legNumber);

        for (Street street : streets) {
            int commonIntersection = street.checkNeighbour(newStreet, adjacencyList);
            if (maxIntersection < commonIntersection) {
                maxIntersection = commonIntersection;
            }
        }

        //maxIntersection > 1, the same start and end point is given with different name
        if (maxIntersection > 1) {
            return false;
        }
        //maxIntersection=0, newStreet isn't connected to this street
        else if (maxIntersection == 0) {
            legNumber++;
            adjacencyList.add(new ArrayList<>());
        }
        //maxIntersection = 1, newStreet is connected to this street
        //add adjacent streets
        else if (maxIntersection == 1) {
            legNumber++;
            adjacencyList.add(new ArrayList<>());
            for (Street street : streets) {
                street.addNeighbour(newStreet, adjacencyList);
            }
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

        int depotIdx=-1, destinationIdx=-1;

        for (Street street:streets){
            if (street.getStreetID().equals(depot.getStreetId())){
                depotIdx=street.getLegNumber();
            }
            if (street.getStreetID().equals(destination.getStreetId())){
                destinationIdx=street.getLegNumber();
            }
        }
        //if either destination or depot isn't on the map, return empty route
        if (depotIdx*destinationIdx<0){
            return new Route(this.streets);
        }
        Route route= routeNoLeftTurn(depotIdx,destinationIdx,this.adjacencyList, this.depot, destination);
        return null;
    }

    public Route routeNoLeftTurn(int depotIdx, int destinationIdx, ArrayList<ArrayList<Street>> adjacencyList, Location depot, Location destination) {

        Route route= new Route(this.streets);

        int[] distance = new int[legNumber];
        //set distance for all adjacent legs to infinity
        Arrays.fill(distance, Integer.MAX_VALUE);

        PriorityQueue<Street> pq= new PriorityQueue<>((v1,v2)-> (int) (v1.getDistance()- v2.getDistance()));
        Street depotStreet=new Street();
        for (Street street:streets){
            if (street.getLegNumber()==depotIdx){
                depotStreet=street;
            }
        }
        pq.add(new Street(depotStreet.getStart(),depotStreet.getEnd(),depotStreet.getStreetID(),depotIdx,0));

        while (!pq.isEmpty()){
            Street currentStreet=pq.poll();

//            for (Street street: adjacencyList.get(currentStreet.getLegNumber())){
//                if ((distance[currentStreet.getLegNumber()] +
//                        street.getDistance()< distance[street.getLegNumber()]) &&
//                ) {
//            }
        }
        return route;
    }

    public ArrayList<Street> getStreetList() {
        return this.streets;
    }

}
