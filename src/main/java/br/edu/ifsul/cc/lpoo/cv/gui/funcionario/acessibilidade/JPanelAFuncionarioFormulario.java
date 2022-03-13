package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;

public class JPanelAFuncionarioFormulario extends JPanel implements ActionListener {

    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;

    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;

    private JPanel pnlDadosCadastrais;

    private GridBagLayout gridBagLayoutDadosCadastrais;
    private JLabel lblNickname;
    private JTextField txfNickname;

    private JLabel lblSenha;
    private JPasswordField txfSenha;

    private JLabel lblCargo;
    private JComboBox cbxCargo;

    private JLabel lblNumeroPis;
    private JTextField txfNumeroPis;

    private JLabel lblNumeroCtps;
    private JTextField txfNumeroCtps;

    private JLabel lblCpf;
    private JTextField txfCpf;

    private JLabel lblCep;
    private JTextField txfCep;

    private JLabel lblComplemento;
    private JTextField txfComplemento;

    private JLabel lblDataCadastro;
    private JTextField txfDataCadastro;

    private JLabel lblDataNascimento;
    private JTextField txfDataNascimento;

    private JLabel lblEmail;
    private JTextField txfEmail;

    private JLabel lblEndereco;
    private JTextField txfEndereco;

    private JLabel lblNumeroCelular;
    private JTextField txfNumeroCelular;

    private JLabel lblRg;
    private JTextField txfRg;

    private Funcionario funcionario;
    private SimpleDateFormat sdf;

    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;

    public JPanelAFuncionarioFormulario(JPanelAFuncionario pnlAFuncionario, Controle controle) {

        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;

        initComponents();

    }

