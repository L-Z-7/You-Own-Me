package com.jnu.youownme.dataprocessor;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class DataBank {
    static private final String DATES_FILE_NAME = "data.txt";
    static private Date selectedDate;
    static private ArrayList<Record> records = new ArrayList<>();
    static private ArrayList<Record> dateRecords = new ArrayList<>();
//    static private ArrayList<Record> reasonRecords = new ArrayList<>();

    static public ArrayList<Record> getDateRecords() {
        return dateRecords;
    }

    static public void Save(Context context){
        //序列化
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(context.openFileOutput(DATES_FILE_NAME, Context.MODE_PRIVATE));
            outputStream.writeObject(records);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void Load(Context context){
        //反序列化
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(context.openFileInput(DATES_FILE_NAME));
            records = (ArrayList<Record>) inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void Clean(){
        records.clear();
        dateRecords.clear();
//        reasonRecords = new ArrayList<>();
    }

    static public Date getSelectedDate() {
        return selectedDate;
    }

    static public void Update(){
        dateRecords.clear();
        for (Record record: records){
            if(selectedDate.equals(record.getDate())){
                dateRecords.add(record);
            }
        }
    }

    static public void setSelectedDate(Date date) {
        selectedDate = date;
        Update();
    }

    static public void add(Record record){
        records.add(record);
        if(record.getDate().equals(selectedDate))
            dateRecords.add(record);
    }

    public static void remove(int position) {
        Record record = dateRecords.get(position);
        for (Record t: records){
            if(t.equals(record)){
                records.remove(t);
                break;
            }
        }

        dateRecords.remove(position);
    }

    public static void change(int position, Record record) {
        dateRecords.get(position).assign(record);

        for (Record t: records){
            if (t.equals(record)) {
                t.assign(record);
                break;
            }
        }
    }
}
