package io.github.mthli.EatWhat.Usual;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import io.github.mthli.EatWhat.Database.UDBAction;
import io.github.mthli.EatWhat.Database.Usual;
import io.github.mthli.EatWhat.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsualActivity extends Activity {
    private ListView listView;
    private List<UsualItem> usualItems;
    private UsualAdapter usualAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usual);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        usualItems = new ArrayList<UsualItem>();
        refreshList();
        listView = (ListView) findViewById(R.id.usual);
        listView.setAdapter(usualAdapter);
        usualAdapter.notifyDataSetChanged();

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
                final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.usual_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.usual_dialog_title))
                        .setView(linearLayout)
                        .setPositiveButton(
                                getString(R.string.usual_dialog_confirm),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EditText editText = (EditText) linearLayout.findViewById(R.id.usual_dialog_restaurant);
                                        String restaurant = editText.getText().toString();
                                        editText = (EditText) linearLayout.findViewById(R.id.usual_dialog_path);
                                        String path = editText.getText().toString();
                                        UDBAction udbAction = new UDBAction(UsualActivity.this);
                                        try {
                                            udbAction.openDatabase(true);
                                            Usual usual = new Usual();
                                            usual.setRestaurant(restaurant);
                                            usual.setPath(path);
                                            udbAction.newUsual(usual);
                                        } catch (SQLException s) {
                                            Toast.makeText(
                                                    UsualActivity.this,
                                                    getString(R.string.error_database_open),
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                        udbAction.closeDatabase();
                                        refreshList();
                                        listView.setAdapter(usualAdapter);
                                        usualAdapter.notifyDataSetChanged();
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

    public void refreshList() {
        usualItems.clear();
        UDBAction udbAction = new UDBAction(UsualActivity.this);
        try {
            udbAction.openDatabase(true);
            List<Usual> usualList = udbAction.usualList();

            for (int i = 0; i < usualList.size(); i++) {
                usualItems.add(
                        new UsualItem(
                                usualList.get(i).getRestaurant(),
                                usualList.get(i).getPath(),
                                new ImageButton(this)
                        )
                );
            }
            usualAdapter = new UsualAdapter(
                    this,
                    R.layout.usual_item,
                    usualItems
            );
        } catch (SQLException s) {
            Toast.makeText(
                    UsualActivity.this,
                    getString(R.string.error_database_open),
                    Toast.LENGTH_SHORT
            ).show();
        }
        udbAction.closeDatabase();
    }
}
