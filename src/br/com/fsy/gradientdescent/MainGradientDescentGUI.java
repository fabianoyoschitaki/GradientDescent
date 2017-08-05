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
		DATA.add(new XY(0.24,0.60));
		DATA.add(new XY(0.33,0.61));
		DATA.add(new XY(0.37,0.80));
		DATA.add(new XY(0.44,0.86));
		DATA.add(new XY(0.44,0.84));
		DATA.add(new XY(0.57,1.10));
		DATA.add(new XY(0.75,1.30));
		DATA.add(new XY(0.65,1.40));
		DATA.add(new XY(0.93,1.7));
		DATA.add(new XY(1.0,2.03));
	}
	
	private static final BigDecimal PACE = new BigDecimal(0.01).setScale(2, RoundingMode.HALF_DOWN);
	
	/** find best a,b using gradient descent **/
	private static BigDecimal A_INTERCEPT = new BigDecimal(0.15).setScale(XY.SCALE, XY.ROUNDING_MODE);
	private static BigDecimal B_SLOPE = new BigDecimal(-2.15).setScale(XY.SCALE, XY.ROUNDING_MODE);
	
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
        GradientDescent gd = new GradientDescent(A_INTERCEPT, B_SLOPE, DATA, PACE);
		
		final MainGradientDescentGUI gui = new MainGradientDescentGUI(gd);
        gui.pack();
        RefineryUtilities.centerFrameOnScreen(gui);
        gui.setVisible(true);
        
        gd.calculateGradientDescent(100);
		
        final MainGradientDescentGUI gui2 = new MainGradientDescentGUI(gd);
        gui2.pack();
        RefineryUtilities.centerFrameOnScreen(gui2);
        gui2.setVisible(true);
        
        gd.calculateGradientDescent(300);
		
        final MainGradientDescentGUI gui3 = new MainGradientDescentGUI(gd);
        gui3.pack();
        RefineryUtilities.centerFrameOnScreen(gui3);
        gui3.setVisible(true);
        
        gd.calculateGradientDescent(300);
		
        final MainGradientDescentGUI gui4 = new MainGradientDescentGUI(gd);
        gui4.pack();
        RefineryUtilities.centerFrameOnScreen(gui4);
        gui4.setVisible(true);
        
        
    }

    /**
     * Constructs the demo application.
     *
     * @param gd  the frame title.
     */
    public MainGradientDescentGUI(final GradientDescent gd) {
        super("Gradient Descent");
        XYDataset dataset = createSampleDataset(gd);
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Gradient Descent [" + gd.getCurrentIteration() + "]",
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
     * @param gd 
     * 
     * @return A dataset.
     */
    private XYDataset createSampleDataset(GradientDescent gd) {
        XYSeries predictedY = new XYSeries("Predicted Y");
        XYSeries actualY = new XYSeries("Actual Y");
        List<BigDecimal> xValues = gd.getInitialXValues();
        List<BigDecimal> yPred = gd.getPredictedY(xValues);
        List<BigDecimal> yActual = gd.getInitialYValues();
        for (int cont = 0; cont < xValues.size(); cont++){
        	predictedY.add(xValues.get(cont), yPred.get(cont));
        	System.out.println("pred: " + xValues.get(cont) + ", " + yPred.get(cont));
        	actualY.add(xValues.get(cont), yActual.get(cont));
        	System.out.println("actual: " + xValues.get(cont) + ", " + yActual.get(cont));
		}
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(predictedY);
        dataset.addSeries(actualY);
        return dataset;
    }
}
