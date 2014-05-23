package io.github.mthli.EatWhat.Usual;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import io.github.mthli.EatWhat.R;

public class UsualActivity extends Activity {
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usual);

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        /* SimpleAdapter and Database */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.usual_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.usual_menu_add:
                /* Do something */
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
