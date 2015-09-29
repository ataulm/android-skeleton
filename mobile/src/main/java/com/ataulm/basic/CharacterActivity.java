package com.ataulm.basic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CharacterActivity extends Activity {

    public static final String EXTRA_CHARACTER = "EXTRA_CHAR";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        Character character = (Character) getIntent().getSerializableExtra(EXTRA_CHARACTER);
        setTitle(character.getName());

        ImageView characterImageView = (ImageView) findViewById(R.id.character_image);
        TextView characterLabelTextView = (TextView) findViewById(R.id.character_text_label);
        TextView characterSublabelTextView = (TextView) findViewById(R.id.character_text_sublabel);

        characterImageView.setBackgroundColor(character.getIconColor());
        characterLabelTextView.setText(character.getName());
        characterSublabelTextView.setText(character.getDescription());
    }

}
