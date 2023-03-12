import java.util.*;

public class MapPlanner {

    /**
     * Create the Map Planner object.  The degrees provided tell us how much deviation from straight-forward
     * is needed to identify an actual turn in a route rather than a straight-on driving.
     *
     * @param degrees
     */
    private int degrees;
    private ArrayList<ArrayList<Edge>> adjacencyList;
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

        for (Street street : streets) {
            if (depot.getStreetId().equals(street.getStreetID())) {
                this.depot = depot;
                return true;
            }
        }
        return false;
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
        for (Street street : streets) {
            if (street.getStreetID().equals(streetId)) {
                return false;
            }
        }
        //records intersecting points
        int maxIntersection = 0;

        //create new street
        Street newStreet = new Street(start, end, streetId, legNumber);

        //compute common intersection number
        for (Street street : streets) {
            int commonIntersection = street.checkNeighbour(newStreet);
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
                street.addNeighbour(newStreet, adjacencyList, this.degrees);
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
        int depotIdx = -1;
        Street depotStreet = new Street();
        //find deport leg number and depot street
        for (Street street : streets) {
            if (street.getStreetID().equals(depot.getStreetId())) {
                depotIdx = street.getLegNumber();
                depotStreet=street;
                break;
            }

        }

        double[] distance = new double[legNumber];
        //set distance for all adjacent legs to infinity
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[depotIdx] = 0;

        //set priority based on least distance
        PriorityQueue<Edge> pq = new PriorityQueue<>((v1, v2) -> (int) (v1.getDistance() - v2.getDistance()));

        //add depot edge with 0 distance so it is selected first in the priority queue
        pq.add(new Edge(depotStreet.getLegNumber(), depotStreet.getStreetID(), TurnDirection.Straight, 0, new Point(0, 0)));

        while (!pq.isEmpty()) {
            //pick edge with least distance
            Edge currentEdge = pq.poll();

            //select edge if it satisfies d[u]+c(u,v)<d[v]
            for (Edge edge : adjacencyList.get(currentEdge.getLegNumber())) {
                if ((distance[currentEdge.getLegNumber()] +
                        edge.getDistance() < distance[edge.getLegNumber()])) {
                    distance[edge.getLegNumber()] = (edge.getDistance() + distance[currentEdge.getLegNumber()]);
                    pq.add(new Edge(edge.getLegNumber(), edge.getStreedID(), edge.getTurn(), edge.getDistance(), edge.getEntryPoint()));
                }
            }
        }

        int maxIdx = -1;
        double maxValue = -1;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] > maxValue) {
                maxValue = distance[i];
                maxIdx = i;
            }
        }

        for (Street street : streets) {
            if (street.getLegNumber() == maxIdx) {
                return street.getStreetID();
            }
        }
        return "";
    }

    /**
     * Compute a route to the given destination from the depot, given the current map and not allowing
     * the route to make any left turns at intersections.
     *
     * @param destination -- the destination for the route
     * @return -- the route to the destination, or null if no route exists.
     */
    public Route routeNoLeftTurn(Location destination) {

        int depotIdx = -1, destinationIdx = -1;
        Street depotStreet = new Street();
        Street destinationSreet = new Street();

        //find index and street of depot and destination from their location
        for (Street street : streets) {
            if (street.getStreetID().equals(depot.getStreetId())) {
                depotIdx = street.getLegNumber();
                depotStreet = street;
            }
            if (street.getStreetID().equals(destination.getStreetId())) {
                destinationIdx = street.getLegNumber();
                destinationSreet = street;
            }
        }
        //if either destination or depot isn't on the map, return empty route
        if (depotIdx * destinationIdx < 0) {
            return new Route(this.streets);
        }
        //initialize route
        Route route = new Route(this.streets);

        double[] distance = new double[legNumber];
        //set distance for all adjacent legs to infinity
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[depotIdx] = 0;

        //set priority based on least distance
        PriorityQueue<Edge> pq = new PriorityQueue<>((v1, v2) -> (int) (v1.getDistance() - v2.getDistance()));

        //find dest
        for (Street street : streets) {

        }
        //add depot edge with 0 distance so it is selected first in the priority queue
        pq.add(new Edge(depotStreet.getLegNumber(), depotStreet.getStreetID(), TurnDirection.Straight, 0, new Point(0, 0)));

        while (!pq.isEmpty()) {
            //pick edge with least distance
            Edge currentEdge = pq.poll();

            //if we reach destination street
            if (currentEdge.getStreedID().equals(destination.getStreetId())) {
                route.appendTurn(currentEdge.getTurn(), currentEdge.getStreedID());
                //get street associated with this current edge
                Street currentStreet = new Street();
                for (Street street : streets) {
                    if (street.getStreetID().equals(currentEdge.getStreedID())) {
                        currentStreet = street;
                    }
                }

                //we enter the street from its start point
                if (currentEdge.getEntryPoint().equals(destinationSreet.getStart())) {

                    //we entered through start point, street side of destination will remain the same
                    if (depot.getStreetSide().equals(destination.getStreetSide())) {
                        return route;
                    }
                    //location is on the other side of the street, see if u-turn is possible
                    else if (currentStreet.getEndNeighbour().size() == 0) {
                        //append u-turn
                        route.appendTurn(TurnDirection.UTurn, currentEdge.getStreedID());
                        return route;
                    }
                    //reaching this else statement means that the current location is unreachable. At which case, it would return the route until
                    //the destination street, but on the opposite street side. At which case the driver would have to park vehicle and cross the
                    //street.
                    else {
                        return route;
                    }
                }

                //we enter the street from it's end point
                else if (currentEdge.getEntryPoint().equals(destinationSreet.getEnd()) ) {
                    //if we enter the street from its end point, street side of destination will be flipped
                    if (!depot.getStreetSide().equals(destination.getStreetSide())) {
                        return route;
                    }
                    //location is on the other side of the street, see if u-turn is possible
                    else if (currentStreet.getStartNeighbour().size()==0){
                        route.appendTurn(TurnDirection.UTurn, currentEdge.getStreedID());
                        return route;
                    }
                    //reaching this else statement means that the current location is unreachable. At which case, it would return the route until
                    //the destination street, but on the opposite street side. At which case the driver would have to park vehicle and cross the
                    //street.
                    else {
                        return route;
                    }
                }

            }

            //select edge if it satisfies d[u]+c(u,v)<d[v] and if that edge isn't on the left side.
            //only take left side if the only direction available is left
            for (Edge edge : adjacencyList.get(currentEdge.getLegNumber())) {
                if ((distance[currentEdge.getLegNumber()] +
                        edge.getDistance() < distance[edge.getLegNumber()]) &&
                        (edge.getTurn() != TurnDirection.Left ||
                                adjacencyList.get(currentEdge.getLegNumber()).size() == 1)) {
                    if (route.getLegList().size()==0){
                        route.appendTurn(edge.getTurn(), depotStreet.getStreetID());
                    }
                    route.appendTurn(edge.getTurn(), edge.getStreedID());

                    distance[edge.getLegNumber()] = (edge.getDistance() + distance[currentEdge.getLegNumber()]);
                    pq.add(new Edge(edge.getLegNumber(), edge.getStreedID(), edge.getTurn(), edge.getDistance(), edge.getEntryPoint()));
                }
            }
        }
        return route;
    }

    /**
     * get list of streets in the map
     * @return -- arraylist of streets
     */
    public ArrayList<Street> getStreetList() {
        return this.streets;
    }

}
