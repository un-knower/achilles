package com.quancheng.achilles.dao.odps;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordWriter;

public class UploadOdpsThread implements Callable<Boolean> {

    private Logger       logger = LoggerFactory.getLogger(UploadOdpsThread.class);
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
        int end = (int) ((id + 1) * pageSize - 1);
        if (id == threadSize - 1) {
            end = recordList.size() - 1;
        }

        for (int i = start; i <= end; i++) {
            try {
                recordWriter.write(recordList.get(i));
            } catch (IOException e) {
                logger.error("UploadOdpsThread [{}] have a error {}", id, e);
                result = false;
                break;
            }
        }
        try {
            recordWriter.close();
        } catch (IOException e) {
            logger.error("UploadOdpsThread [{}] close have a error {}", id, e);
        }
        return result;
    }
}
