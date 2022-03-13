package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.*;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TestPersistenciaJDBC {
    
    // @Test
    public void testConexao() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {
            System.out.println("abriu a conexao com o BD via JDBC");

            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }

    }

    @Test
    public void testPersistenciaCV() throws Exception {
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<Medico> lista = persistencia.listMedicos();

            int cont = 0;
            if (!lista.isEmpty()) {
                cont++;
                for (Medico m : lista) {
                    System.out.println("Cpf: " + m.getCpf()
                            + ", Rg: " + m.getRg()
                            + ", Nome: " + m.getNome()
                            + ", Senha: " + m.getSenha()
                            + ", Numero de Celular: " + m.getNumero_Celular()
                            + ", Email: " + m.getEmail()
                            + ", Data de Nascimento: " + sdf.format(m.getData_Nascimento().getTime())
                            + ", Data de Cadastro: " + sdf.format(m.getData_Cadastro().getTime())
                            + ", Cep: " + m.getCep()
                            + ", Endereco: " + m.getEndereco()
                            + ", Complemento: " + m.getComplemento()
                            + ", Numero Crmv: " + m.getNumero_crmv());

                    List<Consulta> lista2 = persistencia.listConsultasMedico(m);

                    if (!lista2.isEmpty()) {
                        for (Consulta c : lista2) {
                            System.out.println("Id: " + c.getId()
                                    + " Data: " + sdf.format(c.getData().getTime())
                                    + ", Data de retorno: " + sdf.format(c.getData_Retorno().getTime())
                                    + ", Observacao: " + c.getObservacao()
                                    + ", Valor: " + c.getValor()
                                    + ", Pet: " + c.getPet().getId()
                                    + ", Medico: " + c.getMedico().getNome()
                                    + ", Id:  " + c.getMedico().getCpf());

                            List<Receita> r = c.getReceitas();

                            if (r != null && !r.isEmpty()) {
                                for (Receita res : r) {
                                    System.out.println("Id: " + res.getId()
                                            + "Orientacao: " + res.getOrientacao());
                                }
                            }
                        }
                    }
                    /*if (cont == 1) {
                        System.out.print("\n");
                        persistencia.remover(m);
                    }*/

                }

            } else {
                System.out.println("O sistema nao encontrou um medico");
                Calendar dtNascimento = Calendar.getInstance();
                Medico query = new Medico();

                Medico m = new Medico();
                m.setCpf("56672160000");
                m.setRg("4500224");
                m.setNome("Sueli");
                m.setSenha("1234");
                m.setNumero_Celular("54991002322");
                m.setEmail("suelivera@gmail.com");

                dtNascimento.setTime(sdf.parse("04/01/1962"));
                m.setData_Nascimento(dtNascimento);

                m.setCep("91040632");
                m.setEndereco("Rua Tito Chaves - Jardim Floresta");
                m.setComplemento("N 222");
                m.setNumero_crmv("12211");

                persistencia.persist(m);
                query = (Medico) persistencia.find(m.getClass(), m.getCpf());
                System.out.println("Medico inserido: " + query.getNome());

                Medico med = new Medico();
                med.setCpf("81935151029");
                med.setRg("1442383");
                med.setNome("Alexandre Pietro");
                med.setSenha("12333");
                med.setNumero_Celular("54989110024");
                med.setEmail("alexandreprietro@gmail.com");

                dtNascimento.setTime(sdf.parse("01/01/1965"));
                med.setData_Nascimento(dtNascimento);

                med.setCep("90830632");
                med.setEndereco("Acesso I - Santa Tereza");
                med.setComplemento("N 180");
                med.setNumero_crmv("155621");

                persistencia.persist(med);
                query = (Medico) persistencia.find(med.getClass(), med.getCpf());
                System.out.println("Medico inserido: " + query.getNome());

                List<Consulta> lista3 = persistencia.listConsultas();

                if (lista3.isEmpty()) {
                    System.out.println("O sistema nao encontrou uma consulta");
                    Calendar dtRetorno = Calendar.getInstance();
                    Float valor = 384.99f;
                    Consulta query2 = new Consulta();

                    Pet p = new Pet();
                    p.setId(2);

                    Consulta con = new Consulta();

                    dtRetorno.setTime(sdf.parse("15/06/2022"));
                    con.setData_Retorno(dtRetorno);

                    con.setObservacao("Nenhuma receita recomendada na consulta");
                    con.setValor(valor);
                    con.setMedico(m);
                    con.setPet(p);

                    persistencia.persist(con);
                    query2 = (Consulta) persistencia.find(con.getClass(), con.getId());
                    System.out.println("A consulta com ID: " + query2.getId()
                            + " foi inserida no sistema. O medico responsavel pela consulta eh: "
                            + query2.getMedico().getNome());

                    con = new Consulta();
                    dtRetorno.setTime(sdf.parse("17/05/2022"));
                    con.setData_Retorno(dtRetorno);
                    con.setObservacao("Ha receitas recomendadas na consulta");
                    con.setValor(valor);
                    con.setMedico(med);
                    con.setPet(p);

                    persistencia.persist(con);
                    query2 = (Consulta) persistencia.find(con.getClass(), con.getId());
                    System.out.println("A consulta com ID: " + query2.getId()
                            + " foi inserida no sistema. O medico responsavel pela consulta eh: "
                            + query2.getMedico().getNome());

                    List<Receita> lista4 = persistencia.listReceitas();

                    if (lista4.isEmpty()) {
                        System.out.println("O sistema nao encontrou uma receita");
                        Receita query3 = new Receita();

                        Receita r = new Receita();

                        r.setOrientacao("Observar o comportamento do animal");
                        r.setConsulta(con);
                        persistencia.persist(r);
                        query3 = (Receita) persistencia.find(r.getClass(), r.getId());
                        System.out
                                .println("A receita com ID: " + query3.getId()
                                        + " foi inserida no sistema. Ela corresponde a consulta de ID: "
                                        + query3.getConsulta().getId());

                        r = new Receita();
                        r.setOrientacao("Dar os dois comprimidos com 6 horas de diferenca entre eles");
                        r.setConsulta(con);
                        persistencia.persist(r);
                        query3 = (Receita) persistencia.find(r.getClass(), r.getId());
                        System.out
                                .println("A receita com ID: " + query3.getId()
                                        + " foi inserida no sistema. Ela corresponde a consulta de ID: "
                                        + query3.getConsulta().getId());
                    }
                }
            }
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC");
        }
    }

    @Test
    public void testPersistenciaFuncionario() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();

        if (persistencia.conexaoAberta()) {

            Funcionario f = new Funcionario();

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Calendar dtNascimento = Calendar.getInstance();

            f.setCpf("11122233311");
            f.setNome("Jonas");
            f.setEmail("jonas@gmail.com");
            f.setSenha("1233");

            dtNascimento.setTime(formatter.parse("23/01/1990"));
            f.setData_Nascimento(dtNascimento);

            f.setNumero_Celular("989981122");
            f.setRg("1234555");
            f.setCep("122111");
            f.setEndereco("Rua Tiradentes, 623");
            f.setComplemento("Apto 1304");
            f.setNumero_ctps("09188211");
            f.setNumero_pis("01283911");
            f.setCargo(Cargo.AUXILIAR_VETERINARIO);
            persistencia.persist(f);
        }

        persistencia.fecharConexao();
    }

    // @Test
    public void testListPersistenciaMedico() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {

            List<Medico> lista = persistencia.listMedicos();

            if (!lista.isEmpty()) {
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Medico(s) encontrado(s):");

                for (Medico m : lista) {
                    System.out.println("Cpf: " + m.getCpf()
                            + ", Rg: " + m.getRg()
                            + ", Nome: " + m.getNome()
                            + ", Senha: " + m.getSenha()
                            + ", Numero de Celular: " + m.getNumero_Celular()
                            + ", Email: " + m.getEmail()
                            + ", Data de Nascimento: " + sdf.format(m.getData_Nascimento().getTime())
                            + ", Data de Cadastro: " + sdf.format(m.getData_Cadastro().getTime())
                            + ", Cep: " + m.getCep()
                            + ", Endereco: " + m.getEndereco()
                            + ", Complemento: " + m.getComplemento()
                            + ", Numero Crmv: " + m.getNumero_crmv());

                    //persistencia.remover(m);
                }
            } else {
                System.out.println("Nao foi encontrado um medico no sistema");

                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar dtNascimento = Calendar.getInstance();

                Medico m = new Medico();

                m.setCpf("81935151029");
                m.setRg("1442383");
                m.setNome("Alexandre Pietro");
                m.setSenha("12333");
                m.setNumero_Celular("54989110024");
                m.setEmail("alexandreprietro@gmail.com");

                dtNascimento.setTime(sdf.parse("01/01/1965"));
                m.setData_Nascimento(dtNascimento);

                m.setCep("90830632");
                m.setEndereco("Acesso I - Santa Tereza");
                m.setComplemento("N 180");
                m.setNumero_crmv("155621");

                persistencia.persist(m);
            }

            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

    // @Test
    public void testListPersistenciaConsulta() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {

            List<Consulta> lista = persistencia.listConsultas();
            List<Medico> lista2 = persistencia.listMedicos();

            if (!lista.isEmpty()) {
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Consulta(s) cadastrada(s):");

                for (Consulta c : lista) {
                    System.out.println("Id: " + c.getId()
                            + " Data: " + sdf.format(c.getData().getTime())
                            + ", Data de retorno: " + sdf.format(c.getData_Retorno().getTime())
                            + ", Observacao: " + c.getObservacao()
                            + ", Valor: " + c.getValor()
                            + ", Pet: " + c.getPet().getId()
                            + ", Medico: " + c.getMedico().getNome()
                            + ", Id:  " + c.getMedico().getCpf());

                    List<Receita> r = c.getReceitas();

                    if (r != null && !r.isEmpty()) {
                        for (Receita res : r) {
                            System.out.println("Id: " + res.getId()
                                    + "Orientacao: " + res.getOrientacao());
                        }
                    }

                    //persistencia.remover(c);
                }
            } else if (!lista2.isEmpty()) {
                System.out.println("Nao foi encontrado uma consulta no sistema");

                Medico med = new Medico();
                for (Medico m : lista2) {
                    med.setCpf(m.getCpf());
                    break;
                }

                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar dtRetorno = Calendar.getInstance();
                Float valor = 384.99f;

                Pet p = new Pet();
                p.setId(2);

                Consulta c = new Consulta();

                dtRetorno.setTime(sdf.parse("15/06/2022"));
                c.setData_Retorno(dtRetorno);

                c.setObservacao("Nenhuma receita recomendada na consulta");
                c.setValor(valor);
                c.setMedico(med);
                c.setPet(p);

                persistencia.persist(c);
            }

            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

    // @Test
    public void testListPersistenciaReceita() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {

            List<Receita> lista = persistencia.listReceitas();
            List<Consulta> lista2 = persistencia.listConsultas();

            if (!lista.isEmpty()) {

                for (Receita r : lista) {
                    System.out.println("Id: " + r.getId()
                            + ", Orientacao: " + r.getOrientacao()
                            + ", Consulta: " + r.getConsulta().getId());

                    //persistencia.remover(r);
                }
            } else if (!lista2.isEmpty()) {
                System.out.println("Nenhuma Receita foi encontrada! ");

                Receita r = new Receita();
                Consulta c = new Consulta();
                for (Consulta con : lista2) {
                    c.setId(con.getId());
                    break;
                }

                r.setOrientacao("Dar os dois comprimidos com 6 horas de diferenca entre eles");
                r.setConsulta(c);
                persistencia.persist(r);
            }

            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

}