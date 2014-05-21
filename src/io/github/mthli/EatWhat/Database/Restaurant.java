package io.github.mthli.EatWhat.Database;

public class Restaurant {
    public static final String TABLE = "restaurant";
    public static final String RESTAURANT = "RESTAURANT";
    public static final String PATH = "PATH";

    public static final String CREATE_SQL = "CREATE TABLE "
            + TABLE
            + " ("
            + " RESTAURANT text,"
            + " PATH text"
            + ")";

    private String restaurant;
    private String path;

    public String getRestaurant() {
        return restaurant;
    }
    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
