package io.github.mthli.EatWhat.Usual;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import io.github.mthli.EatWhat.Database.UDBAction;
import io.github.mthli.EatWhat.Database.Usual;
import io.github.mthli.EatWhat.R;

import java.sql.SQLException;
import java.util.List;

public class UsualAdapter extends ArrayAdapter<UsualItem> {
    private Context context;
    private int layoutResId;
    private List<UsualItem> usualItems;

    public UsualAdapter(
            Context context,
            int layoutResId,
            List<UsualItem> usualItems
    ) {
        super(context, layoutResId, usualItems);
        this.context = context;
        this.layoutResId = layoutResId;
        this.usualItems = usualItems;
    }

    private class Holder {
        TextView restaurant;
        TextView path;
        ImageButton imageButton;
    }

    @Override
    public View getView(
            final int position,
            View convertView,
            ViewGroup viewGroup
    ) {
        Holder holder = null;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResId, viewGroup, false);

            holder = new Holder();
            holder.restaurant = (TextView) view.findViewById(R.id.usual_item_restaurant);
            holder.path = (TextView) view.findViewById(R.id.usual_item_path);
            holder.imageButton = (ImageButton) view.findViewById(R.id.usual_item_delete);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final UsualItem anItem = usualItems.get(position);
        holder.restaurant.setText(anItem.getRestaurant());
        holder.path.setText(anItem.getPath());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UDBAction udbAction = new UDBAction(context);
                try {
                    udbAction.openDatabase(true);
                    String restaurant = anItem.getRestaurant();
                    String path = anItem.getPath();
                    Usual usual = new Usual();
                    usual.setRestaurant(restaurant);
                    usual.setPath(path);
                    udbAction.deleteUsual(usual);
                    usualItems.clear();
                    List<Usual> usualList = udbAction.usualList();
                    for (int i = 0; i < usualList.size(); i++) {
                        usualItems.add(
                                new UsualItem(
                                        usualList.get(i).getRestaurant(),
                                        usualList.get(i).getPath(),
                                        new ImageButton(context)
                                )
                        );
                    }
                    notifyDataSetChanged();
                } catch (SQLException s) {
                    Toast.makeText(
                            context,
                            context.getString(R.string.error_database_open),
                            Toast.LENGTH_SHORT
                    ).show();
                }
                udbAction.closeDatabase();
            }
        });

        return view;
    }
}
