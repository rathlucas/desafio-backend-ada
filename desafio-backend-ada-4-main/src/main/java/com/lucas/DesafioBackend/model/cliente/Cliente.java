package com.lucas.DesafioBackend.model.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
public abstract class Cliente {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID uuid;

    @NotNull(message = "O campo cpf não pode estar vazio!")
    @Pattern(regexp = "\\d{11}", message = "O campo cpf deve possuir 11 dígitos!")
    @Schema(example = "85419265087")
    private String cpf;

    @NotNull(message = "O campo nome não pode estar vazio!")
    @Size(min = 3, max = 50, message = "O campo nome precisa ter entre 3 e 50 caracteres!")
    @Schema(example = "Nome de Teste")
    private String nome;

    @NotNull(message = "O campo email não pode estar vazio!")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$",
            message = "Email inválido!")
    @Schema(example = "teste@mail.com")
    private String email;

    @NotNull(message = "O campo mcc não pode estar vazio!")
    @Pattern(regexp = "\\d{4}", message = "O MCC deve possuir 4 dígitos!")
    @Schema(example = "1234")
    private String mcc;

    public Cliente(String cpf, String nome, String email, String mcc) {
        this.uuid = UUID.randomUUID();
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.mcc = mcc;
    }
}
