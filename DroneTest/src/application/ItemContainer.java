package application;



import java.util.ArrayList;


public class ItemContainer extends FarmItems {


    private ArrayList<FarmItems> items = new ArrayList<FarmItems>();

    
    public ItemContainer(){

    }

    public ItemContainer(String name, double price, double x_coord, double y_coord, int length, int width, int height){
        this.name = name;
        this.price = price;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.length = length;
        this.width = width;
        this.height = height;
    }
    
    
    
    public boolean add(FarmItems item){
        item.setParent(this);
        return items.add(item);
    }

    

    public void delete(){
        name = "";
        price = 0;
        x_coord = 0.0;
        y_coord = 0.0;
        length = 0;
        width = 0;
        height = 0;
    }
     

    
    public ArrayList<FarmItems> getItems(){
        return items;
    }


    public double getMarket_value() {
        throw new ClassCastException();
    }


    public void setMarket_value(double market_value) {
        throw new ClassCastException();
    }

    public double accept(Visitor v) {
    	return v.visitItemContainer(this);
    }


}