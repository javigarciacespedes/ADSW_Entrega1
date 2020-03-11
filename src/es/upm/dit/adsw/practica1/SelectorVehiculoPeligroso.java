package es.upm.dit.adsw.practica1;

public class SelectorVehiculoPeligroso implements SelectorVehiculo {
	private Vehiculo v;
	
	public SelectorVehiculoPeligroso (Vehiculo v) {
		this.v = v;
	}
	public boolean seleccionar(Vehiculo v) {
		return ((v != null) & (this.v.isPeligroso(v)));
	}
}
