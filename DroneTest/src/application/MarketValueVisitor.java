package application;

import java.util.Iterator;

public class MarketValueVisitor implements Visitor{
    private double marketValue;
    private double totalValue;

    public MarketValueVisitor(){
        totalValue = 0.0;
    }

    @Override
    public double visitItem(FarmItems item){
        marketValue = item.getMarket_value();
        return marketValue;
    }

    @Override
    public double visitItemContainer(FarmItems itemContainer){
        Iterator<FarmItems> iterator = FarmItems.getItemData().iterator();
        while(iterator.hasNext()){
            FarmItems currentComponent = iterator.next();
            totalValue += currentComponent.accept(new MarketValueVisitor());
        }
        return totalValue;
    }



}
