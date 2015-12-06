package com.tiny.tinyeasyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements CustomView.VelocityValueChangeListener, View.OnTouchListener {

    private CustomView customView;
    private TextView txtVelocityX, txtMaxVelocityX, txtVelocityY, txtMaxVelocityY;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customView = (CustomView) findViewById(R.id.custom_view);
        txtVelocityX = (TextView) findViewById(R.id.velocityX);
        txtMaxVelocityX = (TextView) findViewById(R.id.maxVelocityX);
        txtVelocityY = (TextView) findViewById(R.id.velocityY);
        txtMaxVelocityY = (TextView) findViewById(R.id.maxVelocityY);

        customView.setVelocityValueChangeListener(this);
        customView.setOnTouchListener(this);

        //the third point : Resolve the Exception that PopupWindow $BadTokenException, use view.post()
        customView.post(new Runnable() {
            @Override
            public void run() {
                showPopWindow();
            }
        });
    }

    private void showPopWindow() {
        //the second point: Example of using PopupWindow
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_window_layout, null);
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.showAsDropDown(customView, 50, 10);
    }

    private void initView(float velocityX, float maxVelocityX, float velocityY, float maxVelocityY) {
        txtVelocityX.setText("custom view velocityX is : " + velocityX);
        txtMaxVelocityX.setText("custom view maxVelocityX is : " + maxVelocityX);
        txtVelocityY.setText("custom view velocityY is : " + velocityY);
        txtMaxVelocityY.setText("custom view maxVelocityY is : " + maxVelocityY);
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
    public void onVelocityValueChange(float velocityX, float maxVelocityX, float velocityY, float maxVelocityY) {
        initView(velocityX, maxVelocityX, velocityY, maxVelocityY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //The first point : The onTouch and onTouchEvent execution order
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        //OnTouchEvent method will not be executed,if return true.
        return false;
    }
}
