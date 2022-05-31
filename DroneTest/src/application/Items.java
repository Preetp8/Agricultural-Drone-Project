package application;

public class Items extends FarmItems {

    private double purchase_value;

	public Items() {
		
	}
	
	
    public Items(String name, double price, double x_coord, double y_coord, int length, int width, int height,double purchase_value, double market_value){
        this.name = name;
        this.price = price;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.length = length;
        this.width = width;
        this.height = height;
        this.purchase_value = purchase_value;
        this.market_value = market_value;

    }
	
    public void delete(){
        name = "";
        price = 0;
        x_coord = 0.0;
        y_coord = 0.0;
        length = 0;
        width = 0;
        height = 0;
        market_value = 0.0;
    }

    @Override
    public double getMarket_value() {
        return market_value;
    }


    public void setMarket_value(double market_value) {
        this.market_value = market_value;
    }

    public void setPurchasePrice(double purchase_value){
        this.purchase_value = purchase_value;
    }

    public double getPurchase_value() {
        return purchase_value;
    }
    
    public double accept(Visitor v) {
    	return v.visitItem(this);
    }
    
	
	
}
