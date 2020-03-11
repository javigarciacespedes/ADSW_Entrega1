package es.upm.dit.adsw.practica1;


/**
 * @author Nerea Encarnación Gonzalez-Cutre
 * @author Javier García Céspedes
 *
 */
public class Vehiculo {
	private String id;
	private Vector pos0;
	private double t0;
	private Vector pos;
	private double t;
	private DetectorVehiculos unDetector; 
	
	private static final double MINIMAL_DISTANCE = 2;
	private static final double SAFETY_DISTANCE = 20;
		
	/**
	 * Construye un nuevo vehiculo a partir de dos posiciones relativas suyas
	 * @param id identificador del vehiculo
	 * @param pos0 posicion inicial del vehiculo
	 * @param t0 instante de medida de la posicion inicial
	 * @param pos posicion actual del vehiculo
	 * @param t instante de medida de la posicion actual
	 */
	public Vehiculo (String id, Vector pos0, double t0, Vector pos, double t) {
		this.id = id;
		this.pos0 = pos0;
		this.t0 = t0;
		this.pos = pos;
		this.t = t;
		this.unDetector = null;
	}
	
	/**
	 * Devuelve el identificador
	 * @return identificador del vehiculo
	 */
	public String getId() {
		return id;
	}

	/**
	 * Actualiza el identificador
	 * @param id valor del nuevo identificador
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Devuelve la posicion 0 que se emplea para estimar velocidad y trayectoria
	 * @return vector p0
	 */
	public Vector getPos0() {
		return pos0;
	}

	/**
	 * Devuelve el instate de medida de pos0
	 * @return valor del instante de medida de pos0
	 */
	public double getT0() {
		return t0;
	}

	/**
	 * Devuelve la última posición registrada del vehiculo
	 * @return devuelve pos
	 */
	public Vector getPos() {
		return pos;
	}

	/**
	 * Devuelve el instante en el que se midio pos
	 * @return instante de la medida de pos
	 */
	public double getT() {
		return t;
	}

	/**
	 * Actualiza la posicion actual
	 * @param pos nueva posicion
	 */
	public void setPos(Vector pos) {
		this.pos = pos;
	}
	
