package com.example.ricktam.pcsmaproject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Raspberry1 extends AppCompatActivity {

    AsyncTask<String, Void, String> task;
    ProgressBar npb,ppb;
    private Handler mHandler;
    int noise,pollution;
    TextView ntext,ptext;
    Animation anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raspberry1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        npb=(ProgressBar)findViewById(R.id.npb);
        ppb=(ProgressBar)findViewById(R.id.ppb);

        ntext=(TextView)findViewById(R.id.ntext);
        ptext=(TextView)findViewById(R.id.ptext);

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable, 5000);


    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            // Toast.makeText(Performance.this,"in runnable",Toast.LENGTH_SHORT).show();
            task = new HttpAsyncTask1().execute("http://ec2-52-35-198-120.us-west-2.compute.amazonaws.com:3000/sensorData");

            Raspberry1.this.mHandler.postDelayed(m_Runnable, 5000);
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
            try {
                //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

                ////////////////////////////String Parsing////////////////////
                //String str="{\"qid\":12,\"ques\":\"Question you know?\",\"timer\":1,\"answer\":\"NA\"}";
                String[] strings = result.split(";");
                String[] sensordata = strings[1].split("_");
                String[] noiseData = sensordata[0].split("=");
                String[] pollutionData = sensordata[1].split("=");
                noise = Integer.parseInt(noiseData[1]);
                pollution = Integer.parseInt(pollutionData[1]);

                // Toast.makeText(getBaseContext(), noise+"--"+pollution, Toast.LENGTH_LONG).show();
                //ques = qt[1].substring(1, (qt[1].length() - 1));
                // timer = Integer.parseInt(time[1]);
                float n = (float)(((float)(noise) / (float)(npb.getMax()))) * 100;
                float p = (float)((float)(pollution) / (float)((ppb.getMax()))) * 100;
                //npb.setProgress(noise);
                //ntext.setText((int) n);
                Log.d("TAG","kajal-->  "+n);
                String strProgress = n + " %";
                npb.setProgress(noise);
                              /* update secondary progress of horizontal progress bar */
                ntext.setText(strProgress);
                String strProgress1 = p + " %";
                ppb.setProgress(pollution);
                              /* update secondary progress of horizontal progress bar */
                ptext.setText(strProgress1);




                //ppb.setProgress(pollution);
                //ptext.setText((int) p);

                if(noise>12000)
                {
                    npb.setBackgroundColor(Color.RED);
                    ntext.startAnimation(anim);

                }
                if(pollution>800){
                    ppb.setBackgroundColor(Color.RED);
                    ptext.startAnimation(anim);
                }

            }
            catch (Exception e)
            {
                // Toast.makeText(getBaseContext(), "Nothing to Show", Toast.LENGTH_SHORT).show();
                // timer = 0;
                // ques = null;
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////



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
