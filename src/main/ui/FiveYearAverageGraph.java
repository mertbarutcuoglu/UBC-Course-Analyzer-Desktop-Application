package ui;

import model.Course;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FiveYearAverageGraph extends JPanel {
    private XYDataset dataset;

    public FiveYearAverageGraph(Course course) {
        createXYDatasetForAverages(course);
        createAveragePlot();
        this.setVisible(false);
    }

    // CREDITS: https://www.boraji.com/jfreechart-xy-line-chart-example
    // EFFECTS: creates an XYDataset from the five year average data of the given course
    private void createXYDatasetForAverages(Course course) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        List<Double> termAverages = course.getCourseAveragesForYears();
        XYSeries termAveragesSeries = new XYSeries("Average");

        int term = 0;
        for (Double average: termAverages) {
            if (!(average == 0)) {
                termAveragesSeries.add(term, average);
                term++;
            }
        }
        dataset.addSeries(termAveragesSeries);
        this.dataset = dataset;
    }

    // CREDITS: https://www.programcreek.com/java-api-examples/?api=org.jfree.chart.renderer.xy.XYSplineRenderer
    // EFFECTS: plots the given course yearly average data and returns it
    private void createAveragePlot() {
        XYSplineRenderer renderer = new XYSplineRenderer();
        NumberAxis yearAxis = new NumberAxis("nth Term Since 2014");
        yearAxis.getAutoRangeIncludesZero();
        NumberAxis averageAxis =  new NumberAxis("Average");
        averageAxis.setRange(50, 100);

        XYPlot plot = new XYPlot(dataset, yearAxis, averageAxis, renderer);
        String title = "Course Average Distribution";
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        ChartUtilities.applyCurrentTheme(chart);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setRangeZoomable(false);
        chartPanel.setSize(new Dimension(175, 150));
        add(chartPanel);
    }
}
