package com.lucas.DesafioBackend.repository;

import com.lucas.DesafioBackend.model.cliente.Cliente;
import com.lucas.DesafioBackend.model.cliente.PessoaFisica;
import com.lucas.DesafioBackend.model.cliente.PessoaJuridica;
import com.lucas.DesafioBackend.util.MyQueue;
import com.lucas.DesafioBackend.util.Queue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class InMemoryClientRepository {

    private final Queue<Cliente> serviceQueue = new MyQueue<>(100);

    private final List<PessoaFisica> registeredPhysicalClients = new ArrayList<>();
    private final List<PessoaJuridica> registeredLegalClients = new ArrayList<>();

    public void clear() {
        registeredLegalClients.clear();
        registeredPhysicalClients.clear();
    }

    public Cliente fromQueue() {
        return serviceQueue.dequeue();
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

    public boolean add(PessoaFisica payload) {
        List<PessoaFisica> foundPerson = registeredPhysicalClients.stream().filter((o) ->
                o.getCpf().equals(payload.getCpf())).toList();

        if (!foundPerson.isEmpty()) {
            return false;
        }

        var pessoaFisica = new PessoaFisica(
                payload.getCpf(),
                payload.getNome(),
                payload.getEmail(),
                payload.getMcc());

        registeredPhysicalClients.add(pessoaFisica);
        serviceQueue.enqueue(pessoaFisica);
        return true;
    }

    public boolean add(PessoaJuridica payload) {
        for (var cliente : registeredLegalClients) {
            if (cliente.getCnpj().equals(payload.getCnpj())) {
                return false;
            }
        }

        payload.setUuid(UUID.randomUUID());
        registeredLegalClients.add(payload);
        serviceQueue.enqueue(payload);
        return true;
    }

    public boolean update(String uuid, PessoaFisica payload) {
        List<PessoaFisica> foundPerson = registeredPhysicalClients.stream().filter((o) ->
                o.getUuid().equals(UUID.fromString(uuid))).toList();

        if (foundPerson.isEmpty()) {
            return false;
        }

        var person = foundPerson.get(0);
        person.setNome(payload.getNome());
        person.setEmail(payload.getEmail());
        person.setMcc(payload.getMcc());
        person.setCpf(payload.getCpf());

        registeredPhysicalClients.remove(person);
        registeredPhysicalClients.add(person);
        serviceQueue.enqueue(person);
        return true;
    }

    public boolean update(String uuid, PessoaJuridica payload) {
        List<PessoaJuridica> foundPerson = registeredLegalClients.stream().filter((o) ->
                o.getUuid().equals(UUID.fromString(uuid))).toList();

        if (foundPerson.isEmpty()) {
            return false;
        }

        PessoaJuridica person = foundPerson.get(0);
        person.setCnpj(payload.getCnpj());
        person.setRazaoSocial(payload.getRazaoSocial());
        person.setNome(payload.getNome());
        person.setEmail(payload.getEmail());
        person.setMcc(payload.getMcc());

        registeredLegalClients.remove(person);
        registeredLegalClients.add(person);
        serviceQueue.enqueue(person);
        return true;
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
