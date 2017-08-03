package br.com.fsy.gradientdescent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class MainGradientDescentGUI extends ApplicationFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static List<XY> DATA = new ArrayList<XY>();
	static {
		DATA.add(new XY(0.0, 0.0));
		DATA.add(new XY(0.22,0.22));
		DATA.add(new XY(0.24,0.58));
		DATA.add(new XY(0.33,0.20));
		DATA.add(new XY(0.37,0.55));
		DATA.add(new XY(0.44,0.39));
		DATA.add(new XY(0.44,0.54));
		DATA.add(new XY(0.57,0.53));
		DATA.add(new XY(0.93,1.0));
		DATA.add(new XY(1.0,0.61));
	}
	
	private static final BigDecimal PACE = new BigDecimal(0.01).setScale(2, RoundingMode.HALF_DOWN);
	
	/** find best a,b using gradient descent **/
	private static BigDecimal A_INTERCEPT = new BigDecimal(0.45).setScale(XY.SCALE, XY.ROUNDING_MODE);
	private static BigDecimal B_SLOPE = new BigDecimal(0.75).setScale(XY.SCALE, XY.ROUNDING_MODE);
	
	// ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final MainGradientDescentGUI demo = new MainGradientDescentGUI("MainGradientDescentGUI Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        GradientDescent gd = new GradientDescent(A_INTERCEPT, B_SLOPE, DATA, PACE);
		gd.startGradientDescent(500);
    }

    /**
     * Constructs the demo application.
     *
     * @param title  the frame title.
     */
    public MainGradientDescentGUI(final String title) {
        super(title);
        XYDataset dataset = createSampleDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "X",
            "Y",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            false,
            false
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);        
        plot.setRenderer(renderer);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
        setContentPane(chartPanel);

    }
    
    /**
     * Creates a sample dataset.
     * 
     * @return A dataset.
     */
    private XYDataset createSampleDataset() {
        XYSeries predictedY = new XYSeries("Predicted Y");
        predictedY.add(1.0, 3.3);
        predictedY.add(2.0, 4.4);
        predictedY.add(3.0, 1.7);
        
        XYSeries actualY = new XYSeries("Actual Y");
        actualY.add(1.0, 7.3);
        actualY.add(2.0, 6.8);
        actualY.add(3.0, 9.6);
        actualY.add(4.0, 5.6);
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(predictedY);
        dataset.addSeries(actualY);
        return dataset;
    }
}
