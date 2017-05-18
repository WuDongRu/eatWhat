package com.example.m05368.eatwhat.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.m05368.eatwhat.DBHelper;
import com.example.m05368.eatwhat.DownloadImageTask;
import com.example.m05368.eatwhat.R;
import com.example.m05368.eatwhat.view.SlotRestaurant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;


public class SlotFragment extends Fragment{


    public SlotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_slot, container, false);
        final NumberPicker picker = (NumberPicker) view.findViewById(R.id.picker);
        final Button slot_btn = (Button) view.findViewById(R.id.slot_btn);
        final TextView time = (TextView) view.findViewById(R.id.time);


        final SQLiteDatabase db = getActivity().openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);

        Cursor c=db.query("slot",null,null,null,null,null,null);
        final String[] str = new String [c.getCount()] ;
        for(int i = 0 ; i < c.getCount() ; i++) {
        c.moveToPosition(i);
                str[i]= c.getString(1);
            }

        picker.setMinValue(0);
        picker.setMaxValue(str.length - 1);
        picker.setDisplayedValues(str);
        picker.setWrapSelectorWheel(true);
        slot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot_btn.setEnabled(false);
                final int picker_position;
                picker_position= (int)(Math.random()*str.length);
                for (int i =0 ; i<picker_position ; i++) {
                    changeValueByOne(picker, true);
                }

                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        picker.setValue(picker_position);
                    }

                });

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View slot_dialog = inflater.inflate(R.layout.slot_dialog, null );
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setView(slot_dialog);
                        final TextView name = (TextView) slot_dialog.findViewById(R.id.name);
                        name.setText(str[picker_position]);
                        Cursor c=db.rawQuery("SELECT * FROM restaurantGet WHERE S_name = ?",new String[]{str[picker_position]});
                        c.moveToFirst();
                        new DownloadImageTask((ImageView) slot_dialog.findViewById(R.id.img))
                                .execute(c.getString(10));

                        builder.setPositiveButton("Let's GO!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteDatabase db = getActivity().openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
                                DBHelper helper = new DBHelper(getActivity().getApplicationContext());

                                Intent intent = new Intent(getActivity(), SlotRestaurant.class);
                                intent.putExtra("name",str[picker_position]);
                                startActivity(intent);

                                Cursor c=db.rawQuery("SELECT * FROM restaurantGet WHERE S_name = ?",new String[]{str[picker_position]});
                                c.moveToFirst();

                                Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH)+1;
                                int day = calendar.get(Calendar.DATE);
                                helper.addtodiary(year,month,day,str[picker_position],c.getString(2),c.getString(10));

                                db.close();
                                helper.close();
                            }
                        });
                        builder.setNegativeButton("在思考一下...", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        //button style
                        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setTextColor(Color.parseColor("#00BFFF"));
                        pbutton.setTextSize(16);
                        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                        nbutton.setTextColor(Color.parseColor("#00BFFF"));

                    }
                }, 500);

                new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        time.setText(""+millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        time.setText("拉吧!");
                        slot_btn.setEnabled(true);
                    }
                }.start();
                }
        });

        return view;
    }

    private void changeValueByOne(final NumberPicker picker, final boolean increment) {

        Method method;
        try {
            // refelction call for
            // higherPicker.changeValueByOne(true);
            method = picker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(picker, increment);

        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}


