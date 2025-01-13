package deedev.xpense.utils;

import java.util.ArrayList;

import deedev.xpense.R;
import deedev.xpense.models.Category;

public class Constants {
    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";
    public static ArrayList<Category> categories;
    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_STATS_TYPE = Constants.INCOME;
    public static int DAILY=0;
    public static int MONTHLY=1;
    public static int CALENDER=2;
    public static int SUMMARY=3;
    public static int NOTES=4;


    public static void setCategories() {
        categories = new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.salary, R.color.category1));
        categories.add(new Category("Business", R.drawable.business, R.color.category2));
        categories.add(new Category("Investment", R.drawable.investment, R.color.category3));
        categories.add(new Category("Loan", R.drawable.loan, R.color.category4));
        categories.add(new Category("Rent", R.drawable.rent, R.color.category5));
        categories.add(new Category("Other", R.drawable.other, R.color.category6));
    }

    public static Category getCategoryDetails(String categoryName) {
        if (categoryName == null) {
            return null; // Handle null case
        }

        for (Category cat : categories) {
            if (categoryName.equals(cat.getCategoryName())) { // Ensure categoryName is not null
                return cat;
            }
        }

        return null; // Return null if no category matches
    }

    public static int getAccountsColor(String accountName) {
        if (accountName == null) {
            return R.color.defaultColor; // Return a default color if accountName is null
        }

        switch (accountName) {
            case "Cash":
                return R.color.cashColor;
            case "Bank":
                return R.color.bankColor;
            case "UPI":
                return R.color.UPIColor;
            case "Card":
                return R.color.cardColor;
            default:
                return R.color.defaultColor; // Default color if no match is found
        }
    }


}
