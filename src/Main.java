import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Point p1= new Point(2,2);
        Point p2= new Point(2,6);
        Point p3= new Point(6,6);
        Point p4= new Point(6,10);
        Point p5= new Point(1000,20);
        Point p6= new Point(2,3453);


//        System.out.println(p1.equals(p2));

        MapPlanner mapPlanner= new MapPlanner(30);

        mapPlanner.addStreet("Mohammadpur", p1, p2);
        mapPlanner.addStreet("Mohakhali", p3, p4);
        mapPlanner.addStreet("DMD", p2, p3);
        mapPlanner.addStreet("oof", p4, p3);
        mapPlanner.addStreet("Agargoan",p5,p6);
        ArrayList<Street> streets=mapPlanner.getStreetList();
        for (Street s: streets){
            System.out.println(s.toString());
        }
    }
}
