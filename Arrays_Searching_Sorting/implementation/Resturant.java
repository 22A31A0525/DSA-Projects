package implementation;

import java.util.ArrayList;

public class Resturant {

    private String resturantName, location;
    private int rating;
    private double price;
    private String signatureDish;

    // Prints restaurant details including the signature dish.
    public void get_details() {
        System.out.print("Resturant Name: " + resturantName + " , ");
        System.out.print("Location: " + location + " , ");
        System.out.print("Price: " + price + " , ");
        System.out.print("Rating: " + rating + " , ");
        System.out.println("Dish: " + (signatureDish != null ? signatureDish : "N/A"));
        System.out.println();
    }

    public int get_rating() {
        return rating;
    }

    public double get_price() {
        return price;
    }

    public String get_signatureDish() {
        return signatureDish;
    }

    // Updated setter to include signature dish.
    public void set_details(String resturantName, int rating, String location, double price, String signatureDish) {
        this.resturantName = resturantName;
        this.rating = rating;
        this.price = price;
        this.location = location;
        this.signatureDish = signatureDish;
    }

    public void set_details(String resturantName, int rating, String location, double price) {
        set_details(resturantName, rating, location, price, "N/A");
    }

    // Prints the details of all restaurants in the provided list.
    public void print_data(ArrayList<Resturant> data) {
        for (Resturant ele : data) {
            ele.get_details();
        }
    }

    // Prints the details of restaurants from index 'start' to 'end'.
    public void print_data(ArrayList<Resturant> data, int start, int end) {
        System.out.println("______________________________");
        for (int i = start; i <= end; i++) {
            data.get(i).get_details();
        }
        System.out.println("______________________________");
    }
}
