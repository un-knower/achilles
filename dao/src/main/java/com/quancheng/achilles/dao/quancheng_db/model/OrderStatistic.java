package com.quancheng.achilles.dao.quancheng_db.model;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

public class OrderStatistic  {
     private final OrderStatisticCity orderStatisticCity;
     private AtomicInteger orderCountCache = new AtomicInteger(0);
     private DoubleAdder orderSumCache=new DoubleAdder();
     private AtomicInteger peopleSumCache=new AtomicInteger(0);
    public OrderStatistic(String cityName ) {
        super( );
        orderStatisticCity= new OrderStatisticCity(cityName);
    }
     
    public OrderStatistic(OrderStatisticCity orderStatisticCity ) {
        super( );
        this.orderStatisticCity= orderStatisticCity;
        caculate(orderStatisticCity.getPeopleSum(),orderStatisticCity.getOrderSum().doubleValue(),orderStatisticCity.getOrderCount());
    }
    public OrderStatisticCity getOrderStatisticCity() {
        orderStatisticCity.setOrderCount(orderCountCache.get());
        orderStatisticCity.setOrderSum(new BigDecimal(orderSumCache.sum()).setScale(2,BigDecimal.ROUND_HALF_UP));
        orderStatisticCity.setPeopleSum(peopleSumCache.get());
        return orderStatisticCity;
    }
    public  void caculate(int peopleNum,double predictCost,int orderCount) {
        orderSumCache.add(predictCost);
        peopleSumCache.getAndAdd(peopleNum);
        orderCountCache.addAndGet(orderCount);
    }
}
