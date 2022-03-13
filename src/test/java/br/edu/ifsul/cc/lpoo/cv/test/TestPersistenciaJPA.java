package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import org.junit.Test;

public class TestPersistenciaJPA {

    @Test
    public void testConexaoGeracaoTabelas() {

        PersistenciaJPA persistencia = new PersistenciaJPA();
        if (persistencia.conexaoAberta()) {
            System.out.println("Abriu a conexao com o BD via JPA");

            persistencia.fecharConexao();

        } else {
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }

    }

    @Test
    public void testGeracaoFuncionarioLogin() throws Exception {

        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");

            Funcionario f = persistencia.doLogin("11122233344", "1234");

            if(f == null){
                f = new Funcionario();
                f.setCpf("22211133344");
                f.setSenha("1234");

                persistencia.persist(f);
                System.out.println("Cadastrou nova pessoa!");
            }else{
                System.out.println("Encontrou pessoa cadastrado!");
            }

            persistencia.fecharConexao();

        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }

    }

}
