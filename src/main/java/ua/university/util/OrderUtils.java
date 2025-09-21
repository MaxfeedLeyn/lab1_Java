package ua.university.util;

import ua.university.model.Customer;
import ua.university.model.MenuItem;
import ua.university.model.Order;


import java.util.Date;
import java.util.Locale;

public class OrderUtils {

    private OrderUtils(){
    }

    public static boolean isValidDate(Date date){
        return ValidationHelper.isValidDate(date);
    }

    public static boolean isValidCustomer(Customer customer){
        return customer != null;
    }

    public static boolean isValidMenu(MenuItem[] menuItems){
        return menuItems != null && menuItems.length != 0;
    }
}
