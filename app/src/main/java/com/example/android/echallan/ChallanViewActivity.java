package com.example.android.echallan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class ChallanViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challan_view);

        new ChallanDataCollector().execute("AP09TVA2762");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.challan_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ChallanDataCollector extends AsyncTask<String,String,String> {

        protected String doInBackground(String... urls) {
            return getData(urls[0]);
        }

        public String getData(String VehicleNumber) {
                /*
        url = "https://www.echallan.org/publicview/CaptchaServlet.do"

        querystring = {"ctrl":"new"}

        response1 = s.request("POST", url, params=querystring)

        print(response1.text)

        captcha = response1.text.replace(" ","")

        print captcha

        url = "https://www.echallan.org/publicview/PendingChallans.do"

        querystring = {"ctrl":"tab1","obj":"AP09TVA2762","captchaText":captcha}

        dic = response1.cookies.get_dict()
        print dic

        response = s.request("POST",url, headers=dic, params=querystring)
        */

            String responseString= "";

            try {
                String url = "https://www.echallan.org/publicview/CaptchaServlet.do";

                HttpClient httpclient;
                HttpPost httppost;
                ArrayList<NameValuePair> postParameters;
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost(url);


                postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("ctrl", "new"));

                httppost.setEntity(new UrlEncodedFormEntity(postParameters));

                HttpResponse response = httpclient.execute(httppost);

                responseString = new BasicResponseHandler().handleResponse(response);

                responseString = responseString.replace(" ", "");

                url = "https://www.echallan.org/publicview/PendingChallans.do";
                //querystring = {"ctrl":"tab1","obj":"AP09TVA2762","captchaText":captcha}

                postParameters.remove(0);

                postParameters.add(new BasicNameValuePair("ctrl", "tab1"));
                postParameters.add(new BasicNameValuePair("obj", VehicleNumber));
                postParameters.add(new BasicNameValuePair("captchaText", responseString));

                httppost = new HttpPost(url);
                httppost.setEntity(new UrlEncodedFormEntity(postParameters));

                response = httpclient.execute(httppost);

                responseString = new BasicResponseHandler().handleResponse(response);

            /*
            * TextView foo = (TextView)findViewById(R.id.foo);
            foo.setText(Html.fromHtml(responseString));
            *
            * */

                Log.d("aa", "dd");

            } catch (Exception e) {
                // process execption
                Log.d("aa", "dd");
            }
            Log.d("aa", "dd");

            parse(responseString);

            return responseString;
        }

        public class ChallanEntry
        {
            public String SerialNumber;
            public boolean Selected;
            public String UnitName;
            public String EchallanNo;
            public String Date;
            public String Time;
            public String PlaceOfViolation;
            public String TrafficPSLimits;
            public String Violation;
            public String TotalFinaAmount;
            public String UserCharges;
            public String Total;
            public String Image;
        }

        List<ChallanEntry> entries = new ArrayList<ChallanEntry>();

        void parse(String html)
        {
            Document doc = Jsoup.parseBodyFragment(html);
            //Element body = doc.body();

            //first entry is selectAll and last entry is printAll
            Elements tableEntries = doc.select("tr:has(input)");

            for(int i=1;i<tableEntries.size()-1;i++)
            {
                ChallanEntry c = new ChallanEntry();
                c.SerialNumber=tableEntries.get(i).child(0).text();
                c.Selected=false;
                c.UnitName=tableEntries.get(i).child(2).text();
                c.EchallanNo=tableEntries.get(i).child(3).text();
                c.Date=tableEntries.get(i).child(4).text();
                c.Time=tableEntries.get(i).child(5).text();
                c.PlaceOfViolation=tableEntries.get(i).child(6).text();
                c.TrafficPSLimits=tableEntries.get(i).child(7).text();
                c.Violation=tableEntries.get(i).child(8).text();
                c.TotalFinaAmount=tableEntries.get(i).child(9).text();
                c.UserCharges=tableEntries.get(i).child(10).text();
                c.Total=tableEntries.get(i).child(11).text();
                c.Image=tableEntries.get(i).child(12).text();
                entries.add(c);
            }
        }

        protected void onPostExecute(String result) {

            TextView foo = (TextView) findViewById(R.id.foo);
            String ret="";
            for(int i=0;i<entries.size();i++)
            {
                ret+=entries.get(i).UnitName+"\t";
            }

            foo.setText(ret);
        }
    }
}
