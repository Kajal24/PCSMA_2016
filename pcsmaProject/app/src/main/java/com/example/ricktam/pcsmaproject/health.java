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
import android.view.animation.Animation;
import android.widget.LinearLayout;
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

public class health extends AppCompatActivity {

    AsyncTask<String, Void, String> task;

    private Handler mHandler;

    Animation anim;
    LinearLayout l1,l2,l3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
         l1=(LinearLayout)findViewById(R.id.noise);
        l2=(LinearLayout)findViewById(R.id.pollution);
         l3=(LinearLayout)findViewById(R.id.pi);
        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable, 5000);




    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            // Toast.makeText(Performance.this,"in runnable",Toast.LENGTH_SHORT).show();
            task = new HttpAsyncTask2().execute("http://ec2-52-35-198-120.us-west-2.compute.amazonaws.com:3000/health");
            //task2 = new HttpAsyncTask2().execute("http://ec2-52-35-198-120.us-west-2.compute.amazonaws.com:3000/health");
            health.this.mHandler.postDelayed(m_Runnable, 5000);
        }


    };//runnable

    @Override
    protected void onStop(){
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

    private class HttpAsyncTask2 extends AsyncTask<String, Void, String>
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
                for(int i=0;i<strings.length;i++){
                    String[] all=strings[i].split("=");
                    if(all[0].equals("noiseSensor")&& all[1].equals("0")){
                        l1.setBackgroundColor(Color.RED);
                    }
                    if(all[0].equals("pollutionSensor")&& all[1].equals("0")){
                        l2.setBackgroundColor(Color.RED);
                    }

                }

            }
            catch (Exception e)
            {
                l3.setBackgroundColor(Color.RED);
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
