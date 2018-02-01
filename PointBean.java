package json_to_csk;

public class PointBean {
	
	private float eX,eY;
	private int pressure;
	private long t;

	
	public float geteX() {
		return eX;
	}
	public void seteX(float eX) {
		this.eX = eX;
	}
	public float geteY() {
		return eY;
	}
	public void seteY(float eY) {
		this.eY = eY;
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
	public void setT(int t) {
		this.t = t;
	}	

}
