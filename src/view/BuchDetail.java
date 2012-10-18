package view;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;

import domain.Book;
import domain.Copy;
import domain.Library;
import domain.Loan;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JScrollPane;

public class BuchDetail implements Observer {

	private JFrame frame;
	private JTextField txtTitel;
	private JTextField txtAutor;
	private JTextField txtVerlag;
	private JTextField txtRegal;
	private JLabel lblAnzahl;
	private Book book;
	private Library library;
	private List<Copy> copies;
	private List<Loan> lent;

	/**
	 * Create the application.
	 */
	public BuchDetail(Book book, Library library) {
		this.book = book;
		this.library = library;
		initialize();
		updateFields();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 360);
		// frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setTitle("Buch Detail Ansicht");

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Buch Informationen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 1.0, 0.0,
				1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblTitel = new JLabel("Titel");
		GridBagConstraints gbc_lblTitel = new GridBagConstraints();
		gbc_lblTitel.gridwidth = 2;
		gbc_lblTitel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitel.gridx = 0;
		gbc_lblTitel.gridy = 0;
		panel.add(lblTitel, gbc_lblTitel);

		txtTitel = new JTextField();
		GridBagConstraints gbc_txtTitel = new GridBagConstraints();
		gbc_txtTitel.insets = new Insets(0, 0, 5, 0);
		gbc_txtTitel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitel.gridx = 3;
		gbc_txtTitel.gridy = 0;
		panel.add(txtTitel, gbc_txtTitel);
		txtTitel.setColumns(10);

		JLabel lblAutor = new JLabel("Autor");
		GridBagConstraints gbc_lblAutor = new GridBagConstraints();
		gbc_lblAutor.gridwidth = 2;
		gbc_lblAutor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAutor.gridx = 0;
		gbc_lblAutor.gridy = 2;
		panel.add(lblAutor, gbc_lblAutor);

		txtAutor = new JTextField();
		GridBagConstraints gbc_txtAutor = new GridBagConstraints();
		gbc_txtAutor.gridheight = 2;
		gbc_txtAutor.insets = new Insets(0, 0, 5, 0);
		gbc_txtAutor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAutor.gridx = 3;
		gbc_txtAutor.gridy = 1;
		panel.add(txtAutor, gbc_txtAutor);
		txtAutor.setColumns(10);

		JLabel lblVerlag = new JLabel("Verlag");
		GridBagConstraints gbc_lblVerlag = new GridBagConstraints();
		gbc_lblVerlag.gridheight = 2;
		gbc_lblVerlag.gridwidth = 2;
		gbc_lblVerlag.insets = new Insets(0, 0, 5, 5);
		gbc_lblVerlag.gridx = 0;
		gbc_lblVerlag.gridy = 3;
		panel.add(lblVerlag, gbc_lblVerlag);

		txtVerlag = new JTextField();
		GridBagConstraints gbc_txtVerlag = new GridBagConstraints();
		gbc_txtVerlag.gridheight = 2;
		gbc_txtVerlag.insets = new Insets(0, 0, 5, 0);
		gbc_txtVerlag.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVerlag.gridx = 3;
		gbc_txtVerlag.gridy = 3;
		panel.add(txtVerlag, gbc_txtVerlag);
		txtVerlag.setColumns(10);

		JLabel lblRegal = new JLabel("Regal");
		GridBagConstraints gbc_lblRegal = new GridBagConstraints();
		gbc_lblRegal.gridheight = 2;
		gbc_lblRegal.gridwidth = 2;
		gbc_lblRegal.insets = new Insets(0, 0, 0, 5);
		gbc_lblRegal.gridx = 0;
		gbc_lblRegal.gridy = 5;
		panel.add(lblRegal, gbc_lblRegal);

		txtRegal = new JTextField();
		GridBagConstraints gbc_txtRegal = new GridBagConstraints();
		gbc_txtRegal.gridheight = 2;
		gbc_txtRegal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRegal.gridx = 3;
		gbc_txtRegal.gridy = 5;
		panel.add(txtRegal, gbc_txtRegal);
		txtRegal.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u00CBxemplare",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		lblAnzahl = new JLabel("Anzahl: 1");
		lblAnzahl.setText("Anzahl: " + library.getCopiesOfBook(book).size());
		GridBagConstraints gbc_lblAnzahl = new GridBagConstraints();
		gbc_lblAnzahl.gridwidth = 4;
		gbc_lblAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnzahl.gridx = 0;
		gbc_lblAnzahl.gridy = 0;
		panel_1.add(lblAnzahl, gbc_lblAnzahl);

		JButton btnAusgewaehlteEntfernen = new JButton(
				"Ausgew\u00E4hlte Entfernen");
		btnAusgewaehlteEntfernen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAusgewaehlteEntfernen.setEnabled(false);
		GridBagConstraints gbc_btnAusgewaehlteEntfernen = new GridBagConstraints();
		gbc_btnAusgewaehlteEntfernen.insets = new Insets(0, 0, 5, 5);
		gbc_btnAusgewaehlteEntfernen.gridx = 4;
		gbc_btnAusgewaehlteEntfernen.gridy = 0;
		panel_1.add(btnAusgewaehlteEntfernen, gbc_btnAusgewaehlteEntfernen);

		JButton btnExemplarHinzufuegen = new JButton(
				" Exemplar hinzuf\u00FCgen");
		GridBagConstraints gbc_btnExemplarHinzufuegen = new GridBagConstraints();
		gbc_btnExemplarHinzufuegen.insets = new Insets(0, 0, 5, 0);
		gbc_btnExemplarHinzufuegen.gridx = 7;
		gbc_btnExemplarHinzufuegen.gridy = 0;
		panel_1.add(btnExemplarHinzufuegen, gbc_btnExemplarHinzufuegen);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);

		JList listBuchDetail = new JList();
		scrollPane.setViewportView(listBuchDetail);
		listBuchDetail.setBorder(new LineBorder(new Color(0, 0, 0)));
		copies = library.getCopiesOfBook(book); // B�cherliste holen
		lent = library.getLentCopiesOfBook(book);
		DefaultListModel<String> listBuchDetailModel = new DefaultListModel<String>();
		// Liste f�llen

		for (Copy c : copies) {
			if (library.isCopyLent(c)) {			
				for (Loan l : lent) {
					if (l.getCopy().equals(c)) { // TODO Datum muss noch formatiert werden!!!
						listBuchDetailModel.addElement(l.getCopy().getInventoryNumber()
								+ " : " + l.getPickupDate().DAY_OF_MONTH + "." + l.getPickupDate().MONTH + "." + l.getPickupDate().YEAR + " - "
								+ l.getReturnDate().DAY_OF_MONTH + "." + l.getReturnDate().MONTH + "." + l.getReturnDate().YEAR);
								
					}
				}
			} else {
				listBuchDetailModel.addElement("" + c.getInventoryNumber()
						+ " : Verf�gbar");
			}
		}
		listBuchDetail.setModel(listBuchDetailModel);
	}

	/**
	 * @param
	 * @author Simon Brouwer, Adrian Rieser
	 * @return void Updates the fields that have changed in another BuchDetail
	 */
	void updateFields() {
		if (book == null) {
			txtTitel.setText("");
			txtAutor.setText("");
			txtVerlag.setText("");
			txtRegal.setText("");
		} else {
			txtTitel.setText(book.getName());
			txtAutor.setText(book.getAuthor());
			txtVerlag.setText(book.getPublisher());
			txtRegal.setText(book.getShelf().toString());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		updateFields();
	}

}
