package application;

import java.util.Iterator;

public class PricingVisitor implements Visitor{
    private double total;

    public PricingVisitor(){
        total = 0;
    }


    public double visitItemContainer(FarmItems itemContainer){
        total = itemContainer.getPrice();
        Iterator<FarmItems> iterator = FarmItems.getItemData().iterator();
        while(iterator.hasNext()){
            FarmItems currentComponent = iterator.next();
            total += currentComponent.accept(new PricingVisitor());
        }
        return total;
    }


    @Override
    public double visitItem(FarmItems item){
        total = item.getPrice();
        return total;
    }


}
