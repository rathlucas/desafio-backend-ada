package com.lucas.DesafioBackend.controller;

import com.lucas.DesafioBackend.model.cliente.PessoaFisica;
import com.lucas.DesafioBackend.model.cliente.PessoaJuridica;
import com.lucas.DesafioBackend.service.AwsSqsQueueService;
import com.lucas.DesafioBackend.service.ClienteService;
import io.awspring.cloud.sqs.operations.SendResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Cadastro, atualização, consulta e deleção de clientes.")
@RestController
@RequestMapping(value = "/api/v1/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    private final AwsSqsQueueService awsSqsQueueService;

    @GetMapping("/pessoa-fisica")
    public ResponseEntity<List<PessoaFisica>> indexAllPhysicalClients() {
        return new ResponseEntity<>(clienteService.getPhysicalClientsList(), HttpStatus.OK);
    }

    @GetMapping("/pessoa-juridica")
    public ResponseEntity<List<PessoaJuridica>> indexAllLegalClients() {
        return new ResponseEntity<>(clienteService.getLegalClientsList(), HttpStatus.OK);
    }

    @GetMapping("/pessoa-fisica/{uuid}")
    public ResponseEntity<Object> showPhysicalClient(@PathVariable("uuid") String uuid) {
        PessoaFisica cliente = clienteService.findPhysicalPerson(uuid);
        if (cliente == null) {
            return new ResponseEntity<>("Cliente não cadastrado no sistema!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/pessoa-juridica/{uuid}")
    public ResponseEntity<Object> showLegalClient(@PathVariable("uuid") String uuid) {
        PessoaJuridica cliente = clienteService.findLegalPerson(uuid);
        if (cliente == null) {
            return new ResponseEntity<>("Cliente não cadastrado no sistema!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/pessoa-fisica/cadastrar")
    public ResponseEntity<String> registerPhysicalPerson(@Valid @RequestBody PessoaFisica payload) {
        PessoaFisica result = clienteService.registerPhysicalPerson(payload);
        if (result == null) {
            return new ResponseEntity<>("Cliente já cadastrado no sistema, o cadastro foi abortado!",
                    HttpStatus.BAD_REQUEST);
        }

        try {
            awsSqsQueueService.publishToServiceQueue(result);
        } catch (RuntimeException e) {
            System.err.println("Falha ao enviar o cliente para a fila de atendimentos: " + e);
        }

        return new ResponseEntity<>("Cliente cadastrado com sucesso!", HttpStatus.CREATED);
    }


    @PutMapping("/pessoa-fisica/atualizar/{uuid}")
    public ResponseEntity<String> updatePhysicalPerson(@PathVariable("uuid") String uuid,
                                                       @Valid @RequestBody PessoaFisica payload) {
        PessoaFisica result = clienteService.updatePhysicalPerson(uuid, payload);
        if (result == null) {
            return new ResponseEntity<>("Cliente não cadastrado no sistema, a atualização foi abortada!",
                    HttpStatus.NOT_FOUND);
        }

        try {
            awsSqsQueueService.publishToServiceQueue(result);
        } catch (RuntimeException e) {
            System.err.println("Falha ao enviar o cliente para a fila de atendimentos: " + e);
        }

        return new ResponseEntity<>("Cliente atualizado com sucesso!", HttpStatus.OK);
    }

    @PostMapping("/pessoa-juridica/cadastrar")
    public ResponseEntity<String> registerLegalPerson(@Valid @RequestBody PessoaJuridica payload) {
        PessoaJuridica result = clienteService.registerLegalPerson(payload);
        if (result == null) {
            return new ResponseEntity<>("Empresa já cadastrada no sistema, o cadastro foi abortado!",
                    HttpStatus.BAD_REQUEST);
        }

        try {
            awsSqsQueueService.publishToServiceQueue(result);
        } catch (RuntimeException e) {
            System.err.println("Falha ao enviar o cliente para a fila de atendimentos: " + e);
        }

        return new ResponseEntity<>("Empresa cadastrada com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping("/pessoa-juridica/atualizar/{uuid}")
    public ResponseEntity<String> updateLegalPerson(@PathVariable("uuid") String uuid,
                                                    @Valid @RequestBody PessoaJuridica payload) {
        PessoaJuridica result = clienteService.updateLegalPerson(uuid, payload);
        if (result == null) {
            return new ResponseEntity<>("Empresa não cadastrada no sistema, a atualização foi abortada!",
                    HttpStatus.NOT_FOUND);
        }

        try {
            awsSqsQueueService.publishToServiceQueue(result);
        } catch (RuntimeException e) {
            System.err.println("Falha ao enviar o cliente para a fila de atendimentos: " + e);
        }

        return new ResponseEntity<>("Empresa atualizada com sucesso!", HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteCliente(@PathVariable("uuid") String uuid) {
        boolean result = clienteService.deleteCliente(uuid);
        if (!result) {
            return new ResponseEntity<>("Cliente não cadastrado no sistema!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Cliente deletado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/fila-de-atendimento/retirar")
    public ResponseEntity<Object> getClienteFromServiceQueue() {
        var result = awsSqsQueueService.getNextClientFromServiceQueue();
        if (result == null) {
            return new ResponseEntity<>("A fila de atendimento está vazia!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}