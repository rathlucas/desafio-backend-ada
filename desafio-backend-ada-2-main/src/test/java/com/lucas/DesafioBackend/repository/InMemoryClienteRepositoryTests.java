package com.lucas.DesafioBackend.repository;

import com.lucas.DesafioBackend.model.cliente.PessoaFisica;
import com.lucas.DesafioBackend.model.cliente.PessoaJuridica;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InMemoryClienteRepositoryTests {

    @Autowired
    InMemoryClientRepository inMemoryClientRepository;

    private final PessoaJuridica pessoaJuridica = new PessoaJuridica(
            "48851791813",
            "lucin",
            "lucin189@gmail.com",
            "1234",
            "12345678909876",
            "Teste Eireli");

    private final PessoaFisica pessoaFisica = new PessoaFisica(
            "48851791813",
            "lucin",
            "lucin189@gmail.com",
            "1234");

    @Test
    public void findAllPhysical_shouldReturnEmptyListWhenNotFound() {

        inMemoryClientRepository.clear();
        var result = inMemoryClientRepository.findAllPhysicalClients();
        assertThat(result).isEmpty();
    }

    @Test
    public void findAllPhysical_shouldReturnListWithClientesWhenFound() {

        inMemoryClientRepository.add(pessoaFisica);

        var result = inMemoryClientRepository.findAllPhysicalClients();
        assertThat(result).isNotEmpty();
        inMemoryClientRepository.clear();
    }

    @Test
    public void findAllLegal_shouldReturnEmptyListWhenNotFound() {

        inMemoryClientRepository.clear();
        var result = inMemoryClientRepository.findAllLegalClients();
        assertThat(result).isEmpty();
    }

    @Test
    public void findAllLegal_shouldReturnListWithClientesWhenFound() {

        inMemoryClientRepository.add(pessoaJuridica);

        var result = inMemoryClientRepository.findAllLegalClients();
        assertThat(result).isNotEmpty();
        inMemoryClientRepository.clear();
    }

    @Test
    public void findPhysicalBy_shouldReturnNullIfNotFound() {

        var result = inMemoryClientRepository.findPhysicalBy(UUID.randomUUID().toString());
        assertThat(result).isNull();
        inMemoryClientRepository.clear();
    }

    @Test
    public void add_legalPerson_shouldReturnFalseIfExists() {

        inMemoryClientRepository.add(pessoaJuridica);
        var result = inMemoryClientRepository.add(pessoaJuridica);
        assertThat(result).isFalse();
        inMemoryClientRepository.clear();
    }

    @Test
    public void update_physicalPerson_shouldReturnFalseIfNotFound() {

        var result = inMemoryClientRepository.update(pessoaFisica.getUuid().toString(),
                pessoaFisica);

        assertThat(result).isFalse();
        inMemoryClientRepository.clear();
    }

    @Test
    public void update_legalPerson_shouldReturnFalseIfNotFound() {

        var result = inMemoryClientRepository.update(pessoaJuridica.getUuid().toString(),
                pessoaJuridica);

        assertThat(result).isFalse();
        inMemoryClientRepository.clear();
    }

    @Test
    public void update_legalPerson_shouldReturnTrueIfIsSuccess() {

        inMemoryClientRepository.add(pessoaJuridica);

        var result = inMemoryClientRepository.update(pessoaJuridica.getUuid().toString(),
                pessoaJuridica);

        assertThat(result).isTrue();
        inMemoryClientRepository.clear();
    }
}