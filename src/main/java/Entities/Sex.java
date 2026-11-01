package Entities;




import java.io.Serializable;


public class Sex implements Serializable {

    private int sexId;


    private String sexValue;

    // Getters and Setters
    public int getSexId() {
        return sexId;
    }

    public void setSexId(int sexId) {
        this.sexId = sexId;
    }

    public String getSexValue() {
        return sexValue;
    }

    public void setSexValue(String sexValue) {
        this.sexValue = sexValue;
    }
}