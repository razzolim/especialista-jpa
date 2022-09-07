package com.razzolim.ecommerce.mapeamentobasico;

import com.razzolim.ecommerce.EntityManagerTest;
import com.razzolim.ecommerce.model.Cliente;
import com.razzolim.ecommerce.model.SexoCliente;
import org.junit.Assert;
import org.junit.Test;

public class MapeandoEnumeracoesTest extends EntityManagerTest {

    @Test
    public void testarEnum() {
        var cliente = new Cliente();
        cliente.setId(4);
        cliente.setNome("José Mineiro");
        cliente.setSexoCliente(SexoCliente.MASCULINO);

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var clienteVerificacao = entityManager.find(Cliente.class, 4);
        Assert.assertNotNull(clienteVerificacao);
        Assert.assertEquals(SexoCliente.MASCULINO, clienteVerificacao.getSexoCliente());
    }
}
