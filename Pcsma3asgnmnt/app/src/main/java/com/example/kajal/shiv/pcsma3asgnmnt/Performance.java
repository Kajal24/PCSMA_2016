package com.example.kajal.shiv.pcsma3asgnmnt;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Performance extends AppCompatActivity
{
    ArrayList<Integer> resultx;
    ArrayList<Integer> qid;
    ArrayList<Integer> rInt;
    ArrayList<Integer> qidd;
    AsyncTask<String, Void, String> task;
    String email;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prfrmnc);

        Intent data1 = getIntent();
        email=(data1.getExtras().get("email")).toString();

        task = new HttpAsyncTask1().execute("http://192.168.21.207:8080/detailedPerformance");


      /* BarChart barChart = (BarChart) findViewById(R.id.chart);

        resultx=rInt;
        qid=qidd;
       // resultx= (ArrayList<Integer>) getIntent().getSerializableExtra("result");
       // qid= (ArrayList<Integer>) getIntent().getSerializableExtra("qid");

        // HorizontalBarChart barChart= (HorizontalBarChart) findViewById(R.id.chart);
       try{
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<resultx.size();i++) {
            entries.add(new BarEntry(resultx.get(i).floatValue(), i));
        }

        Toast.makeText(getApplicationContext(),resultx.get(0).toString(),Toast.LENGTH_LONG).show();
        BarDataSet dataset = new BarDataSet(entries, "Correct Answer ");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0; i<qid.size();i++) {
            labels.add("QuizID "+qid.get(i).toString());
        }

        BarData data = new BarData(labels, dataset);
        //dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        barChart.setData(data);
           dataset.setColors(ColorTemplate.COLORFUL_COLORS);
           barChart.animateY(5000);}catch (Exception e){

        }

        */

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


        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable,25000);
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
           // Toast.makeText(Performance.this,"in runnable",Toast.LENGTH_SHORT).show();
            task = new HttpAsyncTask1().execute("http://192.168.21.207:8080/detailedPerformance");
            Performance.this.mHandler.postDelayed(m_Runnable, 25000);
        }


    };//runnable

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(m_Runnable);
    }

    public static String GET(String url)
    {
        InputStream inputStream = null;
        String result = "";
        try
        {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        }
        catch (Exception e)
        {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = " ";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        System.out.println(result);
        return result;
    }

     private class HttpAsyncTask1 extends AsyncTask<String, Void, String>
      {
        @Override
        protected String doInBackground(String... urls)
        {
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override

        protected void onPostExecute(final String result)
        {
           // Runnable r = new Runnable() {
               // @Override
                //public void run() {
                    try
                    {
                        ////////////////////////////String Parsing////////////////////
                        //String str="{\"qid\":12,\"ques\":\"Question you know?\",\"timer\":1,\"answer\":\"NA\"}";
                        String resultN=result.substring(2, result.length() - 1);
                        String[] commaSplit = resultN.split(",");
                        List<Integer>quizId=new ArrayList<Integer>();
                        List<String> emails=new ArrayList<String>();
                        List<String> qrslt=new ArrayList<String>();

                        rInt=new ArrayList<Integer>();
                       qidd=new ArrayList<Integer>();
                        int length=commaSplit.length;
                        int i=0;
                        while(length>i)
                        {
                            String[] qid = commaSplit[i].split(":");
                            quizId.add(Integer.parseInt(qid[1]));
                            String[] email = commaSplit[i+4].split(":");
                            String[] emailnew=email[1].split("_");
                            // emails.add(email[1].substring(1,(email[1].length()-1)));
                            emails.add(emailnew[0].substring(1));
                            String[] rslt = commaSplit[i+5].split(":");
                            qrslt.add(rslt[1].substring(0,rslt[1].length()-1));
                            i=i+6;
                        }
                        for(int j=0;j<quizId.size();j++){

                            if(emails.get(j).equals(email)) {
                               qidd.add(quizId.get(j));
                                if(qrslt.get(j).equals("true")){
                                    rInt.add(1);
                                }
                                else
                                    rInt.add(0);
                        }
                        }
                        //ques = qt[1].substring(1, (qt[1].length() - 1));
                        // timer = Integer.parseInt(time[1]);

                         Toast.makeText(getBaseContext(),rInt.get(0)+qidd.get(0), Toast.LENGTH_LONG).show();
                        ////////////////////////////////////////////////

                        // Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();


                    }
                    catch (Exception e)
                    {
                       // Toast.makeText(getBaseContext(), "Nothing to Show", Toast.LENGTH_SHORT).show();
                        // timer = 0;
                        // ques = null;
                    }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////

            BarChart barChart = (BarChart) findViewById(R.id.chart);

            resultx=rInt;
            qid=qidd;
            // result= (ArrayList<Integer>) getIntent().getSerializableExtra("result");
            // qid= (ArrayList<Integer>) getIntent().getSerializableExtra("qid");
             barChart.setBackgroundColor(Color.GRAY);
            //barChart.animate().rotationX(10f);
            barChart.animateXY(1000,5000);

            // HorizontalBarChart barChart= (HorizontalBarChart) findViewById(R.id.chart);
            try{
                int count=0;
                for(int x=0;x<qid.size();x++){
                    if(qid.get(x)==1){
                        count++;
                    }
                }
                ArrayList<BarEntry> entries = new ArrayList<>();
                for(int k=0;k<resultx.size();k++) {
                    entries.add(new BarEntry(resultx.get(k).floatValue(), k));
                }

                Toast.makeText(getApplicationContext(),resultx.get(0).toString(),Toast.LENGTH_LONG).show();
                BarDataSet dataset = new BarDataSet(entries, "Color-Correct Answer & Percentage: "+(resultx.size()/count)*100);

                ArrayList<String> labels = new ArrayList<String>();
                for(int k=0; k<qid.size();k++) {
                    labels.add("QuizID "+qid.get(k).toString());
                }

                BarData data = new BarData(labels, dataset);
                //dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                barChart.setData(data);
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                barChart.animateY(5000);}catch (Exception e){

            }

            /////////////////////////////////////////////////////////////////////////////
            //if(!isCancelled())
              //  task = new HttpAsyncTask1().execute("http://192.168.21.207:8080/detailedPerformance");
                //}
           // };
           // Handler h = new Handler();
           // h.postDelayed(r, 3000);
        }
        @Override
        protected void onCancelled()
        {
            super.onCancelled();
            //timer = 0;
            //ques = null;
        }
    }
}