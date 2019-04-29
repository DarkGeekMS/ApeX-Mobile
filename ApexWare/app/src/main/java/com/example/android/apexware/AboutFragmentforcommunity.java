package com.example.android.apexware;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AboutFragmentforcommunity extends Fragment {
    View view;

    public AboutFragmentforcommunity() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.aboutfragmentforcommunity,container,false);
        ListView moderats= view.findViewById(R.id.moderators);
        ExpandableListView Rules=view.findViewById(R.id.Ruleslist);
        ArrayList<String>  RulesHeader = new ArrayList<String>();
        HashMap<String, List<String>> RuleDetails = new HashMap<String, List<String>>();
        String [] modearators   ={"ali","yasser","yomna","shaddad","pewds","ali","yasser","yomna","shaddad","pewds","ali","yasser","yomna","shaddad","pewds","ali","yasser","yomna","shaddad","pewds"};
        ArrayAdapter<String> mods=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,modearators);
        moderats.setAdapter(mods);
        RulesHeader.add("rule 1");
        RulesHeader.add("rule 2");
        RulesHeader.add("rule 3");
        RulesHeader.add("rule 4");
        RulesHeader.add("rule 5");
        RulesHeader.add("rule 6");
        RulesHeader.add("rule 7");
        List<String> rule1details=new ArrayList<String>();
        rule1details.add("bllllllllllllllllllllllla");

        RuleDetails.put(RulesHeader.get(0),rule1details);
        RuleDetails.put(RulesHeader.get(1),rule1details);
        RuleDetails.put(RulesHeader.get(2),rule1details);
        RuleDetails.put(RulesHeader.get(3),rule1details);
        RuleDetails.put(RulesHeader.get(4),rule1details);
        RuleDetails.put(RulesHeader.get(5),rule1details);
        RuleDetails.put(RulesHeader.get(6),rule1details);
        aboutExListAdapter rulesAdapter =new aboutExListAdapter(this.getActivity(),RulesHeader,RuleDetails);
        Rules.setAdapter(rulesAdapter);
        //this ontouch listener makes the listview scrollable but   NOT THE WHOLE VIEW
        moderats.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                      v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                       v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });
        return view;

    }

/*
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
*/


}
