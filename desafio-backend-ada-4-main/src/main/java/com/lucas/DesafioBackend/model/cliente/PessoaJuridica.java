package com.lucas.DesafioBackend.model.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaJuridica extends Cliente {

    @Pattern(regexp = "\\d{14}", message = "O campo cnpj deve possuir 14 dígitos!")
    @Schema(example = "36799544000102")
    private String cnpj;

    @Size(min = 3, max = 50, message = "O campo razaoSocial precisa ter entre 3 e 50 caracteres!")
    @Schema(example = "Fábrica de Testes Eirelli.")
    private String razaoSocial;

    public PessoaJuridica(
            String cpf,
            String nome,
            String email,
            String MCC,
            String cnpj,
            String razaoSocial) {
        super(cpf, nome, email, MCC);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }
}
