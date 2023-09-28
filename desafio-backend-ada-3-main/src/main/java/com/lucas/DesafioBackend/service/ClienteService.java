package com.lucas.DesafioBackend.service;

import com.lucas.DesafioBackend.model.cliente.PessoaFisica;
import com.lucas.DesafioBackend.model.cliente.PessoaJuridica;
import com.lucas.DesafioBackend.repository.InMemoryClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final InMemoryClientRepository inMemoryClientRepository;

    public ClienteService(InMemoryClientRepository inMemoryClientRepository) {
        this.inMemoryClientRepository = inMemoryClientRepository;
    }

    public List<PessoaFisica> getPhysicalClientsList() {
        return inMemoryClientRepository.findAllPhysicalClients();
    }

    public List<PessoaJuridica> getLegalClientsList() {
        return inMemoryClientRepository.findAllLegalClients();
    }

    public PessoaFisica findPhysicalPerson(String uuid) {
        return inMemoryClientRepository.findPhysicalBy(uuid);
    }

    public PessoaJuridica findLegalPerson(String uuid) {
        return inMemoryClientRepository.findLegalBy(uuid);
    }

    public PessoaFisica registerPhysicalPerson(PessoaFisica payload) {
        return inMemoryClientRepository.add(payload);
    }

    public PessoaFisica updatePhysicalPerson(String uuid, PessoaFisica payload) {
        return inMemoryClientRepository.update(uuid, payload);
    }

    public PessoaJuridica registerLegalPerson(PessoaJuridica payload) {
        return inMemoryClientRepository.add(payload);
    }

    public PessoaJuridica updateLegalPerson(String uuid, PessoaJuridica payload) {
        return inMemoryClientRepository.update(uuid, payload);
    }

    public boolean deleteCliente(String uuid) {
        return inMemoryClientRepository.delete(uuid);
    }
}
