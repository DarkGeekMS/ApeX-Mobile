package com.example.android.apexware;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterForReplayMessages extends ArrayAdapter {
    private final Activity context;
    List<Replays> replaysList = new ArrayList<>();
    /**
     * this is constructor for the this class it the setup each item with predefined xml layout
     *
     * @param context Activity context
     * @param list Array list posts that we want to represent it in a list
     */
    public CustomAdapterForReplayMessages(Activity context, ArrayList<Replays> list) {
        super(context, R.layout.homepgaelistview, list);
        this.context = context;
        replaysList=list;
    }
    public View getView(final int position, View view, ViewGroup parent) {
        View listItem = view;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.message_replays_listview, parent, false);
        }
        final Replays currentReplay = replaysList.get(position);

        //set replay sender
        TextView replayusername=(TextView) listItem.findViewById(R.id.replayMessageUsername);
        String temp=currentReplay.getSender()+"."+currentReplay.getAgoTime();
        replayusername.setText(temp);
        //set replay content
        TextView replaycontent =(TextView) listItem.findViewById(R.id.replayMessageContent);
        replaycontent.setText(currentReplay.getConten());
        return listItem;
    }
}
