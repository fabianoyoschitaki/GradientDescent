package br.com.fsy.gradientdescent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MainGradientDescent {
	
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
	
	public static void main(String[] args) {
		GradientDescent gd = new GradientDescent(A_INTERCEPT, B_SLOPE, DATA, PACE);
		gd.calculateGradientDescent(500);
	}
	
}
