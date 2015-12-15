package com.tiny.tinyeasyview;

import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by ${Tiny} on 2015/12/15.
 */
public class DrawableActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout bitmap_drawable_bg;
    private Button btn_disable, btn_repeat, btn_mirror, btn_clamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_layout);
        findView();
        setListener();
    }

    private void findView() {
        bitmap_drawable_bg = (LinearLayout) findViewById(R.id.bitmap_drawable_bg);
        btn_disable = (Button) findViewById(R.id.btn_disable);
        btn_repeat = (Button) findViewById(R.id.btn_repeat);
        btn_mirror = (Button) findViewById(R.id.btn_mirror);
        btn_clamp = (Button) findViewById(R.id.btn_clamp);
    }

    private void setListener() {
        btn_disable.setOnClickListener(this);
        btn_repeat.setOnClickListener(this);
        btn_mirror.setOnClickListener(this);
        btn_clamp.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bitmap_drawable) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) bitmap_drawable_bg.getBackground();
        switch (v.getId()) {
            case R.id.btn_disable:
                bitmapDrawable.setTileModeXY(null, null);
                break;
            case R.id.btn_repeat:
                bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                break;
            case R.id.btn_mirror:
                bitmapDrawable.setTileModeXY(Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
                break;
            case R.id.btn_clamp:
                bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
                break;
        }
    }
}
