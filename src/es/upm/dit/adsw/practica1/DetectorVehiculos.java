package es.upm.dit.adsw.practica1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Nerea Encarnación Gonzalez-Cutre
 * @author Javier García Céspedes
 *
 */
public class DetectorVehiculos {

	private Vehiculo v;
	private List<Vehiculo> detectados;
	private int n;
	
	/**
	 * Construye un DetectorVehiculos para el vehiculo v 
	 * @param v vehiculo en el que se encuentra el detector
	 */
	public DetectorVehiculos(Vehiculo v) {
		this.v = v;
		this.detectados = new ArrayList<Vehiculo> (); 
		this.n = 0;
		v.setDetector(this);
	}

	/**
	 * Devuelve el vehiculo respecto del que se hacen las detecciones
	 * @return vehiculo del detector
	 */
	public Vehiculo getV() {
		return this.v;
	}
		
	@Override
	public String toString() {
		return "DetectorVehiculos [v=" + v + ", detectados=" + detectados + "]";
	}
	
	/**
	 * Incluye un vehiculo como detectado
	 * @param v nuevo vehiculo detectado
	 */
	public void addVehiculo (Vehiculo v) {
		this.detectados.add(v);
	}

	/**
	 * Busca un vehiculo entre los detectados, teniedo en cuenta que la 
	 * busqueda se hace a partir de los identificadores
	 * @param veh vehiculo a buscar
	 * @return datos que tenemos del vehiculo encontrado. Null si no está entre los detectados
	 */
	public Vehiculo buscaVehiculo(Vehiculo veh) {
		if (this.detectados.contains(veh))
			return this.detectados.get(this.detectados.indexOf(veh));
		return null;
	}

	
	/**
	 * Obtiene el conjunto de vehiculos detectados que cumplen el selector
	 * @param s selector de filtrado de vehiculos
	 * @return conjunto de detectados que cumplen la seleccion
	 */
	public List<Vehiculo> getVehiculos (SelectorVehiculo s) {
		ArrayList<Vehiculo> vs = new ArrayList<Vehiculo> ();
		for (Vehiculo v : this.detectados) {
			if (s.seleccionar(v))
				vs.add(v);
		}
		return vs;
	}
	
	/**
	 * Anade una nueva deteccion comprobado si estaba anteriormente detectado. Para
	 * ver si estaba anteriormente detectado, filtra los detectados para ver si tienen una
	 * posicion compatible con v, si encuentra alguno, actualiza su posicion, y si no
	 * lo anade como nueva deteccion
	 * @param v posicion del vehiculo detectado
	 * @param t instante de la deteccion
	 */
	public void addDeteccion(Vector v, double t) {
		SelectorVehiculo s = new SelectorVehiculoCompatible(v, t);
		List<Vehiculo> compatibles = this.getVehiculos(s);
		if (compatibles.size() > 0) {
			compatibles.get(0).mover(v, t);
		}
		else {
			Vehiculo veh = new Vehiculo("AUTO" + this.n, v, t, v, t);
			this.n ++;
			this.addVehiculo(veh);
		}
	}
	
	private enum Orden {Antes,Igual,Despues};
	// Nos dice si v1 va a impactar con nuestro vehiculo antes, igual o despues que v2
	private Orden impactoRelativoAEsteVehiculo(Vehiculo v1, Vehiculo v2) {
		double t1=v.impacto(v1);
		double t2=v.impacto(v2);
		if (t1 == t2)
			return Orden.Igual;
		if (t1 == Double.POSITIVE_INFINITY)
			return Orden.Despues;
		if (t2 == Double.POSITIVE_INFINITY)
			return Orden.Antes;
		if (t1 > 0.0)
			if (t2 > 0.0)
				if (t1 < t2)
					return Orden.Antes;
				else
					return Orden.Despues;
			else
				return Orden.Antes;
		else
			if (t2 < 0.0)
				if (t1 < t2)
					return Orden.Antes;
				else
					return Orden.Despues;
			else
				return Orden.Despues;
	}
	
	public java.util.List<Vehiculo> vehiculoSeMueve(double t){
		//1: Clonar los elementos y calcular la estimación de la posición:
		List<Vehiculo> array_clone = estimar(t);
		//2: Algoritmo de ordenación:
		bubbleSort(array_clone);
		//3: Seguir los pasos indicados:
		notificar(array_clone,t);
		return array_clone;
		
	}
	//Criterio de ordenación:
	private int compareTo(Vehiculo v1, Vehiculo v2) {
		Orden orden = impactoRelativoAEsteVehiculo(v1,v2);
		if(orden == orden.Antes) {
			return -1;
		}
		else if(orden == orden.Despues) {
			return 1;
		}
		else {
			return 0;
		}
	}
	//Método estimar de prueba:
	private List<Vehiculo>estimar(double t){
		List<Vehiculo> array_clone = new ArrayList<>();
		for(Vehiculo v: this.detectados) {
			Vehiculo v_clone = new Vehiculo(v.getId(),v.getPos0(),v.getT0(),v.getPos(),v.getT());
			array_clone.add(v_clone);
			v_clone.mover(t);
			v_clone.setDetector(v.getDetector());
		}
		return array_clone;
	}
	
	private void notificar(List<Vehiculo> array_clone, double t) {
		for(Vehiculo v: array_clone) {
			DetectorVehiculos detector = v.getDetector();
			Vehiculo v_encontrado = detector.buscaVehiculo(this.v);
			if(v_encontrado != null) {
				detector.addDeteccion(this.v.getPos(),t);
			}
			else {
				detector.addVehiculo(this.v);
			}
		}
	}
	
	//https://www.javatpoint.com/bubble-sort-in-java#:~:text=We%20can%20create%20a%20java,compared%20with%20the%20next%20element.
	private void bubbleSort(List<Vehiculo> array_clone) {  
	int n = array_clone.size();   
		for(int i=0; i < array_clone.size(); i++){  
			for(int j=1; j < (n-i); j++){  
				if(compareTo(array_clone.get(j-1),array_clone.get(j)) == 1){  
					Vehiculo aux_vehiculo = null;
					aux_vehiculo = array_clone.get(j-1);
					Vehiculo change1 = array_clone.get(j-1); 
					Vehiculo change2 = array_clone.get(j);
					aux_vehiculo = change1;
					change1 = change2;
					change2 = aux_vehiculo; 
				}  	
			}  
		}  
	}  
	
}
