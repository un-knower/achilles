package com.quancheng.achilles.service.realtime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.quancheng.achilles.dao.ds_qc.model.Region;
import com.quancheng.achilles.dao.ds_qc.repository.RegionRepository;
import com.quancheng.achilles.dao.quancheng_db.model.OrderStatisticCity;
import com.quancheng.achilles.dao.quancheng_db.repository.OrderStatisticCityRepository;
import com.quancheng.starter.messagequeue.MessageListenerDef;

@MessageListenerDef(topic = "mq.aliyun.topic", tags = "terra_order_entry")
public class MqConsumer implements MessageListener ,InitializingBean{
    
    private final Logger LOGGER = LoggerFactory.getLogger(MqConsumer.class);
    @Autowired
    OrderStatisticCityRepository orderStatisticCityRepository;
    @Autowired
    RegionRepository regionRepository;
    
    private static final Map<String,AtomicInteger> cityCount = new HashMap<>();
    
    private static final Map<String,OrderStatisticCity> entityMap = new ConcurrentHashMap<>();
    
    private   Integer lastMaxId= 0;
    
    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            String   msgStr = new String(message.getBody());
            msgStr  = msgStr.substring(msgStr.indexOf("{"));
            JSONObject json = JSONObject.parseObject(msgStr);
            JSONObject datas = json.getJSONObject("data");
            if(!"INSERT".equals(json.getString("opt"))) {
                return Action.CommitMessage;
            }
            JSONArray attribute = datas.getJSONArray("attribute");
            if(attribute==null) {
                return Action.CommitMessage;
            }
            JSONObject jsonAttribute = JSONObject.parseObject(attribute.getString(0));
            if(jsonAttribute==null) {
                return  Action.CommitMessage;
            }
            Integer terraId = datas.getJSONArray("id").getInteger(0);
            LOGGER.info("statistic for terra_id:{}",terraId);
            if(lastMaxId!=0) {
                synchronized (lastMaxId) {
                    if(lastMaxId!=0) {
                        doMakeUp(terraId);
                        lastMaxId=0;
                    }
                }
            }
            OrderStatisticCity orderStatisticCity =doStatistic(jsonAttribute,terraId);
            if(orderStatisticCity!=null) {
                orderStatisticCityRepository.save(orderStatisticCity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Action.ReconsumeLater;
        }
        return Action.CommitMessage;
    }
    
    private void doMakeUp(Integer maxId) {
        int pageSize = 1000;
        List<String> attributes = null ;
        int i=0;
        do {
            attributes=orderStatisticCityRepository.queryOrderAttribute(lastMaxId, maxId,pageSize*i , pageSize );
            i++;
            if(attributes.isEmpty()) {
                break;
            }
            for(String attribute:attributes){
                try {
                    doStatistic(JSONObject.parseObject(attribute),maxId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            orderStatisticCityRepository.save(entityMap.values());
        }while(attributes!=null && attributes.size()==pageSize);
        
    }
    
    private OrderStatisticCity doStatistic( JSONObject jsonAttribute,Integer terraId  ) {
        if(jsonAttribute==null) {
            return null;
        }
        String city=jsonAttribute.getString("cityName");
        if(StringUtils.isEmpty(city)) {
            Integer cityId=jsonAttribute.getInteger("cityId");
            Region rg =cityId!=null?regionRepository.findOne(cityId.longValue()):regionRepository.findOne(321l);
            city =rg==null?null:rg.getRegionName();
        }
        if(StringUtils.isEmpty(city)) {
            return  null;
        }
        final String newCity=city.replaceAll("市", "");
        if(!entityMap.containsKey(newCity)) {
            List<OrderStatisticCity> orderStatisticCities=orderStatisticCityRepository.findAll(Specifications.where(new Specification<OrderStatisticCity>() {
                public Predicate toPredicate(Root<OrderStatisticCity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.equal(root.get("order_city"), newCity);
                }
            }));
            entityMap.put(newCity, CollectionUtils.isEmpty(orderStatisticCities)? new OrderStatisticCity(0,0d,newCity ,terraId):orderStatisticCities.get(0));
        }
        OrderStatisticCity orderStatisticCity= entityMap.get(newCity);
        if( terraId!=null) {
            orderStatisticCity.setLastOrderId(terraId);
        }
        AtomicInteger ai=cityCount.get(newCity);
        if(ai==null) {
            ai=new AtomicInteger(orderStatisticCity.getOrder_count());
            cityCount.put(newCity, ai);
        }
        int newCount=ai.incrementAndGet();
        orderStatisticCity.setOrder_count(newCount);
        
       
        Double currentOrderPredictCost = jsonAttribute.getDouble("predictCost");
        if(currentOrderPredictCost==null||currentOrderPredictCost>100000) {
            return null;
        }
        BigDecimal now=BigDecimal.valueOf(orderStatisticCity.getOrderSum());
        BigDecimal result = now.add( currentOrderPredictCost==null?BigDecimal.ZERO:BigDecimal.valueOf(currentOrderPredictCost))
        .setScale(2, BigDecimal.ROUND_HALF_UP);
        orderStatisticCity.setOrderSum(result.doubleValue());
        return orderStatisticCity;
    }
    
    public static List<OrderStatisticCity> export(){
        List<OrderStatisticCity> result = new  ArrayList<>(entityMap.values());
        Collections.sort(result ,new Comparator<OrderStatisticCity>() {
            public int compare(OrderStatisticCity o1, OrderStatisticCity o2) {
                return o1.getOrder_count().equals(o2.getOrder_count())?0:(o1.getOrder_count()>o2.getOrder_count()?1:-1);
            }
        });
        return result;
    }
    public void afterPropertiesSet() throws Exception {
        List<OrderStatisticCity> orderStatisticCities=orderStatisticCityRepository.findAll();
        int max=0;
        for (OrderStatisticCity orderStatisticCity : orderStatisticCities) {
            if(orderStatisticCity.getLastOrderId()!=null&& orderStatisticCity.getLastOrderId()>max) {
                max=orderStatisticCity.getLastOrderId();
            }
            entityMap.put(orderStatisticCity.getOrder_city(), orderStatisticCity);
            cityCount.put(orderStatisticCity.getOrder_city(), new AtomicInteger(orderStatisticCity.getOrder_count()));
        }
        lastMaxId=max;
    }
}
