package es.upm.dit.adsw.practica1;

import java.util.List;

public class PruebaDetectorVehiculos {
	public static void main (String[] args) {
		Vehiculo veh1 = new Vehiculo("000", new Vector(0.0, 0.0), 0.0, new Vector(10.0, 10.0), 10.0);
		DetectorVehiculos dv1 = new DetectorVehiculos(veh1);
		System.out.println(dv1);

		// addVehiculo(Vehiculo): con huecos, completo eliminando el antiguo
		Vehiculo[] vs = new Vehiculo [12];
		for (int i = 0; i < vs.length; i++) {
			vs[i] = new Vehiculo ("id" + i, new Vector(i, i), i, new Vector(i, i), i + 1);
		}
		for (int i = 0; i < vs.length; i++) {
			dv1.addVehiculo(vs[i]);
		}
		System.out.println(dv1);
		
		// buscaVehiculo(Vehiculo): esta, no esta
		System.out.println("Buscando vehiculo que no esta: " + dv1.buscaVehiculo(vs[0]));
		System.out.println("Buscando vehiculo que no esta: " + dv1.buscaVehiculo(vs[1]));
		System.out.println("Buscando vehiculo que si esta: " + dv1.buscaVehiculo(vs[11]));
		System.out.println("Buscando vehiculo que si esta: " + dv1.buscaVehiculo(vs[2]));
		
		// getVehiculos(): vacio, con huecos, completo
		Vehiculo veh2 = new Vehiculo("000", new Vector(0.0, 0.0), 0.0, new Vector(10.0, 10.0), 10.0);
		DetectorVehiculos dv2 = new DetectorVehiculos(veh2);
		
		System.out.println("GetVehiculos (vacio): ");
		SelectorVehiculo s = new SelectorVehiculoTrue();
		List<Vehiculo> vss = dv2.getVehiculos(s);
		for (Vehiculo v : vss)
			System.out.println(v);

		System.out.println("GetVehiculos (algunos elementos): ");
		for (int i = 0; i < 4; i++) {
			dv2.addVehiculo(vs[i]);
		}
		vss = dv2.getVehiculos(s);
		for (Vehiculo v : vss)
			System.out.println(v);

		System.out.println("GetVehiculos (completo): ");
		for (int i = 0; i < vs.length; i++) {
			dv2.addVehiculo(vs[i]);
		}
		vss = dv2.getVehiculos(s);
		for (Vehiculo v : vss)
			System.out.println(v);
		
		// getVehiculosPeligrosos(): vacio, con huecos, completo
		Vehiculo veh3 = new Vehiculo("000", new Vector(0.0, 0.0), 0.0, new Vector(10.0, 10.0), 10.0);
		DetectorVehiculos dv3 = new DetectorVehiculos(veh3);
		Vehiculo[] vp = new Vehiculo[5];
		vp[0] = new Vehiculo("pel0", new Vector(0.0, 0.0), 0.0, new Vector(10.0, 10.0), 10.0);
		vp[1] = new Vehiculo("pel1", new Vector(40.0, 0.0), 0.0, new Vector(30.0, 10.0), 10.0);
		dv3.addVehiculo(vp[0]);
		dv3.addVehiculo(vp[1]);
		for (int i = 0; i < vs.length; i++) 
			dv3.addVehiculo(vs[i]);
		SelectorVehiculo s2 = new SelectorVehiculoPeligroso(veh3);
		vss = dv3.getVehiculos(s2);
		System.out.println("GetVehiculosPeligrosos: ");
		for (Vehiculo v : vss)
			System.out.println(v);
		
		// getVehiculosCompatibles(): vacio, con huecos, completo
		Vehiculo veh4 = new Vehiculo("000", new Vector(0.0, 0.0), 0.0, new Vector(10.0, 10.0), 10.0);
		DetectorVehiculos dv4 = new DetectorVehiculos(veh4);
		Vehiculo[] vsc = new Vehiculo[8];
		for (int i = 0; i < vsc.length; i++) {
			vsc[i] = new Vehiculo("comp" + i, new Vector(i, i), 0, new Vector(i+10, i+10), 10);
			dv4.addVehiculo(vsc[i]);
		}
		SelectorVehiculo s3 = new SelectorVehiculoCompatible(new Vector (50.0, 50.0), 50);
		vss = dv4.getVehiculos(s3);
		System.out.println("GetVehiculosCompatibles: ");
		for (Vehiculo v : vss)
			System.out.println(v);
		
		// addDeteccion(Vector, double): hay compatible, no hay compatible
		Vehiculo veh5 = new Vehiculo("000", new Vector(0.0, 0.0), 0.0, new Vector(10.0, 10.0), 10.0);

		DetectorVehiculos dvad = new DetectorVehiculos(veh5);
		Vehiculo[] vsad = new Vehiculo [10];
		for (int i = 0; i < vsad.length; i++) {
			vsad[i] = new Vehiculo ("vsad" + i, new Vector(i*10,i*10), 0, new Vector((i+1)*10, (i+1)*10), 10);
		}
		for (int i = 0; i < vsad.length; i++) {
			dvad.addVehiculo(vsad[i]);
		}
		System.out.println("Antes de addDetecion: ");
		System.out.println(dvad);
		dvad.addDeteccion(new Vector(41, 40), 11);
		System.out.println("Despues de addDeteccion: ");
		System.out.println(dvad);
		dvad.addDeteccion(new Vector(65, 140), 11);
		System.out.println("Despues de addDeteccion: ");
		System.out.println(dvad);
		}
	}

