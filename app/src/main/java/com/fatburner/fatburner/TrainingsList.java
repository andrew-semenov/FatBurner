package com.fatburner.fatburner;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TrainingsList extends Menu {

    // имена атрибутов для Map
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_PB = "pb";
    final String ATTRIBUTE_NAME_LL = "ll";

    ListView lvSimple;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_trainings_list, null, false);
        mDrawerLayout.addView(contentView, 0);


        Intent intent = getIntent();
        String selectedProgram = intent.getStringExtra("selectedProgram");
        //add switch for selected programm and fill array according to selectedProgram

        // массив данных
        int load[] = { 100, 100, 100, 100, 100, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        // упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(load.length);
        Map<String, Object> m;
        for (int i = 0; i < load.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, "Training " + i + ": " + load[i] + "%");
            m.put(ATTRIBUTE_NAME_PB, load[i]);
            m.put(ATTRIBUTE_NAME_LL, load[i]);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_PB,
                ATTRIBUTE_NAME_LL };
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.tvLoad, R.id.pbLoad, R.id.llLoad };

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
        // Указываем адаптеру свой биндер
        sAdapter.setViewBinder(new TrainingsList.MyViewBinder());

        // определяем список и присваиваем ему адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);


        AdapterView.OnItemClickListener mOnListClick = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position != -1) {
                    Intent intent;
                    intent = new Intent(TrainingsList.this, SelectedTraining.class);
                    intent.putExtra("selectedTraining", Integer.toString(position));
                    startActivity(intent);
                }
            }
        };

        lvSimple.setOnItemClickListener(mOnListClick);


    }

    class MyViewBinder implements SimpleAdapter.ViewBinder {

        int red = getResources().getColor(R.color.Red);
        int orange = getResources().getColor(R.color.Orange);
        int green = getResources().getColor(R.color.Green);
        int white = getResources().getColor(R.color.White);

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public boolean setViewValue(View view, Object data,
                                    String textRepresentation) {
            int i = 0;
            switch (view.getId()) {
                // LinearLayout
                case R.id.llLoad:
                    i = ((Integer) data).intValue();
                    view.setBackgroundColor(white);
                    return true;

                // ProgressBar 
                case R.id.pbLoad:
                    i = ((Integer) data).intValue();
                    ((ProgressBar)view).setProgress(i);

                    if (i < 40) ((ProgressBar)view).getProgressDrawable().setColorFilter(red, PorterDuff.Mode.MULTIPLY); else
                    if (i < 70) ((ProgressBar)view).getProgressDrawable().setColorFilter(orange, PorterDuff.Mode.MULTIPLY); else
                        ((ProgressBar)view).getProgressDrawable().setColorFilter(green, PorterDuff.Mode.MULTIPLY);
                    return true;
            }
            return false;
        }
    }

}