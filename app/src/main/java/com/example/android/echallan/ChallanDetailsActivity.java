package com.example.android.echallan;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ChallanDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challan_details);
        int challanID = getIntent().getIntExtra("challanID", 0);
        ChallanEntry c = ChallanLab.getChallan(challanID);

        ((TextView)findViewById(R.id.challanDetailsSerialNumber)).setText(c.SerialNumber);
        ((TextView)findViewById(R.id.challanDetailsUnitName)).setText(c.UnitName);
        ((TextView)findViewById(R.id.challanDetailsEchallanNo)).setText(c.EchallanNo);
        ((TextView)findViewById(R.id.challanDetailsDate)).setText(c.Date);
        ((TextView)findViewById(R.id.challanDetailsTime)).setText(c.Time);
        ((TextView)findViewById(R.id.challanDetailsPlaceOfViolation)).setText(c.PlaceOfViolation);
        ((TextView)findViewById(R.id.challanDetailsTrafficPSLimits)).setText(c.TrafficPSLimits);
        ((TextView)findViewById(R.id.challanDetailsViolation)).setText(c.Violation);
        ((TextView)findViewById(R.id.challanDetailsTotalFinaAmount)).setText(c.TotalFinaAmount);
        ((TextView)findViewById(R.id.challanDetailsUserCharges)).setText(c.UserCharges);
        ((TextView)findViewById(R.id.challanDetailsTotal)).setText(c.Total);
        ((TextView)findViewById(R.id.challanDetailsImage)).setText(c.Image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challant_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
