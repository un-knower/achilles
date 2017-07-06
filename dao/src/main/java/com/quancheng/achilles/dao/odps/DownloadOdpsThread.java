package com.quancheng.achilles.dao.odps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.odps.Column;
import com.aliyun.odps.TableSchema;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.type.TypeInfo;

public class DownloadOdpsThread implements Callable<List<Map<String, Object>>> {

    private Logger       logger = LoggerFactory.getLogger(DownloadOdpsThread.class);
    private long         id;
    private RecordReader recordReader;
    private TableSchema  tableSchema;

    public DownloadOdpsThread(long id, RecordReader recordReader, TableSchema tableSchema){
        this.id = id;
        this.recordReader = recordReader;
        this.tableSchema = tableSchema;
    }

    @Override
    public List<Map<String, Object>> call() {
        List<Map<String, Object>> list = new ArrayList<>();
        Long recordNum = 0L;
        try {
            Record record;
            while ((record = recordReader.read()) != null) {
                recordNum++;
                // System.out.println("Thread " + id + "\t");
                Map<String, Object> consumeRecord = consumeRecord(record, tableSchema);
                list.add(consumeRecord);
            }
            recordReader.close();
        } catch (IOException e) {
            logger.error("DownloadOdpsThread [{}] have a error {}", id, e);
        }
        return list;
    }

    private static Map<String, Object> consumeRecord(Record record, TableSchema schema) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < schema.getColumns().size(); i++) {
            Column column = schema.getColumn(i);
            String colValue = null;
            String colName = column.getName();
            TypeInfo typeInfo = column.getTypeInfo();
            switch (typeInfo.getOdpsType()) {
                case BIGINT: {
                    Long v = record.getBigint(i);
                    colValue = v == null ? null : (v.toString().equals("") ? null : v.toString());
                    break;
                }
                case BOOLEAN: {
                    Boolean v = record.getBoolean(i);
                    colValue = v == null ? null : (v.toString().equals("") ? null : v.toString());
                    break;
                }
                case DATETIME: {
                    Date v = record.getDatetime(i);
                    colValue = v == null ? null : (v.toString().equals("") ? null : v.toString());
                    break;
                }
                case DOUBLE: {
                    Double v = record.getDouble(i);
                    colValue = v == null ? null : (v.toString().equals("") ? null : v.toString());
                    break;
                }
                case STRING: {
                    String v = record.getString(i);
                    colValue = v == null ? null : (v.toString().equals("") ? null : v.toString());
                    break;
                }
                default:
                    throw new RuntimeException("Unknown column type: " + typeInfo.getOdpsType());
            }
            map.put(colName, colValue);
            // System.out.print(colValue == null ? "null" : colValue);
            // if (i != schema.getColumns().size()) System.out.print("\t");
        }
        // System.out.println();
        return map;
    }
}
