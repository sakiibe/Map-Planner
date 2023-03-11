import java.util.ArrayList;

public class SubRoute {
    /**
     * Identify a sub-part of a route.  The sub-part of the route goes from the start of the startLeg
     * to the end of the endLeg.
     *
     * The Route from which we start may contain loops.
     * @param walk -- the route from which we are identifying a subroute
     * @param startLeg -- the starting leg of the subroute
     * @param endLeg -- the ending leg of the subroute
     */
    Route walk;
    int startLeg;
    int endLeg;
    ArrayList<Leg> legs;
    public SubRoute( Route walk, int startLeg, int endLeg ) {
        this.walk=walk;
        this.startLeg=startLeg;
        this.endLeg=endLeg;
        this.legs= walk.getLegList();
    }

    /**
     * Return the leg number that starts this subroute
     * @return -- the starting leg number
     */
    public int subrouteStart() {
        return startLeg;
    }

    /**
     * The ending leg number for this subroute
     * @return - the leg number that ends the subroute
     */
    public int subrouteEnd() {
        return endLeg;
    }

    /**
     * Convert this subroute into a pure route of its own.
     * @return -- the Route that represents the subroute all on its own.
     */
    public Route extractRoute() {
        Route route = new Route(walk.getStreetList());

        for (int i=startLeg-1; i<endLeg; i++){
            Leg leg= legs.get(i);
            route.appendTurn(leg.getTurn(), leg.getStreetID());
        }
        return route;
    }
}
