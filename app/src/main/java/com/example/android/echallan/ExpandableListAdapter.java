package com.example.android.echallan;

/**
 * Created by nitesi on 16-08-2015.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<ChallanEntry> _listDataHeader; // header titles
    // child data in format of header title, child title

    public ExpandableListAdapter(Context context, ArrayList<ChallanEntry> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return _listDataHeader.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ChallanEntry c = (ChallanEntry) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        ((TextView)convertView.findViewById(R.id.challanDetailsSerialNumber)).setText(c.SerialNumber);
        ((TextView)convertView.findViewById(R.id.challanDetailsUnitName)).setText(c.UnitName);
        ((TextView)convertView.findViewById(R.id.challanDetailsEchallanNo)).setText(c.EchallanNo);
        ((TextView)convertView.findViewById(R.id.challanDetailsDate)).setText(c.Date);
        ((TextView)convertView.findViewById(R.id.challanDetailsTime)).setText(c.Time);
        ((TextView)convertView.findViewById(R.id.challanDetailsPlaceOfViolation)).setText(c.PlaceOfViolation);
        ((TextView)convertView.findViewById(R.id.challanDetailsTrafficPSLimits)).setText(c.TrafficPSLimits);
        ((TextView)convertView.findViewById(R.id.challanDetailsViolation)).setText(c.Violation);
        ((TextView)convertView.findViewById(R.id.challanDetailsTotalFinaAmount)).setText(c.TotalFinaAmount);
        ((TextView)convertView.findViewById(R.id.challanDetailsUserCharges)).setText(c.UserCharges);
        ((TextView)convertView.findViewById(R.id.challanDetailsTotal)).setText(c.Total);
        ((TextView)convertView.findViewById(R.id.challanDetailsImage)).setText(c.Image);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ChallanEntry header = (ChallanEntry) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeaderEchallanNo = (TextView) convertView
                .findViewById(R.id.lblListHeaderEchallanNo);
        lblListHeaderEchallanNo.setTypeface(null, Typeface.BOLD);
        lblListHeaderEchallanNo.setText(header.EchallanNo);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
