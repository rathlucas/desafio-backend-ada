package com.lucas.DesafioBackend.service;

import com.lucas.DesafioBackend.model.cliente.Cliente;
import com.lucas.DesafioBackend.model.cliente.PessoaFisica;
import com.lucas.DesafioBackend.model.cliente.PessoaJuridica;
import com.lucas.DesafioBackend.repository.InMemoryClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ClienteServiceTests {

    @Mock
    private InMemoryClientRepository inMemoryClientRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void getPhysicalList_shouldReturnEmptyList() {

        when(inMemoryClientRepository.findAllPhysicalClients()).thenReturn(new ArrayList<>());
        var result = clienteService.getPhysicalClientsList();
        assertThat(result).isEmpty();
    }

    @Test
    public void getPhysicalList_shouldReturnListWithItems() {

        List<PessoaFisica> registeredClients = new ArrayList<>();
        registeredClients.add(
                new PessoaFisica(
                        "48851791813",
                        "lucin",
                        "lucin189@gmail.com",
                        "1234")
        );

        when(inMemoryClientRepository.findAllPhysicalClients()).thenReturn(registeredClients);

        var result = clienteService.getPhysicalClientsList();
        assertThat(result).isNotEmpty();
    }

    @Test
    public void findPhysicalClient_shouldReturnNullIfNotFound() {

        when(inMemoryClientRepository.findPhysicalBy(any())).thenReturn(null);
        var result = clienteService.findPhysicalPerson(any());
        assertThat(result).isNull();
    }

    @Test
    public void getLegalList_shouldReturnEmptyList() {

        when(inMemoryClientRepository.findAllLegalClients()).thenReturn(new ArrayList<>());
        var result = clienteService.getLegalClientsList();
        assertThat(result).isEmpty();
    }

    @Test
    public void getLegalList_shouldReturnListWithItems() {

        List<PessoaJuridica> registeredClients = new ArrayList<>();
        registeredClients.add(
                new PessoaJuridica(
                        "48851791813",
                        "lucin",
                        "lucin189@gmail.com",
                        "1234",
                        "12345678909876",
                        "Teste Eireli")
        );

        when(inMemoryClientRepository.findAllLegalClients()).thenReturn(registeredClients);

        var result = clienteService.getLegalClientsList();
        assertThat(result).isNotEmpty();
    }

    @Test
    public void findPhysicalClient_shouldReturnFoundCliente() {

        PessoaFisica pessoaFisica = new PessoaFisica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234");

        when(inMemoryClientRepository.findPhysicalBy(any())).thenReturn(pessoaFisica);
        var result = clienteService.findPhysicalPerson(any());
        assertThat(result.getUuid()).isEqualByComparingTo(pessoaFisica.getUuid());
    }

    @Test
    public void findLegalClient_shouldReturnFoundCliente() {

        PessoaJuridica pessoaJuridica = new PessoaJuridica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234",
                "12345678909876",
                "Teste Eireli");

        when(inMemoryClientRepository.findLegalBy(any())).thenReturn(pessoaJuridica);
        var result = clienteService.findLegalPerson(any());
        assertThat(result.getUuid()).isEqualByComparingTo(pessoaJuridica.getUuid());
    }

    @Test
    public void registerPhysical_shouldReturnFalseIfExists() {

        PessoaFisica pessoaFisica = new PessoaFisica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234");

        inMemoryClientRepository.add(pessoaFisica);

        when(inMemoryClientRepository.add(any(PessoaFisica.class))).thenReturn(false);
        var result = clienteService.registerPhysicalPerson(pessoaFisica);
        assertThat(result).isFalse();
    }

    @Test
    public void registerPhysical_shouldReturnTrueIfSuccesful() {

        PessoaFisica pessoaFisica = new PessoaFisica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234");

        when(inMemoryClientRepository.add(any(PessoaFisica.class))).thenReturn(true);
        var result = clienteService.registerPhysicalPerson(pessoaFisica);
        assertThat(result).isTrue();
    }

    @Test
    public void updatePhysical_shouldReturnFalseIfDoesntExist() {

        when(inMemoryClientRepository.update(any(), any(PessoaFisica.class))).thenReturn(false);
        var result = clienteService.updatePhysicalPerson(any(), any());
        assertThat(result).isFalse();
    }

    @Test
    public void updatePhysical_shouldReturnTrueIfSuccesful() {

        PessoaFisica pessoaFisica = new PessoaFisica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234");

        when(inMemoryClientRepository.update(any(), any(PessoaFisica.class))).thenReturn(true);
        var result = clienteService.updatePhysicalPerson(pessoaFisica.getUuid().toString(), pessoaFisica);
        assertThat(result).isTrue();
    }

    @Test
    public void registerLegal_shouldReturnFalseIfExists() {

        PessoaJuridica pessoaJuridica = new PessoaJuridica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234",
                "12345678909876",
                "teste");

        inMemoryClientRepository.add(pessoaJuridica);

        when(inMemoryClientRepository.add(any(PessoaJuridica.class))).thenReturn(false);
        var result = clienteService.registerLegalPerson(pessoaJuridica);
        assertThat(result).isFalse();
    }

    @Test
    public void registerLegal_shouldReturnTrueIfSuccesful() {

        PessoaJuridica pessoaJuridica = new PessoaJuridica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234",
                "12345678909876",
                "teste");

        when(inMemoryClientRepository.add(any(PessoaJuridica.class))).thenReturn(true);
        var result = clienteService.registerLegalPerson(pessoaJuridica);
        assertThat(result).isTrue();
    }


    @Test
    public void updateLegal_shouldReturnFalseIfDoesntExist() {

        when(inMemoryClientRepository.update(any(), any(PessoaJuridica.class))).thenReturn(false);
        var result = clienteService.updateLegalPerson(any(), any());
        assertThat(result).isFalse();
    }

    @Test
    public void updateLegal_shouldReturnTrueIfSuccesful() {

        PessoaJuridica pessoaJuridica = new PessoaJuridica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234",
                "12345678909876",
                "teste");

        when(inMemoryClientRepository.update(any(), any(PessoaJuridica.class))).thenReturn(true);
        var result = clienteService.updateLegalPerson(pessoaJuridica.getUuid().toString(), pessoaJuridica);
        assertThat(result).isTrue();
    }

    @Test
    public void deleteCliente_shouldReturnFalseIfNotFound() {

        when(inMemoryClientRepository.delete(any())).thenReturn(false);
        var result = clienteService.deleteCliente(any());
        assertThat(result).isFalse();
    }

    @Test
    public void deleteCliente_shouldReturnTrueIfSuccessful() {

        PessoaJuridica pessoaJuridica = new PessoaJuridica(
                "48851791813",
                "lucin",
                "lucin189@gmail.com",
                "1234",
                "12345678909876",
                "teste");

        inMemoryClientRepository.add(pessoaJuridica);

        when(inMemoryClientRepository.delete(any())).thenReturn(true);
        var result = clienteService.deleteCliente(pessoaJuridica.getUuid().toString());
        assertThat(result).isTrue();
    }
}
