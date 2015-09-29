package com.ataulm.basic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ataulm.rv.SpacesItemDecoration;

public class ActionListActivity extends Activity implements CharacterClickListener {

    public static final int VARIANT_NO_CLICK = 0;
    public static final int VARIANT_NO_CLICK_FOO_IMAGE = 1;
    public static final int VARIANT_CLICKABLE_TRUE = 2;
    public static final int VARIANT_WITH_CLICK = 3;
    public static final int VARIANT_WITH_CLICK_FOO_IMAGE = 4;
    public static final int VARIANT_WITH_CLICK_FOO_IMAGE_NOT_IMPORTANT_IMAGE = 5;
    public static final int VARIANT_WITH_CLICK_CD_ON_ITEM_FOO_IMAGE = 6;
    private AccessibilityEnabledChecker accessibilityEnabledChecker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Action List");
        setContentView(R.layout.activity_action_list);

        RecyclerView actionListView = (RecyclerView) findViewById(R.id.action_list_view);
        actionListView.setLayoutManager(new LinearLayoutManager(this));
        actionListView.addItemDecoration(SpacesItemDecoration.newInstance(4, 4, 1));

        accessibilityEnabledChecker = AccessibilityEnabledChecker.newInstance(this);
        actionListView.setAdapter(new ActionListAdapter(getLayoutInflater(), getIntent().getIntExtra(ExamplesActivity.EXTRA_VARIANT, VARIANT_NO_CLICK), this, accessibilityEnabledChecker));
    }

    @Override
    public void onClick(Character character) {
        Intent intent = new Intent(this, CharacterActivity.class);
        intent.putExtra(CharacterActivity.EXTRA_CHARACTER, character);
        startActivity(intent);
    }

    @Override
    public void onClickAction(Character character) {
        Toaster.display(this, "inline action pressed for " + character.getName());
    }

    @Override
    public void onLongClick(final Character character) {
        new AlertDialog.Builder(this)
                .setItems(DIALOG_ITEMS, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("cancel".equals(DIALOG_ITEMS[which])) {
                            dialog.dismiss();
                        }
                        Toaster.display(ActionListActivity.this, character.getName() + " click " + DIALOG_ITEMS[which]);
                    }
                })
                .show();
    }

    private static final CharSequence[] DIALOG_ITEMS = {"Add to favourites", "Share", "Cancel"};

    private static class ActionListAdapter extends RecyclerView.Adapter<ActionListItemViewHolder> {

        private final LayoutInflater layoutInflater;
        private final int variant;
        private final CharacterClickListener listener;
        private final AccessibilityEnabledChecker accessibilityEnabledChecker;

        ActionListAdapter(LayoutInflater layoutInflater, int variant, CharacterClickListener listener, AccessibilityEnabledChecker accessibilityEnabledChecker) {
            this.layoutInflater = layoutInflater;
            this.variant = variant;
            this.listener = listener;
            this.accessibilityEnabledChecker = accessibilityEnabledChecker;
        }

        @Override
        public ActionListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ActionListItemViewHolder.inflate(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ActionListItemViewHolder holder, int position) {
            final Character character = Character.values()[position];
            holder.setIconColor(character.getIconColor());
            holder.setLabel(character.getName());
            holder.setSublabel(character.getDescription());

            setClickListener(holder, character);
            setImageContentDescription(holder);
            setImageImportantForAccessibility(holder);
            setItemContentDescription(holder, character);
        }

        private void setItemContentDescription(ActionListItemViewHolder holder, Character character) {
            if (shouldSetItemContentDescription()) {
                holder.itemView.setContentDescription(character.getName());
                return;
            }
        }

        private boolean shouldSetItemContentDescription() {
            return variant == VARIANT_WITH_CLICK_CD_ON_ITEM_FOO_IMAGE;
        }

        private void setImageContentDescription(ActionListItemViewHolder holder) {
            if (shouldSetFooContentDescription()) {
                holder.iconView.setContentDescription("foo");
                return;
            }
        }

        private void setImageImportantForAccessibility(ActionListItemViewHolder holder) {
            if (shouldSetImportantForAccessibilityNo()) {
                holder.iconView.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
                return;
            }
        }

        private boolean shouldSetImportantForAccessibilityNo() {
            return variant == VARIANT_WITH_CLICK_FOO_IMAGE_NOT_IMPORTANT_IMAGE;
        }

        private boolean shouldSetFooContentDescription() {
            return variant == VARIANT_NO_CLICK_FOO_IMAGE
                    || variant == VARIANT_WITH_CLICK_FOO_IMAGE
                    || variant == VARIANT_WITH_CLICK_FOO_IMAGE_NOT_IMPORTANT_IMAGE
                    || variant == VARIANT_WITH_CLICK_CD_ON_ITEM_FOO_IMAGE;
        }

        private void setClickListener(ActionListItemViewHolder holder, final Character character) {
            if (shouldHaveNoClickNorBeClickable()) {
                return;
            }

            if (shouldBeClickable()) {
                holder.itemView.setClickable(true);
                return;
            }

            if (shouldAddClickListener()) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listener.onClick(character);
                    }

                });

                holder.actionImageView.setVisibility(View.VISIBLE);
                holder.actionImageView.setContentDescription("action " + character.getName());
                holder.actionImageView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listener.onClickAction(character);
                    }

                });

                if (accessibilityEnabledChecker.isTouchExplorationEnabled()) {
                    holder.actionImageView.setVisibility(View.GONE);
                    // alternative!
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {
                            listener.onLongClick(character);
                            return true;
                        }

                    });
                } else {
                    holder.actionImageView.setVisibility(View.VISIBLE);
                }

            }
        }

        private boolean shouldHaveNoClickNorBeClickable() {
            return variant == VARIANT_NO_CLICK || variant == VARIANT_NO_CLICK_FOO_IMAGE;
        }

        private boolean shouldBeClickable() {
            return variant == VARIANT_CLICKABLE_TRUE;
        }

        private boolean shouldAddClickListener() {
            return variant == VARIANT_WITH_CLICK
                    || variant == VARIANT_WITH_CLICK_FOO_IMAGE
                    || variant == VARIANT_WITH_CLICK_FOO_IMAGE_NOT_IMPORTANT_IMAGE
                    || variant == VARIANT_WITH_CLICK_CD_ON_ITEM_FOO_IMAGE;
        }

        @Override
        public int getItemCount() {
            return Character.values().length;
        }

    }

    private static final class ActionListItemViewHolder extends RecyclerView.ViewHolder {

        private final View iconView;
        private final TextView labelTextView;
        private final TextView sublabelTextView;
        private final ImageView actionImageView;

        static ActionListItemViewHolder inflate(LayoutInflater layoutInflater, ViewGroup root) {
            View view = layoutInflater.inflate(R.layout.view_action_list_item, root, false);
            View iconView = view.findViewById(R.id.action_list_item_view_icon);
            TextView labelTextView = (TextView) view.findViewById(R.id.action_list_item_text_label);
            TextView sublabelTextView = (TextView) view.findViewById(R.id.action_list_item_text_sublabel);
            ImageView actionImageView = (ImageView) view.findViewById(R.id.action_list_item_image_action);
            actionImageView.setBackgroundColor(Color.parseColor("#3DF2B1"));
            return new ActionListItemViewHolder(view, iconView, labelTextView, sublabelTextView, actionImageView);
        }

        private ActionListItemViewHolder(View itemView, View iconView, TextView labelTextView, TextView sublabelTextView, ImageView actionImageView) {
            super(itemView);
            this.iconView = iconView;
            this.labelTextView = labelTextView;
            this.sublabelTextView = sublabelTextView;
            this.actionImageView = actionImageView;
        }

        public void setIconColor(@ColorInt int color) {
            iconView.setBackgroundColor(color);
        }

        public void setLabel(String label) {
            labelTextView.setText(label);
        }

        public void setSublabel(String sublabel) {
            sublabelTextView.setText(sublabel);
        }

    }

}
