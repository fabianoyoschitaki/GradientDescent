package br.com.fsy.gradientdescent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GradientDescent {
	
	private static final BigDecimal TWO = new BigDecimal(2);
	private BigDecimal intercept;
	private BigDecimal slope;
	private BigDecimal pace;
	private int countIteration;
	private List<BigDecimal> xValues;
	private List<BigDecimal> actualY;
	
	public GradientDescent(BigDecimal intercept, BigDecimal slope,
		List<XY> data, BigDecimal pace) {
		if (data == null || data.size() == 0) {
			throw new IllegalArgumentException("Parameter data must be not null and not empty.");
		}
		this.intercept = intercept;
		this.slope = slope;
		this.pace = pace;
		this.countIteration = 0;
		this.xValues = getXValues(data);
		this.actualY = getYValues(data);
	}

	/**
	 * Find best a,b using gradient descent 
	 * 
	 * @param a
	 * @param b
	 * @param data
	 * @param pace
	 */
	public void calculateGradientDescent(int iterations) {
		iterations+= countIteration;
		while (countIteration < iterations){ 
			List<BigDecimal> predictedY = getPredictedY(xValues);
			
			/** Step 1: To fit a line Ypred = a + bX, start off with random values of a and b and calculate prediction error (SSE) **/
			BigDecimal predictionError = getPredictionError(actualY, predictedY);
			
			/** Step 2: Calculate the gradient i.e. change in SSE when the weights (a & b) are changed by a very small value from their 
			 * original randomly initialized value. This helps us move the values of a & b in the direction in which SSE is minimized.*/
			BigDecimal gradientA = getInterceptGradient(actualY, predictedY);
			BigDecimal gradientB = getSlopeGradient(actualY, predictedY, xValues);
			
			/** Step 3:Adjust the weights with the gradients to reach the optimal values where SSE is minimized **/
			BigDecimal newIntercept = getNewIntercept(gradientA);
			BigDecimal newSlope = getNewSlope(gradientB);
			
			System.out.println("[" + countIteration + "] A = [" + this.intercept + "], B = [" + this.slope + "] --> predictionError = [" + predictionError + "] Gradient (A,B) = (" + gradientA + "," + gradientB + "). New A = [" + newIntercept + "], New B = [" + newSlope + "]");
			countIteration++;
			this.intercept = newIntercept;
			this.slope = newSlope;
		}
	}

	/**
	 * Returns new weight with the B gradient to reach the optimal values where SSE is minimized
	 * 
	 * @param gradientB
	 * @return
	 */
	private BigDecimal getNewSlope(BigDecimal gradientB) {
		return this.slope.subtract(this.pace.multiply(gradientB)).setScale(XY.SCALE, XY.ROUNDING_MODE);
	}

	/**
	 * Returns new weight with the A gradient to reach the optimal values where SSE is minimized
	 * 
	 * @param gradientA
	 * @return
	 */
	private BigDecimal getNewIntercept(BigDecimal gradientA) {
		return this.intercept.subtract(this.pace.multiply(gradientA)).setScale(XY.SCALE, XY.ROUNDING_MODE);
	}

	/**
	 * Calculates Slope gradient = –(Y-YP)X
	 * 
	 * @param actualY
	 * @param predictedY
	 * @param xValues
	 * @return
	 */
	private BigDecimal getSlopeGradient(List<BigDecimal> actualY,
			List<BigDecimal> predictedY, List<BigDecimal> xValues) {
		BigDecimal slopeGradient = new BigDecimal(0);
		for (int cont = 0; cont < actualY.size(); cont++) {
			BigDecimal ig = actualY.get(cont).subtract(predictedY.get(cont)).multiply(xValues.get(cont)).negate().setScale(XY.SCALE, XY.ROUNDING_MODE);
			slopeGradient = slopeGradient.add(ig);
		}
		return slopeGradient;
	}

	/**
	 * Calculates Intercept gradient = –(Y-YP)
	 * 
	 * @param actualY
	 * @param predictedY
	 * @return
	 */
	private BigDecimal getInterceptGradient(List<BigDecimal> actualY,
			List<BigDecimal> predictedY) {
		BigDecimal interceptGradient = new BigDecimal(0);
		for (int cont = 0; cont < actualY.size(); cont++) {
			BigDecimal ig = actualY.get(cont).subtract(predictedY.get(cont)).negate().setScale(XY.SCALE, XY.ROUNDING_MODE);
			interceptGradient = interceptGradient.add(ig);
		}
		return interceptGradient;
	}

	/**
	 * Returns X values from XY List
	 * 
	 * @param data
	 * @return
	 */
	private List<BigDecimal> getXValues(List<XY> data) {
		List<BigDecimal> xValues = new ArrayList<BigDecimal>();
		for (XY xy : data) {
			xValues.add(xy.getX());
		}
		return xValues;
	}

	/**
	 * Returns Y values from XY List
	 * 
	 * @param data
	 * @return
	 */
	private List<BigDecimal> getYValues(List<XY> data) {
		List<BigDecimal> yValues = new ArrayList<BigDecimal>();
		for (XY xy : data) {
			yValues.add(xy.getY());
		}
		return yValues;
	}

	/**
	 * Calculates predicted Y output from X input
	 * 
	 * @param xValue
	 * @return the predicted Y value
	 */
	private BigDecimal getYPred(BigDecimal x) {
		return this.intercept.add(this.slope.multiply(x));
	}

	/**
	 * Returns predictedY List from x List
	 * 
	 * @param x
	 * @return
	 */
	public List<BigDecimal> getPredictedY(Collection<BigDecimal> x) {
		List<BigDecimal> predictedY = new ArrayList<BigDecimal>();
		for (BigDecimal currentX : x) {
			predictedY.add(getYPred(currentX));
		}
		return predictedY;
	}
	
	public List<BigDecimal> getInitialXValues(){
		return this.xValues;
	}
	
	public List<BigDecimal> getInitialYValues(){
		return this.actualY;
	}

	/**
	 * Returns the Sum of Squared Errors (SSE) = ½ Sum (Actual House Price –
	 * Predicted House Price)^2 = ½ Sum(Y – Ypred)^2
	 * 
	 * @param actualY
	 * @param predictedY
	 * @return
	 */
	public BigDecimal getPredictionError(List<BigDecimal> actualY, List<BigDecimal> predictedY) {
		BigDecimal sse = new BigDecimal(0);
		for (int cont = 0; cont < actualY.size(); cont++) {
			BigDecimal differenceSquared = actualY.get(cont)
					.subtract(predictedY.get(cont)).abs().pow(2).divide(TWO)
					.setScale(XY.SCALE, XY.ROUNDING_MODE);
			sse = sse.add(differenceSquared);
		}
		return sse;
	}

	public Integer getCurrentIteration() {
		return this.countIteration;
	}
}
