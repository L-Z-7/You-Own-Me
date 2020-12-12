package com.jnu.youownme.dataprocessor;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class DataBank {
    static private final String DATES_FILE_NAME = "data.txt";
    static private Date selectedDate;
    static private Reason selectedReason = Reason.BIRTHDAY;
    static private ArrayList<Record> allRecords = new ArrayList<>();
    static private ArrayList<Record> dateRecords = new ArrayList<>();
    static private ArrayList<Integer> yearGroup = new ArrayList<>();
    static private ArrayList<ArrayList<Record>> yearChild = new ArrayList<>();
    static private ArrayList<Record> reasonRecords = new ArrayList<>();

    static public void Save(Context context){
        //序列化
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(context.openFileOutput(DATES_FILE_NAME, Context.MODE_PRIVATE));
            outputStream.writeObject(allRecords);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static public void Load(Context context){
        //反序列化
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(context.openFileInput(DATES_FILE_NAME));
            allRecords = (ArrayList<Record>) inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void Clean(){
        allRecords.clear();
        dateRecords.clear();
        yearChild.clear();
        yearGroup.clear();
        reasonRecords.clear();
    }

    static public Date getSelectedDate() {
        return selectedDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static public void Update(){
        // 按年份顺排
        allRecords.sort(new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                return r1.getDate().getYear() - r2.getDate().getYear();
            }
        });

        // 更新home的数组
        dateRecords.clear();
        for (Record record: allRecords){
            if(selectedDate.equals(record.getDate())){
                dateRecords.add(record);
            }
        }

        // 更新收礼的数组
        reasonRecords.clear();
        for (Record record: allRecords){
            if(selectedReason == record.getReason()){
                reasonRecords.add(record);
            }
        }

        // 更新随礼的数组
        int i;
        Integer year;
        yearChild.clear();
        yearGroup.clear();
        for (Record record: allRecords){
            year = record.getDate().getYear();
            if(!yearGroup.contains(year)){
                yearGroup.add(year);
                yearChild.add(new ArrayList<Record>());
            }
            i = yearGroup.indexOf(year);
            yearChild.get(i).add(record);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static public void setSelectedDate(Date date) {
        selectedDate = date;
        Update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static public void add(Record record){
        allRecords.add(record);
        Update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void remove(int position) {
        Record record = dateRecords.get(position);
        for (Record t: allRecords){
            if(t.equals(record)){
                allRecords.remove(t);
                break;
            }
        }

        Update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void change(Record record) {
        for (Record t: allRecords){
            if (t.equals(record)) {
                t.assign(record);
                break;
            }
        }
        Update();
    }

    public static ArrayList<Integer> getYearGroup() {
        return yearGroup;
    }

    public static ArrayList<ArrayList<Record>> getYearChild() {
        return yearChild;
    }

    static public ArrayList<Record> getDateRecords() {
        return dateRecords;
    }

    public static ArrayList<Record> getReasonRecords() {
        return reasonRecords;
    }
}
