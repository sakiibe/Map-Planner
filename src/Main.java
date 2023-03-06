import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Point p1= new Point(2,2);
        Point p2= new Point(2,6);
        Point p3= new Point(6,6);
        Point p4= new Point(6,10);


//        System.out.println(p1.equals(p2));

        MapPlanner mapPlanner= new MapPlanner(30);

        mapPlanner.addStreet("Mohammadpur", p1, p2);

        mapPlanner.addStreet("Mohakhali", p3, p4);
        mapPlanner.addStreet("DMD", p2, p3);
        mapPlanner.addStreet("oof", p4, p3);

        ArrayList<Street> streetArrayList= mapPlanner.getStreetList();


//        for (Street s: streetArrayList){
//            System.out.println("Start of: "+s);
//            for (Street s1: s.getStartNeighbour()){
//                System.out.println(s1.toString());
//            }
//
//            System.out.println("End of: "+s);
//            for (Street s1: s.getEndNeighbour()){
//                System.out.println(s1.toString());
//            }
//        }
    }
}
