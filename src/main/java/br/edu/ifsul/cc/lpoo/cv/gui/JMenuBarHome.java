package br.edu.ifsul.cc.lpoo.cv.gui;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class JMenuBarHome extends JMenuBar implements ActionListener{

    private JMenu menuArquivo;
    private JMenuItem menuItemHome;
    private JMenuItem menuItemLogout;
    private JMenuItem menuItemSair;

    private JMenu menuCadastro;
    private JMenuItem menuItemMedico;
    private JMenuItem menuItemFuncionario;

    private Controle controle;

    public JMenuBarHome(Controle controle){

        this.controle = controle;

        initComponents();
    }

    private void initComponents(){

        menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A);//ativa o ALT + A para acessar esse menu - acessibilidade
        menuArquivo.setToolTipText("Arquivo"); //acessibilidade
        menuArquivo.setFocusable(true); //acessibilidade

        menuItemHome = new JMenuItem("Home");
        menuItemHome.setToolTipText("Home");
        menuItemHome.setFocusable(true);
        menuItemHome.addActionListener(this);
        menuItemHome.setActionCommand("menu_home");
        menuArquivo.add(menuItemHome);

        menuItemSair = new JMenuItem("Sair");
        menuItemSair.setToolTipText("Sair"); //acessibilidade
        menuItemSair.setFocusable(true);     //acessibilidade

        menuItemLogout = new JMenuItem("Logout");
        menuItemLogout.setToolTipText("Logout"); //acessibilidade
        menuItemLogout.setFocusable(true);     //acessibilidade

        menuItemLogout.addActionListener(this);
        menuItemLogout.setActionCommand("menu_logout");
        menuArquivo.add(menuItemLogout);

        menuItemSair.addActionListener(this);
        menuItemSair.setActionCommand("menu_sair");
        menuArquivo.add(menuItemSair);

        menuCadastro = new JMenu("Cadastros");
        menuCadastro.setMnemonic(KeyEvent.VK_C);//ativa o ALT + C para acessar esse menu - acessibilidade
        menuCadastro.setToolTipText("Cadastro"); //acessibilidade
        menuCadastro.setFocusable(true); //acessibilidade

        menuItemMedico = new JMenuItem("Medico");
        menuItemMedico.setToolTipText("Medico"); //acessibilidade
        menuItemMedico.setFocusable(true); //acessibilidade

        menuItemMedico.addActionListener(this);
        menuItemMedico.setActionCommand("menu_medico");
        menuCadastro.add(menuItemMedico);

        menuItemFuncionario = new JMenuItem("Funcionario");
        menuItemFuncionario.setToolTipText("Funcionario"); //acessibilidade
        menuItemFuncionario.setFocusable(true); //acessibilidade

        menuItemFuncionario.addActionListener(this);
        menuItemFuncionario.setActionCommand("menu_funcionario");
        menuCadastro.add(menuItemFuncionario);

        this.add(menuArquivo);
        this.add(menuCadastro);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(menuItemSair.getActionCommand())){

            //se o usuario clicou no menuitem Sair
            int d = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema? ", "Sair", JOptionPane.YES_NO_OPTION);
            if(d == 0){
                controle.fecharBD();//fecha a conexao com o banco de dados.
                System.exit(0);//finaliza o processo do programa.
            }

        }else if(e.getActionCommand().equals(menuItemMedico.getActionCommand())){
            //se o usuario clicou no menuitem Usuario
            controle.showTela("tela_medico");
        }else if(e.getActionCommand().equals(menuItemFuncionario.getActionCommand())){
            //se o usuario clicou no menuitem Usuario
            controle.showTela("tela_funcionario");
        }else if(e.getActionCommand().equals(menuItemLogout.getActionCommand())){
            controle.showTela("tela_autenticacao");
        }else if(e.getActionCommand().equals(menuItemHome.getActionCommand())) {
            controle.showTela("tela_home");
        }

    }

}