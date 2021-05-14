package main;

public class ShoeSize {
    String id, size, price;
    long stock;

    @Override
    public String toString() {
        return id + " Size: "+ size + "\tStock:"+ stock + "\tPrice:"+ price;
    }
}
