package com.ceiba.boardgamesnfood.dominio.repositorio;

import com.ceiba.boardgamesnfood.dominio.Reserva;

public interface RepositorioReserva {
	
	/**
	 * Permite obtener una reverva por el codigo
	 * @param codigo
	 * @param codigo
	 * @return entity o null si no existe
	 */
	Reserva obtener(String codigo);

	/**
	 * Permite agregar una reserva al repositorio de reserva
	 * @param reserva
	 */
	void agregar(Reserva reserva);
	
}