package com.ataulm.basic.activecheck;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.ataulm.basic.R;
import com.ataulm.basic.Story;

public class StoryActivity extends Activity {

    private AccessibilityDependentViewPropertyUpdater viewPropertyUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        View storyTextView = findViewById(R.id.story_text_short_1);
        View storyTextView2 = findViewById(R.id.story_text_short_2);
        TextView storyTextView3 = (TextView) findViewById(R.id.story_text_lorem_ipsum);
        storyTextView3.setText(Html.fromHtml(Story.LOREM_IPSUM));

        viewPropertyUpdater =
                AccessibilityDependentViewPropertyUpdater.newInstance(this, storyTextView, storyTextView2, storyTextView3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPropertyUpdater.updateFocusablePropertyForNonInteractiveElements();
    }

}
