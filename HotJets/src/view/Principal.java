package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.Font;
import java.awt.Color;

public class Principal extends JFrame {

	// Instaciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	//estes componentes serão alterados pela tela de Login (public)
	public JLabel lblUsuario;
	public JButton btnUsuarios;
	public JButton btnRelatorios;
	public JPanel panelRodape;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("HotJets");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/carwash64.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// evento ativar janela
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// abrir tela de usuários
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/users.png")));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setBounds(42, 69, 128, 128);
		contentPane.add(btnUsuarios);

		panelRodape = new JPanel();
		panelRodape.setBackground(new Color(255, 0, 0));
		panelRodape.setBounds(0, 507, 784, 54);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		lblData = new JLabel("");
		lblData.setBounds(429, 13, 261, 30);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 14));
		
				lblStatus = new JLabel("");
				lblStatus.setBounds(10, 13, 32, 32);
				panelRodape.add(lblStatus);
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdoff.png")));
				
				JLabel lblNewLabel_1 = new JLabel("Usuário: ");
				lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblNewLabel_1.setForeground(SystemColor.text);
				lblNewLabel_1.setBounds(65, 26, 61, 17);
				panelRodape.add(lblNewLabel_1);
				
				lblUsuario = new JLabel("");
				lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblUsuario.setForeground(SystemColor.text);
				lblUsuario.setBounds(137, 27, 282, 14);
				panelRodape.add(lblUsuario);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/clientes.png")));
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(223, 69, 128, 128);
		contentPane.add(btnClientes);
		
		JButton btnOS = new JButton("");
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnOS.setIcon(new ImageIcon(Principal.class.getResource("/img/os128.png")));
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setToolTipText("Ordem de Serviço");
		btnOS.setBounds(42, 288, 128, 128);
		contentPane.add(btnOS);
		
		btnRelatorios = new JButton("");
		btnRelatorios.setEnabled(false);
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/report.png")));
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setToolTipText("Relatórios");
		btnRelatorios.setBounds(422, 288, 128, 128);
		contentPane.add(btnRelatorios);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/carwash64.png")));
		lblNewLabel.setBounds(720, 416, 64, 80);
		contentPane.add(lblNewLabel);
		
		JButton btnFornecedores = new JButton("");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);
			}
		});
		btnFornecedores.setIcon(new ImageIcon(Principal.class.getResource("/img/fornecedores.png")));
		btnFornecedores.setToolTipText("Fornecedores");
		btnFornecedores.setBounds(422, 69, 128, 128);
		contentPane.add(btnFornecedores);
		
		JButton btnProdutos = new JButton("");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtoss produtos = new Produtoss();
				produtos.setVisible(true);
			}
		});
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/produtos.png")));
		btnProdutos.setToolTipText("Produtos");
		btnProdutos.setBounds(223, 288, 128, 128);
		contentPane.add(btnProdutos);
		
		JButton btnSobre = new JButton("");
		btnSobre.setBorder(null);
		btnSobre.setBorderPainted(false);
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
			
		});
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/information-button.png")));
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setBounds(710, 11, 64, 73);
		contentPane.add(btnSobre);
	}// fim do construtor

	/**
	 * Método responsável por exibir o status da conexão
	 */
	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				// System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdoff.png")));
			} else {
				// System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/bdon.png")));
			}
			// NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método status()
	
	/**
	 * Método responsável por setar a data no rodapé
	 */
	private void setarData() {
		// criar objeto para trazer a data do sistema
		Date data = new Date();
		// criar objeto para formatar a data
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// alterar o texto da label pela data atual formatada
		lblData.setText(formatador.format(data));
	}
}// fim do código
