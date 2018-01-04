package com.example.mahammad.shopping_guider;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Items extends AppCompatActivity  implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
String bool=" ";
        ListView mylist;
        int cas = 0;
        ArrayList<String> mynewlist = new ArrayList<>();
String title="";
        database obj = new database(this);
        String name="ada";
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar=(Toolbar)findViewById(R.id.app1);
        setSupportActionBar(toolbar);

       getnameoftopi(name);
       refresh(name);
       title=name.toUpperCase();
        setTitle(title+ "'s Items");
            mylist = (ListView) findViewById(R.id.book);
     // refresh(name);
     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mynewlist);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
        mylist.setOnItemLongClickListener(this);
        }

@Override
public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tempt = (TextView) view;
        Toast.makeText(this, tempt.getText(), Toast.LENGTH_SHORT).show();
        }

@Override
public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
final TextView tempt = (TextView) view;
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Items.this);
        View lView = getLayoutInflater().inflate(R.layout.long_click, null);
        Button delete = (Button) lView.findViewById(R.id.btndelete);
        Button add_new = (Button) lView.findViewById(R.id.btnnewtopic);
        Button rename = (Button) lView.findViewById(R.id.btnrename);
 delete.setOnClickListener(new View.OnClickListener() {
@Override
        public void onClick(View v) {
        TextView temp = (TextView) view;
        obj.removeSingleItem(name,temp.getText().toString());

        finish();
        startActivity(getIntent());
        }
        });
 add_new.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
final AlertDialog.Builder mbuilder = new AlertDialog.Builder(Items.this);
final View mView = getLayoutInflater().inflate(R.layout.new_topic_name, null);
// TextView textView = (TextView) mView.findViewById(R.id.txt_name_of_new_Item);
final EditText nameoftopic = (EditText) mView.findViewById(R.id.txt_name_of_new_Item);
        Button save = (Button) mView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        if (nameoftopic.getText().toString().equals("")) {
        Toast.makeText(Items.this, "PLease write name of the Topic", Toast.LENGTH_SHORT).show();
        } else {
                bool=nameoftopic.getText().toString();
                if(check()==true){Toast.makeText(Items.this, "This Name is Already exist", Toast.LENGTH_SHORT).show();}
                else{  obj.insert_New_Item(name,nameoftopic.getText().toString());

                        finish();
                        startActivity(getIntent());}

        }

        }
        });

        mbuilder.setView(mView);
        AlertDialog dialog = mbuilder.create();
        dialog.show();

        }
        });

        rename.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
final AlertDialog.Builder mbuilder = new AlertDialog.Builder(Items.this);
final View mView = getLayoutInflater().inflate(R.layout.new_topic_name, null);
//   TextView textView = (TextView) mView.findViewById(R.id.textView);
final EditText nameoftopic = (EditText) mView.findViewById(R.id.txt_name_of_new_Item);
        Button save = (Button) mView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        if (!nameoftopic.getText().toString().equals("")) {

                bool=nameoftopic.getText().toString();
                if(check()==true){Toast.makeText(Items.this, "This Name is Already exist", Toast.LENGTH_SHORT).show();}
                else{ obj.Rename(name, tempt.getText().toString(), nameoftopic.getText().toString());
                        finish();
                        startActivity(getIntent());

                }

        } else {
        Toast.makeText(Items.this, "PLease write name of the Topic", Toast.LENGTH_SHORT).show();
        }
        }
        });
        mbuilder.setView(mView);
        AlertDialog dialog = mbuilder.create();
        dialog.show();


        }
        });
        mbuilder.setView(lView);
        AlertDialog dialog = mbuilder.create();
        dialog.show();
        /*
        TextView temp = (TextView) view;
       // Toast.makeText(this, temp.getText() + " " + i, Toast.LENGTH_SHORT).show();
         obj.removeSingleContact(temp.getText().toString());*/

        return true;
        }

public void refresh(String name) {
        mynewlist.clear();
        Cursor res = obj.getAllData(name);
        if (res.getCount() == 0) {
        Toast.makeText(Items.this, "There is not anything to show", Toast.LENGTH_SHORT).show();
        mynewlist.add("Add+");
        return;
        } else {
        mynewlist.clear();
        while (res.moveToNext()) {
        mynewlist.add(res.getString(1));

        }
        res.close();


        }

        }
        public void getnameoftopi( String names) {


               Cursor res = obj.getAllData("putextra");

                if (res.getCount() == 0) {
                        Toast.makeText(Items.this, "There is not anything to show", Toast.LENGTH_SHORT).show();
                      this.name="Add++";

                } else {
                        mynewlist.clear();
                        while (res.moveToNext()) {
                                this.name=res.getString(1);

                        }
                        res.close();
                }

        }
        public boolean check() {
                boolean ad = false;
                for (int i = 0; i < mynewlist.size(); i++) {
                        if (bool.equals(mynewlist.get(i).toString())) {
                                ad = true;
                                return ad;
                        }
                }
                return ad;
        }

        }
