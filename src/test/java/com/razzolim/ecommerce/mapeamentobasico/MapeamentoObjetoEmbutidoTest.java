package com.razzolim.ecommerce.mapeamentobasico;

import com.razzolim.ecommerce.EntityManagerTest;
import com.razzolim.ecommerce.model.EnderecoEntregaPedido;
import com.razzolim.ecommerce.model.Pedido;
import com.razzolim.ecommerce.model.StatusPedido;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MapeamentoObjetoEmbutidoTest extends EntityManagerTest {

    @Test
    public void analisarMapeamentoObjetoEmbutido() {
        var endereco = new EnderecoEntregaPedido();
        endereco.setCep("00000-000");
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("1");
        endereco.setBairro("Centro");
        endereco.setCidade("Testarildes");
        endereco.setEstado("MG");


        var pedido = new Pedido();
//        pedido.setId(1); comentado pq estamos utilizando IDENTITY
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal(1000));
        pedido.setEnderecoEntrega(endereco);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao);
        Assert.assertNotNull(pedidoVerificacao.getEnderecoEntrega());
        Assert.assertNotNull(pedidoVerificacao.getEnderecoEntrega().getCep());

    }
}
