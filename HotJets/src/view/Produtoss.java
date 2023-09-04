package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import com.toedter.calendar.JDateChooser;

import model.DAO;

@SuppressWarnings("unused")
public class Produtoss extends JDialog {

	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// Instancioar objetos para fluxo de bytes
	private FileInputStream fis;

	// Variável global para armazenar o tamanho da imagem(bytes)
	private int tamanho;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Instanciar objetos e variáveis para trabalhar com imagens
	// jfc -> objeto usado
	JFileChooser jfc = new JFileChooser();
	private JTextField txtID;
	private JTextField txtBarCode;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTextField txtLocalArmazenamento;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JLabel lblFoto;
	private JButton btnCarregar;
	private JLabel lblNewLabel_1;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUNID;
	private JLabel txt;
	private JScrollPane scrollPane;
	@SuppressWarnings("rawtypes")
	private JList listaFornecedor;
	private JTextField txtFornecedor;
	private JTextField txtIDFornecedor;
	private JButton btnBuscarID;
	private JTextField txtProduto;
	private JTextField txtLote;
	private JTextField txtFabricante;
	private JTextField txtCusto;
	private JLabel lblCusto;
	private JLabel lblLucro;
	private JTextField txtLucro;
	private JDateChooser dateEnt;
	private JTextArea txtaDescricao;
	private JDateChooser dateVal;
	@SuppressWarnings("rawtypes")
	private JList listProdutos;
	private JScrollPane scrollPaneProdutos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtoss dialog = new Produtoss();
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
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public Produtoss() {
		getContentPane().setLocation(-322, -13);
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtoss.class.getResource("/img/carwash64.png")));
		setTitle("Produtos\r\n");
		setBounds(-8, -16, 800, 600);
		getContentPane().setLayout(null);
		
		scrollPaneProdutos = new JScrollPane();
		scrollPaneProdutos.setBounds(137, 179, 309, 60);
		getContentPane().add(scrollPaneProdutos);
		
		listProdutos = new JList();
		scrollPaneProdutos.setViewportView(listProdutos);
		listProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarProdutoLista();
			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(296, 13, 206, 82);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(10, 38, 185, 54);
		panel.add(scrollPane);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		listaFornecedor = new JList();
		scrollPane.setViewportView(listaFornecedor);
		listaFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
				listaFornecedor.setEnabled(true);
				;
			}
		});
		listaFornecedor.setBorder(null);

		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtFornecedor.setBounds(10, 21, 186, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("ID do Fornecedor");
		lblNewLabel_2.setBounds(10, 52, 104, 14);
		panel.add(lblNewLabel_2);

		txtIDFornecedor = new JTextField();
		txtIDFornecedor.setEditable(false);
		txtIDFornecedor.setBounds(114, 49, 71, 20);
		panel.add(txtIDFornecedor);
		txtIDFornecedor.setColumns(10);

		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
		});
		txtID.setEnabled(false);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBorder(new LineBorder(Color.BLACK));
		txtID.setBounds(137, 57, 86, 20);
		getContentPane().add(txtID);

		JLabel lblNewLabel = new JLabel("Código");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(25, 59, 67, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblBarcode = new JLabel("BarCode");
		lblBarcode.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBarcode.setBounds(25, 120, 67, 14);
		getContentPane().add(lblBarcode);

		txtBarCode = new JTextField();
		txtBarCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
			// leitor de código de barras
			// evento de pressionar uma tecla específica (ENTER)}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarBarCode();
				}
			}
		});
		txtBarCode.setColumns(10);
		txtBarCode.setBorder(new LineBorder(Color.BLACK));
		txtBarCode.setBounds(137, 117, 309, 20);
		getContentPane().add(txtBarCode);
		// Uso do Validador para limitar o número de caracteres
		txtBarCode.setDocument(new Validador(20));

		JLabel lblDescrio = new JLabel("Imagem");
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDescrio.setBounds(25, 250, 86, 20);
		getContentPane().add(lblDescrio);

		JLabel lblEstoque = new JLabel("Estoque");
		lblEstoque.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEstoque.setBounds(25, 512, 86, 14);
		getContentPane().add(lblEstoque);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoque.setColumns(10);
		txtEstoque.setBorder(new LineBorder(Color.BLACK));
		txtEstoque.setBounds(137, 510, 180, 20);
		getContentPane().add(txtEstoque);

		JLabel lblEstoqueMin = new JLabel("Estoque Min.");
		lblEstoqueMin.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEstoqueMin.setBounds(327, 512, 86, 14);
		getContentPane().add(lblEstoqueMin);

		txtEstoqueMin = new JTextField();
		txtEstoqueMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoqueMin.setColumns(10);
		txtEstoqueMin.setBorder(new LineBorder(Color.BLACK));
		txtEstoqueMin.setBounds(423, 510, 180, 20);
		getContentPane().add(txtEstoqueMin);

		btnCarregar = new JButton("Pesquisar");
		btnCarregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnCarregar.setBounds(137, 250, 173, 23);
		getContentPane().add(btnCarregar);

		JLabel lblUnid = new JLabel("UNID.");
		lblUnid.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUnid.setBounds(472, 395, 61, 14);
		getContentPane().add(lblUnid);

		cboUNID = new JComboBox();
		cboUNID.setModel(new DefaultComboBoxModel(new String[] { "UN", "CX", "PC", "KG", "M" }));
		cboUNID.setFont(new Font("Tahoma", Font.BOLD, 13));
		cboUNID.setBounds(517, 391, 46, 22);
		getContentPane().add(cboUNID);

		JLabel lblLocalDeArmazem = new JLabel("Local ");
		lblLocalDeArmazem.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLocalDeArmazem.setBounds(472, 349, 52, 14);
		getContentPane().add(lblLocalDeArmazem);

		txtLocalArmazenamento = new JTextField();
		txtLocalArmazenamento.setColumns(10);
		txtLocalArmazenamento.setBorder(new LineBorder(Color.BLACK));
		txtLocalArmazenamento.setBounds(566, 364, 207, 20);
		getContentPane().add(txtLocalArmazenamento);
		// Uso do Validador para limitar o número de caracteres
		txtLocalArmazenamento.setDocument(new Validador(50));

		JLabel lblArmazenamento = new JLabel("Armazenamento");
		lblArmazenamento.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblArmazenamento.setBounds(456, 363, 121, 21);
		getContentPane().add(lblArmazenamento);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Produtoss.class.getResource("/img/adicionar.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(582, 422, 60, 60);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarProdutos();
			}
		});
		btnEditar.setIcon(new ImageIcon(Produtoss.class.getResource("/img/editar.png")));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(652, 422, 60, 60);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirProdutoss();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Produtoss.class.getResource("/img/excluir.png")));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(726, 422, 60, 60);
		getContentPane().add(btnExcluir);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Produtoss.class.getResource("/img/eraser.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(726, 493, 60, 60);
		getContentPane().add(btnLimpar);

		lblFoto = new JLabel("");
		lblFoto.setToolTipText("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Produtoss.class.getResource("/img/camera.png")));
		lblFoto.setBounds(517, 82, 256, 256);
		getContentPane().add(lblFoto);

		lblNewLabel_1 = new JLabel("=================>");
		lblNewLabel_1.setBounds(340, 254, 205, 14);
		getContentPane().add(lblNewLabel_1);

		txt = new JLabel("Descrição");
		txt.setFont(new Font("Tahoma", Font.BOLD, 13));
		txt.setBounds(25, 296, 86, 20);
		getContentPane().add(txt);

		btnBuscarID = new JButton("");
		btnBuscarID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarID();
			}
		});
		btnBuscarID.setBorder(null);
		btnBuscarID.setContentAreaFilled(false);
		btnBuscarID.setIcon(new ImageIcon(Produtoss.class.getResource("/img/search.png")));
		btnBuscarID.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarID.setBounds(233, 49, 32, 32);
		getContentPane().add(btnBuscarID);

		txtProduto = new JTextField();
		txtProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarProdutos();
			}
		});
		txtProduto.setColumns(10);
		txtProduto.setBorder(new LineBorder(Color.BLACK));
		txtProduto.setBounds(137, 161, 309, 20);
		getContentPane().add(txtProduto);

		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProduto.setBounds(25, 164, 67, 14);
		getContentPane().add(lblProduto);

		txtLote = new JTextField();
		txtLote.setColumns(10);
		txtLote.setBorder(new LineBorder(Color.BLACK));
		txtLote.setBounds(137, 205, 309, 20);
		getContentPane().add(txtLote);

		JLabel lblLote = new JLabel("Lote");
		lblLote.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLote.setBounds(25, 208, 67, 14);
		getContentPane().add(lblLote);

		txtFabricante = new JTextField();
		txtFabricante.setColumns(10);
		txtFabricante.setBorder(new LineBorder(Color.BLACK));
		txtFabricante.setBounds(137, 378, 309, 20);
		getContentPane().add(txtFabricante);

		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFabricante.setBounds(25, 381, 86, 14);
		getContentPane().add(lblFabricante);

		JLabel lblDataEnt = new JLabel("Data Ent");
		lblDataEnt.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDataEnt.setBounds(25, 425, 86, 14);
		getContentPane().add(lblDataEnt);
		MaskFormatter msf = null;
		try {
			msf = new MaskFormatter("####-##-##");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel lblDataEnt_1 = new JLabel("Data Val");
		lblDataEnt_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDataEnt_1.setBounds(25, 464, 86, 14);
		getContentPane().add(lblDataEnt_1);
		MaskFormatter msf2 = null;
		try {
			msf2 = new MaskFormatter("####-##-##");
		} catch (Exception e) {
			e.printStackTrace();
		}

		txtCusto = new JTextField();
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCusto.setColumns(10);
		txtCusto.setBorder(new LineBorder(Color.BLACK));
		txtCusto.setBounds(340, 423, 106, 20);
		getContentPane().add(txtCusto);

		lblCusto = new JLabel("Custo");
		lblCusto.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCusto.setBounds(286, 425, 61, 14);
		getContentPane().add(lblCusto);

		lblLucro = new JLabel("Lucro");
		lblLucro.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLucro.setBounds(286, 464, 61, 14);
		getContentPane().add(lblLucro);

		txtLucro = new JTextField();
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtLucro.setText("0");
		txtLucro.setColumns(10);
		txtLucro.setBorder(new LineBorder(Color.BLACK));
		txtLucro.setBounds(340, 462, 106, 20);
		getContentPane().add(txtLucro);

		dateVal = new JDateChooser();
		dateVal.setBounds(137, 464, 139, 20);
		getContentPane().add(dateVal);

		dateEnt = new JDateChooser();
		dateEnt.setBounds(137, 425, 139, 20);
		getContentPane().add(dateEnt);

		txtaDescricao = new JTextArea();
		txtaDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtaDescricao.setBounds(137, 295, 309, 59);
		getContentPane().add(txtaDescricao);

	}// FIM DO CONSTRUTOR

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar arquivo de Imagem");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		// se o usuário selecionar un arquivo de imagem
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				// linha abaixo "pega" o arquivo selecionado
				fis = new FileInputStream(jfc.getSelectedFile());
				// linha abaixo armazena o tamanho do arquivo
				// (int) converter para inteiro (bytes)
				tamanho = (int) jfc.getSelectedFile().length();
				// a linha abaixo configura a imagem para preencher a largura e altura da JLabel
				// do formulário
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
			} catch (Exception e) {
				System.out.println(e);

			}
		}
		
		btnAdicionar.setEnabled(true);

	}

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		dateEnt.setDate(null);
		dateVal.setDate(null);
		txtProduto.setText(null);
		txtLote.setText(null);
		txtFabricante.setText(null);
		txtCusto.setText(null);
		txtLucro.setText(null);
		txtFornecedor.setText(null);
		txtIDFornecedor.setText(null);;
		txtID.setText(null);
		txtBarCode.setText(null);
		txtaDescricao.setText(null);
		lblFoto.setIcon(null);
		txtEstoque.setText(null);
		txtEstoqueMin.setText(null);
		txtLocalArmazenamento.setText(null);
		cboUNID.setSelectedItem(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscarID.setEnabled(true);
		lblFoto.setIcon(new ImageIcon(Principal.class.getResource("/img/camera.png")));

	}// fim do método limparCampos()

	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatórios
		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto");
			txtProduto.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto");
			txtLote.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtaDescricao.requestFocus();
		} else if (dateVal.getDate().toString().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a data de validade do produto");
			dateVal.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto");
			txtCusto.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque do produto");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();
		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {

			// Lógica Principal
			// CRUD Create
			String create = "insert into produtos(barcode,produto,lote,descricao,foto,fabricante,dataval,estoque,estoquemin,unidade,localarm,custo,lucro,idfor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql - CRUD Read)
				pst = con.prepareStatement(create);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtLote.getText());
				pst.setBlob(5, fis, tamanho);
				pst.setString(6, txtFabricante.getText());
				pst.setString(4, txtaDescricao.getText());
				SimpleDateFormat formatador2 = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada2 = formatador2.format(dateVal.getDate());
				pst.setString(7, dataFormatada2);
				pst.setString(8, txtEstoque.getText());
				pst.setString(9, txtEstoqueMin.getText());
				pst.setString(10, cboUNID.getSelectedItem().toString());
				pst.setString(11, txtLocalArmazenamento.getText());
				pst.setString(12, txtCusto.getText());
				pst.setString(13, txtLucro.getText());
				pst.setString(14, txtIDFornecedor.getText());
				// executa a query(instrução sql (CRUD - Create))
				// pst.executeUpdate();
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto Adicionado");
				} else {
					JOptionPane.showMessageDialog(null, "Erro! Produto não adicionado.");
				}

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
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarProdutos() {
		// System.out.println("teste do botão editar");
		// Validação dos campos obrigatórios
		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtaDescricao.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();
		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {
			// lógica principal
			// CRUD - Update
			String update = "update produtos set barcode=?, produto=?,lote=?,descricao=?,fabricante=?,estoque=?,estoquemin=?,unidade=?,localarm=?,custo=?,lucro=? where codigo =?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtLote.getText());
				pst.setString(4, txtaDescricao.getText());
				pst.setString(5, txtFabricante.getText());
				pst.setString(6, txtEstoque.getText());
				pst.setString(7, txtEstoqueMin.getText());
				pst.setString(8, cboUNID.getSelectedItem().toString());
				pst.setString(9, txtLocalArmazenamento.getText());
				pst.setString(10, txtCusto.getText());
				pst.setString(11, txtLucro.getText());
				pst.setString(12, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do produto editado com sucesso.");
				// Limpar os campos
				limparCampos();
				// Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}// fim do método editar contato
	
	private void excluirProdutoss() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete
			String delete = "delete from produtos where codigo=?";
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
				JOptionPane.showMessageDialog(null, "Produto excluído");
				// fechar a conexão
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Produto não excluido. \nEste produto ainda tem um serviço pendente");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
		
	}

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirProdutos() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete
			String delete = "delete from produtos where codigo=?";
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
				JOptionPane.showMessageDialog(null, "Produto excluído");
				// fechar a conexão
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Produto não excluido. \nEste produto ainda tem um serviço pendente");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	} // fim do método excluir contato
	
		// PlainDocument -> recursos para formatação

	public class Validador extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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
	 * Método usado para listar o nome dos fornecedores na lista
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
		String readListaFornecedor = "select * from fornecedores where razao like '" + txtFornecedor.getText()
				+ "%'" + "order by razao";
		try {
			// Abrir a conexão
			con = dao.conectar();
			// Preparar a query (instrução sql)
			pst = con.prepareStatement(readListaFornecedor);
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
				if (txtFornecedor.getText().isEmpty()) {
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
		// System.out.println("Teste");
		// Variável que captura o índice da linha da lista
		int linha = listaFornecedor.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução SQL)
			// limit (0,1) -> seleciona o índice 0 do vetor e 1 usuário da lista
			String readListaFornecedor = "select * from fornecedores where razao like '" + txtFornecedor.getText()
					+ "%'" + "order by razao limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query para execução
				pst = con.prepareStatement(readListaFornecedor);
				// executar e obter o resultado
				rs = pst.executeQuery();

				if (rs.next()) {
					// Esconder a lista
					scrollPane.setVisible(false);
					// Setar os campos
					txtFornecedor.setText(rs.getString(2));
					txtIDFornecedor.setText(rs.getString(1));
					// Validação (liberação dos botões)
					listaFornecedor.setEnabled(false);

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

	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscarID() {

		// Captura do número da OS (sem usar a caixa de texto)
		String numOS = JOptionPane.showInputDialog("ID do Produto");

		// dica - testar o evento primeiro
		// System.out.println("teste do botão buscar");
		// Criar uma variável com a query (instrução do banco)
		String read = "select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where codigo = ?";
		// tratamento de exceções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			// Substituir a ?(Parâmetro) pelo número da OS
			pst.setString(1, numOS);
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				txtProduto.setText(rs.getString(3));
				txtLote.setText(rs.getString(4));
				txtaDescricao.setText(rs.getNString(5));
				// Área de texto deve ser utilizado getNString
				txtFabricante.setText(rs.getString(7));
				dateEnt.setDate(rs.getDate(8));
				dateVal.setDate(rs.getDate(9));
				txtEstoque.setText(rs.getString(10));
				txtEstoqueMin.setText(rs.getString(11));
				cboUNID.setSelectedItem(rs.getString(12));
				txtLocalArmazenamento.setText(rs.getString(13));
				txtCusto.setText(rs.getString(14));
				txtLucro.setText(rs.getString(15));
				txtIDFornecedor.setText(rs.getString(16));
				// buscar do fornecedor usando inner join
				txtFornecedor.setText(rs.getString(19));
				Blob blob = (Blob) rs.getBlob(6);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				// Validação (liberação dos botões)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "Código do produto não cadastrado");
				// Validação (liberação do botão adicionar)
				btnAdicionar.setEnabled(true);
				btnBuscarID.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
			//buscarNomeFornecedor();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO METODO BUSCAR

	private void buscarBarCode() {
		// Criar uma variável com a query (instrução do banco)
		String readBarCode = "select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where barcode = ?";
		// tratamento de exceções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(readBarCode);
			// Substituir a ?(Parâmetro) pelo número da OS
			pst.setString(1, txtBarCode.getText());
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtProduto.setText(rs.getString(3));
				txtLote.setText(rs.getString(4));
				txtaDescricao.setText(rs.getNString(5));
				txtFabricante.setText(rs.getString(7));
				dateEnt.setDate(rs.getDate(8));
				dateVal.setDate(rs.getDate(9));
				txtEstoque.setText(rs.getString(10));
				txtEstoqueMin.setText(rs.getString(11));
				cboUNID.setSelectedItem(rs.getString(12));
				txtLocalArmazenamento.setText(rs.getString(13));
				txtCusto.setText(rs.getString(14));
				txtLucro.setText(rs.getString(15));
				txtIDFornecedor.setText(rs.getString(16));
				Blob blob = (Blob) rs.getBlob(6);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				// Validação (liberação dos botões)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "BarCode do produto não encontrado");
				// Validação (liberação do botão adicionar)
				btnAdicionar.setEnabled(true);
				btnBuscarID.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
			//buscarNomeFornecedor();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO METODO BUSCAR
	
	@SuppressWarnings("unused")
	private void buscarNomeFornecedor() {
		// dica - testar o evento primeiro
		// System.out.println("teste do botão buscar");
		// Criar uma variável com a query (instrução do banco)
		String readForn = "select fantasia from fornecedores where idfor= ?";
		// tratamento de exceções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(readForn);
			// Substituir a ?(Parâmetro) pelo número da OS
			pst.setString(1, txtFornecedor.getText());
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtFornecedor.setText(rs.getString(1));
				try {
				} catch (Exception e) {
					System.out.println(e);
				}

			} else {
			}
			// fechar conexão (IMPORTANTE)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO METODO BUSCAR
	/**
	 * Método que busca o usuário selecionado na lista
	 */
	private void buscarProdutoLista() {
		// System.out.println("Teste");
		// Variável que captura o índice da linha da lista
		int linha = listProdutos.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução SQL)
			// limit (0,1) -> seleciona o índice 0 do vetor e 1 usuário da lista
			String readListaProduto = "select * from produtos where produto like '" + txtProduto.getText()
					+ "%'" + "order by produto limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query para execução
				pst = con.prepareStatement(readListaProduto);
				// executar e obter o resultado
				rs = pst.executeQuery();

				if (rs.next()) {
					// Esconder a lista
					scrollPaneProdutos.setVisible(false);
					// Setar os campos
					txtID.setText(rs.getString(1));
					txtBarCode.setText(rs.getString(2));
					txtLote.setText(rs.getString(4));
					txtaDescricao.setText(rs.getNString(5));
					txtFabricante.setText(rs.getString(7));
					dateEnt.setDate(rs.getDate(8));
					dateVal.setDate(rs.getDate(9));
					txtEstoque.setText(rs.getString(10));
					txtEstoqueMin.setText(rs.getString(11));
					cboUNID.setSelectedItem(rs.getString(12));
					txtLocalArmazenamento.setText(rs.getString(13));
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIDFornecedor.setText(rs.getString(16));
					// Validação (liberação dos botões)
					listProdutos.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
				}
				// Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// Se não existir no banco um usuário da lista
			scrollPaneProdutos.setVisible(false);
		}

	} // Fim método buscar Usuário AVANÇADO (Busca Google)
	
	@SuppressWarnings("unchecked")
	private void listarProdutos() {
		// System.out.println("Teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// obejto irá temporariamente armazenar os dados
		DefaultListModel<String> modelos = new DefaultListModel<>();
		// setar o model (vetor na lista)
		listProdutos.setModel(modelos);
		// Query (instrução sql)
		String readLista = "select* from produtos where produto like '" + txtProduto.getText() + "%'" + "order by produto";
		try {
			// abri conexão
			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			// uso do while para trazer os usuários enquanto exisitr
			while (rs.next()) {
				// mostrar a lista
				scrollPaneProdutos.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelos.addElement(rs.getString(3));
				// esconder a lista se nenhuma letra for digitada
				if (txtProduto.getText().isEmpty()) {
					scrollPaneProdutos.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}