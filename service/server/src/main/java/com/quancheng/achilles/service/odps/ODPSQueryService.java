package com.quancheng.achilles.service.odps;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.aliyun.odps.OdpsException;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;
import com.quancheng.achilles.dao.odps.AbstractOdpsQuery;
import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;
import net.sf.json.JSONArray;


@Service
public class ODPSQueryService extends AbstractOdpsQuery{
    public List<OdpsFlyCheck> queryUserCostByClient(String clientId ,String happenDate) throws OdpsException, IOException{
        Map<String,Object> paramaters = new HashMap<>();
        paramaters.put("client_id", clientId);
        paramaters.put("happen_date", happenDate);
        return query(paramaters,InnConstantODPSTables.outSjFlyCheckMonth,OdpsFlyCheck.class);
    }
    
    public JSONArray queryUserCostByClientReturnJson(String clientId ,String happenDate) throws OdpsException, IOException{
        Map<String,Object> paramaters = new HashMap<>();
        paramaters.put("client_id", clientId);
        paramaters.put("happen_date", happenDate);
        return query(paramaters,InnConstantODPSTables.outSjFlyCheckMonth );
    }
    
    
}
