package implementation;

import java.util.ArrayList;

public class Sort {

    private String sortBy;

    private void merge(ArrayList<Resturant> res, int left, int mid, int right) {
        ArrayList<Resturant> ls = new ArrayList<>();
        int l = left, r = mid + 1;
        while (l <= mid && r <= right) {
            boolean condition = false;

            if (sortBy.equalsIgnoreCase("rating")) {
                condition = res.get(l).get_rating() <= res.get(r).get_rating();
            } else if (sortBy.equalsIgnoreCase("price")) {
                condition = res.get(l).get_price() <= res.get(r).get_price();
            } else if (sortBy.equalsIgnoreCase("dish")) {

                condition = res.get(l).get_signatureDish().compareToIgnoreCase(res.get(r).get_signatureDish()) <= 0;
            } else {
                // Default to rating if sortBy is not recognized
                condition = res.get(l).get_rating() <= res.get(r).get_rating();
            }

            if (condition) {
                ls.add(res.get(l));
                l++;
            } else {
                ls.add(res.get(r));
                r++;
            }
        }
        while (l <= mid) {
            ls.add(res.get(l));
            l++;
        }
        while (r <= right) {
            ls.add(res.get(r));
            r++;
        }
        for (int i = 0; i < ls.size(); i++) {
            res.set(left + i, ls.get(i));
        }
    }

    private void mergeSort(ArrayList<Resturant> res, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(res, left, mid);
        mergeSort(res, mid + 1, right);
        merge(res, left, mid, right);
    }

    public void sort(ArrayList<Resturant> res, String criteria) {
        this.sortBy = criteria;
        mergeSort(res, 0, res.size() - 1);
    }

    public Sort(ArrayList<Resturant> res, String criteria) {
        this.sort(res, criteria);
    }

    public static void main(String[] args) {

    }
}
