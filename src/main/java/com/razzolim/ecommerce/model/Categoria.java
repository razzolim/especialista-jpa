package com.razzolim.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "categoria")
public class Categoria {

    @Id
    /* @GeneratedValue(strategy = GenerationType.AUTO) hibernate vai escolher estratégia que id vai ser atribuído - gera 1 tabela a mais */
    /*
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "sequencia_chave_primaria")
    // nesse caso, é interessante criar uma sequenceName para cada entidade, senão não ficará 'sequencial', pois outras entidades utilizarão
    */
    /*
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tabela") // vai setar as PKs das tabelas utilizando essa como base
    @TableGenerator(name = "tabela", table = "hibernate_sequences",
            pkColumnName = "sequenceName", pkColumnValue = "categoria", valueColumnName = "next_val", initialValue = 0, allocationSize = 50)
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pega a PK da coluna do banco de dados (mysql, postgres, sqlserver, etc)
    @EqualsAndHashCode.Include
    private Integer id;

    private String nome;

    @Column(name = "categoria_pai_id")
    private Integer categoriaPaiId;

}
