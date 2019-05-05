package com.example.android.apexware;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
/** This class extend from android array adapter to get all community lists */
public class CommAdapter extends ArrayAdapter {
  private final Activity context;
  // to store the list of communities names
  ArrayList<CommunityInfo> list1;
  /**
   * Constructor to adapter of comments
   *
   * @param context : application context
   * @param l1 : List of community
   */
  CommAdapter(Activity context, ArrayList<CommunityInfo> l1) {
    super(context, R.layout.community_sample, l1);
    this.context = context;
    this.list1 = l1;
  }
  /**
   * This function get attribute of current post
   *
   * @param position : position of item
   * @param parent :view extend from android
   */
  public View getView(int position, View view, ViewGroup parent) {
    CommunityInfo comm = list1.get(position);
    if (view == null)
      view = LayoutInflater.from(context).inflate(R.layout.community_sample, parent, false);
    TextView nameTextField = view.findViewById(R.id.community_name_TV);
    ImageView imageView = view.findViewById(R.id.globeIMG);
    nameTextField.setText(comm.getCommunityName());
    Picasso.get().load(comm.getCommunityLogo()).into(imageView);
    return view;
  }
}
