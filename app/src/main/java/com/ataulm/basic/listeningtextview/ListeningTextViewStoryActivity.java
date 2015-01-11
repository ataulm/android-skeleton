package com.ataulm.basic.listeningtextview;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.ataulm.basic.R;
import com.ataulm.basic.Story;

public class ListeningTextViewStoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_textview_story);

        TextView storyTextView3 = (TextView) findViewById(R.id.story_text_lorem_ipsum);
        storyTextView3.setText(Html.fromHtml(Story.LOREM_IPSUM));
    }

}
