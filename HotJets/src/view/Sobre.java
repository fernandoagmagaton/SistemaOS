package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Color;

public class Sobre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
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
	public Sobre() {
		getContentPane().setBackground(SystemColor.text);
		setModal(true);
		setTitle("Sobre");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/information-button.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("HotJets");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(29, 30, 81, 26);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("@autor  Fernando A G Magaton");
		lblNewLabel_1.setBounds(30, 100, 215, 15);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_3.setBounds(282, 122, 128, 128);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_1_1 = new JLabel("Lava - Rápido HotJets o melhor e mais pratico lava - rápido.");
		lblNewLabel_1_1.setBounds(30, 60, 329, 26);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblGit = new JLabel("fernandoagmagaton");
		lblGit.setBounds(80, 165, 110, 15);
		getContentPane().add(lblGit);
		
		JLabel lblInsta = new JLabel("__nando.zl");
		lblInsta.setBounds(80, 215, 60, 15);
		getContentPane().add(lblInsta);
		
		JButton btnGitHub = new JButton("");
		btnGitHub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/fernandoagmagaton/sistemaOS");
			}
		});
		btnGitHub.setToolTipText("GitHub");
		btnGitHub.setContentAreaFilled(false);
		btnGitHub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGitHub.setBorder(null);
		btnGitHub.setIcon(new ImageIcon(Sobre.class.getResource("/img/github32.png")));
		btnGitHub.setBounds(30, 150, 40, 40);
		getContentPane().add(btnGitHub);
		
		JButton btnInstagram = new JButton("");
		btnInstagram.setIcon(new ImageIcon(Sobre.class.getResource("/img/instagram32.png")));
		btnInstagram.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInstagram.setBorder(null);
		btnInstagram.setContentAreaFilled(false);
		btnInstagram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://www.instagram.com/__nando.zl/");
			}
		});
		btnInstagram.setToolTipText("Instagram");
		btnInstagram.setBounds(30, 200, 40, 40);
		getContentPane().add(btnInstagram);
			
	}
	
	/**
	 * link
	 * 
	 * @param site
	 */
	private void link(String site) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(site);
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
