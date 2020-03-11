package es.upm.dit.adsw.practica1;

public class SelectorVehiculoCompatible implements SelectorVehiculo {

	private Vector v;
	private double t;
	public SelectorVehiculoCompatible (Vector v, double t){
		this.v = v;
		this.t = t;
	}
	@Override
	public boolean seleccionar(Vehiculo v) {
		return ((v != null) && (v.isPosicionCompatible(this.v, this.t)));
	}

}
