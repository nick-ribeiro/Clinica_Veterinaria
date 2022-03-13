package br.edu.ifsul.cc.lpoo.cv.model.dao;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import br.edu.ifsul.cc.lpoo.cv.model.*;

public class PersistenciaJPA implements InterfacePersistencia {

    public EntityManagerFactory factory;
    public EntityManager entity;

    public PersistenciaJPA() {

        factory = Persistence.createEntityManagerFactory("pu_db_clinica_veterinaria");
        entity = factory.createEntityManager();

    }

    @Override
    public Boolean conexaoAberta() {

        return entity.isOpen();

    }

    @Override
    public void fecharConexao() {

        entity.close();

    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        throw new UnsupportedOperationException("Não disponivel");
    }

    @Override
    public void persist(Object o) throws Exception {

    }

    @Override
    public void remover(Object o) throws Exception {

    }

    @Override
    public List<Medico> listMedicos() throws Exception {
        throw new UnsupportedOperationException("Não disponivel");
    }

    @Override
    public List<Funcionario> listFuncionarios() throws Exception {
        throw new UnsupportedOperationException("Não disponivel");
    }

    @Override
    public List<Consulta> listConsultas() throws Exception {
        throw new UnsupportedOperationException("Não disponivel");
    }

    @Override
    public List<Receita> listReceitas() throws Exception {
        throw new UnsupportedOperationException("Não disponivel");
    }

    public List<Consulta> listConsultasMedico(Object o) throws Exception {
        throw new UnsupportedOperationException("Não disponivel");
    }

    @Override
    public Funcionario doLogin(String cpf, String senha) throws Exception {

        List<Funcionario> list = entity.createNamedQuery("Funcionario.login").setParameter("paramN", cpf).setParameter("paramS", senha).getResultList();
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }
}
