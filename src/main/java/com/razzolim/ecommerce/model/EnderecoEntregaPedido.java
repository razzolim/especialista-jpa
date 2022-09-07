package com.razzolim.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class EnderecoEntregaPedido {

    @Column(name = "end_cep")
    private String cep;

    @Column(name = "end_logradouro")
    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;

}
