package implementation;

import java.util.ArrayList;

public class BinarySearch {

    // Constructor for searching by rating
    public BinarySearch(int target, ArrayList<Resturant> data) {
        int start = BS_by_rating(data, target, true);
        int end = BS_by_rating(data, target, false);
        if (start == -1 || end == -1) {
            System.out.println("Not Found");
            return;
        }
        Resturant res = new Resturant();
        res.print_data(data, start, end);
    }

    // Constructor for searching by price
    public BinarySearch(double target, ArrayList<Resturant> data) {
        int start = BS_by_price(data, target, true);
        int end = BS_by_price(data, target, false);
        if (start == -1 || end == -1) {
            System.out.println("Not Found");
            return;
        }
        Resturant res = new Resturant();
        res.print_data(data, start, end);
    }

    // Constructor for searching by dish with additional conditions for rating and price.
    // Here, 'target' is the dish name, 'cond1' is the required rating, and 'cond2' is the required price.
    public BinarySearch(String target, ArrayList<Resturant> data, int cond1, double cond2) {
        int start = BS_by_dish(data, target, true);
        int end = BS_by_dish(data, target, false);
        if (start == -1 || end == -1) {
            System.out.println("Not Found");
            return;
        }
        // Iterate only over the range where the dish matches.
        for (int i = start; i <= end; i++) {
            if (data.get(i).get_rating() == cond1 && data.get(i).get_price() == cond2) {
                data.get(i).get_details(); // Print details of the matching restaurant.
            }
        }
    }

    // Binary search for rating
    public int BS_by_rating(ArrayList<Resturant> restaurants, int target, boolean searchFirst) {
        int l = 0, r = restaurants.size() - 1, index = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (restaurants.get(mid).get_rating() == target) {
                index = mid;
                if (searchFirst) {
                    r = mid - 1; // look left for the first occurrence
                } else {
                    l = mid + 1; // look right for the last occurrence
                }
            } else if (restaurants.get(mid).get_rating() > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return index;
    }

    // Binary search for price
    public int BS_by_price(ArrayList<Resturant> restaurants, double target, boolean searchFirst) {
        int l = 0, r = restaurants.size() - 1, index = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            double midPrice = restaurants.get(mid).get_price();
            if (midPrice == target) {
                index = mid;
                if (searchFirst) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else if (midPrice > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return index;
    }

    // Binary search for dish (signature dish)
    public int BS_by_dish(ArrayList<Resturant> restaurants, String target, boolean searchFirst) {
        int left = 0;
        int right = restaurants.size() - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = restaurants.get(mid).get_signatureDish().compareToIgnoreCase(target);
            if (cmp == 0) {
                index = mid;
                if (searchFirst) {
                    right = mid - 1; // search left for first occurrence
                } else {
                    left = mid + 1;  // search right for last occurrence
                }
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {

    }
}
