package io.github.mthli.EatWhat.Main;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import io.github.mthli.EatWhat.About.AboutActivity;
import io.github.mthli.EatWhat.R;

public class MainActivity extends Activity implements SensorEventListener {
    private ImageView background;
    private Bitmap bitmap;
    private SharedPreferences sharedPreferences;

    private SensorManager sensorManager;

    private SoundPool soundPool;
    private Vibrator vibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sharedPreferences = getSharedPreferences("background", MODE_PRIVATE);
        String path = sharedPreferences.getString("background", null);
        background = (ImageView) findViewById(R.id.main_background_image);
        if (path != null) {
            bitmap = BitmapFactory.decodeFile(path);
            background.setImageBitmap(bitmap);
        } else {
            background.setImageResource(R.drawable.background);
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
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
                Toast.makeText(
                        MainActivity.this,
                        "sjahdisygdsaig",
                        Toast.LENGTH_SHORT
                ).show();
                vibrator.vibrate(250);
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
