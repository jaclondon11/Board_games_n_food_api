package com.ceiba.boardgamesnfood.dominio.servicio.mesas_por_reserva;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.boardgamesnfood.dominio.Table;
import com.ceiba.boardgamesnfood.dominio.excepcion.MesasDisponiblesNoEncontradasException;
import com.ceiba.boardgamesnfood.dominio.repositorio.RepositorioMesa;
import com.ceiba.boardgamesnfood.dominio.repositorio.RepositorioMesasPorReserva;

@Component
public class ServicioObtenerMesasDisponibles {
	
	private final RepositorioMesasPorReserva repositorioMesasPorReserva;
	private final RepositorioMesa repositorioMesa;
	
	@Autowired
	public ServicioObtenerMesasDisponibles(RepositorioMesasPorReserva repositorioMesasPorReserva,
			RepositorioMesa repositorioMesa) {
		super();
		this.repositorioMesasPorReserva = repositorioMesasPorReserva;
		this.repositorioMesa = repositorioMesa;
	}
	
	public List<Table> obtenerMesasDisponiblesByFecha(Date fechaReserva) {
		List<Table> mesasConReserva = repositorioMesasPorReserva.obtenerMesasConReservaByFecha(fechaReserva);
		List<Table> mesas = repositorioMesa.obtenerMesas();
		
		List<Table> mesasDisponibles = mesas.stream()
				.filter(m -> !mesasConReserva.contains(m))
				.collect(Collectors.toList());
		
		
		if (mesasDisponibles == null || mesasDisponibles.isEmpty()) {
			throw new MesasDisponiblesNoEncontradasException();
		}
		return mesasDisponibles;
	}

}
