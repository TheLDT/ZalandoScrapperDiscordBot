package main;

public class ShoeSize {
    String id, size, price;
    String stock;
    /**
     * Finds the length of the size and adds spaces in the end for better alignment
     * @return 
     */
    public String align(){
        String toReturn = "";
        for(int i = this.size.length();i<4;i++){
            toReturn += " ";
        }
        return toReturn;
    }
    
    public void setStock(String stock){
        if(stock.equals("MANY")){
            //this.stock = ":green_circle:" + stock;
            this.stock = "🟢";
        } else {
            this.stock = "🔴";
        }
    }
    
    @Override
    public String toString() {
        return id + " Size: "+ size + "\tStock:"+ stock + "\tPrice:"+ price;
    }
}
