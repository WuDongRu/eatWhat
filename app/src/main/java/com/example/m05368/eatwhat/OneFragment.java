package com.example.m05368.eatwhat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class OneFragment extends Fragment{


    public OneFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        Button btn1 = (Button) rootView.findViewById(R.id.btn1);
        final NumberPicker picker = (NumberPicker) rootView.findViewById(R.id.picker);
        final String[] values = {"AAAAA", "BBBBB", "CCCCC", "DDDDD", "EEEEE", "FFFFF"};
        //Populate NumberPicker values from String array values
        //Set the minimum value of NumberPicker
        picker.setMinValue(0); //from array first value
        //Specify the maximum value/number of NumberPicker
        picker.setMaxValue(values.length - 1); //to array last value
        //Specify the NumberPicker data source as array elements
        picker.setDisplayedValues(values);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        picker.setWrapSelectorWheel(true);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeValueByOne(picker, true);
                }
        });
        return rootView;

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

