/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.grey1
import com.example.androiddevchallenge.ui.theme.white
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme(theme = MyTheme.Theme.Dark) {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Column {
        TopBar()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            val viewModel: MainViewModel = viewModel()
            var progress by remember { mutableStateOf(1f) }
            val animatedProgress by animateFloatAsState(
                targetValue = viewModel.progress,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
            )
            CircularProgressIndicator(
                progress = 1f,
                modifier = Modifier
                    .size(200.dp),
                color = MyTheme.colors.filter,
                strokeWidth = 8.dp
            )

            CircularProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier
                    .size(200.dp),
                color = MyTheme.colors.primary,
                strokeWidth = 8.dp
            )
            Text(
                text = viewModel.time,
                fontSize = 45.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                color = MyTheme.colors.primary
            )
        }

        BottomButtons()
    }
}

@Composable
private fun BottomButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val viewModel: MainViewModel = viewModel()
        MyButton(
            if (viewModel.playState == PlayState.PLAYING) "PAUSE" else "PLAY"
        ) {
            viewModel.startOrPause()
        }
        MyButton("RESET") {
            viewModel.stop()
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Composable
fun TopBar(text: String = "Count Down") {
    Row(
        Modifier
            .background(color = MyTheme.colors.primary)
            .height(55.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = text,
            color = white,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 50.dp)
        )
    }
}

@Preview
@Composable
fun MyButton(text: String = "", onClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .width(100.dp),
        colors = ButtonDefaults.buttonColors(
            MyTheme.colors.accent,
            MyTheme.colors.welcomeText
        ),
        shape = MaterialTheme.shapes.large,
        onClick = onClick
    ) {
        Text(
            text = text,
            color = white
        )
    }
}


