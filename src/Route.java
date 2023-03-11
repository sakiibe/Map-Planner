import java.util.ArrayList;
import java.util.List;

/**
 * Define a route to travel in the map.  It's a sequence of turns and streets in the city map.
 *
 * The first leg of a route is leg 1.
 */
public class Route {

    private ArrayList<Leg> legs;
    private ArrayList<Street> streets;
    private int legNumber;

    public Route(ArrayList<Street> streets ){

        this.legs=new ArrayList<>();
        this.streets=streets;
        this.legNumber=1;
    }
    /**
     * Grow a Route by adding one step (called a "leg") of the route at a time.  This method adds one more
     * leg to an existing route
     * @param turn -- from the current route, what kind of turn do you make onto the next leg
     * @param streetTurnedOnto -- the street id onto which the next leg of the route turns
     * @return -- true if the leg was added to the route.
     */

    public Boolean appendTurn( TurnDirection turn, String streetTurnedOnto ) {
       legs.add(new Leg(turn,streetTurnedOnto, legNumber));
       legNumber++;
        return true;
    }

    /**
     * Given a route, report whether the street of the given leg number of the route.
     *
     * Leg numbers begin with 1.
     * @param legNumber -- the leg number for which we want the next street.
     * @return -- the street id of the next leg, or null if there is an error.
     */
    public String turnOnto( int legNumber ) {
        return legs.get(legNumber-1).getStreetID();
    }

    /**
     * Given a route, report whether the type of turn that initiates the given leg number of the route.
     *
     * Leg numbers begin with 1.
     * @param legNumber -- the leg number for which we want the next turn.
     * @return -- the turn direction for the leg, or null if there is an error.
     */
    public TurnDirection turnDirection( int legNumber ) {
        return legs.get(legNumber-1).getTurn();
    }

    /**
     * Report how many legs exist in the current route
     * @return -- the number of legs in this route.
     */
    public int legs() {
        return legs.size();
    }

    /**
     * Report the length of the current route.  Length is computed in metres by Euclidean distance.
     *
     * By assumption, the route always starts and ends at the middle of a road, so only half of the length
     * of the first and last leg roads contributes to the length of the route
     * @return -- the length of the current route.
     */
    public Double length() {

        double length=0.0;
        //extract distance out of list of street for legs in this route
        for (Leg leg:legs){
            for (Street street:streets){
                if (street.getStreetID().equals(leg.getStreetID())){
                    //if current leg is the depot leg or destination leg, add half distance. Otherwise, add full distance
                    if (leg.getLegNumber()==1 || legs.indexOf(leg)+1== this.legs.size()){
                        length+=street.getDistance()/2;
                    } else {
                        length+=street.getDistance();
                    }
                    break;
                }
            }

        }

        return length;
    }

    /**
     * Given a route, return all loops in the route.
     *
     * A loop in a route is a sequence of streets where we start and end at the same intersection.  A typical
     * example of a loop would be driving around the block in a city.  A loop does not need you to start and end
     * the loop going in the same direction.  It's just a point of driving through the same intersection again.
     *
     * A route may contain more than one loop.  Return the loops in order that they start along the route.
     *
     * If one loop is nested inside a larger loop then only report the larger loop.
     * @return -- a list of subroutes (starting and ending legs) of each loop.  The starting leg and the ending leg
     * share a common interesection.
     */
    public List<SubRoute> loops() {
        List<SubRoute> loops= new ArrayList<>();
        Route route= new Route(this.streets);
        for (Leg leg:legs){
            //if route does not reach the same leg, grow the route
            if (!route.getLegList().contains(leg)){
                route.appendTurn(leg.getTurn(),leg.getStreetID());
            }
            //if it reaches a common intersection, instantiate a new subroute with it's start and end leg and append it to list of loops
            else {
                loops.add(new SubRoute(route, route.getLegList().get(0).getLegNumber(), route.getLegList().get(getLegList().size()-1).getLegNumber()));
                route=new Route(this.streets);
            }
        }

        return loops;
    }

    /**
     * Given a route, produce a new route with simplified instructions.  The simplification reports a route
     * that reports the turns in the route but does not report the points where you should keep going straight
     * along your current path.
     * @return -- the simplified route.
     */
    public Route simplify() {

        Route simpleRoute= new Route(this.streets);
        //add all legs except legs containing straight turns
        for (Leg leg:legs){
            if (!leg.getTurn().equals(TurnDirection.Straight)){
                simpleRoute.appendTurn(leg.getTurn(),leg.getStreetID());
            }
        }

        return simpleRoute;
    }

    /**
     * get the legs of this route
     * @return -- Arraylist of legs of this route
     */
    public ArrayList<Leg> getLegList() {
        return legs;
    }

    /**
     * get streets in the map
     * @return -- Arraylist of streets of the map
     */
    public ArrayList<Street> getStreetList() {
        return streets;
    }

    public void printRoute(){
        for (Leg leg:legs){
            System.out.println(leg.toString());
        }
    }
}
