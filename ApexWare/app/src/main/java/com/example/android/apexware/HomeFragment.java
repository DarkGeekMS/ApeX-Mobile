package com.example.android.apexware;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView;
/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the to
 * handle interaction events. create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
  public HomeFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    Toolbar toolbar = view.findViewById(R.id.toolbar);
    activity.setSupportActionBar(toolbar);
    activity.getSupportActionBar().setTitle("");

    ActionBar actionbar = activity.getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.list_white);

    TabLayout tabLayout = (TabLayout) view.findViewById(R.id.viewMode_tablayout);
    ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewMode_viewpager);

    ViewPagerAdapter viewPagerAdapter =
        new ViewPagerAdapter(getChildFragmentManager()); // activity.getSupportFragmentManager()
    viewPagerAdapter.addFragment(new HomeSubscribedCommunities(), "Home");
    viewPagerAdapter.addFragment(new PopularUnsubscribedCommunities(), "Popular");

    viewPager.setAdapter(viewPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
    setHasOptionsMenu(true);
    return view;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    try {
      final AppCompatActivity activity = (AppCompatActivity) getActivity();
      activity.getMenuInflater().inflate(R.menu.options_menu, menu);
      MenuItem mSearch = menu.findItem(R.id.action_search);
      SearchView mSearchView = (SearchView) mSearch.getActionView();
      mSearchView.setOnSearchClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(getContext(), SerachList.class);
              startActivity(i);
            }
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
    super.onCreateOptionsMenu(menu, inflater);
  }
}
