package com.quancheng.achilles.service.web;

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
    public OdpsBaseResponse<OrderStatisticCity> orderRealTimeStatistic() {
        return new OdpsBaseResponse<>(MqConsumer.export());
    }
}
