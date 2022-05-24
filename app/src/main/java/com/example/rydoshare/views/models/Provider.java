package com.example.rydoshare.views.models;

public class Provider {
    private String name;
    private String origin;
    private String destination;
    private long phonenumber;
    private String date;
    private String time;
    private float price;
    private String id;

    public Provider(String name, String origin, String destination, long phonenumber, String date, String time, float price) {
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.phonenumber = phonenumber;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public Provider() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    @Override
//    public String toString() {
//        return "Provider{" +
//                "name='" + name + '\'' +
//                ", origin='" + origin + '\'' +
//                ", destination='" + destination + '\'' +
//                ", phonenumber=" + phonenumber +
//                ", date='" + date + '\'' +
//                ", time='" + time + '\'' +
//                ", price=" + price +
//                ", id='" + id + '\'' +
//                '}';
//    }
}
