package com.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NewsStory() }
    }

    @Composable
    fun NewsStory() {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth(),
                    asset = imageFromResource(resources, R.drawable.header)
            )
            Spacer(modifier = Modifier.preferredHeight(16.dp))
            Text(text = "A day in Shark Fin cove")
            Text(text = "Davenport California")
            Text(text = "December 2018")
        }
    }

    @Preview
    @Composable
    fun PreviewNewsStory() = NewsStory()
}
