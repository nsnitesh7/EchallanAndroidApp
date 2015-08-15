package com.example.android.echallan;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ChallanListView extends ListActivity {

    public ChallanListView() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Change Adapter to display your content
   //     setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
     //           android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
        new ChallanDataCollector().execute("AP28DE9398");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Log.d(TAG,c.getTitle() + "is clicked!");
        Intent i = new Intent(ChallanListView.this,ChallanDetailsActivity.class);
        i.putExtra("challanID",position);
        startActivity(i);
    }

    public void DisplayItems()
    {
        ChallanAdapter adapter = new ChallanAdapter(ChallanLab.GetChallans());
        setListAdapter(adapter);
    }

    private class ChallanAdapter extends ArrayAdapter<ChallanEntry>
    {
        public ChallanAdapter(ArrayList<ChallanEntry> challans)
        {
            super(ChallanListView.this,0,challans);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
            {
                convertView=getLayoutInflater().inflate(R.layout.list_item_challan,null);
            }

            ChallanEntry c = getItem(position);
            ((TextView)convertView.findViewById(R.id.date)).setText(c.Date);
            ((TextView)convertView.findViewById(R.id.violation)).setText(c.Violation);
            ((TextView)convertView.findViewById(R.id.amount)).setText(c.TotalFinaAmount);

            return convertView;
        }
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

        void parse(String html)
        {
            Document doc = Jsoup.parseBodyFragment(html);
            //Element body = doc.body();

            //first entry is selectAll and last entry is printAll
            Elements tableEntries = doc.select("tr:has(input)");

            ArrayList<ChallanEntry> entries=new ArrayList<>();

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
                entries.add(c);
            }

            ChallanLab.SetChallans(entries);
        }

        protected void onPostExecute(String result) {
            DisplayItems();
        }
    }
}