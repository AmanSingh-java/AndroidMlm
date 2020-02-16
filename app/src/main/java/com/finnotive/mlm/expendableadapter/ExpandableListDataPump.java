package com.finnotive.mlm.expendableadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> unit1 = new ArrayList<String>();
        unit1.add("India");
        unit1.add("Pakistan");
        unit1.add("Australia");
        unit1.add("England");
        unit1.add("South Africa");

        List<String> unit2 = new ArrayList<String>();
        unit2.add("Brazil");
        unit2.add("Spain");
        unit2.add("Germany");
        unit2.add("Netherlands");
        unit2.add("Italy");

        List<String> unit3 = new ArrayList<String>();
        unit3.add("United States");
        unit3.add("Spain");
        unit3.add("Argentina");
        unit3.add("France");
        unit3.add("Russia");

        List<String> unit4 = new ArrayList<String>();
        unit4.add("India");
        unit4.add("Pakistan");
        unit4.add("Australia");
        unit4.add("England");
        unit4.add("South Africa");

        List<String> unit5 = new ArrayList<String>();
        unit5.add("Brazil");
        unit5.add("Spain");
        unit5.add("Germany");
        unit5.add("Netherlands");
        unit5.add("Italy");

        expandableListDetail.put("UNIT 1", unit1);
        expandableListDetail.put(" UNIT 2", unit2);
        expandableListDetail.put("UNIT 3", unit3);
        expandableListDetail.put("UNIT 4", unit4);
        expandableListDetail.put(" UNIT 5", unit5);
        return expandableListDetail;
    }
}
