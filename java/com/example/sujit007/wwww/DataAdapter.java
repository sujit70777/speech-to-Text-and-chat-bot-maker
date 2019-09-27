package com.example.sujit007.wwww;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 5/1/2017.
 */

public class DataAdapter extends BaseAdapter {

    private ArrayList<Data> studentInfos = new ArrayList<Data>();
    private Data studentInfo = new Data();
    private static LayoutInflater inflater = null;
    private Context context;

    public DataAdapter(Context context, ArrayList<Data> studentInfos) {
        this.context = context;
        this.studentInfos = studentInfos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return studentInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int get_id(int position){

        studentInfo = studentInfos.get(position);

        return studentInfo.getId();
    }

    public String get_text(int position){

        studentInfo = studentInfos.get(position);

        return studentInfo.getText();
    }
    public String get_replay(int position){

        studentInfo = studentInfos.get(position);

        return studentInfo.getReplay();
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.l_row, null);

        TextView StudentID = (TextView) convertView.findViewById(R.id.StudentID_LV_TV);
        TextView StudentName = (TextView)convertView.findViewById(R.id.studentNameLV_TV);


        studentInfo = studentInfos.get(position);





        StudentID.setText(studentInfo.getText());
        StudentName.setText(studentInfo.getReplay());

        return convertView;

    }
}
