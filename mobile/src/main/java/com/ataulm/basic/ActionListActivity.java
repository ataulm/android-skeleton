package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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

    private View characterView;
    private ImageView characterImageView;
    private TextView characterLabelTextView;
    private TextView characterSublabelTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Action List");
        setContentView(R.layout.activity_action_list);

        characterView = findViewById(R.id.character_view);
        characterImageView = (ImageView) characterView.findViewById(R.id.character_image);
        characterLabelTextView = (TextView) characterView.findViewById(R.id.character_text_label);
        characterSublabelTextView = (TextView) characterView.findViewById(R.id.character_text_sublabel);

        RecyclerView actionListView = (RecyclerView) findViewById(R.id.action_list_view);
        actionListView.setLayoutManager(new LinearLayoutManager(this));
        actionListView.addItemDecoration(SpacesItemDecoration.newInstance(4, 4, 1));
        actionListView.setAdapter(new ActionListAdapter(getLayoutInflater(), getIntent().getIntExtra(ExamplesActivity.EXTRA_VARIANT, VARIANT_NO_CLICK), this));
    }

    @Override
    public void onClick(Character character) {
        characterImageView.setBackgroundColor(character.getIconColor());
        characterLabelTextView.setText(character.getName());
        characterSublabelTextView.setText(character.getDescription());
        characterView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (characterView.getVisibility() == View.VISIBLE) {
            characterView.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    private static class ActionListAdapter extends RecyclerView.Adapter<ActionListItemViewHolder> {

        private final LayoutInflater layoutInflater;
        private final int variant;
        private final CharacterClickListener listener;

        ActionListAdapter(LayoutInflater layoutInflater, int variant, CharacterClickListener listener) {
            this.layoutInflater = layoutInflater;
            this.variant = variant;
            this.listener = listener;
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
            return variant == ActionListActivity.VARIANT_WITH_CLICK_FOO_IMAGE_NOT_IMPORTANT_IMAGE;
        }

        private boolean shouldSetFooContentDescription() {
            return variant == ActionListActivity.VARIANT_NO_CLICK_FOO_IMAGE | variant == ActionListActivity.VARIANT_WITH_CLICK_FOO_IMAGE | variant == ActionListActivity.VARIANT_WITH_CLICK_FOO_IMAGE_NOT_IMPORTANT_IMAGE;
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
                return;
            }
        }

        private boolean shouldHaveNoClickNorBeClickable() {
            return variant == VARIANT_NO_CLICK || variant == VARIANT_NO_CLICK_FOO_IMAGE;
        }

        private boolean shouldBeClickable() {
            return variant == VARIANT_CLICKABLE_TRUE;
        }

        private boolean shouldAddClickListener() {
            return variant == VARIANT_WITH_CLICK || variant == VARIANT_WITH_CLICK_FOO_IMAGE || variant == VARIANT_WITH_CLICK_FOO_IMAGE_NOT_IMPORTANT_IMAGE;
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

        static ActionListItemViewHolder inflate(LayoutInflater layoutInflater, ViewGroup root) {
            View view = layoutInflater.inflate(R.layout.view_action_list_item, root, false);
            View iconView = view.findViewById(R.id.action_list_item_view_icon);
            TextView labelTextView = (TextView) view.findViewById(R.id.action_list_item_text_label);
            TextView sublabelTextView = (TextView) view.findViewById(R.id.action_list_item_text_sublabel);
            return new ActionListItemViewHolder(view, iconView, labelTextView, sublabelTextView);
        }

        private ActionListItemViewHolder(View itemView, View iconView, TextView labelTextView, TextView sublabelTextView) {
            super(itemView);
            this.iconView = iconView;
            this.labelTextView = labelTextView;
            this.sublabelTextView = sublabelTextView;
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
