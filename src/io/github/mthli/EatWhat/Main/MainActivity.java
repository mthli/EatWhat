package io.github.mthli.EatWhat.Main;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;

import io.github.mthli.EatWhat.About.AboutActivity;
import io.github.mthli.EatWhat.R;

public class MainActivity extends Activity implements SensorEventListener, ActionBar.OnNavigationListener {
    private ImageView background;
    private Bitmap bitmap;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SensorManager sensorManager;

    private SoundPool soundPool;

    private PopupWindow popupWindow;
    private View popupView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(
                new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[] {
                                getString(R.string.main_dropdown_random),
                                getString(R.string.main_dropdown_usual)
                        }
                ),
                this
        );

        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String path = sharedPreferences.getString("background", null);
        background = (ImageView) findViewById(R.id.main_background_image);
        if (path != null) {
            bitmap = BitmapFactory.decodeFile(path);
            background.setImageBitmap(bitmap);
        } else {
            background.setImageResource(R.drawable.main_background);
        }

        popupView = getLayoutInflater().inflate(R.layout.popup, null);
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        editor.putInt("dropdown", position);
        editor.commit();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        /* Do nothing */
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float[] values= sensorEvent.values;

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            if ((Math.abs(values[0]) > 19) || Math.abs(values[1]) > 19 || Math.abs(values[2]) > 19){
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

                popupWindow.setAnimationStyle(R.style.popup_show);
                popupWindow.showAtLocation(background, Gravity.CENTER, 0, 200);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.main_menu_share:
                /* Do something */
                break;
            case R.id.main_menu_background:
                /* Do something */
                break;
            case R.id.main_menu_usual:
                /* Do something */
                break;
            case R.id.main_menu_about:
                Intent intent_about = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent_about);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
