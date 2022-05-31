package application;

import java.util.*;




public abstract class FarmItems {

	
	FarmItems parent;
	public String name;
    public double price;
    public double x_coord;
    public double y_coord;
    public int length;
    public int width;
    public int height;
    public double market_value;

    private static ArrayList<FarmItems> items = new ArrayList<>();

    private static ArrayList<FarmItems> item_container = new ArrayList<>();


    public static ArrayList<FarmItems> getItemContainerData(){
        return item_container;
    }

    public static ArrayList<FarmItems> getItemData(){
        return items;
    }

    public static boolean addItemContainer(FarmItems itemcontainer) {
        item_container.add(itemcontainer);
        return true;
    }

    public static boolean addItem(FarmItems item) {
        items.add(item);
        return true;
    }


    public FarmItems() {
    	
    }


    public void setParent(FarmItems parent){
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
    	this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getXLocation(){
        return this.x_coord;
    }

    public void setXLocation(double x) {
        this.x_coord = x;
    }


    public double getYLocation(){
        return this.y_coord;
    }
    
    public void setYLocation(double y) {
        this.y_coord = y;
    }

    public int getLength(){
        return this.length;
    }

    public void setLength(int length){
        this.length = length;
    }

    public int getWidth(){
        return this.width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return this.height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public abstract double getMarket_value();

    public abstract void setMarket_value(double value);




    public abstract double accept(Visitor v);

    @Override
    public String toString() {
    	return name + " " + price + " " + x_coord + " " + y_coord + " " + length + " " + width+ " " + height + " " + market_value;
    }



}