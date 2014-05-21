package io.github.mthli.EatWhat.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import io.github.mthli.EatWhat.About.AboutActivity;
import io.github.mthli.EatWhat.R;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
