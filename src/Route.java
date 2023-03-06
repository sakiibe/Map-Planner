import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Define a route to travel in the map.  It's a sequence of turns and streets in the city map.
 *
 * The first leg of a route is leg 1.
 */
public class Route {

    private int degrees;

    private ArrayList<Pair<String,TurnDirection>> currentRoute;
    private ArrayList<Street> streetList;

    public Route( ArrayList<Street> streetList){
        this.currentRoute= new ArrayList<>();
        this.streetList= streetList;
    }
    /**
     * Grow a Route by adding one step (called a "leg") of the route at a time.  This method adds one more
     * leg to an existing route
     * @param turn -- from the current route, what kind of turn do you make onto the next leg
     * @param streetTurnedOnto -- the street id onto which the next leg of the route turns
     * @return -- true if the leg was added to the route.
     */

    public Boolean appendTurn( TurnDirection turn, String streetTurnedOnto ) {
        currentRoute.add(new Pair<>(streetTurnedOnto, turn));
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
        return currentRoute.get(legNumber).getKey();
    }

    /**
     * Given a route, report whether the type of turn that initiates the given leg number of the route.
     *
     * Leg numbers begin with 1.
     * @param legNumber -- the leg number for which we want the next turn.
     * @return -- the turn direction for the leg, or null if there is an error.
     */
    public TurnDirection turnDirection( int legNumber ) {
        return currentRoute.get(legNumber).getValue();
    }

    /**
     * Report how many legs exist in the current route
     * @return -- the number of legs in this route.
     */
    public int legs() {
        return currentRoute.size();
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

        for (Pair<String,TurnDirection> pair: currentRoute ){

            //capture street object for the current pair
            Street currentStreet=new Street();
            for (Street street:streetList){
                if (street.getStreetID().equals(pair.getKey())){
                    currentStreet=street;
                    break;
                }
            }
            //Start and end leg's distance will be half because we start and end at the centre
            if (currentRoute.indexOf(pair)==0 || currentRoute.indexOf(pair)==legs()){
                length+=currentStreet.getDistance()/2;
            } else {
                length+=currentStreet.getDistance();
            }
        }

        return 0.0;
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
        return null;
    }

    /**
     * Given a route, produce a new route with simplified instructions.  The simplification reports a route
     * that reports the turns in the route but does not report the points where you should keep going straight
     * along your current path.
     * @return -- the simplified route.
     */
    public Route simplify() {
        return null;
    }
}
