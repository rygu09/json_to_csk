package json_to_csk;

public class PointBean {
	
	private double eX,eY,sX,sY;
	private int pressure;
	private long t;
	public double geteX() {
		return eX;
	}
	public void seteX(double eX) {
		this.eX = eX;
	}
	public double geteY() {
		return eY;
	}
	public void seteY(double eY) {
		this.eY = eY;
	}
	public double getsX() {
		return sX;
	}
	public void setsX(double sX) {
		this.sX = sX;
	}
	public double getsY() {
		return sY;
	}
	public void setsY(double sY) {
		this.sY = sY;
	}
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public long getT() {
		return t;
	}
	public void setT(long t) {
		this.t = t;
	}	

}
