package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Locale;

public class MyActivity extends AppCompatActivity {

    private static final int TOAST_TWEET_EXCERPT_LENGTH = 15;
    private Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ((TweetSummaryView) findViewById(R.id.many_actions))
                .display(
                        new Tweet() {

                            @Override
                            public String getAuthor() {
                                return "Mr Fox";
                            }

                            @Override
                            public String getText() {
                                return "Entiendo lo que dices y sus comentarios son valiosos. pero voy a ignorar su consejo.";
                            }

                        },
                        new TweetSummaryView.ActionListener() {

                            @Override
                            public void onClick(Tweet tweet) {
                                displayAction("click", tweet.getText());
                            }

                            @Override
                            public void onClickReplyTo(Tweet tweet) {
                                displayAction("click reply to", tweet.getText());
                            }

                            @Override
                            public void onClickRetweet(Tweet tweet) {
                                displayAction("click retweet", tweet.getText());
                            }

                            @Override
                            public void onClickLike(Tweet tweet) {
                                displayAction("click like", tweet.getText());
                            }

                        }
                );

    }

    private void displayAction(String action, String quote) {
        String abbreviatedQuote = quote.length() > TOAST_TWEET_EXCERPT_LENGTH ? quote.substring(0, TOAST_TWEET_EXCERPT_LENGTH) : quote;
        display(action.toUpperCase(Locale.UK) + ": \"" + abbreviatedQuote + "...\"");
    }

    private void display(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
