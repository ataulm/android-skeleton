package com.ataulm.basic.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Switch;

import com.ataulm.basic.R;

public class NavigationDrawerView extends ScrollView {

    private NavigationDrawerHeaderView headerView;
    private NavigationDrawerPrimaryItemView myShowsItemView;
    private NavigationDrawerPrimaryItemView discoverItemView;
    private NavigationDrawerActionItemView downloadOnlyItemView;
    private Switch switchView;

    public NavigationDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_navigation_drawer, this);

        headerView = (NavigationDrawerHeaderView) findViewById(R.id.navigation_drawer_header);

        myShowsItemView = (NavigationDrawerPrimaryItemView) findViewById(R.id.navigation_drawer_item_my_shows);
        myShowsItemView.update(R.drawable.ic_my_shows, "My Shows");
        myShowsItemView.setContentDescription("Open My Shows button");

        discoverItemView = (NavigationDrawerPrimaryItemView) findViewById(R.id.navigation_drawer_item_discover);
        discoverItemView.update(R.drawable.ic_discover, "Discover");
        discoverItemView.setContentDescription("Open Discover button");

        downloadOnlyItemView = (NavigationDrawerActionItemView) findViewById(R.id.navigation_drawer_item_downloaded_only);
        switchView = ((Switch) downloadOnlyItemView.findViewById(R.id.navigation_drawer_item_switch));
        downloadOnlyItemView.update("Downloaded only");
        String checkedText = switchView.isChecked() ? "checked" : "not checked";
        downloadOnlyItemView.setContentDescription("Show only downloaded items checkbox, " + checkedText);
    }

    private static void setClickListener(View view, final String item, final Listener listener) {
        view.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickItem(item);
                    }
                }
        );
    }

    public void setMyShowsAsCurrentPage() {
        discoverItemView.setActivated(false);
        myShowsItemView.setActivated(true);
    }

    public void setDiscoverAsCurrentPage() {
        discoverItemView.setActivated(true);
        myShowsItemView.setActivated(false);
    }

    public void setListener(Listener listener) {
        headerView.setListener(listener);
        setClickListener(myShowsItemView, "My Shows", listener);
        setClickListener(discoverItemView, "Discover", listener);
        downloadOnlyItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView.setChecked(!switchView.isChecked());
                if (switchView.isChecked()) {
                    switchView.announceForAccessibility("showing downloaded items only");
                } else {
                    switchView.announceForAccessibility("showing all items");
                }
            }
        });
    }

}
