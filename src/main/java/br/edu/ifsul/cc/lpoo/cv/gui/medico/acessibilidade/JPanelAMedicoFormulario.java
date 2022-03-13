package br.edu.ifsul.cc.lpoo.cv.gui.medico.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class JPanelAMedicoFormulario extends JPanel implements ActionListener {

        private JPanelAMedico pnlAMedico;
        private Controle controle;

        private BorderLayout borderLayout;
        private JTabbedPane tbpAbas;

        private JPanel pnlDadosCadastrais;
        private JPanel pnlCentroDadosCadastrais;

        private GridBagLayout gridBagLayoutDadosCadastrais;
        private JLabel lblNickname;
        private JTextField txfNickname;

        private JLabel lblSenha;
        private JPasswordField txfSenha;

        private JLabel lblNumeroCrmv;
        private JTextField txfNumeroCrmv;

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

        private Medico medico;
        private SimpleDateFormat sdf;

        private JPanel pnlSul;
        private JButton btnGravar;
        private JButton btnCancelar;

        public JPanelAMedicoFormulario(JPanelAMedico pnlAMedico, Controle controle) {

            this.pnlAMedico = pnlAMedico;
            this.controle = controle;

            initComponents();

        }

        public Medico getMedicobyFormulario() {

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

            }else if(txfNumeroCrmv.getText().trim().length() != 8){
                msg += "Numero CRMV inserido invalido, informe um CRMV com 8 digitos \n";

            }else {
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

                Medico m = new Medico();
                m.setNome(txfNickname.getText().trim());
                m.setSenha(new String(txfSenha.getPassword()).trim());
                m.setCpf(txfCpf.getText().trim());
                m.setCep(txfCep.getText().trim());
                m.setComplemento(txfComplemento.getText().trim());
                m.setEmail(txfEmail.getText().trim());
                m.setEndereco(txfEndereco.getText().trim());
                m.setNumero_Celular(txfNumeroCelular.getText().trim());
                m.setRg(txfRg.getText().trim());
                m.setNumero_crmv(txfNumeroCrmv.getText().trim());

                Calendar data_nasc = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    data_nasc.setTime(sdf.parse(txfDataNascimento.getText().trim()));
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(this, "Erro ao gravar data nascimento");
                    ex.printStackTrace();
                }
                m.setData_Nascimento(data_nasc);

                if(medico != null) {
                    m.setData_Cadastro(medico.getData_Cadastro());
                }
                return m;
            }
            return null;
        }

        public void setMedicoFormulario(Medico m) {

            if (m == null) {//se o parametro estiver nullo, limpa o formulario
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
                txfNumeroCrmv.setText("");

                txfNickname.setEditable(true);
                txfCpf.setEditable(true);
                medico = null;

            } else {

                medico = m;
                txfNickname.setEditable(false);
                txfNickname.setText(medico.getNome());
                txfSenha.setText(medico.getSenha());
                txfCpf.setEditable(false);
                txfCpf.setText(medico.getCpf());
                txfCep.setText(medico.getCep());
                txfComplemento.setText(medico.getComplemento());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                txfDataNascimento.setText(sdf.format(medico.getData_Nascimento().getTime()));
                txfEmail.setText(medico.getEmail());
                txfEndereco.setText(medico.getEndereco());
                txfNumeroCelular.setText(medico.getNumero_Celular());
                txfRg.setText(medico.getRg());
                txfDataCadastro.setText(sdf.format(medico.getData_Cadastro().getTime()));
                txfNumeroCrmv.setText(medico.getNumero_crmv());
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

            txfCpf = new JTextField(14);
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

            txfCep = new JTextField(9);
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

            txfRg = new JTextField(12);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 10;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfRg, posicionador);

            lblNumeroCrmv = new JLabel("Numero CRMV:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 11;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblNumeroCrmv, posicionador);

            txfNumeroCrmv = new JTextField(10);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 11;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfNumeroCrmv, posicionador);

            tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);

            pnlSul = new JPanel();
            pnlSul.setLayout(new FlowLayout());

            btnGravar = new JButton("Gravar");
            btnGravar.addActionListener(this);
            btnGravar.setFocusable(true);    //acessibilidade
            btnGravar.setToolTipText("btnGravarMedico"); //acessibilidade
            btnGravar.setMnemonic(KeyEvent.VK_G);
            btnGravar.setActionCommand("botao_gravar_formulario_medico");

            pnlSul.add(btnGravar);

            btnCancelar = new JButton("Cancelar");
            btnCancelar.addActionListener(this);
            btnCancelar.setFocusable(true);    //acessibilidade
            btnCancelar.setToolTipText("btnCancelarMedico"); //acessibilidade
            btnCancelar.setActionCommand("botao_cancelar_formulario_medico");

            pnlSul.add(btnCancelar);

            this.add(pnlSul, BorderLayout.SOUTH);

        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {

                Medico m = getMedicobyFormulario();//recupera os dados do formulÃ¡rio

                if (m != null) {

                    try {

                        pnlAMedico.getControle().getConexaoJDBC().persist(m);

                        JOptionPane.showMessageDialog(this, "Medico armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);

                        pnlAMedico.showTela("tela_medico_listagem");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar Medico! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }

                } else {

                    JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
                }


            } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {


                pnlAMedico.showTela("tela_medico_listagem");
            }
        }
}
