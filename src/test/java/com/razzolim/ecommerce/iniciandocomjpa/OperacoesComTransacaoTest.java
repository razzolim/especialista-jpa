package com.razzolim.ecommerce.iniciandocomjpa;

import com.razzolim.ecommerce.EntityManagerTest;
import com.razzolim.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OperacoesComTransacaoTest extends EntityManagerTest {

    @Test
    public void impedirOperacaoComBancoDeDados() {
        var produto = entityManager.find(Produto.class, 1);
        entityManager.detach(produto); // desanexa instância que tá na memória do EM

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paper White 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear(); // limpando memória do EM para executar select na base e nao pegar registro da memória

        var produtoUpdated = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoUpdated);
        Assert.assertEquals("Kindle", produtoUpdated.getNome());
    }

    @Test
    public void mostrarDiferençaPersistMerge() {
        var produtoPersist = new Produto();
        produtoPersist.setId(4);
        produtoPersist.setNome("Smartphone One Plus");
        produtoPersist.setDescricao("O processador mais rápido.");
        produtoPersist.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        entityManager.persist(produtoPersist); // só serve para persistir
        entityManager.getTransaction().commit();

        entityManager.clear(); // limpando memória do EM para executar select na base e nao pegar registro da memória

        var produtoVerificacaoPersisted = entityManager.find(Produto.class, produtoPersist.getId());
        Assert.assertNotNull(produtoVerificacaoPersisted);
        Assert.assertEquals(produtoPersist.getId(), produtoVerificacaoPersisted.getId());

        /* ---------------- merge() ------------------- */
        var produtoMerge = new Produto();
        produtoMerge.setId(4);
        produtoMerge.setNome("Notebook Dell");
        produtoMerge.setDescricao("O melhor da categoria.");
        produtoMerge.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        // merge serve para inserção e atualização
        entityManager.merge(produtoMerge); // ele cria uma cópia e joga essa cópia pro EM gerenciar (a cópia é devolvida no retorno do método)
        produtoMerge.setNome("Smartphone Two Plus"); // essa instância não é gerenciada, então não vai funcionar, exceto se receber o retorno do método
        entityManager.getTransaction().commit();

        entityManager.clear(); // limpando memória do EM para executar select na base e nao pegar registro da memória

        var produtoVerificacaoMerge = entityManager.find(Produto.class, produtoMerge.getId());
        Assert.assertNotNull(produtoVerificacaoMerge);
        Assert.assertEquals(produtoMerge.getId(), produtoVerificacaoMerge.getId());
    }

    @Test
    public void inserirObjetoComMerge() {
        var produto = new Produto();
        produto.setId(4);
        produto.setNome("Microfone Rode Videmic");
        produto.setDescricao("A melhor qualidade de som");
        produto.setPreco(new BigDecimal(1000));

        entityManager.getTransaction().begin();
        entityManager.merge(produto); // merge pode ser utilizado tanto para atualizar quanto para inserir...
        entityManager.getTransaction().commit();

        entityManager.clear(); // limpando memória do EM para executar select na base e nao pegar registro da memória

        var produtoPersisted = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoPersisted);
        Assert.assertEquals(produto.getId(), produtoPersisted.getId());
    }

    @Test
    public void atualizarObjetoGerenciado() {
        var produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paper White 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear(); // limpando memória do EM para executar select na base e nao pegar registro da memória

        var produtoUpdated = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoUpdated);
        Assert.assertEquals("Kindle Paper White 2ª Geração", produtoUpdated.getNome());
    }

    @Test
    public void atualizarObjeto() {
        var produto = new Produto();
        produto.setId(1);
        produto.setNome("Kindle Paper White");
        produto.setDescricao("Conheça o novo Kindle");
        produto.setPreco(new BigDecimal(599));

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear(); // limpando memória do EM para executar select na base e nao pegar registro da memória

        var produtoUpdated = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoUpdated);
        Assert.assertEquals("Kindle Paper White", produtoUpdated.getNome());
    }

    @Test
    public void removendoObjeto() {
        var produto = entityManager.find(Produto.class, 3);

        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().begin();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNull(produtoVerificacao);
    }

    @Test
    public void inserirOPrimeiroObjeto() {
        var produto = new Produto();
        produto.setId(2);
        produto.setNome("Câmera Canon");
        produto.setDescricao("A melhor definição para suas fotos");
        produto.setPreco(new BigDecimal(5000));

        entityManager.getTransaction().begin();
        entityManager.persist(produto); // não necessariamente precisa ficar dentro do begin/commit. Mas, obrigatório ter uma trx
        entityManager.getTransaction().commit();

        entityManager.clear(); // limpando memória do EM para executar select na base e nao pegar registro da memória

        var produtoPersisted = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoPersisted);
        Assert.assertEquals(produto.getId(), produtoPersisted.getId());
    }

    @Test
    public void abrirEFecharATransacao() {
        var produto = new Produto();
        entityManager.getTransaction().begin();

        // aqui dentro realizo as operações que alteram o banco
        entityManager.persist(produto);
        entityManager.merge(produto);
        entityManager.remove(produto);

        entityManager.getTransaction().commit();
    }
}
