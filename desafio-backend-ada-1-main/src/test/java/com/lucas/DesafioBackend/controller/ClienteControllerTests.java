package com.lucas.DesafioBackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.DesafioBackend.model.cliente.PessoaFisica;
import com.lucas.DesafioBackend.model.cliente.PessoaJuridica;
import com.lucas.DesafioBackend.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private final PessoaFisica pessoaFisica = new PessoaFisica(
            "48851791813",
            "Lucas",
            "lucin189@gmail.com",
            "1234");

    private final PessoaJuridica pessoaJuridica = new PessoaJuridica(
            "12345678909",
            "testeaten",
            "teste@teste.com",
            "1234",
            "12345678909876",
            "teste"
    );

    @Test
    public void getPhysical_shouldReturn200WithOrWithoutClients() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/pessoa-fisica")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getLegal_shouldReturn200WithOrWithoutClients() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/pessoa-juridica")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void showPhysicalClient_shouldReturn404IfNotFound() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/clientes/pessoa-fisica/{uuid}", pessoaFisica.getUuid())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void show_physicalClient() throws Exception {

        when(clienteService.findPhysicalPerson(any())).thenReturn(pessoaFisica);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/clientes/pessoa-fisica/{uuid}", pessoaFisica.getUuid())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(pessoaFisica.getUuid().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void showLegalClient_shouldReturn404IfNotFound() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/clientes/pessoa-juridica/{uuid}", pessoaJuridica.getUuid())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void show_legalClient() throws Exception {

        when(clienteService.findLegalPerson(any())).thenReturn(pessoaJuridica);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/clientes/pessoa-juridica/{uuid}", pessoaJuridica.getUuid())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(pessoaJuridica.getUuid().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void post_pessoaJuridica_shouldReturn400IfExists() throws Exception {

        when(clienteService.registerLegalPerson(any())).thenReturn(false);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/clientes/pessoa-juridica/cadastrar")
                                .content(objectMapper.writeValueAsString(pessoaJuridica))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_pessoaJuridica_shouldReturn201WhenCreated() throws Exception {

        when(clienteService.registerLegalPerson(any())).thenReturn(true);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/clientes/pessoa-juridica/cadastrar")
                                .content(objectMapper.writeValueAsString(pessoaJuridica))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void put_pessoaJuridica_shouldReturn404IfDoesntExist() throws Exception {

        when(clienteService.updateLegalPerson(any(), any())).thenReturn(false);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/clientes/pessoa-juridica/atualizar/{uuid}",
                                        pessoaJuridica.getUuid())
                                .content(objectMapper.writeValueAsString(pessoaJuridica))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void put_pessoaJuridica_shouldReturn200IfUpdated() throws Exception {

        when(clienteService.updateLegalPerson(any(), any())).thenReturn(true);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/clientes/pessoa-juridica/atualizar/{uuid}",
                                        pessoaJuridica.getUuid())
                                .content(objectMapper.writeValueAsString(pessoaJuridica))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void post_pessoaFisica_shouldReturn400IfExists() throws Exception {

        when(clienteService.registerPhysicalPerson(any())).thenReturn(false);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/clientes/pessoa-fisica/cadastrar")
                                .content(objectMapper.writeValueAsString(pessoaFisica))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_pessoaFisica_shouldReturn201WhenCreated() throws Exception {

        when(clienteService.registerPhysicalPerson(any())).thenReturn(true);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/clientes/pessoa-fisica/cadastrar")
                                .content(objectMapper.writeValueAsString(pessoaFisica))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void put_pessoaFisica_shouldReturn404IfDoesntExist() throws Exception {

        when(clienteService.updatePhysicalPerson(any(), any())).thenReturn(false);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/clientes/pessoa-fisica/atualizar/{uuid}",
                                        pessoaFisica.getUuid())
                                .content(objectMapper.writeValueAsString(pessoaFisica))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void put_pessoaFisica_shouldReturn200IfUpdated() throws Exception {

        when(clienteService.updatePhysicalPerson(any(), any())).thenReturn(true);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/clientes/pessoa-fisica/atualizar/{uuid}",
                                        pessoaFisica.getUuid())
                                .content(objectMapper.writeValueAsString(pessoaFisica))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_shouldReturn404ifDoesntExist() throws Exception {

        when(clienteService.deleteCliente(any())).thenReturn(false);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/clientes/{uuid}",
                                        pessoaFisica.getUuid())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_shouldReturn200ifDeleted() throws Exception {

        when(clienteService.deleteCliente(any())).thenReturn(true);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/clientes/{uuid}",
                                        pessoaFisica.getUuid())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

