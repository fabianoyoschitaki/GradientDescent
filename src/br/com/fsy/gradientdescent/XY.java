package br.com.fsy.gradientdescent;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class XY {
	
	public static int SCALE = 4;
	public static RoundingMode ROUNDING_MODE = RoundingMode.HALF_DOWN;
	private BigDecimal x;
	private BigDecimal y;
	
	public XY(BigDecimal x, BigDecimal y){
		this.x = x.setScale(SCALE, ROUNDING_MODE);
		this.y = y.setScale(SCALE, ROUNDING_MODE);
	}
	public XY(Double x, Double y) {
		this.x = new BigDecimal(x).setScale(SCALE, ROUNDING_MODE);
		this.y = new BigDecimal(y).setScale(SCALE, ROUNDING_MODE);
	}
	public BigDecimal getX() {
		return x;
	}
	public void setX(BigDecimal x) {
		this.x = x.setScale(SCALE, ROUNDING_MODE);
	}
	public BigDecimal getY() {
		return y;
	}
	public void setY(BigDecimal y) {
		this.y = y.setScale(SCALE, ROUNDING_MODE);
	}
	
	public static void setScale(int scale){
		SCALE = scale;
	}
	public static void setRoundingMode(RoundingMode roundingMode){
		ROUNDING_MODE = roundingMode;
	}
}
