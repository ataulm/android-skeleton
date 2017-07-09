package com.example;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidskeleton.R;
import com.novoda.accessibility.Action;
import com.novoda.accessibility.Actions;
import com.novoda.accessibility.ActionsAlertDialogCreator;

import java.util.Arrays;

public class MyActivity extends AppCompatActivity {

    private ActionsAlertDialogCreator actionsAlertDialogCreator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        actionsAlertDialogCreator = new ActionsAlertDialogCreator(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3) {
            @Override
            public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfoForItem(recycler, state, host, info);
                ViewGroup.LayoutParams lp = host.getLayoutParams();
                if (!(lp instanceof LayoutParams)) {
                    return;
                }

                LayoutParams globalLayoutParams = (LayoutParams) lp;
                int spanGroupIndex = getSpanGroupIndex(recycler, state, globalLayoutParams.getViewLayoutPosition());
//                if (getOrientation() == HORIZONTAL) {
//                    info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(
//                            globalLayoutParams.getSpanIndex(), globalLayoutParams.getSpanSize(),
//                            spanGroupIndex, 1,
//                            false, false));
//                } else { // VERTICAL
                info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(
                        -1, 1,
                        -1, globalLayoutParams.getSpanSize(),
                        false, true));
//                }
                info.setCanOpenPopup(true);

            }

            private int getSpanGroupIndex(RecyclerView.Recycler recycler, RecyclerView.State state,
                                          int viewPosition) {
                if (!state.isPreLayout()) {
                    return getSpanSizeLookup().getSpanGroupIndex(viewPosition, getSpanCount());
                }
                final int adapterPosition = recycler.convertPreLayoutPositionToPostLayout(viewPosition);
                if (adapterPosition == -1) {
                    return 0;
                }
                return getSpanSizeLookup().getSpanGroupIndex(adapterPosition, getSpanCount());
            }
        };
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 6 == 0) {
                    return 3;
                }
                return 1;

            }
        });
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new SectionsAdapter(actionsAlertDialogCreator));
    }

    private static class SectionsAdapter extends RecyclerView.Adapter {

        private static final int HEADER_VIEW_TYPE = 0;
        private static final int ITEM_VIEW_TYPE = 1;
        private static final int ITEMS_PER_SECTION = 6;

        private final ActionsAlertDialogCreator dialogCreator;

        private Toast toast;

        private SectionsAdapter(ActionsAlertDialogCreator dialogCreator) {
            this.dialogCreator = dialogCreator;
        }

        @Override
        public int getItemViewType(int position) {
            if (position % ITEMS_PER_SECTION == 0) {
                return HEADER_VIEW_TYPE;
            } else {
                return ITEM_VIEW_TYPE;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == HEADER_VIEW_TYPE) {
                return HeaderViewHolder.inflate(parent);
            } else {
                return ItemViewHolder.inflate(parent);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final TextView itemView = (TextView) holder.itemView;
            int section = position / ITEMS_PER_SECTION;

            if (getItemViewType(position) == HEADER_VIEW_TYPE) {
                itemView.setText("header: " + String.valueOf(section + 1));
            } else {
                final String text = "item " + String.valueOf(position - (section * ITEMS_PER_SECTION));
                final Action actionOne = new Action(R.id.action_one, R.string.action_one, new Runnable() {
                    @Override
                    public void run() {
                        toast(itemView, text);
                        itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")));
                    }
                });
                final AlertDialog alertDialog = dialogCreator.create(new Actions(Arrays.asList(actionOne)));
                itemView.setText(text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.show();
                        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                itemView.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
                            }
                        });
                    }
                });
            }
        }

        private void toast(TextView itemView, String text) {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(itemView.getContext(), "click " + text, Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        public int getItemCount() {
            return 24;
        }

    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View itemView) {
            super(itemView);
        }

        static RecyclerView.ViewHolder inflate(ViewGroup parent) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header, parent, false));
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemViewHolder(View itemView) {
            super(itemView);
        }

        static RecyclerView.ViewHolder inflate(ViewGroup parent) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false));
        }
    }

}
