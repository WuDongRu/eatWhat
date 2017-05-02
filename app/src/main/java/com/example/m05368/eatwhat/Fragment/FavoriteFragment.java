package com.example.m05368.eatwhat.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.m05368.eatwhat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.m05368.eatwhat.R.layout.fragment_favorite;


public class FavoriteFragment extends Fragment{

    private ListView list_daily;
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

        View view = inflater.inflate(fragment_favorite, container, false);

        data = getData();

        list_daily = (ListView) view.findViewById(R.id.list_daily);
        list_daily.setAdapter(new FavoriteFragment.MyAdapter(getActivity().getApplicationContext()));


        return view;
    }
    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<10;i++)
        {
            map = new HashMap<String, Object>();
            map.put("img", R.drawable.food);
            map.put("name", "店名");
            map.put("address", "地址");
            list.add(map);
        }
        return list;
    }

    static class ViewHolder {
        ImageView imageView;
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
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.imageView.setImageResource((Integer) data.get(position).get("img"));
            viewHolder.name.setText((String)data.get(position).get("name"));
            viewHolder.address.setText((String)data.get(position).get("address"));

            return view;
        }
    }
}
