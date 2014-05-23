package io.github.mthli.EatWhat.Usual;

import android.widget.ImageButton;

public class UsualItem {
    private String restaurant;
    private String path;
    private ImageButton imageButton;

    public UsualItem(
            String restaurant,
            String path,
            ImageButton imageButton
    ) {
        super();
        this.restaurant = restaurant;
        this.path = path;
        this.imageButton = imageButton;
    }

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

    public ImageButton getImageButton() {
        return imageButton;
    }
    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }
}
