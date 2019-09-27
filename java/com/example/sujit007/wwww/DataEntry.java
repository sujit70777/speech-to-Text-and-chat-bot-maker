package com.example.sujit007.wwww;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DataEntry extends AppCompatActivity {
    EditText addid, addName;
    Button addbtn;
    String id, name;
    private Dialog dialog;
    Button D_save, D_cancel;

    EditText classN, descp;
    TextView nam, dets;
    private ArrayList<Data> studentInfoArrayList;
    private DataAdapter studentAdapter;
    private DataBaseAdapter studentDatabaseAdapter;
    ListView listView;
    String ClassTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dialog = new Dialog(DataEntry.this);
        addbtn = (Button) findViewById(R.id.saveStudent);
        addid = (EditText) findViewById(R.id.stdAddID);
        addName = (EditText) findViewById(R.id.stdAddName);
        listView = (ListView) findViewById(R.id.studentListView);
        studentInfoArrayList = new ArrayList<Data>();
        studentDatabaseAdapter = new DataBaseAdapter(this);
        studentInfoArrayList = studentDatabaseAdapter.getStudentData();

        studentAdapter = new DataAdapter(DataEntry.this, studentInfoArrayList);

        listView.setAdapter(studentAdapter);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = addid.getText().toString();
                name = addName.getText().toString();

                if (id.equals("") || name.equals("")) {
                    Toast.makeText(getApplicationContext() , "Fill all the Field" , Toast.LENGTH_SHORT).show();

                } else {
                    long lid = studentDatabaseAdapter.Insert(id, name);
                    studentInfoArrayList = studentDatabaseAdapter.getStudentData();

                    studentAdapter = new DataAdapter(DataEntry.this, studentInfoArrayList);

                    listView.setAdapter(studentAdapter);
                    Toast.makeText(getApplicationContext() , "Data Added" , Toast.LENGTH_SHORT).show();
                    addid.setText("");
                    addName.setText("");

                }


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                final Dialog longD = new Dialog(DataEntry.this);
                longD.setContentView(R.layout.d__class_long_press);
                ImageView close = (ImageView) longD.findViewById(R.id.closeImg);
                Button delete = (Button) longD.findViewById(R.id.deletebtn);
                Button update = (Button) longD.findViewById(R.id.updatebtn);
                longD.setCancelable(false);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        longD.dismiss();
                    }

                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(DataEntry.this);
                        builder.setTitle("Delete Data");
                        builder.setCancelable(false);
                        builder.setMessage("Are you sure to delete this data ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = String.valueOf(studentAdapter.get_id(position));
                                studentDatabaseAdapter.Delete(id);
                                Toast.makeText(getApplicationContext() , "Data Deleted" , Toast.LENGTH_SHORT).show();
                                studentInfoArrayList = studentDatabaseAdapter.getStudentData();

                                studentAdapter = new DataAdapter(DataEntry.this, studentInfoArrayList);

                                listView.setAdapter(studentAdapter);
                                dialog.cancel();
                                longD.dismiss();

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        longD.dismiss();
                        dialog.setContentView(R.layout.d__student_data_update);
                        dialog.setCancelable(false);

                        D_cancel = (Button) dialog.findViewById(R.id.cancelbtns);
                        D_save = (Button) dialog.findViewById(R.id.savebtns);
                        classN = (EditText) dialog.findViewById(R.id.classNameETs);
                        descp = (EditText) dialog.findViewById(R.id.classDescriptionETs);


                        classN.setText(String.valueOf(studentAdapter.get_text(position)));

                        descp.setText(studentAdapter.get_replay(position));
                        descp.setHint("Enter Text");
                        classN.setHint("Enter Replay");
                        D_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        D_save.setText("Update");


                        D_save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String Std_ID = classN.getText().toString();
                                String Std_name = descp.getText().toString();

                                if (Std_ID.equals("") || Std_name.equals("")) {
                                    Snackbar.make(v, "Fill all the Fields", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {

                                    String id = String.valueOf(studentAdapter.get_id(position));
                                    studentDatabaseAdapter.Update(id , Std_ID, Std_name);
                                    Toast.makeText(getApplicationContext() , "Data Updated" , Toast.LENGTH_SHORT).show();
                                    studentInfoArrayList = studentDatabaseAdapter.getStudentData();

                                    studentAdapter = new DataAdapter(DataEntry.this, studentInfoArrayList);

                                    listView.setAdapter(studentAdapter);

                                    dialog.dismiss();
                                }
                            }
                        });


                        dialog.show();


                    }


                });

                longD.show();


                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
