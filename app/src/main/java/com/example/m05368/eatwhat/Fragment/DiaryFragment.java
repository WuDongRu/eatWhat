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
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.m05368.eatwhat.DBHelper;
import com.example.m05368.eatwhat.view.DiaryDetail;
import com.example.m05368.eatwhat.DownloadImageTask;
import com.example.m05368.eatwhat.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.example.m05368.eatwhat.R.layout.fragment_daily;


public class DiaryFragment extends Fragment {

    private ListView list_daily;
    private TextView date;
    private List<Map<String, Object>> data;

    public DiaryFragment() {
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
        View view = inflater.inflate(fragment_daily, container, false);


        date = (TextView) view.findViewById(R.id.month);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        date.setText(year+"/"+month);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();
            }
        });

        data = getData();

        list_daily = (ListView) view.findViewById(R.id.list_daily);
        list_daily.setAdapter(new MyAdapter(getActivity().getApplicationContext()));
        list_daily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {//
                Intent intent = new Intent(getActivity(), DiaryDetail.class);
                intent.putExtra("id",(Integer) data.get(position).get("id"));
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
        Cursor c=db.rawQuery("SELECT * FROM diary WHERE D_year = "+String.valueOf(date.getText()).substring(0,4)+" and D_month = "+String.valueOf(date.getText()).substring(5,6),null);
        DBHelper helper = new DBHelper(getActivity().getApplicationContext());

        for(int i=c.getCount()-1;i>0;i--)
        {
            c.moveToPosition(i);
            map = new HashMap<String, Object>();
            map.put("id", c.getInt(0));
            map.put("date", c.getString(3));
            map.put("img", c.getString(11));
            map.put("name", c.getString(4));
            map.put("address", c.getString(5));
            list.add(map);
        }
        db.close();
        helper.close();
        return list;
    }

    static class ViewHolder {
        ImageView imageView,delete;
        TextView date,name,address;
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
            final ViewHolder viewHolder;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.diary_item, null);
                viewHolder = new ViewHolder();
                viewHolder.date = (TextView) view.findViewById(R.id.date);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.food);
                viewHolder.name = (TextView) view.findViewById(R.id.name);
                viewHolder.address = (TextView) view.findViewById(R.id.address);
                viewHolder.delete = (ImageView) view.findViewById(R.id.delete);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.date.setText((String)data.get(position).get("date"));
            //viewHolder.imageView.setImageResource((Integer) data.get(position).get("img"));
            new DownloadImageTask(viewHolder.imageView)
                    .execute((String)data.get(position).get("img"));
            viewHolder.name.setText((String)data.get(position).get("name"));
            viewHolder.address.setText((String)data.get(position).get("address"));
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("確定要刪除嗎?")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = getActivity().openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
                                    DBHelper helper = new DBHelper(getActivity().getApplicationContext());
                                    helper.deletediary((Integer) data.get(position).get("id"));
                                    data.remove(position);
                                    list_daily.setAdapter(new MyAdapter(getActivity().getApplicationContext()));
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

    private void dateDialog(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.custom_date_picker, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        final int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year-10);
        yearPicker.setMaxValue(year);
        yearPicker.setValue(year);
        yearPicker.setWrapSelectorWheel(false);
        builder.setTitle("請選擇時間");
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        date.setText(yearPicker.getValue()+"/"+monthPicker.getValue());
                        data = getData();
                        list_daily.setAdapter(new MyAdapter(getActivity().getApplicationContext()));
                    }
                });
        builder.show();

    }
}
