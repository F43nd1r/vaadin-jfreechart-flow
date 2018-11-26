package org.vaadin.addon;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceWriter;
import org.apache.batik.svggen.SVGGraphics2D;
import org.jfree.chart.JFreeChart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author lukas
 * @since 11.10.18
 */
@Tag("object")
public class JFreeChartWrapper extends Component implements HasSize, HasStyle {

    // 809x 500 ~golden ratio
    private static final int DEFAULT_WIDTH = 809;
    private static final int DEFAULT_HEIGHT = 500;

    private JFreeChart chart;
    private int graphWidthInPixels = -1;
    private int graphHeightInPixels = -1;
    private String aspectRatio = "none";

    public JFreeChartWrapper() {
        getElement().setAttribute("type", "image/svg+xml");
    }

    public JFreeChartWrapper(JFreeChart chartToBeWrapped) {
        this();
        setChart(chartToBeWrapped);
    }

    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
        getElement().setAttribute("data", getStreamResource());
    }

    /**
     * This method may be used to tune rendering of the chart when using
     * relative sizes. Most commonly you should use just use common methods
     * inherited from {@link HasSize} interface.
     * <p>
     * Sets the pixel size of the area where the graph is rendered. Most
     * commonly developer may need to fine tune the value when the
     * {@link JFreeChartWrapper} has a relative size.
     *
     * @see JFreeChartWrapper#getGraphWidth()
     * @see #setSvgAspectRatio(String)
     */
    public void setGraphWidth(int width) {
        graphWidthInPixels = width;
    }

    /**
     * This method may be used to tune rendering of the chart when using
     * relative sizes. Most commonly you should use just use common methods
     * inherited from {@link HasSize} interface.
     * <p>
     * Sets the pixel size of the area where the graph is rendered. Most
     * commonly developer may need to fine tune the value when the
     * {@link JFreeChartWrapper} has a relative size.
     *
     * @see JFreeChartWrapper#getGraphHeight()
     * @see #setSvgAspectRatio(String)
     */
    public void setGraphHeight(int height) {
        graphHeightInPixels = height;
    }

    /**
     * Gets the pixel width into which the graph is rendered. Unless explicitly
     * set, the value is derived from the components size, except when the
     * component has relative size.
     */
    public int getGraphWidth() {
        if (graphWidthInPixels > 0) {
            return graphWidthInPixels;
        }
        return DEFAULT_WIDTH;
    }

    /**
     * Gets the pixel height into which the graph is rendered. Unless explicitly
     * set, the value is derived from the components size, except when the
     * component has relative size.
     */
    public int getGraphHeight() {
        if (graphHeightInPixels > 0) {
            return graphHeightInPixels;
        }
        return DEFAULT_HEIGHT;
    }

    public String getSvgAspectRatio() {
        return aspectRatio;
    }

    /**
     * See SVG spec from W3 for more information.Default is "none" (stretch),
     * another common value is "xMidYMid" (stretch proportionally, align middle
     * of the area).
     *
     * @param svgAspectRatioSetting the aspect ratio setting
     */
    public void setSvgAspectRatio(String svgAspectRatioSetting) {
        aspectRatio = svgAspectRatioSetting;
    }

    private StreamResource getStreamResource() {
        return new StreamResource("chart.svg", (StreamResourceWriter) (stream, session) -> {
            if (chart != null) {
                session.lock();
                try (Writer writer = new OutputStreamWriter(stream)) {
                    int width = getGraphWidth();
                    int height = getGraphHeight();
                    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder;
                    try {
                        docBuilder = docBuilderFactory.newDocumentBuilder();
                    } catch (ParserConfigurationException e1) {
                        throw new RuntimeException(e1);
                    }
                    Document document = docBuilder.newDocument();
                    Element svgelem = document.createElement("svg");
                    document.appendChild(svgelem);
                    // Create an instance of the SVG Generator
                    SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

                    // draw the chart in the SVG generator
                    chart.draw(svgGenerator, new Rectangle(width, height));
                    Element el = svgGenerator.getRoot();
                    el.setAttributeNS(null, "viewBox", "0 0 " + width + " " + height + "");
                    el.setAttributeNS(null, "style", "width:100%;height:100%;");
                    el.setAttributeNS(null, "preserveAspectRatio", getSvgAspectRatio());
                    /*
                     * don't use css, FF3 can'd deal with the result perfectly: wrong font sizes
                     */
                    boolean useCSS = false;
                    svgGenerator.stream(el, writer, useCSS, false);
                } finally {
                    session.unlock();
                }
            }
        });
    }

}
