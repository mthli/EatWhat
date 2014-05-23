package io.github.mthli.EatWhat.Usual;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.github.mthli.EatWhat.R;

public class UsualActivity extends Activity {
    private AlertDialog.Builder builder;
    private TextView restuarant;
    private TextView path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usual);
        getActionBar().setDisplayHomeAsUpEnabled(true);


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
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.usual_dialog, null);
                builder = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.usual_dialog_title))
                        .setView(linearLayout)
                        .setPositiveButton(
                                getString(R.string.usual_dialog_confirm),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                /* Do something */
                                    }
                                }
                        ).setNegativeButton(
                                getString(R.string.usual_dialog_cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                /* Do nothing */
                                    }
                                }
                        );
                builder.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
