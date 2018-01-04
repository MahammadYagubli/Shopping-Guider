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

public class shopping extends AppCompatActivity  implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    String bool = "false";
    ListView mylist;
    int cas = 0;
    ArrayList<String> mynewlist = new ArrayList<>();
    database obj = new database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app);
        setSupportActionBar(toolbar);
        setTitle("List\'s Name");
        mylist = (ListView) findViewById(R.id.book);
        mynewlist.add("Add+");
        refresh();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mynewlist);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
        mylist.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tempt = (TextView) view;
        if (tempt.getText().toString().equals("Add+")){
            Toast.makeText(shopping.this, "PLease Long Click on Add+", Toast.LENGTH_SHORT).show();
        }
        else{obj.renew();
            obj.temporily(tempt.getText().toString());
            Intent news = new Intent(this, Items.class);

            startActivity(news);}

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
        final TextView tempt = (TextView) view;
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(shopping.this);
        View lView = getLayoutInflater().inflate(R.layout.long_click, null);
        Button delete = (Button) lView.findViewById(R.id.btndelete);
        Button add_new = (Button) lView.findViewById(R.id.btnnewtopic);
        Button rename = (Button) lView.findViewById(R.id.btnrename);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView temp = (TextView) view;
                obj.removeSingleContact(temp.getText().toString());

                refresh();
                finish();
                startActivity(getIntent());
            }
        });
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(shopping.this);
                final View mView = getLayoutInflater().inflate(R.layout.new_topic_name, null);
                // TextView textView = (TextView) mView.findViewById(R.id.txt_name_of_new_Item);
                final EditText nameoftopic = (EditText) mView.findViewById(R.id.txt_name_of_new_Item);
                Button save = (Button) mView.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (nameoftopic.getText().toString().equals("")) {
                            Toast.makeText(shopping.this, "PLease write name of the Topic", Toast.LENGTH_SHORT).show();
                        } else {
                            bool = nameoftopic.getText().toString();

                            if (check() == true) {
                                Toast.makeText(shopping.this, "This Name is already Exist", Toast.LENGTH_SHORT).show();
                            } else {
                                obj.insert_New_Topic_Name(nameoftopic.getText().toString());
                                obj.shopping_list(nameoftopic.getText().toString());

                                refresh();
                                finish();
                                startActivity(getIntent());
                            }


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
                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(shopping.this);
                final View mView = getLayoutInflater().inflate(R.layout.new_topic_name, null);
                //   TextView textView = (TextView) mView.findViewById(R.id.textView);
                final EditText nameoftopic = (EditText) mView.findViewById(R.id.txt_name_of_new_Item);
                Button save = (Button) mView.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!nameoftopic.getText().equals("")) {
                            bool = nameoftopic.getText().toString();

                            if (check() == true) {
                                Toast.makeText(shopping.this, "This Name is already Exist", Toast.LENGTH_SHORT).show();
                            } else {

                                obj.rename_table(tempt.getText().toString(), nameoftopic.getText().toString());
                                obj.Rename(tempt.getText().toString(), nameoftopic.getText().toString());
                                refresh();
                                finish();
                                startActivity(getIntent());
                            }

                        } else {

                            Toast.makeText(shopping.this, "PLease write name of the Topic", Toast.LENGTH_SHORT).show();
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

    public void refresh() {
        mynewlist.clear();
        Cursor res = obj.getAllData("topics");

        if (res.getCount() == 0) {
            Toast.makeText(shopping.this, "There is not anything to show", Toast.LENGTH_SHORT).show();
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




