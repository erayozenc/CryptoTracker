package com.example.cryptotracker.presentation.coinMarkets

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.cryptotracker.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SparklineChartStyle @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun styleChart(lineChart: LineChart) = lineChart.apply {
        axisRight.isEnabled = false
        axisLeft.isEnabled = false


        xAxis.apply {
            axisMinimum = 0f
            isGranularityEnabled = true
            granularity = 4f
            setDrawGridLines(false)
            setDrawAxisLine(false)
            position = XAxis.XAxisPosition.BOTTOM

            typeface = ResourcesCompat.getFont(context, R.font.acrom)
            textColor = ContextCompat.getColor(context, R.color.black)
        }

        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(false)
        setPinchZoom(false)

        description = null
        legend.isEnabled = false
    }

    fun styleLineDataSet(lineDataSet: LineDataSet, resColor: Int) = lineDataSet.apply {
        color = ContextCompat.getColor(context, resColor)
        valueTextColor = ContextCompat.getColor(context, resColor)
        setDrawValues(false)
        lineWidth = 3f
        isHighlightEnabled = true
        setDrawHighlightIndicators(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER

    }


}