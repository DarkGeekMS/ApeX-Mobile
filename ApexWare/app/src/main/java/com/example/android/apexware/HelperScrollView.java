package com.example.android.apexware;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
/** This class gives best experience in scroll to user */
public class HelperScrollView {
  public static void getListViewSize(ListView myListView) {
    ListAdapter myListAdapter = myListView.getAdapter();
    if (myListAdapter == null) {
      // do nothing return null
      return;
    }
    // set listAdapter in loop for getting final size
    int totalHeight = 0;
    for (int size = 0; size < myListAdapter.getCount(); size++) {
      View listItem = myListAdapter.getView(size, null, myListView);
      listItem.measure(0, 0);
      totalHeight += listItem.getMeasuredHeight();
    }
    // setting listview item in adapter
    ViewGroup.LayoutParams params = myListView.getLayoutParams();
    params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount()));
    myListView.setLayoutParams(params);
  }
}
