package com.example.android.echallan;

import java.util.ArrayList;

/**
 * Created by nitesi on 16-08-2015.
 */
public class ChallanLab {
    private static ArrayList<ChallanEntry> entries;

    public static ChallanEntry getChallan(int position)
    {
        return entries.get(position);
    }

    public static ArrayList<ChallanEntry> GetChallans()
    {
        return entries;
    }

    public static void SetChallans(ArrayList<ChallanEntry> challans)
    {
        entries=challans;
    }
}
