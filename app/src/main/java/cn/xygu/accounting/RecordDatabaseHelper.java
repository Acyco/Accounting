package cn.xygu.accounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by Acyco on 2019-06-11.
 */

public class RecordDatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = "RecordDatabaseHelper";

    public static final String DB_NAME = "Record";

    private static final String CREATE_RECORD_DB = "create table Record (" +
            "id integer primary key autoincrement," +
            "uuid text," +
            "type integer," +
            "category text," +
            "remark text," +
            "amount double," +
            "time integer," +
            "date date)";

    public RecordDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
        Log.d(TAG, "RecordDatabaseHelper: init");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RECORD_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addRecord(RecordBean bean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid", bean.getUuid());
        values.put("type", bean.getType());
        values.put("category", bean.getCategory());
        values.put("remark", bean.getRemark());
        values.put("amount", bean.getAmount());
        values.put("date", bean.getData());
        values.put("time", bean.getTimeStamp());
        db.insert(DB_NAME, null, values);
        values.clear();
        Log.d(TAG, bean.getUuid()+" added");
    }

    public void removeRecord(String uuid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME, "uuid = ?", new String[]{uuid});
    }

    public void editRecord(String uuid, RecordBean bean) {
        removeRecord(uuid);
        bean.setUuid(uuid);
        addRecord(bean);
    }

    public LinkedList<RecordBean> readRecords(String dateStr) {
        LinkedList<RecordBean> records = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from " + DB_NAME + " where date = ? order by time asc", new String[]{dateStr});
        if (cursor.moveToFirst()) {
            do {
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                long timeStamp = cursor.getLong(cursor.getColumnIndex("time"));

                RecordBean record = new RecordBean();
                record.setUuid(uuid);
                record.setType(type);
                record.setCategory(category);
                record.setRemark(remark);
                record.setAmount(amount);
                record.setData(date);
                record.setTimeStamp(timeStamp);

                records.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public LinkedList<String> getAvaliableDate() {
        Log.d(TAG, "getDate");
        LinkedList<String> dates = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from " + DB_NAME + " order by date asc", new String[]{});

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if (!dates.contains(date)) {
                    dates.add(date);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dates;
    }
}
