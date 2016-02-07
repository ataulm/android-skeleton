package com.ataulm.basic;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TweetSummaryView extends LinearLayout {

    private TextView authorTextView;
    private TextView contentTextView;
    private View replyButton;
    private View retweetButton;
    private View likeButton;

    public TweetSummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
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

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: actions.onClick(tweet)
                showActionsDialog(tweet, actions);
            }
        });
    }

    enum Action {

        OPEN_DETAIL("Open detail"),
        REPLY("Reply"),
        RETWEET("Re-tweet"),
        LIKE("Like");

        private final String label;

        Action(String label) {
            this.label = label;
        }

        public static CharSequence[] labels() {
            CharSequence[] labels = new CharSequence[values().length];
            for (int i = 0; i < values().length; i++) {
                labels[i] = values()[i].label;
            }
            return labels;
        }

        public static Action atPosition(int actionId) {
            return values()[actionId];
        }

    }

    private void showActionsDialog(final Tweet tweet, final ActionListener actions) {
        new AlertDialog.Builder(getContext())
                .setTitle("Tweet options")
                .setItems(Action.labels(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (Action.atPosition(item)) {
                            case OPEN_DETAIL:
                                actions.onClick(tweet);
                                break;
                            case REPLY:
                                actions.onClickReplyTo(tweet);
                                break;
                            case RETWEET:
                                actions.onClickRetweet(tweet);
                                break;
                            case LIKE:
                                actions.onClickLike(tweet);
                                break;
                            default:
                                throw new RuntimeException("Unknown Action item: " + Action.atPosition(item));
                        }
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public interface ActionListener {

        void onClick(Tweet tweet);

        void onClickReplyTo(Tweet tweet);

        void onClickRetweet(Tweet tweet);

        void onClickLike(Tweet tweet);

    }

}
