package com.lucas.DesafioBackend.model.cliente;

public class PessoaFisica extends Cliente {

    public PessoaFisica(String cpf, String nome,
                        String email, String MCC) {
        super(cpf, nome, email, MCC);
    }

    @Override
    public String toString() {
        return "PessoaFisica{} " + super.toString();
    }
}