    public void populaCargo() {

        cbxCargo.removeAllItems();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxCargo.getModel();

        model.addElement("Selecione");
        try {
            List<Cargo> listCargos = Arrays.asList(Cargo.values());

            listCargos.forEach(c -> {
                model.addElement(c);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar os Cargos:"+e.getLocalizedMessage(), "Cargos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Funcionario getFuncionariobyFormulario() {

        //validacao do formulario
        String msg = "";
        if(txfNickname.getText().trim().length() < 4)
            msg += "Nome inserido invalido, informe um nome com ao menos 4 digitos \n";

        else if(new String(txfSenha.getPassword()).trim().length() < 3){
            msg += "Senha inserido invalida, informe uma senha com ao menos 3 digitos \n";

        }else if(txfCpf.getText().trim().length() != 11){
            msg += "Cpf inserido invalido, informe um CPF com 11 digitos \n";

        }else if(txfCep.getText().trim().length() != 8){
            msg += "CEP inserido invalido, informe um CEP com 8 digitos \n";

        }else if(txfComplemento.getText().trim().length() < 5){
            msg += "Complemento inserido invalido, informe um complemento com ao menos 5 digitos \n";

        }else if(txfEmail.getText().trim().length() < 10){
            msg += "Email inserido invalido, informe um email com ao menos 10 digitos \n";

        }else if(txfEndereco.getText().trim().length() < 7){
            msg += "Endereco inserido invalido, informe um endereco com ao menos 7 digitos \n";

        }else if(txfNumeroCelular.getText().trim().length() < 9){
            msg += "Numero de Telefone inserido invalido, informe um email com ao menos 9 digitos \n";

        }else if(txfRg.getText().trim().length() != 7){
            msg += "Numero de RG inserido invalido, informe um RG com 7 digitos \n";

        }else if(txfNumeroCtps.getText().trim().length() != 8){
            msg += "Numero CTPS inserido invalido, informe um CTPS com 8 digitos \n";

        }else if(txfNumeroPis.getText().trim().length() != 10){
            msg += "Numero Pis inserido invalido, informe um PIS com 10 digitos \n";
        } else {
            Calendar dtNascimento = Calendar.getInstance();
            try {
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                dtNascimento.setTime(format.parse(txfDataNascimento.getText().trim()));
            } catch (Exception ex) {
                txfDataNascimento.requestFocus();
                msg = "Data de nascimento inserida invalida, informe a data de nascimento no formato: dd/MM/yyyy \n";
            }
        }
        if(msg != ""){
            JOptionPane.showMessageDialog(this, msg);
        }else {
            Funcionario f = new Funcionario();
            f.setNome(txfNickname.getText().trim());
            f.setSenha(new String(txfSenha.getPassword()).trim());
            f.setCpf(txfCpf.getText().trim());
            f.setCep(txfCep.getText().trim());
            f.setComplemento(txfComplemento.getText().trim());
            f.setEmail(txfEmail.getText().trim());
            f.setEndereco(txfEndereco.getText().trim());
            f.setNumero_Celular(txfNumeroCelular.getText().trim());
            f.setRg(txfRg.getText().trim());
            f.setNumero_ctps(txfNumeroCtps.getText().trim());
            f.setNumero_pis(txfNumeroPis.getText().trim());
            f.setCargo((Cargo) cbxCargo.getSelectedItem());


            Calendar data_nasc = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                data_nasc.setTime(sdf.parse(txfDataNascimento.getText().trim()));
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Erro ao gravar data nascimento");
                ex.printStackTrace();
            }
            f.setData_Nascimento(data_nasc);

            if(funcionario != null) {
                f.setData_Cadastro(funcionario.getData_Cadastro());
            }
            return f;
        }
        return null;
    }

    public void setFuncionarioFormulario(Funcionario f) {

        if (f == null) {//se o parametro estiver nullo, limpa o formulario
            txfNickname.setText("");
            txfSenha.setText("");
            txfCpf.setText("");
            txfCep.setText("");
            txfComplemento.setText("");
            txfDataNascimento.setText("");
            txfEmail.setText("");
            txfEndereco.setText("");
            txfNumeroCelular.setText("");
            txfRg.setText("");
            txfDataCadastro.setText("");
            txfNumeroCtps.setText("");
            txfNumeroPis.setText("");
            cbxCargo.setSelectedIndex(0);

            txfNickname.setEditable(true);
            txfCpf.setEditable(true);
            funcionario = null;

        } else {

            funcionario = f;
            txfNickname.setEditable(false);
            txfNickname.setText(funcionario.getNome());
            txfSenha.setText(funcionario.getSenha());
            txfCpf.setEditable(false);
            txfCpf.setText(funcionario.getCpf());
            txfCep.setText(funcionario.getCep());
            txfComplemento.setText(funcionario.getComplemento());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            txfDataNascimento.setText(sdf.format(funcionario.getData_Nascimento().getTime()));
            txfEmail.setText(funcionario.getEmail());
            txfEndereco.setText(funcionario.getEndereco());
            txfNumeroCelular.setText(funcionario.getNumero_Celular());
            txfRg.setText(funcionario.getRg());
            txfDataCadastro.setText(sdf.format(funcionario.getData_Cadastro().getTime()));
            txfNumeroCtps.setText(funcionario.getNumero_ctps());
            txfNumeroPis.setText(funcionario.getNumero_pis());
            cbxCargo.getModel().setSelectedItem(funcionario.getCargo());
        }

    }

    private void initComponents() {

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);

        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);

        lblNickname = new JLabel("Nome:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNickname, posicionador);

        txfNickname = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNickname, posicionador);

        lblSenha = new JLabel("Senha:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblSenha, posicionador);

        txfSenha = new JPasswordField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfSenha, posicionador);

        lblCpf = new JLabel("CPF:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCpf, posicionador);

        txfCpf = new JTextField(11);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfCpf, posicionador);

        lblCep = new JLabel("CEP:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCep, posicionador);

        txfCep = new JTextField(8);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfCep, posicionador);

        lblComplemento = new JLabel("Complemento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblComplemento, posicionador);

        txfComplemento = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfComplemento, posicionador);

        lblDataNascimento = new JLabel("Data de Nascimento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblDataNascimento, posicionador);

        txfDataNascimento = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfDataNascimento, posicionador);

        lblDataCadastro = new JLabel("Data de Cadastro:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblDataCadastro, posicionador);

        txfDataCadastro = new JTextField(10);
        txfDataCadastro.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfDataCadastro, posicionador);

        lblEmail = new JLabel("Email:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblEmail, posicionador);

        txfEmail = new JTextField(30);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfEmail, posicionador);

        lblEndereco = new JLabel("Endereço:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblEndereco, posicionador);

        txfEndereco = new JTextField(30);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfEndereco, posicionador);

        lblNumeroCelular = new JLabel("Número de Celular:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNumeroCelular, posicionador);

        txfNumeroCelular = new JTextField(30);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNumeroCelular, posicionador);

        lblRg = new JLabel("RG:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblRg, posicionador);

        txfRg = new JTextField(7);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfRg, posicionador);

        lblNumeroCtps = new JLabel("Numero CTPS:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNumeroCtps, posicionador);

        txfNumeroCtps = new JTextField(8);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNumeroCtps, posicionador);

        lblNumeroPis = new JLabel("Numero PIS:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNumeroPis, posicionador);

        txfNumeroPis = new JTextField(15);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNumeroPis, posicionador);

        lblCargo = new JLabel("Cargo:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCargo, posicionador);

        cbxCargo = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(cbxCargo, posicionador);

        tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);    //acessibilidade
        btnGravar.setToolTipText("btnGravarFuncionario"); //acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_funcionario");

        pnlSul.add(btnGravar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);    //acessibilidade
        btnCancelar.setToolTipText("btnCancelarFuncionario"); //acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_funcionario");

        pnlSul.add(btnCancelar);

        this.add(pnlSul, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {

            Funcionario f = getFuncionariobyFormulario();//recupera os dados do formulÃ¡rio

            if (f != null) {

                try {

                    pnlAFuncionario.getControle().getConexaoJDBC().persist(f);

                    JOptionPane.showMessageDialog(this, "Funcionario armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);

                    pnlAFuncionario.showTela("tela_funcionario_listagem");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Funcionario! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            } else {

                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }


        } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {


            pnlAFuncionario.showTela("tela_funcionario_listagem");
        }
    }
}
