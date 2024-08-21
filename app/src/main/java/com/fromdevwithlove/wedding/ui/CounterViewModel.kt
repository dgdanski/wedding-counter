package com.fromdevwithlove.wedding.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.Interval
import org.joda.time.Period
import org.joda.time.PeriodType
import java.util.Date
import java.util.Timer
import java.util.TimerTask

sealed interface CounterUiState {
    data class BeforeWedding(val duration: Period? = null) : CounterUiState
    data class AfterWedding(val duration: Period? = null) : CounterUiState
    object Calculating : CounterUiState
}

class CounterViewModel : ViewModel() {

    // Counter UI state
    var counterUiState: CounterUiState by mutableStateOf(CounterUiState.Calculating)
        private set

    init {
        countLastedTime()
    }

    private fun countLastedTime() {
        viewModelScope.launch(Dispatchers.IO) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (Date().time < 1721484000000L) {
                        val interval = Interval(Date().time, 1721484000000L)
                        val period: Period = interval.toPeriod(PeriodType.yearMonthDayTime())
                        counterUiState = CounterUiState.BeforeWedding(period)
                    } else {
                        val interval = Interval(1721484000000L, Date().time)
                        val period: Period = interval.toPeriod(PeriodType.yearMonthDayTime())
                        counterUiState = CounterUiState.AfterWedding(period)
                    }
                }
            }, 0, 1000)
        }
    }
}