package com.example.cryptotracker.presentation.coinDetail

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.cryptotracker.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DetailChartStyle @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun styleChart(lineChart: LineChart, minPrice: Pair<Double, Double>) = lineChart.apply {
        axisRight.isEnabled = false

        axisLeft.apply {
            isEnabled = true
            axisMinimum = minPrice.first.toFloat()
            axisMaximum = minPrice.second.toFloat()

            typeface = ResourcesCompat.getFont(context, R.font.acrom)
            textColor = ContextCompat.getColor(context, R.color.grey_light)
        }

        xAxis.apply {
            axisMinimum = 0f
            isGranularityEnabled = true
            granularity = 4f
            setDrawGridLines(false)
            setDrawAxisLine(false)
            position = XAxis.XAxisPosition.BOTTOM

            typeface = ResourcesCompat.getFont(context, R.font.acrom)
            textColor = ContextCompat.getColor(context, R.color.dark)
        }

        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(false)
        setPinchZoom(false)

        description = null
        legend.isEnabled = false
    }

    fun styleLineDataSet(lineDataSet: LineDataSet) = lineDataSet.apply {
        color = ContextCompat.getColor(context, R.color.green)
        valueTextColor = ContextCompat.getColor(context, R.color.green)
        setDrawValues(false)
        lineWidth = 3f
        isHighlightEnabled = true
        setDrawHighlightIndicators(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER

        setDrawFilled(true)
        fillDrawable = ContextCompat.getDrawable(context, R.drawable.style_custom_chart_line_dataset)
    }


}