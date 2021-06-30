package com.example.cryptotracker.presentation.coinComparison

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.cryptotracker.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ComparisonChartStyle @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun styleChart(lineChart: LineChart) = lineChart.apply {

        axisLeft.apply {
            isEnabled = true

            setDrawGridLines(false)
            setDrawAxisLine(false)
            typeface = ResourcesCompat.getFont(context, R.font.acrom)
            textColor = ContextCompat.getColor(context, R.color.grey_light)
        }

        axisRight.apply {
            isEnabled = true
            setDrawGridLines(false)
            setDrawAxisLine(false)
            typeface = ResourcesCompat.getFont(context, R.font.acrom)
            textColor = ContextCompat.getColor(context, R.color.dark_blue)
        }

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

    fun styleFirstLineDataSet(lineDataSet: LineDataSet) = lineDataSet.apply {
        color = ContextCompat.getColor(context, R.color.grey_light)
        valueTextColor = ContextCompat.getColor(context, R.color.grey_light)
        setDrawValues(false)
        lineWidth = 3f
        isHighlightEnabled = true
        setDrawHighlightIndicators(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER

        axisDependency = YAxis.AxisDependency.LEFT
    }

    fun styleSecondLineDataSet(lineDataSet: LineDataSet) = lineDataSet.apply {
        color = ContextCompat.getColor(context, R.color.light)
        valueTextColor = ContextCompat.getColor(context, R.color.light)
        setDrawValues(false)
        lineWidth = 3f
        isHighlightEnabled = true
        setDrawHighlightIndicators(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER

        axisDependency = YAxis.AxisDependency.RIGHT
    }


}