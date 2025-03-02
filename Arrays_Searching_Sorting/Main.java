
import implementation.BinarySearch;
import implementation.Resturant;
import implementation.Sort;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class Main {

    // Helper method to measure execution time in milliseconds for a Runnable task
    private static long measureTime(Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - start;
    }

    // Method to filter restaurants by dish, rating, and price using binary search (dish)
    public static void filter_by_dish_price_rating(ArrayList<Resturant> data, Resturant res, Scanner sc) {
        // Sort data based on dish first

        System.out.println("Enter the dish name to search: ");
        sc.nextLine(); // Consume the newline left by previous nextInt()
        String dish = sc.nextLine();
        System.out.println();
        System.out.print("Enter the rating: ");
        int rating = sc.nextInt();
        System.out.println();
        System.out.print("Enter the price: ");
        double price = sc.nextDouble();
        System.out.println();
        System.out.println("Filtering based on dish, rating, and price...");
        long elapsed = measureTime(() -> {
            // It is assumed that data is already sorted by dish.
            new Sort(data, "dish");
            new BinarySearch(dish, data, rating, price);
        });
        System.out.println("Filtering by dish, rating, and price took " + elapsed + " milliseconds.");
    }

    // Method to print all restaurant data
    public static void data_print(ArrayList<Resturant> data, Resturant res) {
        long elapsed = measureTime(() -> res.print_data(data));
        System.out.println("Data printing took " + elapsed + " milliseconds.");
    }

    // Method to sort the data based on the given criteria ("rating", "price", or "dish")
    public static void sort_data(ArrayList<Resturant> data, Resturant res, String sortBy) {
        System.out.println("Performing sort based on " + sortBy + "...");
        long elapsed = measureTime(() -> new Sort(data, sortBy));
        res.print_data(data);
        System.out.println("Sorting took " + elapsed + " milliseconds.");
        System.out.println();
    }

    // Method to filter data by rating using binary search
    public static void filter_by_rating(ArrayList<Resturant> data, int target) {
        System.out.println("Filtering based on rating: " + target);
        long elapsed = measureTime(() -> {
            new Sort(data, "rating");
            new BinarySearch(target, data);
        });
        System.out.println("Filtering by rating took " + elapsed + " milliseconds.");
    }

    // Method to filter data by price using binary search
    public static void filter_by_price(ArrayList<Resturant> data, double target) {
        System.out.println("Filtering based on price: " + target);
        long elapsed = measureTime(() -> {
            new Sort(data, "price");
            new BinarySearch(target, data);
        });
        System.out.println("Filtering by price took " + elapsed + " milliseconds.");
    }

    public static void main(String[] args) {
        ArrayList<Resturant> data = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("testcase1.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                String restaurantName = details[0].trim();
                String location = details[1].trim();
                int rating = Integer.parseInt(details[2].trim());
                double price = Double.parseDouble(details[3].trim());
                Resturant res = new Resturant();
                // If a signature dish is provided in the file, use it; otherwise, use the default setter.
                if (details.length >= 5) {
                    String signatureDish = details[4].trim();
                    res.set_details(restaurantName, rating, location, price, signatureDish);
                } else {
                    res.set_details(restaurantName, rating, location, price);
                }
                data.add(res);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error occurred while reading data...");
        }

        Scanner sc = new Scanner(System.in);
        Resturant res = new Resturant();
        while (true) {
            System.out.println();
            System.out.println("1 -> Show the Actual Data:");
            System.out.println("2 -> Show the Sorted Data based on Rating:");
            System.out.println("3 -> Show the Sorted Data based on Price:");
            System.out.println("4 -> Filtered Data based on Rating:");
            System.out.println("5 -> Filtered Data based on Price:");
            System.out.println("6 -> Filtered Data based on Dish, Rating, and Price:");
            System.out.println("7 -> Exit the Program");
            System.out.println();
            System.out.print("Enter Your Choice: ");
            int val = sc.nextInt();
            switch (val) {
                case 1:
                    data_print(data, res);
                    break;
                case 2:
                    sort_data(data, res, "rating");
                    break;
                case 3:
                    sort_data(data, res, "price");
                    break;
                case 4:
                    System.out.print("Enter Rating to Filter: ");
                    filter_by_rating(data, sc.nextInt());
                    break;
                case 5:
                    System.out.print("Enter Price to Filter: ");
                    filter_by_price(data, sc.nextDouble());
                    break;
                case 6:
                    filter_by_dish_price_rating(data, res, sc);
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
           // sc.close();
        }
        
    }
}
