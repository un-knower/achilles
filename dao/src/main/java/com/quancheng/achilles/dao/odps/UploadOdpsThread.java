package com.quancheng.achilles.dao.odps;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordWriter;

public class UploadOdpsThread implements Callable<Boolean> {

    private long         id;
    private int          threadSize;
    private RecordWriter recordWriter;
    private List<Record> recordList;
    // private TableSchema tableSchema;

    public UploadOdpsThread(long id, int threadSize, RecordWriter recordWriter, List<Record> recordList){
        this.id = id;
        this.recordWriter = recordWriter;
        this.recordList = recordList;
        this.threadSize = threadSize;
        // this.tableSchema = tableSchema;
    }

    @Override
    public Boolean call() {
        boolean result = true;
        double pageSize = Math.ceil((double) recordList.size() / (double) threadSize);
        int start = (int) (id * pageSize);
        int end = (int) (id * pageSize);

        // for (int i = 0; i < tableSchema.getColumns().size(); i++) {
        // Column column = tableSchema.getColumn(i);
        // switch (column.getTypeInfo().getOdpsType()) {
        // case BIGINT:
        // record.setBigint(i, 1L);
        // break;
        // case BOOLEAN:
        // record.setBoolean(i, true);
        // break;
        // case DATETIME:
        // record.setDatetime(i, new Date());
        // break;
        // case DOUBLE:
        // record.setDouble(i, 0.0);
        // break;
        // case STRING:
        // record.setString(i, "sample");
        // break;
        // default:
        // throw new RuntimeException("Unknown column type: " + column.getTypeInfo().getTypeName());
        // }
        // }
        for (int i = start; i <= end; i++) {
            try {
                recordWriter.write(recordList.get(i));
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
                break;
            }
        }
        try {
            recordWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
