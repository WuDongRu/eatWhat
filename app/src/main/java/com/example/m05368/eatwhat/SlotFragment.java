package com.example.m05368.eatwhat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        final String[] values = {"魯肉飯", "豬排飯", "拉麵", "鍋貼", "炒飯", "鍋燒麵"};
        //Populate NumberPicker values from String array values
        //Set the minimum value of NumberPicker
        picker.setMinValue(0); //from array first value
        //Specify the maximum value/number of NumberPicker
        picker.setMaxValue(values.length - 1); //to array last value
        //Specify the NumberPicker data source as array elements
        picker.setDisplayedValues(values);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        picker.setWrapSelectorWheel(true);
        slot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1;
                num1= (int)(Math.random()*100)+1;
                for (int i =0 ; i<num1 ; i++) {
                    changeValueByOne(picker, true);
                }

                slot_btn.setEnabled(false);

                View slot_dialog = inflater.inflate(R.layout.slot_dialog, null );
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(slot_dialog);
                builder.setPositiveButton("Let's GO!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
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

