package com.lucas.DesafioBackend.repository;

import com.lucas.DesafioBackend.model.cliente.PessoaFisica;
import com.lucas.DesafioBackend.model.cliente.PessoaJuridica;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class InMemoryClientRepository {

    private final List<PessoaFisica> registeredPhysicalClients = new ArrayList<>();
    private final List<PessoaJuridica> registeredLegalClients = new ArrayList<>();

    public void clear() {
        registeredLegalClients.clear();
        registeredPhysicalClients.clear();
    }

    public List<PessoaFisica> findAllPhysicalClients() {
        return registeredPhysicalClients;
    }

    public List<PessoaJuridica> findAllLegalClients() {
        return registeredLegalClients;
    }

    public PessoaFisica findPhysicalBy(String uuid) {
        List<PessoaFisica> foundPerson = registeredPhysicalClients.stream().filter((o) ->
                o.getUuid().equals(UUID.fromString(uuid))).toList();

        if (foundPerson.isEmpty()) {
            return null;
        }

        return foundPerson.get(0);
    }

    public PessoaJuridica findLegalBy(String uuid) {
        List<PessoaJuridica> foundPerson = registeredLegalClients.stream().filter((o) ->
                o.getUuid().equals(UUID.fromString(uuid))).toList();

        if (foundPerson.isEmpty()) {
            return null;
        }

        return foundPerson.get(0);
    }

    public PessoaFisica add(PessoaFisica payload) {
        List<PessoaFisica> foundPerson = registeredPhysicalClients.stream().filter((o) ->
                o.getCpf().equals(payload.getCpf())).toList();

        if (!foundPerson.isEmpty()) {
            return null;
        }

        var pessoaFisica = new PessoaFisica(
                payload.getCpf(),
                payload.getNome(),
                payload.getEmail(),
                payload.getMcc());

        registeredPhysicalClients.add(pessoaFisica);
        return pessoaFisica;
    }

    public PessoaJuridica add(PessoaJuridica payload) {
        for (var cliente : registeredLegalClients) {
            if (cliente.getCnpj().equals(payload.getCnpj())) {
                return null;
            }
        }

        payload.setUuid(UUID.randomUUID());
        registeredLegalClients.add(payload);
        return payload;
    }

    public PessoaFisica update(String uuid, PessoaFisica payload) {
        List<PessoaFisica> foundPerson = registeredPhysicalClients.stream().filter((o) ->
                o.getUuid().equals(UUID.fromString(uuid))).toList();

        if (foundPerson.isEmpty()) {
            return null;
        }

        var person = foundPerson.get(0);
        person.setNome(payload.getNome());
        person.setEmail(payload.getEmail());
        person.setMcc(payload.getMcc());
        person.setCpf(payload.getCpf());

        registeredPhysicalClients.remove(person);
        registeredPhysicalClients.add(person);
        return person;
    }

    public PessoaJuridica update(String uuid, PessoaJuridica payload) {
        List<PessoaJuridica> foundPerson = registeredLegalClients.stream().filter((o) ->
                o.getUuid().equals(UUID.fromString(uuid))).toList();

        if (foundPerson.isEmpty()) {
            return null;
        }

        PessoaJuridica person = foundPerson.get(0);
        person.setCnpj(payload.getCnpj());
        person.setRazaoSocial(payload.getRazaoSocial());
        person.setNome(payload.getNome());
        person.setEmail(payload.getEmail());
        person.setMcc(payload.getMcc());

        registeredLegalClients.remove(person);
        registeredLegalClients.add(person);
        return person;
    }

    public boolean delete(String uuid) {
        List<PessoaFisica> foundPhysicalPerson = registeredPhysicalClients.stream().filter((o) ->
                o.getUuid().equals(UUID.fromString(uuid))).toList();

        List<PessoaJuridica> foundLegalPerson = registeredLegalClients.stream().filter((o) ->
                o.getUuid().equals(UUID.fromString(uuid))).toList();

        if (!foundPhysicalPerson.isEmpty()) {
            registeredPhysicalClients.remove(foundPhysicalPerson.get(0));
            return true;
        } else if (!foundLegalPerson.isEmpty()) {
            registeredLegalClients.remove(foundLegalPerson.get(0));
            return true;
        }

        return false;
    }
}
