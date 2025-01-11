package deedev.xpense.utils;

import java.util.ArrayList;

import deedev.xpense.R;
import deedev.xpense.models.Category;

public class Constants {
    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";
    public static ArrayList<Category> categories;


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
        for (Category cat:
                categories) {
            if(cat.getCategoryName().equals(categoryName)){
                return cat;
            }
            
        }
        return null;
    }
    public static int getAccountsColor(String accountName){
        switch (accountName){
            case "Cash":
                return R.color.cashColor;
            case "Bank":
                return R.color.bankColor;
            case "UPI":
                return R.color.UPIColor;
            case "Card":
                return R.color.cardColor;
            default:
                return R.color.defaultColor;

        }
    }

}
