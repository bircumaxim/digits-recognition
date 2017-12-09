package utils

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYAreaRenderer
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DatasetChangeEvent
import org.jfree.ui.ApplicationFrame
import org.jfree.ui.RefineryUtilities
import javax.swing.JFrame

class LineChart : ApplicationFrame {
    private var chart: JFreeChart? = null
    private var dataSet: DefaultCategoryDataset? = null

    constructor(title: String, xLabel: String, yLabel: String) : super(title) {
        dataSet = DefaultCategoryDataset()
        chart!!.getXYPlot().setRenderer(XYAreaRenderer())
        chart = ChartFactory.createLineChart(title, xLabel, yLabel, dataSet, PlotOrientation.VERTICAL, true, true, false)
        setupChartWindow()
    }

    constructor() : super("") {
        dataSet = DefaultCategoryDataset()
        chart = ChartFactory.createLineChart("",
                "",
                "",
                dataSet, PlotOrientation.VERTICAL, false, true, false)
        setupChartWindow()
    }

    constructor(title: String) : super(title) {
        dataSet = DefaultCategoryDataset()
        chart = ChartFactory.createLineChart(title,
                "",
                "",
                dataSet, PlotOrientation.VERTICAL, false, true, false)
        setupChartWindow()
    }

    private fun setupChartWindow() {
        val chartPanel = ChartPanel(chart)
        chartPanel.setPreferredSize(java.awt.Dimension(1024, 768))
        setContentPane(chartPanel)

        this.pack()
        RefineryUtilities.centerFrameOnScreen(this)
        this.setVisible(false)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setLocationRelativeTo(null)
    }

    fun addNewValue(number: Number, columnKey: Comparable<*>) {
        addNewValue(number, "", columnKey)
    }

    fun addNewValue(number: Number, rowKey: Comparable<*>, columnKey: Comparable<*>) {
        if (!this.isVisible()) {
            this.setVisible(true)
        }
        dataSet!!.addValue(number, rowKey, columnKey)
        chart!!.getPlot().datasetChanged(DatasetChangeEvent(this, dataSet))
    }
}