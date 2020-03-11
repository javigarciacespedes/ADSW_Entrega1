/**
 * 
 */
package es.upm.dit.adsw.practica1;

/**
 * @author juancarlosduenaslopez3
 *
 */
public class Vector {
	private double x;
	private double y;
	
	/**
	 * Construye un vector a partir de sus coordenadas
	 * @param x coordenada x
	 * @param y coordenada y
	 */
	public Vector (double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Actualiza la coordenada x
	 * @param x nuevo valor de la coordenada x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return valor de la coordenada x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Actualiza el valor de la coordenada y
	 * @param y nuevo valor de la coordenada y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return valor de la coordenada y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * @return devuelve el modelu del vector
	 */
	public double modulo() {
		return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
	}
	
	/**
	 * @return devuelve el anggulo del vector respecto del orgigen con el eje x
	 */
	public double angulo() {
		return Math.atan2 (this.getY(), this.getX());
	}

	/**
	 * Cacula la distancia entre los dos puntos que representa el vector
	 * @param pos punto respecto del que se mide la distancia
	 * @return
	 */
	public double distancia (Vector pos) {
		Vector v = new Vector (this.getX() - pos.getX(), this.getY() - pos.getY());
		return v.modulo();
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
}
