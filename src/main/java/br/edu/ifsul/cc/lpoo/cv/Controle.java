package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade.JPanelAFuncionario;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cv.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cv.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cv.gui.medico.acessibilidade.JPanelAMedico;
import javax.swing.JOptionPane;

public class Controle {

    private PersistenciaJDBC conexaoJDBC;

    private JFramePrincipal frame;

    private JPanelAutenticacao pnlAutenticacao;

    private JMenuBarHome menuBar;

    private JPanelHome pnlHome; // painel de boas vindas (home)

    private JPanelAMedico pnlAMedico; // painel de manutencao para medico.

    private JPanelAFuncionario pnlAFuncionario; // painel de manutencao para funcionario.

    public Controle(){

    }

    public boolean conectarBD() throws Exception {

        conexaoJDBC = new PersistenciaJDBC();

        if(getConexaoJDBC()!= null){

            return getConexaoJDBC().conexaoAberta();
        }

        return false;
    }

    public void fecharBD(){

        System.out.println("Fechando conexao com o Banco de Dados");
        getConexaoJDBC().fecharConexao();
    }

    public void initComponents(){

        frame = new JFramePrincipal();

        pnlAutenticacao = new JPanelAutenticacao(this);

        menuBar = new JMenuBarHome(this);

        pnlHome = new JPanelHome(this);

        pnlAMedico = new JPanelAMedico(this);

        pnlAFuncionario = new JPanelAFuncionario(this);

        frame.addTela(pnlAutenticacao, "tela_autenticacao");
        frame.addTela(pnlHome, "tela_home");

        frame.addTela(pnlAMedico, "tela_medico");
        frame.addTela(pnlAFuncionario, "tela_funcionario");

        frame.showTela("tela_autenticacao");

        frame.setVisible(true);


    }

    public void autenticar(String cpf, String senha) {

        try{
           Funcionario f =  conexaoJDBC.doLogin(cpf, senha);

            if(f != null){

                JOptionPane.showMessageDialog(pnlAutenticacao, "CPF: "+ f.getCpf() +" autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);

                frame.setJMenuBar(menuBar);
                frame.showTela("tela_home");

            }else{
                JOptionPane.showMessageDialog(pnlAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(pnlAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showTela(String nomeTela){

        if(nomeTela.equals("tela_autenticacao")){
            pnlAutenticacao.requestFocus();
        }else if(nomeTela.equals("tela_medico")){
            pnlAMedico.showTela("tela_medico_listagem");
        }else{
            pnlAFuncionario.showTela("tela_funcionario_listagem");
        }
        frame.showTela(nomeTela);
   }

    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }

}