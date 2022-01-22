package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.AndroidskeletonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my)
        setContent {
            AndroidskeletonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Screen()
                }
            }
        }
    }
}

@Composable
fun Screen() {
    val text = remember { mutableStateOf("") }
    val numbers = IntRange(1, 100).toList()
    LazyColumn {
        item {
            TextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                },
                placeholder = {
                    Text("Type here")
                }
            )
        }
        val filtered = when (text.value) {
            "" -> numbers
            "fizz" -> numbers.fizz()
            "buzz" -> numbers.buzz()
            "fizzbuzz" -> numbers.fizzbuzz()
            else -> emptyList()
        }
        if (filtered.isNotEmpty()) {
            items(filtered) {
                Text("Number $it")
            }
        } else {
            item {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillParentMaxHeight()
                ) {
                    Text("Nope!")
                    Text("You gotta type fizz, buzz or fizzbuzz!")
                }
            }
        }
    }
}

private fun List<Int>.fizz(): List<Int> {
    return filter { it % 3 == 0 }
}

private fun List<Int>.buzz(): List<Int> {
    return filter { it % 5 == 0 }
}

private fun List<Int>.fizzbuzz(): List<Int> {
    return filter { it % 3 == 0 || it % 5 == 0 }
}
