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

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

private const val COUNT_DOWN_MILLIS = 30 * 1000L

class MainViewModel : ViewModel() {

    private var countDownTimer: CountDownTimer? = null

    private var remindMillis = 0L

    var time by mutableStateOf(COUNT_DOWN_MILLIS.formatTime())

    var progress by mutableStateOf(1F)

    var playState by mutableStateOf(PlayState.STOP)

    private fun start() {
        val millisInFuture = when (playState) {
            PlayState.STOP -> COUNT_DOWN_MILLIS
            PlayState.PAUSE -> remindMillis
            else -> {
                return
            }
        }
        playState = PlayState.PLAYING
        countDownTimer = object : CountDownTimer(millisInFuture, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                remindMillis = millisUntilFinished
                progress = millisUntilFinished.toFloat() / COUNT_DOWN_MILLIS
                time = millisUntilFinished.formatTime()
            }

            override fun onFinish() {
                countDownTimer?.cancel()
            }
        }.start()
    }

    private fun pause() {
        playState = PlayState.PAUSE
        countDownTimer?.cancel()
    }

    fun stop() {
        remindMillis = 0
        playState = PlayState.STOP
        countDownTimer?.cancel()
        progress = 1f
        time = COUNT_DOWN_MILLIS.formatTime()
    }

    fun startOrPause() {
        if (playState == PlayState.PLAYING) {
            pause()
        } else {
            start()
        }
    }
}

enum class PlayState {
    PLAYING, PAUSE, STOP
}
