package Entities;



import java.io.Serializable;


public class Color implements Serializable {

    private int colorId;


    private String color;

    // Getters and Setters
    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}