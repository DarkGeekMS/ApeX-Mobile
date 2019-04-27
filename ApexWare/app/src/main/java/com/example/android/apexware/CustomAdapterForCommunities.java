package com.example.android.apexware;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.android.apexware.CreatePost.stPosition;

/**
 * adapter to deal with communities list view and assign data to it
 * @author mostafa
 */
public class CustomAdapterForCommunities extends BaseAdapter {

  // to reference the Activity
  private final Activity context;
public class CustomAdapterForCommunities extends ArrayAdapter {
    // to reference the Activity
    private final Activity context;

  // to store the list of communities names
  private  String[] nameArray=new String[] {"gggg", "hhhhh", "LLLLLL"};

  // to store the list of communities ids
  private  String[] idArray = new String[] {"0", "1", "2"};

  // to store the community images
  private  String[] imageLinkArray =
          new String[] {
                  "https://i.imgur.com/S7USWRb.jpg",
                  "https://i.imgur.com/S7USWRb.jpg",
                  "https://i.imgur.com/S7USWRb.jpg"
          };

  /**
   * constructor for the adapter
   *
   * @param nameArrayParam : array of communities names
   * @param imageLinkArrayParam : array of communities images as links
   * @param idArray : array of communities ids
   */
  public CustomAdapterForCommunities(
      Activity context, String[] nameArrayParam, String[] imageLinkArrayParam, String[] idArray) {
    // super(context, R.layout.community_sample, nameArrayParam);
    this.context = context;
    this.nameArray = nameArrayParam;
    this.imageLinkArray = imageLinkArrayParam;
    this.idArray = idArray;
  }

  public CustomAdapterForCommunities(Activity context) {
    this.context = context;
  }

  public View getView(int position, View view, ViewGroup parent) {
    LayoutInflater inflater = context.getLayoutInflater();
    View rowView = inflater.inflate(R.layout.community_sample, null, true);

    // this code gets references to objects in the listview_row.xml file
    TextView nameTextField = (TextView) rowView.findViewById(R.id.community_name_TV);
    TextView idHolder = (TextView) rowView.findViewById(R.id.idcom);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.globeIMG);

    // this code sets the values of the objects to values from the arrays
    nameTextField.setText(nameArray[position]);
    idHolder.setText(idArray[position]);
    Picasso.get().load(imageLinkArray[position]).into(imageView);

    return rowView;
  }

  public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    /* Toast.makeText(parent.getContext(),
    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
    Toast.LENGTH_SHORT).show();
    */
    stPosition = pos;
  }

  @Override
  public int getCount() {
    return idArray.length;
  }
    }

  @Nullable
  @Override
  public Object getItem(int position) {
    return idArray[position];
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public String getName(int position) {
    return nameArray[position];
  }
}
