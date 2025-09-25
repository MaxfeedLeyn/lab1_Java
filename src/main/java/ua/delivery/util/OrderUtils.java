package ua.delivery.util;

import ua.delivery.model.Customer;
import ua.delivery.model.MenuItem;


import java.util.Date;

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
