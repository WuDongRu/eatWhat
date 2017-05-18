package com.example.m05368.eatwhat.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.m05368.eatwhat.DBHelper;
import com.example.m05368.eatwhat.DownloadImageTask;
import com.example.m05368.eatwhat.R;
import com.example.m05368.eatwhat.view.SlotRestaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.m05368.eatwhat.R.layout.fragment_favorite;


public class FavoriteFragment extends Fragment{

    private ListView list_favorite;
    private List<Map<String, Object>> data;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        data = getData();

        View view = inflater.inflate(fragment_favorite, container, false);

        list_favorite = (ListView) view.findViewById(R.id.list_favorite);
        list_favorite.setAdapter(new FavoriteFragment.MyAdapter(getActivity().getApplicationContext()));
        list_favorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {//
                Intent intent = new Intent(getActivity(), SlotRestaurant.class);
                intent.putExtra("name",(String) data.get(position).get("name"));
                startActivity(intent);
            }
        });

        return view;
    }
    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        SQLiteDatabase db = getActivity().openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM diary WHERE  D_score = "+1,null);
        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        for(int i=0;i<c.getCount();i++)
        {
            c.moveToPosition(i);
            map = new HashMap<String, Object>();
            map.put("id", c.getInt(0));
            map.put("img", c.getString(11));
            map.put("name", c.getString(4));
            map.put("address", c.getString(5));
            list.add(map);
        }

        return list;
    }

    static class ViewHolder {
        ImageView imageView,heart;
        TextView name,address;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public MyAdapter(Context context)
        {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount()
        {
            return data.size();
        }
        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.favotite_item, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.food);
                viewHolder.name = (TextView) view.findViewById(R.id.name);
                viewHolder.address = (TextView) view.findViewById(R.id.address);
                viewHolder.heart = (ImageView) view.findViewById(R.id.heart);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            new DownloadImageTask(viewHolder.imageView)
                    .execute((String)data.get(position).get("img"));
            viewHolder.name.setText((String)data.get(position).get("name"));
            viewHolder.address.setText((String)data.get(position).get("address"));
            viewHolder.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("確定要移出最愛嗎?")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBHelper helper = new DBHelper(getActivity().getApplicationContext());
                                    helper.deletefavorite((Integer) data.get(position).get("id"),0);
                                    data.remove(position);
                                    list_favorite.setAdapter(new FavoriteFragment.MyAdapter(getActivity().getApplicationContext()));
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            });

            return view;
        }
    }
}
