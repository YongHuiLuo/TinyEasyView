package com.tiny.tinyeasyview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData = new ArrayList<>();
        mData.add("custom view activity");
        mData.add("drawable activity");
        mData.add("RxJava activity");

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mData));
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String content = (String) parent.getAdapter().getItem(position);
        if (content.contains("custom view activity")) {
            Intent intent = new Intent();
//            intent.setAction("android.intent.action.CUSTOMVIEW");
            intent.setClass(this, CustomViewActivity.class);
            startActivity(intent);
        } else if (content.contains("drawable activity")) {
            Intent intent = new Intent();
            intent.setClass(this,DrawableActivity.class);
            startActivity(intent);
        } else if(content.contains("RxJava activity")){
            Intent intent = new Intent();
            intent.setClass(this,RxJavaActivity.class);
            startActivity(intent);
        }
    }
}
