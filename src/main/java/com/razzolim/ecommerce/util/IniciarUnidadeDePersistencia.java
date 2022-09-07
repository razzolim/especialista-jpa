package com.razzolim.ecommerce.util;

import com.razzolim.ecommerce.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Exemplo criação entity manager pegando dados a partir do persistence.xml
 */
public class IniciarUnidadeDePersistencia {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // fazer os testes aqui
        Produto produto = entityManager.find(Produto.class, 1);
        System.out.println(produto);

        entityManager.close();
        entityManagerFactory.close();
    }
}
