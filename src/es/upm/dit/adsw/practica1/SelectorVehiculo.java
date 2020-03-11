package es.upm.dit.adsw.practica1;

public interface SelectorVehiculo {
	
	/**
	 * Operador de seleccion que comprueba si un vehiculo cumple una condicion
	 * @param v vehiculo a comprobar si cumple la condicion
	 * @return true si se cumple la condicion
	 */
	public boolean seleccionar (Vehiculo v);
}
