package com.ataulm.basic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class TweetSummaryView extends LinearLayout {

    private TextView authorTextView;
    private TextView contentTextView;
    private View replyButton;
    private View retweetButton;
    private View likeButton;

    public TweetSummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setFocusable(true);
        setBackgroundResource(android.R.color.holo_red_light);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_tweet_summary, this);
        authorTextView = ((TextView) findViewById(R.id.tweet_summary_text_author));
        contentTextView = ((TextView) findViewById(R.id.tweet_summary_text_data));
        replyButton = findViewById(R.id.tweet_summary_button_reply);
        retweetButton = findViewById(R.id.tweet_summary_button_retweet);
        likeButton = findViewById(R.id.tweet_summary_button_like);
    }

    public void display(final Tweet tweet, final ActionListener actions) {
        authorTextView.setText(tweet.getAuthor());
        contentTextView.setText(tweet.getText());

        replyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actions.onClickReplyTo(tweet);
            }
        });

        retweetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actions.onClickRetweet(tweet);
            }
        });

        likeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actions.onClickLike(tweet);
            }
        });

        final ActionsProvider actionsProvider = createNewActionsProviderFor(tweet, actions);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: don't kill `actions.onClick(tweet)` for non-talkback
                showActionsDialog(actionsProvider);
            }
        });

        ViewCompat.setAccessibilityDelegate(this, new ActionsAccessibilityDelegate(getResources(), actionsProvider));
    }

    private ActionsProvider createNewActionsProviderFor(final Tweet tweet, final ActionListener actionListener) {
        return new ActionsProvider(
                Arrays.asList(
                        new ActionItem(R.id.tweet_summary_action_open_detail, R.string.tweet_summary_action_open_detail, new Runnable() {
                            @Override
                            public void run() {
                                actionListener.onClick(tweet);
                            }
                        }),
                        new ActionItem(R.id.tweet_summary_action_reply, R.string.tweet_summary_action_reply, new Runnable() {
                            @Override
                            public void run() {
                                actionListener.onClickReplyTo(tweet);
                            }
                        }),
                        new ActionItem(R.id.tweet_summary_action_retweet, R.string.tweet_summary_action_retweet, new Runnable() {
                            @Override
                            public void run() {
                                actionListener.onClickRetweet(tweet);
                            }
                        }),
                        new ActionItem(R.id.tweet_summary_action_like, R.string.tweet_summary_action_like, new Runnable() {
                            @Override
                            public void run() {
                                actionListener.onClickLike(tweet);
                            }
                        })
                )
        );
    }

    private static class ActionsProvider {

        private final List<ActionItem> actions;

        ActionsProvider(List<ActionItem> actions) {
            this.actions = actions;
        }

        public int getCount() {
            return actions.size();
        }

        public ActionItem getActionItem(int position) {
            return actions.get(position);
        }

        @Nullable
        public ActionItem getActionItemWithId(@IdRes int id) {
            for (ActionItem actionItem : actions) {
                if (actionItem.getId() == id) {
                    return actionItem;
                }
            }
            return null;
        }

    }

    private static class ActionItem {

        @IdRes
        private final int id;

        @StringRes
        private final int labelRes;

        private final Runnable action;

        ActionItem(int id, int labelRes, Runnable action) {
            this.id = id;
            this.labelRes = labelRes;
            this.action = action;
        }

        @IdRes
        public int getId() {
            return id;
        }

        @StringRes
        public int getLabelRes() {
            return labelRes;
        }

        public void doAction() {
            action.run();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ActionItem action = (ActionItem) o;

            if (id != action.id) {
                return false;
            }
            if (labelRes != action.labelRes) {
                return false;
            }
            return this.action.equals(action.action);

        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + labelRes;
            result = 31 * result + action.hashCode();
            return result;
        }

    }

    private static class ActionsAccessibilityDelegate extends AccessibilityDelegateCompat {

        private final Resources resources;
        private final ActionsProvider actionsProvider;

        public ActionsAccessibilityDelegate(Resources resources, ActionsProvider actionsProvider) {
            this.resources = resources;
            this.actionsProvider = actionsProvider;
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            for (int i = 0; i < actionsProvider.getCount(); i++) {
                ActionItem actionItem = actionsProvider.getActionItem(i);
                String label = resources.getString(actionItem.getLabelRes());
                info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(actionItem.getId(), label));
            }
        }

        @Override
        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            ActionItem actionItemWithId = actionsProvider.getActionItemWithId(action);
            if (actionItemWithId == null) {
                return super.performAccessibilityAction(host, action, args);
            } else {
                actionItemWithId.doAction();
                return true;
            }
        }

    }

    private void showActionsDialog(ActionsProvider actionsProvider) {
        CharSequence[] itemLabels = new CharSequence[actionsProvider.getCount()];
        for (int i = 0; i < actionsProvider.getCount(); i++) {
            itemLabels[i] = getResources().getString(actionsProvider.getActionItem(i).getLabelRes());
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Tweet options")
                .setItems(itemLabels, new ActionItemClickListener(actionsProvider))
                .create()
                .show();
    }

    private static class ActionItemClickListener implements DialogInterface.OnClickListener {

        private final ActionsProvider actionProvider;

        ActionItemClickListener(ActionsProvider actionsProvider) {
            this.actionProvider = actionsProvider;
        }

        @Override
        public void onClick(DialogInterface dialog, int item) {
            actionProvider.getActionItem(item).doAction();
            dialog.dismiss();
        }

    }

    public interface ActionListener {

        void onClick(Tweet tweet);

        void onClickReplyTo(Tweet tweet);

        void onClickRetweet(Tweet tweet);

        void onClickLike(Tweet tweet);

    }

}
