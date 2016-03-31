package com.example.kajal.shiv.pcsma3asgnmnt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kajal.shiv.pcsma3asgnmnt.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Performance extends AppCompatActivity
{ ArrayList<Integer> result;
    ArrayList<Integer> qid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prfrmnc);
        BarChart barChart = (BarChart) findViewById(R.id.chart);

        result= (ArrayList<Integer>) getIntent().getSerializableExtra("result");
        qid= (ArrayList<Integer>) getIntent().getSerializableExtra("qid");
        // HorizontalBarChart barChart= (HorizontalBarChart) findViewById(R.id.chart);
       try{
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<result.size();i++) {
            entries.add(new BarEntry(result.get(i).floatValue(), i));
        }
        //entries.add(new BarEntry(4f, 1,"ffhgfh"));
        // entries.add(new BarEntry());
        /*entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        */
        Toast.makeText(getApplicationContext(),result.get(0).toString(),Toast.LENGTH_LONG).show();
        BarDataSet dataset = new BarDataSet(entries, "Correct Answer ");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0; i<qid.size();i++) {
            labels.add("QuizID "+qid.get(i).toString());
        }

        BarData data = new BarData(labels, dataset);
        //dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        barChart.setData(data);
        barChart.animateY(5000);}catch (Exception e){

        }
        /* for create Grouped Bar chart
        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        group1.add(new BarEntry(12f, 3));
        group1.add(new BarEntry(18f, 4));
        group1.add(new BarEntry(9f, 5));

        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));
        group2.add(new BarEntry(15f, 4));
        group2.add(new BarEntry(10f, 5));

        BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<BarDataSet> dataset = new ArrayList<>();
        dataset.add(barDataSet1);
        dataset.add(barDataSet2);
        */

        //BarData data = new BarData(labels, dataset);
        //dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        //barChart.setData(data);
        //barChart.animateY(5000);

    }
}