package com.quancheng.achilles.service.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quancheng.achilles.dao.quancheng_db.model.OrderStatisticCity;
import com.quancheng.achilles.service.model.OdpsBaseResponse;
import com.quancheng.achilles.service.realtime.MqConsumer;

@RequestMapping("/api")
@RestController
public class DataRestApiController {
    @RequestMapping(path="/order/realtime/statistic",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OdpsBaseResponse<List<OrderStatisticCity>> orderRealTimeStatistic(Integer top) {
        return new OdpsBaseResponse<>(Arrays.asList(MqConsumer.exportDetail(top==null?0:top)));
    }
    
    
    @RequestMapping(path="/order/realtime/total",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OdpsBaseResponse<OrderStatisticCity> orderRealTimeStatisticTotal() {
        return new OdpsBaseResponse<>(Arrays.asList(MqConsumer.exportStatistic()));
    }
}
