package cn.xygu.accounting;

import android.util.Log;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Acyco on 2019-06-11.
 */

public class RecordBean implements Serializable{
    private static String TAG = "RecordBean";

    public enum RecordType {
        RECORD_TYPE_EXPENSE, RECORD_TYPE_INCOME
    }

    private double amount;
    private RecordType type;
    private String category;
    private String remark;
    private String data;
    private long timeStamp;
    private String uuid;

    public RecordBean() {
        uuid = UUID.randomUUID().toString();
        //  Log.d(TAG, uuid);
        timeStamp = System.currentTimeMillis();
        data = DateUtil.getFormattedDate(timeStamp);
        //Log.d(TAG, timeStamp+""+DateUtil.getFormattedTime(timeStamp));
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {

        if (this.type == RecordType.RECORD_TYPE_EXPENSE) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setType(int type) {
        if (type == 1) {
            this.type = RecordType.RECORD_TYPE_EXPENSE;
        } else {
            this.type = RecordType.RECORD_TYPE_INCOME;
        }

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
