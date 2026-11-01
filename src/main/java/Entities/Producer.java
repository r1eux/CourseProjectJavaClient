package Entities;




import java.io.Serializable;


public class Producer implements Serializable {

    private int producerId;


    private String producer;
    private String producer_country;

    public void setProducer_country(String producer_country) {
        this.producer_country = producer_country;
    }
    public String getProducer_country() {
        return producer_country;
    }

    // Getters and Setters
    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}