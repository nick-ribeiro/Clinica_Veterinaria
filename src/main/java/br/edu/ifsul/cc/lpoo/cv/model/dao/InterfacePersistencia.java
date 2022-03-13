package br.edu.ifsul.cc.lpoo.cv.model.dao;

import java.util.List;

import br.edu.ifsul.cc.lpoo.cv.model.*;

public interface InterfacePersistencia {

    public Boolean conexaoAberta();

    public void fecharConexao();

    public Object find(Class c, Object id) throws Exception;

    public void persist(Object o) throws Exception;

    public void remover(Object o) throws Exception;

    public List<Medico> listMedicos() throws Exception;

    public List<Funcionario> listFuncionarios() throws Exception;

    public List<Consulta> listConsultas() throws Exception;

    public List<Receita> listReceitas() throws Exception;

    public List<Consulta> listConsultasMedico(Object o) throws Exception;

    public Funcionario doLogin(String cpf, String senha) throws Exception;
}
