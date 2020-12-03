package com.ceiba.boardgamesnfood.infraestructura.controllador;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.isNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ceiba.boardgamesnfood.aplicacion.comando.ComandoMesa;
import com.ceiba.boardgamesnfood.dominio.JuegoType;
import com.ceiba.boardgamesnfood.dominio.excepcion.EntityNoEncontradaException;
import com.ceiba.boardgamesnfood.testdatabuilder.MesaTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControladorReservaTest {

	@Autowired
	private MockMvc mvc;
	
    @Autowired
    private ObjectMapper objectMapper;

	@Test
	public void debeRetornarReservaSiExiste() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/reserva/{id}", 1L)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.titular").value("TITULAR_1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.juego").value(JuegoType.EXPLODING_KITTENS.name()));
	}
	
	@Test
	public void debeRetornarNotFoundSiReservaNoExiste() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/reserva/{id}", 0L)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombreExcepcion").value(EntityNoEncontradaException.class.getSimpleName()));	
	}
	
	
	@Test
	public void debeCrearMesa() throws Exception {
		
		ComandoMesa comandoMesa = new MesaTestDataBuilder().buildComando();
		mvc.perform(MockMvcRequestBuilders
				.post("/mesa")
				.content(objectMapper.writeValueAsString(comandoMesa))
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders
				.get("/mesa/{codigo}", comandoMesa.getCodigo())
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(comandoMesa.getCodigo()));
	}
	
	@Test
	public void debeRetornarMesasDisponiblesSiFechaHoraEsDisponible() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/mesa/disponibles/{fecha}", "2020-12-03-15:00:00")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].codigo").value("01"))
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
	}
}