	/**
	 * Actualiza el instante de medida de pos
	 * @param t nuevo instante
	 */
	public void setT(double t) {
		this.t = t;
	}
	
	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", pos0=" + pos0 + ", t0=" + t0 + ", pos=" + pos + ", t=" + t + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vehiculo))
			return false;
		Vehiculo other = (Vehiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/**
	 * Distancia entra la posicion actual de dos vehiculos
	 * @param v2 el tro vehiculo
	 * @return distancia calculada
	 */
	public double distancia (Vehiculo v2) {
		return this.pos.distancia(v2.getPos());
	}

	/**
	 * Nos devuelve el vector que representa la velocidad vectorial
	 * @return velocidad calculada
	 */
	public Vector getVelocidad() {
		if ((this.pos0 == null) || (t == t0))
			return new Vector (0.0, 0.0);
		return new Vector ((this.pos.getX() - this.pos0.getX()) / (t - t0)
				, (this.pos.getY() - this.pos0.getY()) / (t - t0));
	}
	
	/**
	 * Nos dice si nuestra velocidad es 0
	 * @return true si la velocidad es 0
	 */
	public boolean isParado() {
		return (this.getVelocidad().modulo() == 0);
	}

	/**
	 * Actualizamos nuestra posicion. pos0 pasara a ser la actual y pos la nueva
	 * @param pos nueva posicion
	 * @param t instante de medida de la nueva posicion
	 */
	public void mover (Vector pos, double t) {
		if (this.t == t && this.pos.equals(pos))
			return;
		this.pos0 = this.pos;
		this.t0 = this.t;
		this.pos = pos;
		this.t = t;
		
		if(this.unDetector != null) {
			this.unDetector.vehiculoSeMueve(t);
		}
	}


	/**
	 * Estima la posicion en la que nos encontraremos en el instante t.
	 * pos0 pasara a ser la actual y pos la estimacion
	 * @param t instante en el que calculamos la estimacion
	 */
	public void mover(double t){
		if (t == this.t)
			return;
		Vector nuevo = new Vector (this.pos.getX() + this.getVelocidad().getX()* (t - this.t),
				this.pos.getY() + this.getVelocidad().getY()* (t - this.t));
		this.mover(nuevo, t);
	}	
	
	/**
	 * Estima la posicion en la que nos encontraremos en el instante t, y si en ese instante estaremos
	 * en una posicion proxima a pos
	 * @param pos nueva posicion a la que queremos ver si nos aproximamos
	 * @param t tiempo en el queremos saber si nos encontraremos en pos
	 * @return true si nos encontraremos proximos a esa posicion
	 */
	public boolean isPosicionCompatible (Vector pos, double t) {
		Vehiculo v = new Vehiculo ("", this.getPos0(), this.getT0(), this.getPos(), this.getT());
		v.mover(t);
		return (v.getPos().distancia(pos)< MINIMAL_DISTANCE);
	}
	
	/**
	 * Calcula el tiempo que queda para que impactemos con otro vehiculo.
	 * Si este vehiculo y v2 va a impactar devuelve el tiempo que queda en
	 * funcion de la velocidad y la trayectoria de los vehiculos
	 * Si este vehiculo y el otro se estan separando devuelve un negativo que 
	 * es una estimacion de a que velocidad se separan
	 * Si los dos vehiculos se encuentran casi pegados devuelve 0 
	 * @param v2 El otro vehiculo con el que podriamos impactar
	 * @return tiempo al impacto o separacion
	 */
	public double impacto(Vehiculo v2){
		if (Math.abs(this.getPos().distancia(v2.getPos())) < MINIMAL_DISTANCE)
			return 0;
		double xdif = v2.getPos().getX() - this.getPos().getX();
		double ydif = v2.getPos().getY() - this.getPos().getY();
		double vxdif = this.getVelocidad().getX() - v2.getVelocidad().getX();
		double vydif = this.getVelocidad().getY() - v2.getVelocidad().getY();

		if (new Vector(vxdif, vydif).modulo() < MINIMAL_DISTANCE)
			return Double.POSITIVE_INFINITY;
		
		if (xdif == 0 && vxdif != 0)
			return Double.POSITIVE_INFINITY;
		if (xdif != 0 && vxdif == 0)
			return Double.POSITIVE_INFINITY;
		if (ydif == 0 && vydif != 0)
			return Double.POSITIVE_INFINITY;
		if (ydif != 0 && vydif == 0)
			return Double.POSITIVE_INFINITY;

		if (vxdif == 0)
			return (ydif / vydif);
		if (vydif == 0)
			return (xdif / vxdif);
		if ((xdif / vxdif) == (ydif / vydif))
			return xdif / vxdif;
		return Double.POSITIVE_INFINITY;
	}

	/**
	 * Devuelve true si el vehiculo v2 no guarda una 
	 * distancia minima respeto de este vehiculo o si no se 
	 * guarda la distancia de seguridad en funcion de la velocidad
	 * @param v2 vehiculo que es peligroso respector de este
	 * @return devuelve true si v2 es peligroso
	 */
	public boolean isPeligroso (Vehiculo v2) {
		return (this.getPos().distancia(v2.getPos()) < MINIMAL_DISTANCE || Math.abs(this.impacto(v2)) * this.getVelocidad().modulo() < SAFETY_DISTANCE);
	}

	/**
	 * Actualiza el valor de la medida de posicion pos0
	 * @param pos2 nuevo valor de la posicion
	 */
	public void setPos0(Vector pos2) {
		pos0=pos2;
	}

	/**
	 * Actualiza el instante en el que se ha medido pos0
	 * @param t2 nuevo valor del instante de medida de pos0 
	 */
	public void setT0(double t2) {
		t0=t2;
	}
	
		public DetectorVehiculos getDetector() {
		return unDetector;
	}
	public void setDetector(DetectorVehiculos unDetector) {
		this.unDetector = unDetector;
	}
}