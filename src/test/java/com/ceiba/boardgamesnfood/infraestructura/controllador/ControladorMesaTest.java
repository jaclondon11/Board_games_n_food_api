package com.ceiba.boardgamesnfood.infraestructura.controllador;

import static org.hamcrest.Matchers.greaterThan;
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

import com.ceiba.boardgamesnfood.aplicacion.comando.ComandoTable;
import com.ceiba.boardgamesnfood.dominio.excepcion.EntityNoEncontradaException;
import com.ceiba.boardgamesnfood.testdatabuilder.MesaTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControladorMesaTest {

	@Autowired
	private MockMvc mvc;
	
    @Autowired
    private ObjectMapper objectMapper;

	@Test
	public void debeRetornarMesaSiExiste() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/api/mesa/{codigo}", "01")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("01"));
	}
	
	@Test
	public void debeRetornarNotFoundSiMesaNoExiste() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/api/mesa/{codigo}", "00")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombreExcepcion").value(EntityNoEncontradaException.class.getSimpleName()));	
	}
	

	@Test
	public void debeRetornarMesas() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/api/mesa")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].code").value("01"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()", greaterThan(0)));
	}
	
	@Test
	public void debeCrearMesa() throws Exception {
		
		ComandoTable comandoMesa = new MesaTestDataBuilder().buildComando();
		mvc.perform(MockMvcRequestBuilders
				.put("/api/mesa")
				.content(objectMapper.writeValueAsString(comandoMesa))
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders
				.get("/api/mesa/{codigo}", comandoMesa.getCode())
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(comandoMesa.getCode()));
	}
	
	@Test
	public void debeRetornarMesasDisponiblesSiFechaHoraEsDisponible() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/api/mesa/disponibles/{codigo}", "2020-12-04-15:00:00")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].code").value("01"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()", greaterThan(0)));
	}
}
