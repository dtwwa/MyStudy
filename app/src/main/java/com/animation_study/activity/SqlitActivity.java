package com.animation_study.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.animation_study.R;
import com.animation_study.bean.PersonBean;
import com.animation_study.sqlit.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqlitActivity extends AppCompatActivity implements View.OnClickListener {
    private MySQLiteOpenHelper helper;
    private List<PersonBean> personBeans;

    private EditText etAdd;
    private EditText etDelete;
    private EditText etFind;
    private Button btAdd;
    private Button btDelete;
    private Button btFind;
    private ListView lvPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlit);
        findViews();
        initData();
    }

    SQLiteDatabase writableDatabase;
    MyAdapter myAdapter;

    private void initData() {
        if (personBeans == null) {
            personBeans = new ArrayList<>();
        } else {
            personBeans.removeAll(personBeans);
        }
        helper = new MySQLiteOpenHelper(getApplicationContext(), "study.db", null, 10);
        writableDatabase = helper.getWritableDatabase();
        Cursor person = writableDatabase.query("person", null, null, null, null, null, null);
        if (person.moveToFirst()) {
            do {
                personBeans.add(new PersonBean(person.getString(person.getColumnIndex("userid")),
                        person.getString(person.getColumnIndex("name"))
                ));
            } while (person.moveToNext());
        }
        if (myAdapter == null) {
            myAdapter = new MyAdapter();
            lvPerson.setAdapter(myAdapter);
        } else {
            myAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-12-04 14:12:09 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        etAdd = (EditText) findViewById(R.id.et_add);
        etDelete = (EditText) findViewById(R.id.et_delete);
        etFind = (EditText) findViewById(R.id.et_find);
        btAdd = (Button) findViewById(R.id.bt_add);
        btDelete = (Button) findViewById(R.id.bt_delete);
        btFind = (Button) findViewById(R.id.bt_find);
        lvPerson = (ListView) findViewById(R.id.lv_person);

        btAdd.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btFind.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-12-04 14:12:09 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btAdd) {
            String s = etAdd.getText().toString();
            ContentValues values1 = new ContentValues();
            values1.put("name", s);

            writableDatabase.insert("person", null, values1);
            initData();
            // Handle clicks for btAdd
        } else if (v == btDelete) {
            String s = etDelete.getText().toString();
            //参数依次是表名，以及where条件与约束
            writableDatabase.delete("person", "userid = ?", new String[]{s});
            initData();
            // Handle clicks for btDelete
        } else if (v == btFind) {
            // Handle clicks for btFind
        }
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return personBeans.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_person, null);
            }
            TextView tv_id = view.findViewById(R.id.tv_id);
            TextView tv_name = view.findViewById(R.id.tv_name);
            tv_id.setText(personBeans.get(i).getId());
            tv_name.setText(personBeans.get(i).getName());
            return view;
        }
    }

}
