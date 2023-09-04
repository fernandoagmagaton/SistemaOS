package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;

import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.Font;

@SuppressWarnings("unused")
public class Usuarios extends JDialog {

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
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	@SuppressWarnings("rawtypes")
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/carwash64.png")));
		
		setTitle("Usuários");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(54, 31, 23, 14);
		getContentPane().add(lblNewLabel);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(82, 28, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(42, 185, 35, 14);
		getContentPane().add(lblNewLabel_1);

		txtNome = new JTextField();
		txtNome.setBorder(UIManager.getBorder("TextField.border"));
		txtNome.addKeyListener(new KeyAdapter() {
			
		});
		txtNome.setBounds(82, 182, 232, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		//uso do Validador para limitar o número de caracteres
		txtNome.setDocument(new Validador(50));

		JButton btnBuscar = new JButton("");
		btnBuscar.setBorder(null);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/search.png")));
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setForeground(new Color(0, 0, 0));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		btnBuscar.setBounds(272, 134, 76, 23);
		getContentPane().add(btnBuscar);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(42, 134, 35, 14);
		getContentPane().add(lblNewLabel_2);

		txtLogin = new JTextField();
		txtLogin.setBounds(84, 134, 176, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setBounds(42, 249, 46, 14);
		getContentPane().add(lblNewLabel_3);

		JButton btnLimpar = new JButton("");
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/eraser.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBorder(null);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(530, 372, 64, 64);
		getContentPane().add(btnLimpar);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(82, 246, 232, 20);
		getContentPane().add(txtSenha);

		JButton btnAdicionar = new JButton("");
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/adicionar.png")));
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnAdicionar.setBounds(147, 372, 64, 64);
		getContentPane().add(btnAdicionar);

		JButton btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/editar.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setToolTipText("Editar");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBorder(null);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//evento que vai verificar se o checkbox foi selecionado
				if (chckSenha.isSelected()) {
					editarUsuario();
				} else {
					editarUsuarioExcetoSenha();
				}
			}
		});
		btnEditar.setBounds(250, 372, 64, 64);
		getContentPane().add(btnEditar);

		JLabel lblNewLabel_4 = new JLabel("Perfil");
		lblNewLabel_4.setBounds(393, 236, 35, 14);
		getContentPane().add(lblNewLabel_4);

		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboPerfil.setBounds(438, 232, 76, 22);
		getContentPane().add(cboPerfil);
		
		chckSenha = new JCheckBox("Alterar senha");
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					txtSenha.setText(null);
					txtSenha.requestFocus();
					txtSenha.setBackground(Color.YELLOW);
				} else {
					txtSenha.setBackground(Color.WHITE);
				}				
				
			}
		});
		chckSenha.setBounds(410, 301, 133, 23);
		getContentPane().add(chckSenha);
		
		JButton btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/excluir.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(357, 372, 64, 64);
		getContentPane().add(btnExcluir);
		
		JPanel panelRodape = new JPanel();
		panelRodape.setLayout(null);
		panelRodape.setBackground(new Color(255, 128, 255));
		panelRodape.setBounds(10, 438, 774, 123);
		getContentPane().add(panelRodape);
		
		JPanel panelRodape_1 = new JPanel();
		panelRodape_1.setLayout(null);
		panelRodape_1.setBackground(new Color(255, 128, 255));
		panelRodape_1.setBounds(595, 0, 189, 439);
		getContentPane().add(panelRodape_1);

	}// fim do construtor

	

	/**
	 * Método usado para buscar um usuário no banco pelo login
	 */
	private void buscarUsuario() {
		// System.out.println("teste do botão buscar");
		String read = "select * from usuarios where login = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtLogin.setText(rs.getString(3));
				txtSenha.setText(rs.getString(4));
				cboPerfil.setSelectedItem(rs.getString(5));
			} else {
				JOptionPane.showMessageDialog(null, "Usuário inexistente");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para adicionar um novo usuário
	 */
	private void adicionarUsuario() {
		// Criar uma variável/objeto para capturar a senha
		String capturaSenha = new String(txtSenha.getPassword());
		// System.out.println("teste");
		// validação de campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login do usuário");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha do usuário");
			txtLogin.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do usuário");
			cboPerfil.requestFocus();
		} else {
			// lógica principal
			// CRUD Create
			String create = "insert into usuarios(nome,login,senha,perfil) values (?,?,md5(?),?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				// executa a query(instrução sql (CRUD - Create))
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Usuário adicionado");
				// limpar os campos
				limparCampos();
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// fim do método adicionar

	/**
	 * Método para editar os dados do usuário
	 */
	@SuppressWarnings("deprecation")
	private void editarUsuario() {
		String update = "update usuarios set nome=?,login=?,senha=md5(?), perfil=? where id=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, txtSenha.getText());
			pst.setString(4, cboPerfil.getSelectedItem().toString());
			pst.setString(5, txtID.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Dados do usuário editados com sucesso");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método editarUsuario
	
	private void editarUsuarioExcetoSenha() {
		String update2 = "update usuarios set nome=?,login=?,perfil=? where id=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update2);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, cboPerfil.getSelectedItem().toString());
			pst.setString(4, txtID.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Dados do usuário editados com sucesso");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método editarUsuario
	
	/**
	 * Método usado para excluir um usuário
	 */
	private void excluirUsuario() {		
		//validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuário?","Atenção!",JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			//CRUD - Delete
			String delete = "delete from usuarios where id=?";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				//substituir a ? pelo id do contato
				pst.setString(1, txtID.getText());
				//executar a query
				pst.executeUpdate();
				//limpar campos
				limparCampos();
				//exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, "Usuário excluído");				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}		
	} 
	

	/**
	 * Limpar campos / setar componentes
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		txtSenha.setBackground(Color.WHITE);	
		cboPerfil.setSelectedItem("");
		chckSenha.setSelected(false);
	}
}// fim do código
