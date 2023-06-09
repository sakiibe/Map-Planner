import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Point p1 = new Point(15, 15);
        Point p2 = new Point(15, 30);
        Point p3 = new Point(15, 70);
        Point p4 = new Point(15, -15);
        Point p5 = new Point(5, 5);
        Point p6 = new Point(5, -10);
        Point p7 = new Point(-10, -10);
        Point p8 = new Point(-15, -15);
        Point p10 = new Point(-10, 5);
        Point p11 = new Point(-15, 0);
        Point p12 = new Point(-30, -10);
        Point p13 = new Point(-35, 0);
        Point p14 = new Point(-25, 10);
        Point p15 = new Point(-15, 15);


//        System.out.println(p1.equals(p2));

        MapPlanner mapPlanner = new MapPlanner(20);

        mapPlanner.addStreet("Mohammadpur", p1, p2);
        mapPlanner.addStreet("Mohakhali", p2, p3);
        mapPlanner.addStreet("Dhanmondi", p3, p1);
        mapPlanner.addStreet("Taltola", p4, p3);
        mapPlanner.addStreet("Agargoan", p3, p5);
        mapPlanner.addStreet("A", p5, p6);
        mapPlanner.addStreet("B", p3, p5);
        mapPlanner.addStreet("C", p6, p4);
        mapPlanner.addStreet("D", p6, p7);
        mapPlanner.addStreet("E", p5, p10);
        mapPlanner.addStreet("F", p10, p15);
        mapPlanner.addStreet("G", p10, p11);
        mapPlanner.addStreet("H", p11, p7);
//        for (Street s:mapPlanner.getStreetList()){
//
//            System.out.println(s.getDistance());
//        }

        System.out.println(mapPlanner.depotLocation(new Location("Mohammadpur", StreetSide.Left)));
        Route r = mapPlanner.routeNoLeftTurn(new Location("Taltola", StreetSide.Left));
        System.out.println(r.turnOnto(7));
        System.out.println(r.turnDirection(7));
        SubRoute sub = new SubRoute(r, 1, 7);

//        System.out.println(r.length());
        r.printRoute();
//        System.out.println();
//        r = sub.extractRoute();
//        r.printRoute();


//        Route simpleR= r.simplify();
//        System.out.println("simple");
//        simpleR.printRoute();

//        System.out.println(mapPlanner.furthestStreet());


    }
}
