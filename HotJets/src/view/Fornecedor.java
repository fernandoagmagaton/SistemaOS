package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JComboBox;
import java.awt.Font;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Cursor;
import java.awt.Toolkit;

@SuppressWarnings("unused")
public class Fornecedor extends JDialog {
	// Instanciar objetos JDBC
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtRazaoSocial;
	private JTextField txtEmail;
	private JTextField txtCEP;
	private JTextField txtEndereco;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JTextField txtCNPJ;
	private JFormattedTextField txtFone;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUF;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	@SuppressWarnings("rawtypes")
	private JList listaFornecedor;
	private JScrollPane scrollPane;
	private JTextField txtFantasia;
	private JTextField txtVendedor;
	private JTextField txtSite;
	private JLabel lblNewLabel_3_1_2_2;
	private JTextField txtIE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fornecedor dialog = new Fornecedor();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Fornecedor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedor.class.getResource("/img/carwash64.png")));
		setTitle("Fornecedores");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		
		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBorder(new LineBorder(Color.BLACK));
		txtID.setBounds(122, 11, 86, 20);
		getContentPane().add(txtID);
		
		txtRazaoSocial = new JTextField();
		txtRazaoSocial.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		txtRazaoSocial.setColumns(10);
		txtRazaoSocial.setBorder(new LineBorder(Color.BLACK));
		txtRazaoSocial.setBounds(122, 52, 290, 20);
		getContentPane().add(txtRazaoSocial);
		// Uso do Validador para limitar o número de caracteres
		txtRazaoSocial.setDocument(new Validador(50));
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(122, 69, 290, 61);
		getContentPane().add(scrollPane);
		
		listaFornecedor = new JList();
		listaFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
				listaFornecedor.setEnabled(rootPaneCheckingEnabled);;
			}
		});
		scrollPane.setViewportView(listaFornecedor);
		listaFornecedor.setBorder(null);
		
		// Método para marcar o formato do texto
		MaskFormatter msf = null;
		try { msf = new MaskFormatter("(##)#####-####");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtFone = new JFormattedTextField(msf);
		txtFone.setBounds(122, 253, 290, 20);
		getContentPane().add(txtFone);
		// Uso do Validador para limitar o número de caracteres
		txtFone.setDocument(new Validador(18));
		
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBorder(new LineBorder(Color.BLACK));
		txtEmail.setBounds(122, 331, 290, 20);
		getContentPane().add(txtEmail);
		// Uso do Validador para limitar o número de caracteres
		txtEmail.setDocument(new Validador(50));
		
		txtCEP = new JTextField();
		txtCEP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}
		}});
		txtCEP.setColumns(10);
		txtCEP.setBorder(new LineBorder(Color.BLACK));
		txtCEP.setBounds(122, 371, 145, 20);
		getContentPane().add(txtCEP);
		// Uso do Validador para limitar o número de caracteres
		txtCEP.setDocument(new Validador(10));
		
		JButton btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(277, 370, 114, 23);
		getContentPane().add(btnBuscarCep);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBorder(new LineBorder(Color.BLACK));
		txtEndereco.setBounds(122, 410, 290, 20);
		getContentPane().add(txtEndereco);
		// Uso do Validador para limitar o número de caracteres
		txtEndereco.setDocument(new Validador(50));
		
		
		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBorder(new LineBorder(Color.BLACK));
		txtNumero.setBounds(482, 410, 94, 20);
		getContentPane().add(txtNumero);
		// Uso do Validador para limitar o número de caracteres
		txtNumero.setDocument(new Validador(20));
		
		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBorder(new LineBorder(Color.BLACK));
		txtComplemento.setBounds(122, 453, 199, 20);
		getContentPane().add(txtComplemento);
		// Uso do Validador para limitar o número de caracteres
		txtComplemento.setDocument(new Validador(20));
		
		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBorder(new LineBorder(Color.BLACK));
		txtBairro.setBounds(379, 453, 197, 20);
		getContentPane().add(txtBairro);
		// Uso do Validador para limitar o número de caracteres
		txtBairro.setDocument(new Validador(30));
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBorder(new LineBorder(Color.BLACK));
		txtCidade.setBounds(122, 495, 290, 20);
		getContentPane().add(txtCidade);
		// Uso do Validador para limitar o número de caracteres
		txtCidade.setDocument(new Validador(30));
		
		cboUF = new JComboBox();
		cboUF.setModel(new DefaultComboBoxModel(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUF.setFont(new Font("Tahoma", Font.BOLD, 13));
		cboUF.setBounds(463, 493, 46, 22);
		getContentPane().add(cboUF);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/adicionar.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(540, 484, 60, 60);
		getContentPane().add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarFornecedor();
			}
		});
		btnEditar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/editar.png")));
		btnEditar.setEnabled(false);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(630, 484, 60, 60);
		getContentPane().add(btnEditar);
		
		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirFornecedor();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/excluir.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(714, 484, 60, 60);
		getContentPane().add(btnExcluir);
		
		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/eraser.png")));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(714, 12, 60, 60);
		getContentPane().add(btnLimpar);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 13, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 253, 46, 16);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_4_2 = new JLabel("CEP");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4_2.setBounds(10, 373, 60, 14);
		getContentPane().add(lblNewLabel_4_2);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(10, 332, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Endereço");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setBounds(10, 412, 60, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Complemento");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setBounds(10, 455, 92, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Cidade");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5_1.setBounds(10, 497, 92, 14);
		getContentPane().add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("Bairro");
		lblNewLabel_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4_1_1.setBounds(331, 455, 60, 14);
		getContentPane().add(lblNewLabel_4_1_1);
		
		JLabel lblNewLabel_4_1 = new JLabel("Número");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4_1.setBounds(422, 412, 60, 14);
		getContentPane().add(lblNewLabel_4_1);
		
		JLabel lblRazoSocial = new JLabel("Razão Social");
		lblRazoSocial.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRazoSocial.setBounds(10, 55, 114, 14);
		getContentPane().add(lblRazoSocial);
		
		JLabel lblNewLabel_3_1 = new JLabel("CNPJ");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1.setBounds(10, 293, 46, 14);
		getContentPane().add(lblNewLabel_3_1);
		
		txtCNPJ = new JTextField();
		txtCNPJ.setColumns(10);
		txtCNPJ.setBorder(new LineBorder(Color.BLACK));
		txtCNPJ.setBounds(122, 292, 290, 20);
		getContentPane().add(txtCNPJ);
		
		JLabel lblUF = new JLabel("UF");
		lblUF.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUF.setBounds(427, 497, 46, 14);
		getContentPane().add(lblUF);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("CNPJ");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1_1.setBounds(-52, 89, 46, 14);
		getContentPane().add(lblNewLabel_3_1_1);
		
		txtFantasia = new JTextField();
		txtFantasia.setColumns(10);
		txtFantasia.setBorder(new LineBorder(Color.BLACK));
		txtFantasia.setBounds(122, 92, 290, 20);
		getContentPane().add(txtFantasia);
		
		JLabel lblFantasia = new JLabel("Fantasia");
		lblFantasia.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFantasia.setBounds(10, 95, 114, 14);
		getContentPane().add(lblFantasia);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Vendedor");
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1_2.setBounds(10, 137, 94, 14);
		getContentPane().add(lblNewLabel_3_1_2);
		
		txtVendedor = new JTextField();
		txtVendedor.setColumns(10);
		txtVendedor.setBorder(new LineBorder(Color.BLACK));
		txtVendedor.setBounds(122, 136, 290, 20);
		getContentPane().add(txtVendedor);
		
		JLabel lblNewLabel_3_1_2_1 = new JLabel("Site");
		lblNewLabel_3_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1_2_1.setBounds(10, 175, 94, 14);
		getContentPane().add(lblNewLabel_3_1_2_1);
		
		txtSite = new JTextField();
		txtSite.setColumns(10);
		txtSite.setBorder(new LineBorder(Color.BLACK));
		txtSite.setBounds(122, 174, 290, 20);
		getContentPane().add(txtSite);
		
		lblNewLabel_3_1_2_2 = new JLabel("IE");
		lblNewLabel_3_1_2_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1_2_2.setBounds(10, 215, 94, 14);
		getContentPane().add(lblNewLabel_3_1_2_2);
		
		txtIE = new JTextField();
		txtIE.setColumns(10);
		txtIE.setBorder(new LineBorder(Color.BLACK));
		txtIE.setBounds(122, 214, 290, 20);
		getContentPane().add(txtIE);



		
	}//FIM DO CONSTRUTOR
	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtCNPJ.setText(null);
		txtRazaoSocial.setText(null);
		txtEmail.setText(null);
		txtFone.setText(null);
		txtEndereco.setText(null);
		txtComplemento.setText(null);
		txtCEP.setText(null);
		txtNumero.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		txtFantasia.setText(null);
		txtVendedor.setText(null);
		txtSite.setText(null);
		txtIE.setText(null);
		cboUF.setSelectedItem(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		listaFornecedor.setEnabled(true);

	}// fim do método limparCampos()

	
	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatórios
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a razão social do fornecedor");
			txtRazaoSocial.requestFocus();
		} else if (txtFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a fantasia do fornecedor");
			txtFantasia.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o fone do fornecedor");
					txtFone.requestFocus();
		} else if (txtCNPJ.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o CNPJ do fornecedor");
				txtCNPJ.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do fornecedor");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do fornecedor");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do fornecedor");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do fornecedor");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do fornecedor");
			txtCidade.requestFocus();
		} else {

			// Lógica Principal
			// CRUD Create
			String create = "insert into fornecedores(razao,fantasia,fone,vendedor,email,site,cnpj,ie,cep,endereco,numero,complemento,bairro,cidade,uf) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql - CRUD Read)
				pst = con.prepareStatement(create);
				pst.setString(1, txtRazaoSocial.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtCNPJ.getText());
				pst.setString(8, txtIE.getText());
				pst.setString(9, txtCEP.getText());
				pst.setString(10, txtEndereco.getText());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtComplemento.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, txtCidade.getText());
				pst.setString(15, cboUF.getSelectedItem().toString());
				// executa a query(instrução sql (CRUD - Create))
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Fornecedor Adicionado");
				// limpar os campos
				limparCampos();
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "CNPJ JA CADASTRADO!!");
			}

		}
	}// fim do método adicionar

	/**
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarFornecedor() {
		// System.out.println("teste do botão editar");
		// Validação dos campos obrigatórios
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a razão social do fornecedor");
			txtRazaoSocial.requestFocus();
		} else if (txtFantasia.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha a fantasia do fornecedor");
				txtFantasia.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do fornecedor");
			txtFone.requestFocus();
		} else if (txtCNPJ.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o CNPJ do fornecedor");
				txtCNPJ.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do fornecedor");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do fornecedor");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do fornecedor");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do fornecedor");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do fornecedor");
			txtCidade.requestFocus();
		} else {
			// lógica principal
			// CRUD - Update
			String update = "update fornecedores set razao=?,fantasia=?,fone=?,vendedor=?,email=?,site=?,cnpj=?,ie=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,uf=? where idfor =?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(16, txtID.getText());
				pst.setString(1, txtRazaoSocial.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtCNPJ.getText());
				pst.setString(8, txtIE.getText());
				pst.setString(9, txtCEP.getText());
				pst.setString(10, txtEndereco.getText());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtComplemento.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, txtCidade.getText());
				pst.setString(15, cboUF.getSelectedItem().toString());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do fornecedor editado com sucesso.");
				// Limpar os campos
				limparCampos();
				// Fechar a conexão
				con.close();
			}  catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste CNPJ já está sendo utilizado.");
				txtCNPJ.setText(null);
				txtCNPJ.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}

		}

	}// fim do método editar contato

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirFornecedor() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste fornecedor?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete
			String delete = "delete from fornecedores where idfor=?";
			// tratamento de exceções
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// limpar campos
				limparCampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, "Fornecedor excluído");
				// fechar a conexão
				con.close();
				
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Fornecedor não excluido. \nEste fornecedor ainda tem um serviço pendente");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	} // fim do método excluir contato
		// PlainDocument -> recursos para formatação

	@SuppressWarnings("serial")
	public class Validador extends PlainDocument {
		// variável que irá armazenar o número máximo de caracteres permitidos
		private int limite;
		// construtor personalizado -> será usado pela caixas de texto JTextFiled

		public Validador(int limite) {
			super();
			this.limite = limite;
		}

		// Método interno para validar o limite de caracteres
		// BadLocation -> Tratamento de exceções
		public void insertString(int ofs, String str, AttributeSet a) throws BadLocationException {
			// Se o limite não for ultrapassado permitir a digitação
			if ((getLength() + str.length()) <= limite) {
				super.insertString(ofs, str, a);
			}
		}

	} // Fim do validador

	/**
	 * buscarCep
	 */
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCEP.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						System.out.println("OK!");
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Método usado para listar o nome dos usuários na lista
	 */
	@SuppressWarnings("unchecked")
	private void listarFornecedor() {
		// System.out.println("teste");
		// A linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// Setar o modelo (vetor na lista)
		listaFornecedor.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select * from fornecedores where razao like '" + txtRazaoSocial.getText() + "%'" + "order by razao";
		try {
			// Abrir a conexão
			con = dao.conectar();
			// Preparar a query (instrução sql)
			pst = con.prepareStatement(readLista);
			// Executar a query e trazer o resultado para lista
			rs = pst.executeQuery();
			// Uso do while para trzer os usuários enquanto existir
			while (rs.next()) {
				// Mostrar a barra de rolagem (scrollpane)
				scrollPane.setVisible(true);
				// Mostrar a lista
				// listaUsers.setVisible(true);
				// Adicionar os usuários no vetor -> lista
				modelo.addElement(rs.getString(2));
				// Esconder a lista se nenhuma letra for digitada
				if (txtRazaoSocial.getText().isEmpty()) {
					scrollPane.setVisible(false);
			}
			}
			// Fechar Conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	/**
	 * Método que busca o usuário selecionado na lista
	 */
	private void buscarFornecedorLista() {
		//System.out.println("Teste");
		// Variável que captura o índice da linha da lista
		int linha = listaFornecedor.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução SQL)
			// limit (0,1) -> seleciona o índice 0 do vetor e 1 usuário da lista
			String readListaUsuario = "select * from fornecedores where razao like '" + txtRazaoSocial.getText() + "%'" + "order by razao limit " + (linha) + " , 1";
			try {
				//abrir a conexão
				con = dao.conectar();
				//preparar a query para execução
				pst = con.prepareStatement(readListaUsuario);
				// executar e obter o resultado
				rs = pst.executeQuery();
				
				if(rs.next()) {
					// Esconder a lista
					scrollPane.setVisible(false);
					// Setar os campos
					txtID.setText(rs.getString(1)); 
					txtRazaoSocial.setText(rs.getString(2));
					txtFantasia.setText(rs.getString(3));
					txtFone.setText(rs.getString(4));
					txtVendedor.setText(rs.getString(5));
					txtSite.setText(rs.getString(7));
					txtIE.setText(rs.getString(9));
					txtCNPJ.setText(rs.getString(8)); 
					txtEmail.setText(rs.getString(6)); 
					txtCEP.setText(rs.getString(10)); 
					txtEndereco.setText(rs.getString(11));
					txtNumero.setText(rs.getString(12)); 
					txtComplemento.setText(rs.getString(13)); 
					txtBairro.setText(rs.getString(14)); 
					txtCidade.setText(rs.getString(15));
					cboUF.setSelectedItem(rs.getString(16)); 
					// Validação (liberação dos botões)
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					listaFornecedor.setEnabled(false);
					btnAdicionar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
				}
				// Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// Se não existir no banco um usuário da lista
			scrollPane.setVisible(false);
		}
		
	} // Fim método buscar Usuário AVANÇADO (Busca Google)
}//FIM DO CÓDIGO
