package org.vaadin.addon;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;

import java.awt.*;

@Route
public class DemoView extends VerticalLayout {

    public DemoView() {

        JFreeChart chart = createChart(createDataset());
        JFreeChartWrapper wrapper = new JFreeChartWrapper(chart);
        add(wrapper);

        add(getLevelChart());

        add(regressionChart());

    }

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private static CategoryDataset createDataset() {

        // row keys...
        String series1 = "First";
        String series2 = "Second";
        String series3 = "Third";

        // column keys...
        String category1 = "Category 1";
        String category2 = "Category 2";
        String category3 = "Category 3";
        String category4 = "Category 4";
        String category5 = "Category 5";

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, series1, category1);
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);

        dataset.addValue(5.0, series2, category1);
        dataset.addValue(7.0, series2, category2);
        dataset.addValue(6.0, series2, category3);
        dataset.addValue(8.0, series2, category4);
        dataset.addValue(4.0, series2, category5);

        dataset.addValue(4.0, series3, category1);
        dataset.addValue(3.0, series3, category2);
        dataset.addValue(2.0, series3, category3);
        dataset.addValue(3.0, series3, category4);
        dataset.addValue(6.0, series3, category5);

        return dataset;

    }

    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart("Bar Chart Demo 1", // chart
                // title
                "Category", // domain axis label
                "Value", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // ******************************************************************
        // More than 150 demo applications are included with the JFreeChart
        // Developer Guide...for more information, see:
        //
        // > http://www.object-refinery.com/jfreechart/guide.html
        //
        // ******************************************************************
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        // renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f,
                0.0f, new Color(0, 0, 64));
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f,
                0.0f, new Color(0, 64, 0));
        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,
                0.0f, new Color(64, 0, 0));
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions
                .createUpRotationLabelPositions(Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }

    private static Component getLevelChart() {

        DefaultTableXYDataset ds = new DefaultTableXYDataset();
        NumberAxis y = new NumberAxis("Y");

        XYSeries series;

        series = new XYSeries("BAR", false, false);
        series.add(0, 0);
        series.add(1, 56);
        series.add(2, 54);
        series.add(3, 40);
        series.add(4, 12);
        series.add(5, 5);
        series.add(6, 2);
        ds.addSeries(series);

        series = new XYSeries("FOO", false, false);
        series.add(0, 0);
        series.add(1, 66);
        series.add(2, 64);
        series.add(3, 50);
        series.add(4, 22);
        series.add(5, 15);
        series.add(6, 10);
        ds.addSeries(series);

        series = new XYSeries("SDF", false, false);
        series.add(0, 0);
        series.add(1, 76);
        series.add(2, 74);
        series.add(3, 60);
        series.add(4, 32);
        series.add(5, 25);
        series.add(6, 20);
        ds.addSeries(series);

        // Paint p = new Color(0, 0, 0, Color.OPAQUE);
        // r.setSeriesPaint(0, p);
        // BasicStroke s = new BasicStroke(2);
        // r.setSeriesStroke(0, s);
        DefaultTableXYDataset ds2 = new DefaultTableXYDataset();
        series = new XYSeries("DOO", false, false);
        series.add(1, 60);
        series.add(2, 64);
        series.add(3, 54);
        series.add(4, 30);
        series.add(5, 25);
        series.add(6, 15);
        ds2.addSeries(series);

        XYAreaRenderer r = new XYAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES);

        XYPlot plot2 = new XYPlot(ds2, new NumberAxis("X"), y,
                new XYLineAndShapeRenderer());

        plot2.setDataset(1, ds);

        plot2.setRenderer(1, r);

        JFreeChart c = new JFreeChart(plot2);

        return new JFreeChartWrapper(c);
    }

    private static Component regressionChart() {

        DefaultTableXYDataset ds = new DefaultTableXYDataset();

        XYSeries series;

        series = new XYSeries("BAR", false, false);
        series.add(1, 1);
        series.add(2, 4);
        series.add(3, 6);
        series.add(4, 9);
        series.add(5, 9);
        series.add(6, 11);
        ds.addSeries(series);

        JFreeChart scatterPlot = ChartFactory.createScatterPlot("Regression",
                "X", "Y", ds, PlotOrientation.HORIZONTAL, true, false, false);

        XYPlot plot = (XYPlot) scatterPlot.getPlot();

        double[] regression = Regression.getOLSRegression(ds, 0);

        // regression line points
        double v1 = regression[0] + regression[1] * 1;
        double v2 = regression[0] + regression[1] * 6;

        DefaultXYDataset ds2 = new DefaultXYDataset();
        ds2.addSeries("regline", new double[][]{new double[]{1, 6},
        new double[]{v1, v2}});
        plot.setDataset(1, ds2);
        plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));

        JFreeChart c = new JFreeChart(plot);

        return new JFreeChartWrapper(c);
    }

}
