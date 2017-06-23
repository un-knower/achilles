package com.quancheng.achilles.dao.odps;

import java.util.List;
import java.util.Map;

public interface SaveToDB {

    Boolean save(List<Map<String, Object>> dataList);
}
