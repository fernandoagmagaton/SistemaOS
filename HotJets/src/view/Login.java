package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import utils.Validador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("unused")
public class Login extends JFrame {

	// objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	// objeto tela principal
	Principal principal = new Principal();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JButton btnAcessar;
	private JLabel lblStatus;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/carwash64.png")));
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(30, 32, 39, 14);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(75, 29, 201, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		// uso do Validador para limitar o número de caracteres
		txtLogin.setDocument(new Validador(15));

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(25, 69, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(75, 66, 201, 20);
		// uso do Validador para limitar o número de caracteres
		txtSenha.setDocument(new Validador(250));
		contentPane.add(txtSenha);

		btnAcessar = new JButton("Acessar");
		btnAcessar.setForeground(new Color(0, 0, 0));
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(25, 111, 89, 23);
		contentPane.add(btnAcessar);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Login.class.getResource("/img/carwash64.png")));
		lblLogo.setBounds(310, 145, 75, 75);
		contentPane.add(lblLogo);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dbloff.png")));
		lblStatus.setBounds(306, 32, 48, 48);
		contentPane.add(lblStatus);

		// substituir o click pela tecla <ENTER> em um botão
		getRootPane().setDefaultButton(btnAcessar);

	}// fim do construtor

	/**
	 * Método responsável por verificar o status de conexão com o banco
	 */
	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				// System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dbloff.png")));
			} else {
				// System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dblon.png")));
			}
			// NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para autenticar um usuário
	 */
	private void logar() {
		// Criar uma variável/objeto para capturar a senha
		String capturaSenha = new String(txtSenha.getPassword());
		// validação
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			// lógica principal
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					// Capturar o perfil do usuário (apoio a lógica)
					// System.out.println(rs.getString(5)); //apoio a lógica
					// Tratamento do perfil do usuário
					String perfil = rs.getString(5);
					if (perfil.equals("admin")) {
						// logar -> acessar a tela principal
						principal.setVisible(true);
						// Setar a label da tela principal com o nome do usuário
						principal.lblUsuario.setText(rs.getString(2));
						// habilitar os botões
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						// fechar a tela de login(esta tela)
						this.dispose();
						// se o perfil for diferente de admin
					} else {
						// logar -> acessar a tela principal
						principal.setVisible(true);
						// mudar a cor do rodapé
						principal.panelRodape.setBackground(Color.DARK_GRAY);
						// Setar a label da tela principal com o nome do usuário
						principal.lblUsuario.setText(rs.getString(2));
						// fechar a tela de login(esta tela)
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "usuário e/ou senha inválido(s)");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}