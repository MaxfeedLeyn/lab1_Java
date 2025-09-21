package ua.university.model;

import ua.university.util.MenuItemUtils;

import java.util.Objects;

public class MenuItem {
    private String name;
    private float price;
    private String category;

    public MenuItem(){
    }

    public MenuItem(String name, float price, String category) {
        setName(name);
        setPrice(price);
        setCategory(category);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        if(MenuItemUtils.isValidName(name)) {
            this.name = MenuItemUtils.capitalizeText(name);
        }
    }

    public void setCategory(String category) {
        if(MenuItemUtils.isValidCategory(category)) {
            this.category = MenuItemUtils.capitalizeText(category);
        }
    }

    public void setPrice(float price) {
        if (MenuItemUtils.isValidFloat(price)) {
            this.price = price;
        }
    }

    public static MenuItem createMenuItem(String name, float price, String category) {
        if(MenuItemUtils.isValidName(name) &&
                MenuItemUtils.isValidFloat(price) &&
                MenuItemUtils.isValidCategory(category)) {
            return new MenuItem(name, price, category);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Cuisine{" +
                "Name=" + name + '\'' +
                ", Price=" + price + '\'' +
                ", Category='" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Float.compare(menuItem.price, price) == 0 &&
                Objects.equals(name, menuItem.name) &&
                Objects.equals(category, menuItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category);
    }
}
