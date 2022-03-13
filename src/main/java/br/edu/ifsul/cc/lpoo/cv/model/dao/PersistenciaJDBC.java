package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PersistenciaJDBC implements InterfacePersistencia {
    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "ribeiro18";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_clinica_veterinaria";
    private Connection con = null;

    public PersistenciaJDBC() {

        try {
            Class.forName(DRIVER);
            System.out.println("Tentando estabelecer conexao JDBC com : " + URL + " ...");

            this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public Boolean conexaoAberta() {
        try {
            if (con != null)
                return !con.isClosed();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void fecharConexao() {
        try {
            this.con.close();
            System.out.println("Fechou conexao JDBC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception {

        if (c == Medico.class) {

            PreparedStatement ps = this.con.prepareStatement("SELECT p.cpf, p.rg, p.nome, p.senha, p.numero_celular,"
                    + " p.email, p.data_cadastro, p.data_nascimento, p.cep, p.endereco, p.complemento, m.numero_crmv"
                    + " FROM tb_pessoa p INNER JOIN tb_medico m ON p.cpf = m.cpf WHERE p.cpf = ?;");

            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Medico m = new Medico();

                m.setCpf(rs.getString("cpf"));
                m.setComplemento(rs.getString("complemento"));
                m.setCep(rs.getString("cep"));

                if (rs.getDate("data_nascimento") != null) {
                    Calendar dtNascimento = Calendar.getInstance();
                    dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                    m.setData_Nascimento(dtNascimento);
                }

                Calendar dtCadastro = Calendar.getInstance();
                dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                m.setData_Cadastro(dtCadastro);

                m.setEmail(rs.getString("email"));
                m.setEndereco(rs.getString("endereco"));
                m.setNome(rs.getString("nome"));
                m.setNumero_Celular(rs.getString("numero_celular"));
                m.setRg(rs.getString("rg"));
                m.setSenha(rs.getString("senha"));
                m.setNumero_crmv(rs.getString("numero_crmv"));

                ps.close();

                return m;
            }
        } else if (c == Consulta.class) {

            PreparedStatement ps = this.con.prepareStatement(
                    "select c.id, c.data, c.data_retorno, c.observacao,"
                            + " c.valor, c.medico_id, c.pet_id, p.nome as nome_medico from tb_consulta c"
                            + " inner join tb_medico m on c.medico_id = m.cpf"
                            + " inner join tb_pessoa p on p.cpf = m.cpf where c.id = ?;");

            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Consulta con = new Consulta();
                con.setId(rs.getInt("id"));

                Calendar dtCadastro = Calendar.getInstance();
                dtCadastro.setTimeInMillis(rs.getDate("data").getTime());
                con.setData(dtCadastro);

                Calendar dtRetorno = Calendar.getInstance();
                dtRetorno.setTimeInMillis(rs.getDate("data_retorno").getTime());
                con.setData_Retorno(dtRetorno);

                con.setObservacao(rs.getString("observacao"));
                con.setValor(rs.getFloat("valor"));

                Medico med = new Medico();

                med.setCpf(rs.getString("medico_id"));
                med.setNome(rs.getString("nome_medico"));
                con.setMedico(med);

                Pet pet = new Pet();

                pet.setId(rs.getInt("pet_id"));
                con.setPet(pet);

                PreparedStatement ps2 = this.con.prepareStatement(
                        "select r.id, r.orientacao from tb_receita r, tb_consulta c where r.consulta_id = c.id and c.id = ?");

                ps2.setInt(1, Integer.parseInt(id.toString()));

                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {

                    Receita r = new Receita();

                    r.setId(rs2.getInt("id"));
                    r.setOrientacao(rs2.getString("orientacao"));

                    con.setReceita(r);
                }
                return con;
            }
        } else if (c == Receita.class) {

            PreparedStatement ps = this.con.prepareStatement("select id, orientacao, consulta_id " +
                    "from tb_receita where id = ?;");

            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Receita r = new Receita();

                r.setId(rs.getInt("id"));
                r.setOrientacao(rs.getString("orientacao"));

                Consulta con = new Consulta();

                con.setId(rs.getInt("consulta_id"));
                r.setConsulta(con);

                return r;
            }
        } else if (c == Funcionario.class) {

            PreparedStatement ps = this.con.prepareStatement("SELECT p.cpf, p.rg, p.nome, p.senha, p.numero_celular,"
                    + " p.email, p.data_cadastro, p.data_nascimento, p.cep, p.endereco, p.complemento, f.numero_ctps, f.numero_pis, f.cargo"
                    + " FROM tb_pessoa p INNER JOIN tb_funcionario f ON p.cpf = f.cpf WHERE p.cpf = ?;");

            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Funcionario f = new Funcionario();

                f.setCpf(rs.getString("cpf"));
                f.setComplemento(rs.getString("complemento"));
                f.setCep(rs.getString("cep"));

                if (rs.getDate("data_nascimento") != null) {
                    Calendar dtNascimento = Calendar.getInstance();
                    dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                    f.setData_Nascimento(dtNascimento);
                }

                Calendar dtCadastro = Calendar.getInstance();
                dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                f.setData_Cadastro(dtCadastro);

                f.setEmail(rs.getString("email"));
                f.setEndereco(rs.getString("endereco"));
                f.setNome(rs.getString("nome"));
                f.setNumero_Celular(rs.getString("numero_celular"));
                f.setRg(rs.getString("rg"));
                f.setSenha(rs.getString("senha"));
                f.setCargo(Cargo.valueOf(rs.getString("cargo")));
                f.setNumero_ctps(rs.getString("numero_ctps"));
                f.setNumero_pis(rs.getString("numero_pis"));

                ps.close();

                return f;
            }
        }
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {

        if (o instanceof Medico) {

            Medico m = (Medico) o;

            if(m.getData_Cadastro() == null) {

                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                        + "(cpf, complemento, cep, data_nascimento, data_cadastro, email, endereco, nome, numero_celular, rg, senha, dados) values "
                        + "(?, ?, ?, ?, now(), ?, ?, ?, ?, ?, ?, 'M'); ");

                ps.setString(1, m.getCpf());
                ps.setString(2, m.getComplemento());
                ps.setString(3, m.getCep());

                if(m.getData_Nascimento() != null) {
                    ps.setTimestamp(4, new Timestamp(m.getData_Nascimento().getTimeInMillis()));
                }else {
                    ps.setTimestamp(4, null);
                }

                ps.setString(5, m.getEmail());
                ps.setString(6, m.getEndereco());
                ps.setString(7, m.getNome());
                ps.setString(8, m.getNumero_Celular());
                ps.setString(9, m.getRg());
                ps.setString(10, m.getSenha());

                ps.execute();

                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_medico "
                        + "(numero_crmv, cpf) values "
                        + "(?, ?); ");

                ps2.setString(1, m.getNumero_crmv());
                ps2.setString(2, m.getCpf());

                ps2.execute();

                System.out.println("Medico com o CPF: " + m.getCpf() + " cadastrado com sucesso!\n");
            }
            else {

                PreparedStatement ps = this.con.prepareStatement("update tb_medico set numero_crmv = ? where cpf = ?; ");

                ps.setString(1, m.getNumero_crmv());
                ps.setString(2, m.getCpf());

                ps.execute();

                PreparedStatement ps2 = this.con.prepareStatement("update tb_pessoa set "
                        + "nome = ?, dados = 'M', cep = ?, complemento = ?, data_nascimento = ?,"
                        + " email = ?, endereco = ?, numero_celular = ?, rg = ?, senha = ? where cpf = ?");

                ps2.setString(1, m.getNome());
                ps2.setString(2, m.getCep());
                ps2.setString(3, m.getComplemento());
                ps2.setTimestamp(4, new Timestamp(m.getData_Nascimento().getTimeInMillis()));
                ps2.setString(5, m.getEmail());
                ps2.setString(6, m.getEndereco());
                ps2.setString(7, m.getNumero_Celular());
                ps2.setString(8, m.getRg());
                ps2.setString(9, m.getSenha());
                ps2.setString(10, m.getCpf());

                ps2.execute();

                System.out.println("Medico com o CPF: " + m.getCpf() + " alterado com sucesso!\n");
            }
        } else if (o instanceof Consulta) {

            Consulta c = (Consulta) o;

            if (c.getData() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_consulta"
                        + " (id, data, data_retorno, observacao, valor, medico_id, pet_id)"
                        + " values (nextval('seq_consulta_id'), now(), ?, ?, ?, ?, ?) returning id");

                ps.setTimestamp(1, new Timestamp(c.getData_Retorno().getTimeInMillis()));
                ps.setString(2, c.getObservacao());
                ps.setFloat(3, c.getValor());
                ps.setString(4, c.getMedico().getCpf());
                ps.setInt(5, c.getPet().getId());

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    c.setId(rs.getInt(1));
                }

            } else {

                PreparedStatement ps = this.con.prepareStatement("update tb_consulta set"
                        + " data_retorno = ?, observacao = ?, valor = ?, medico_id = ?, pet_id = ?  where id = ?;");

                ps.setTimestamp(1, new Timestamp(c.getData_Retorno().getTimeInMillis()));
                ps.setString(2, c.getObservacao());
                ps.setFloat(3, c.getValor());
                ps.setString(4, c.getMedico().getCpf());
                ps.setInt(5, c.getPet().getId());
                ps.setInt(6, c.getId());

            }
        } else if (o instanceof Receita) {

            Receita r = (Receita) o;

            if (r.getId() == null) {

                PreparedStatement ps = this.con.prepareStatement("insert into tb_receita "
                        + "(id, orientacao, consulta_id) values (nextval('seq_receita_id'), ?, ?) returning id");

                ps.setString(1, r.getOrientacao());
                ps.setInt(2, r.getConsulta().getId());

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    r.setId(rs.getInt(1));
                }

            } else {
                PreparedStatement ps2 = this.con.prepareStatement("update tb_receita set"
                        + " orientacao = ?, consulta_id = ? where id = ?;");

                ps2.setString(1, r.getOrientacao());
                ps2.setInt(2, r.getConsulta().getId());

                System.out.println("A receita com o numero de Id: " + r.getId() + " foi atualizada no sistema");
            }
        } else if (o instanceof Funcionario) {
            Funcionario f = (Funcionario) o;

            if(f.getData_Cadastro() == null) {

                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                        + "(cpf, complemento, cep, data_nascimento, data_cadastro, email, endereco, nome, numero_celular, rg, senha, dados) values "
                        + "(?, ?, ?, ?, now(), ?, ?, ?, ?, ?, ?, 'F'); ");

                ps.setString(1, f.getCpf());
                ps.setString(2, f.getComplemento());
                ps.setString(3, f.getCep());

                if(f.getData_Nascimento() != null) {
                    ps.setTimestamp(4, new Timestamp(f.getData_Nascimento().getTimeInMillis()));
                }else {
                    ps.setTimestamp(4, null);
                }

                ps.setString(5, f.getEmail());
                ps.setString(6, f.getEndereco());
                ps.setString(7, f.getNome());
                ps.setString(8, f.getNumero_Celular());
                ps.setString(9, f.getRg());
                ps.setString(10, f.getSenha());

                ps.execute();

                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_funcionario "
                        + "(cargo, numero_ctps, numero_pis, cpf) values "
                        + "(?, ?, ?, ?); ");

                ps2.setString(1, f.getCargo().toString());
                ps2.setString(2, f.getNumero_ctps());
                ps2.setString(3, f.getNumero_pis());
                ps2.setString(4, f.getCpf());

                ps2.execute();

                System.out.println("Funcionario com o CPF: " + f.getCpf() + " cadastrado com sucesso!\n");
            }
            else {

                PreparedStatement ps = this.con.prepareStatement("update tb_funcionario set cargo = ?, numero_ctps = ?, numero_pis = ? where cpf = ?; ");

                ps.setString(1, f.getCargo().toString());
                ps.setString(2, f.getNumero_ctps());
                ps.setString(3, f.getNumero_pis());
                ps.setString(4, f.getCpf());

                ps.execute();

                PreparedStatement ps2 = this.con.prepareStatement("update tb_pessoa set "
                        + "nome = ?, dados = 'F', cep = ?, complemento = ?, data_nascimento = ?,"
                        + " email = ?, endereco = ?, numero_celular = ?, rg = ?, senha = ? where cpf = ?");

                ps2.setString(1, f.getNome());
                ps2.setString(2, f.getCep());
                ps2.setString(3, f.getComplemento());
                ps2.setTimestamp(4, new Timestamp(f.getData_Nascimento().getTimeInMillis()));
                ps2.setString(5, f.getEmail());
                ps2.setString(6, f.getEndereco());
                ps2.setString(7, f.getNumero_Celular());
                ps2.setString(8, f.getRg());
                ps2.setString(9, f.getSenha());
                ps2.setString(10, f.getCpf());

                ps2.execute();

                System.out.println("Funcionario com o CPF: " + f.getCpf() + " alterado com sucesso!\n");
            }
        }
    }

    @Override
    public void remover(Object o) throws Exception {

        if (o instanceof Medico) {

            Medico m = (Medico) o;

            PreparedStatement ps = this.con
                    .prepareStatement("select id, medico_id from tb_consulta where medico_id = ?;");

            ps.setString(1, m.getCpf());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));

                PreparedStatement ps2 = this.con
                        .prepareStatement("delete from tb_receita where consulta_id = ?;");

                ps2.setInt(1, c.getId());
                ps2.execute();
            }

            PreparedStatement ps3 = this.con
                    .prepareStatement("delete from tb_consulta where medico_id = ?;");

            ps3.setString(1, m.getCpf());
            ps3.execute();
            System.out.println("As consultas e receitas do medico com o numero de cpf: " + m.getCpf()
                    + " acabaram de ser removidas do sistemas \n");

            PreparedStatement ps4 = this.con.prepareStatement("delete from tb_medico where cpf = ?;");

            ps4.setString(1, m.getCpf());
            ps4.execute();

            PreparedStatement ps5 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?;");

            ps5.setString(1, m.getCpf());
            ps5.execute();

            System.out.println("O medico com o numero de cpf: " + m.getCpf() + " acaba de ser removido do sistema \n");

        } else if (o instanceof Consulta) {

            Consulta c = (Consulta) o;

            PreparedStatement ps = this.con
                    .prepareStatement("select id, orientacao, consulta_id from tb_receita where consulta_id = ?;");

            ps.setInt(1, c.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PreparedStatement ps2 = this.con
                        .prepareStatement("delete from tb_receita where consulta_id = ?;");

                ps2.setInt(1, c.getId());
                ps2.execute();

                System.out.println("As receitas presentes na consulta de Id: " + c.getId()
                        + " acabaram de ser removidas do sistema \n");
            }

            PreparedStatement ps3 = this.con.prepareStatement("delete from tb_consulta where id = ?;");

            ps3.setInt(1, c.getId());
            ps3.execute();

            System.out.println("A consulta de Id: " + c.getId() + " acaba de ser removida do sistema \n");

        } else if (o instanceof Receita) {
            Receita r = (Receita) o;

            PreparedStatement ps = this.con.prepareStatement("delete from tb_receita where id = ?;");

            ps.setInt(1, r.getId());
            ps.execute();

            System.out.println("A receita de Id: " + r.getId() + " acaba de ser removida do sistema");

        } else if(o instanceof Funcionario) {

            Funcionario f = (Funcionario) o;

            PreparedStatement ps = this.con.prepareStatement("delete from tb_funcionario where cpf = (?);");
            ps.setString(1, f.getCpf());
            ps.execute();

            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = (?);");
            ps2.setString(1, f.getCpf());
            ps2.execute();

            System.out.println("Funcionario com CPF: " + f.getCpf() + " deletado com sucesso.");
        }
    }

    @Override
    public List<Medico> listMedicos() throws Exception {

        List<Medico> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.cep, p.complemento, "
                + "p.data_nascimento, p.data_cadastro, p.email, p.endereco, p.nome, p.numero_celular,"
                + " p.rg, p.senha, m.numero_crmv from tb_pessoa as p INNER JOIN tb_medico as m "
                + "on p.cpf = m.cpf;\n");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while(rs.next()) {

            Medico m = new Medico();
            m.setCpf(rs.getString("cpf"));
            m.setComplemento(rs.getString("complemento"));
            m.setCep(rs.getString("cep"));

            Calendar dtCadastro = Calendar.getInstance();
            dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            m.setData_Cadastro(dtCadastro);

            if(rs.getDate("data_nascimento") != null) {
                Calendar dtNascimento = Calendar.getInstance();
                dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                m.setData_Nascimento(dtNascimento);
            }

            m.setEmail(rs.getString("email"));
            m.setEndereco(rs.getString("endereco"));
            m.setNome(rs.getString("nome"));
            m.setNumero_Celular(rs.getString("numero_celular"));
            m.setRg(rs.getString("rg"));
            m.setSenha(rs.getString("senha"));
            m.setNumero_crmv(rs.getString("numero_crmv"));

            lista.add(m);
        }

        return lista;
    }

    @Override
    public List<Funcionario> listFuncionarios() throws Exception {

        List<Funcionario> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.cep, p.complemento, "
                + "p.data_nascimento, p.data_cadastro, p.email, p.endereco, p.nome, p.numero_celular,"
                + " p.rg, p.senha, f.cargo, f.numero_ctps, f.numero_pis from tb_pessoa as p INNER JOIN tb_funcionario as f "
                + "on p.cpf = f.cpf;\n");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while(rs.next()) {

            Funcionario f = new Funcionario();
            f.setCpf(rs.getString("cpf"));
            f.setComplemento(rs.getString("complemento"));
            f.setCep(rs.getString("cep"));

            Calendar dtCadastro = Calendar.getInstance();
            dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            f.setData_Cadastro(dtCadastro);

            if(rs.getDate("data_nascimento") != null) {
                Calendar dtNascimento = Calendar.getInstance();
                dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                f.setData_Nascimento(dtNascimento);
            }

            f.setEmail(rs.getString("email"));
            f.setEndereco(rs.getString("endereco"));
            f.setNome(rs.getString("nome"));
            f.setNumero_Celular(rs.getString("numero_celular"));
            f.setRg(rs.getString("rg"));
            f.setSenha(rs.getString("senha"));
            f.setCargo(Cargo.valueOf(rs.getString("cargo")));
            f.setNumero_ctps(rs.getString("numero_ctps"));
            f.setNumero_pis(rs.getString("numero_pis"));

            lista.add(f);
        }

        return lista;
    }

    @Override
    public List<Consulta> listConsultas() throws Exception {

        List<Consulta> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select c.id, c.data, c.data_retorno, c.observacao,"
                + " c.valor, c.medico_id, c.pet_id, p.nome as nome_medico from tb_consulta c "
                + " inner join tb_medico m on c.medico_id = m.cpf"
                + " inner join tb_pessoa p on p.cpf = m.cpf;");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while (rs.next()) {

            Consulta con = new Consulta();

            con.setId(rs.getInt("id"));

            Calendar dtCadastro = Calendar.getInstance();
            dtCadastro.setTimeInMillis(rs.getDate("data").getTime());
            con.setData(dtCadastro);

            Calendar dtRetorno = Calendar.getInstance();
            dtRetorno.setTimeInMillis(rs.getDate("data_retorno").getTime());
            con.setData_Retorno(dtRetorno);

            con.setObservacao(rs.getString("observacao"));
            con.setValor(rs.getFloat("valor"));

            Medico med = new Medico();

            med.setCpf(rs.getString("medico_id"));
            med.setNome(rs.getString("nome_medico"));
            con.setMedico(med);

            Pet pet = new Pet();

            pet.setId(rs.getInt("pet_id"));
            con.setPet(pet);

            PreparedStatement ps2 = this.con
                    .prepareStatement("select id, orientacao from tb_receita where consulta_id = ?");

            ps2.setInt(1, con.getId());

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {

                Receita r = new Receita();

                r.setId(rs2.getInt("id"));
                r.setOrientacao(rs2.getString("orientacao"));

                con.setReceita(r);
            }
            lista.add(con);
        }
        return lista;
    }

    @Override
    public List<Receita> listReceitas() throws Exception {

        List<Receita> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select id, orientacao, consulta_id from tb_receita;");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while (rs.next()) {

            Receita r = new Receita();

            r.setId(rs.getInt("id"));
            r.setOrientacao(rs.getString("orientacao"));

            Consulta con = new Consulta();

            con.setId(rs.getInt("consulta_id"));
            r.setConsulta(con);

            lista.add(r);
        }
        return lista;
    }

    @Override
    public List<Consulta> listConsultasMedico(Object o) throws Exception {

        List<Consulta> lista = null;

        lista = new ArrayList();
        if (o instanceof Medico) {
            Medico m = (Medico) o;

            PreparedStatement ps = this.con
                    .prepareStatement("select c.id, c.data, c.data_retorno, c.observacao,"
                            + " c.valor, c.medico_id, c.pet_id, p.nome as nome_medico from tb_consulta c "
                            + " inner join tb_medico m on c.medico_id = m.cpf"
                            + " inner join tb_pessoa p on m.cpf = p.cpf"
                            + " where medico_id = ?;");

            ps.setString(1, m.getCpf());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Consulta con = new Consulta();

                con.setId(rs.getInt("id"));

                Calendar dtCadastro = Calendar.getInstance();
                dtCadastro.setTimeInMillis(rs.getDate("data").getTime());
                con.setData(dtCadastro);

                Calendar dtRetorno = Calendar.getInstance();
                dtRetorno.setTimeInMillis(rs.getDate("data_retorno").getTime());
                con.setData_Retorno(dtRetorno);

                con.setObservacao(rs.getString("observacao"));
                con.setValor(rs.getFloat("valor"));

                Medico med = new Medico();

                med.setCpf(rs.getString("medico_id"));
                med.setNome(rs.getString("nome_medico"));
                con.setMedico(med);

                Pet pet = new Pet();

                pet.setId(rs.getInt("pet_id"));
                con.setPet(pet);

                PreparedStatement ps2 = this.con
                        .prepareStatement("select id, orientacao, consulta_id from tb_receita where consulta_id = ?");

                ps2.setInt(1, con.getId());

                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {

                    Receita r = new Receita();

                    r.setId(rs2.getInt("id"));
                    r.setOrientacao(rs2.getString("orientacao"));

                    Consulta c = new Consulta();

                    c.setId(rs2.getInt("consulta_id"));
                    r.setConsulta(con);

                    con.setReceita(r);
                }
                lista.add(con);
            }
        } else {
            System.out.println("O que foi informado ao sistema nao eh um medico");
        }
        return lista;
    }

    @Override
    public Funcionario doLogin(String cpf, String senha) throws Exception {

        Funcionario f = null;

        PreparedStatement ps =
                this.con.prepareStatement("select p.cpf, p.senha from tb_pessoa p where p.cpf = ? and p.senha = ? ");

        ps.setString(1, cpf);
        ps.setString(2, senha);

        ResultSet rs = ps.executeQuery();//o ponteiro do REsultSet inicialmente está na linha -1

        if(rs.next()){//se a matriz (ResultSet) tem uma linha
            f = new Funcionario();
            f.setCpf(rs.getString("cpf"));
        }

        ps.close();
        return f;
    }

}