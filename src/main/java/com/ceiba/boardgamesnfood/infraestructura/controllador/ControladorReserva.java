package com.ceiba.boardgamesnfood.infraestructura.controllador;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.boardgamesnfood.aplicacion.comando.ComandoReserva;
import com.ceiba.boardgamesnfood.aplicacion.manejadores.reserva.ManejadorGenerarReserva;
import com.ceiba.boardgamesnfood.aplicacion.manejadores.reserva.ManejadorObtenerReserva;
import com.ceiba.boardgamesnfood.dominio.Reserva;


@RestController
@RequestMapping("/api/reserva")
public class ControladorReserva {
	private final ManejadorObtenerReserva manejadorObtenerReserva;
	private final ManejadorGenerarReserva manejadorGenerarReserva;

	public ControladorReserva(ManejadorObtenerReserva manejadorObtenerReserva,
			ManejadorGenerarReserva manejadorGenerarReserva) {
		this.manejadorObtenerReserva = manejadorObtenerReserva;
		this.manejadorGenerarReserva = manejadorGenerarReserva;
	}

	@PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public Reserva generarReserva(@RequestBody ComandoReserva comandoReserva) {
		return manejadorGenerarReserva.ejecutar(comandoReserva);
	}

	@GetMapping("/{id}")
	public Reserva buscar(
		@PathVariable(name = "id")
		Long id) {
		return this.manejadorObtenerReserva.ejecutar(id);
	}
	
}
