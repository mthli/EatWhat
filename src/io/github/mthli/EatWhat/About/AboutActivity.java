package io.github.mthli.EatWhat.About;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import io.github.mthli.EatWhat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> title = new ArrayList<String>();
        title.add(getString(R.string.about_title_author));
        title.add(getString(R.string.about_title_thanks));
        title.add(getString(R.string.about_title_recruit));
        title.add(getString(R.string.about_title_version));
        title.add(getString(R.string.about_title_homepage));
        title.add(getString(R.string.about_title_license));

        ArrayList<String> content = new ArrayList<String>();
        content.add(getString(R.string.about_content_author));
        content.add(getString(R.string.about_content_thanks));
        content.add(getString(R.string.about_content_recruit));
        content.add(getString(R.string.about_content_version));
        content.add(getString(R.string.about_content_homepage));
        content.add(getString(R.string.about_content_license));

        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 6; i++) {
            Map<String, String> list = new HashMap<String, String>();
            list.put("title", title.get(i));
            list.put("content", content.get(i));
            lists.add(list);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                AboutActivity.this,
                lists,
                R.layout.about_item,
                new String[]{"title", "content"},
                new int[]{R.id.about_item_title, R.id.about_item_content}
        );
        ListView listView = (ListView) findViewById(R.id.about);
        listView.setAdapter(simpleAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
