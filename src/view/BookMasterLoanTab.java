package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import tablemodel.TableModelBookMaster;
import tablemodel.TableModelLoanMaster;

import domain.Book;
import domain.Copy;
import domain.Library;
import domain.Loan;

public class BookMasterLoanTab extends JPanel implements Observer
{
	private JTable table;
	private JTextField txtSuche;
	private List<Book> books;
	private List<Loan> loans;
	private TableRowSorter<TableModelLoanMaster> sorter;
	private TableModelLoanMaster tableModel;
	private Library library;
	private JCheckBox chckbxNurUeberfaellige;
	private JScrollPane scrollPane;
	private JLabel lblAnzahlAusleihen;
	private JLabel lblAktuellAusgeliehen;
	private JButton btnSelektiertesAnzeigen;
	private JButton btnNeueAusleihe;
	private JLabel lblUeberfaelligeAusleihen;
	private final String[] header = new String[] { "Status", "Exemplar-ID", "Titel", "Ausgeliehen Bis", "Ausgeliehen An" };

	public BookMasterLoanTab(Library library)
	{
		this.library = library;
		library.addObserver(this);
		initialize();
	}

	private void initialize()
	{
		GridBagLayout gbl_buecherTab = new GridBagLayout();
		gbl_buecherTab.columnWidths = new int[] { 0, 0 };
		gbl_buecherTab.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_buecherTab.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_buecherTab.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		this.setLayout(gbl_buecherTab);

		JPanel inventarStatistikenPanel = new JPanel();
		inventarStatistikenPanel.setBorder(new TitledBorder(null, "Inventar Statistiken", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GridBagConstraints gbc_inventarStatistikenPanel = new GridBagConstraints();
		gbc_inventarStatistikenPanel.anchor = GridBagConstraints.NORTH;
		gbc_inventarStatistikenPanel.insets = new Insets(0, 5, 5, 0);
		gbc_inventarStatistikenPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inventarStatistikenPanel.gridx = 0;
		gbc_inventarStatistikenPanel.gridy = 0;

		this.add(inventarStatistikenPanel, gbc_inventarStatistikenPanel);

		loans = library.getLoans();
		GridBagLayout gbl_inventarStatistikenPanel = new GridBagLayout();
		gbl_inventarStatistikenPanel.columnWidths = new int[] { 94, 106, 113, 0 };
		gbl_inventarStatistikenPanel.rowHeights = new int[] { 14, 0 };
		gbl_inventarStatistikenPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_inventarStatistikenPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		inventarStatistikenPanel.setLayout(gbl_inventarStatistikenPanel);

		lblAnzahlAusleihen = new JLabel("Anzahl Ausleihen: " + loans.size());
		GridBagConstraints gbc_lblAnzahlAusleihen = new GridBagConstraints();
		gbc_lblAnzahlAusleihen.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAnzahlAusleihen.insets = new Insets(0, 0, 0, 5);
		gbc_lblAnzahlAusleihen.gridx = 0;
		gbc_lblAnzahlAusleihen.gridy = 0;
		inventarStatistikenPanel.add(lblAnzahlAusleihen, gbc_lblAnzahlAusleihen);

		lblAktuellAusgeliehen = new JLabel("Aktuell Ausgeliehen: " + library.getLentOutBooks().size());
		GridBagConstraints gbc_lblAktuellAusgeliehen = new GridBagConstraints();
		gbc_lblAktuellAusgeliehen.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAktuellAusgeliehen.insets = new Insets(0, 0, 0, 5);
		gbc_lblAktuellAusgeliehen.gridx = 1;
		gbc_lblAktuellAusgeliehen.gridy = 0;
		inventarStatistikenPanel.add(lblAktuellAusgeliehen, gbc_lblAktuellAusgeliehen);

		lblUeberfaelligeAusleihen = new JLabel("Überfällige Ausleihen: " + library.getOverdueLoans().size());
		GridBagConstraints gbc_lblUeberfaelligeAusleihen = new GridBagConstraints();
		gbc_lblUeberfaelligeAusleihen.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblUeberfaelligeAusleihen.gridx = 2;
		gbc_lblUeberfaelligeAusleihen.gridy = 0;
		inventarStatistikenPanel.add(lblUeberfaelligeAusleihen, gbc_lblUeberfaelligeAusleihen);

		JPanel buchInventarPanel = new JPanel();
		buchInventarPanel.setBorder(new TitledBorder(null, "Buch Inventar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_buchInventarPanel = new GridBagConstraints();
		gbc_buchInventarPanel.insets = new Insets(0, 5, 0, 0);
		gbc_buchInventarPanel.gridheight = 2;
		gbc_buchInventarPanel.fill = GridBagConstraints.BOTH;
		gbc_buchInventarPanel.gridx = 0;
		gbc_buchInventarPanel.gridy = 1;
		this.add(buchInventarPanel, gbc_buchInventarPanel);

		GridBagLayout gbl_buchInventarPanel = new GridBagLayout();
		gbl_buchInventarPanel.columnWidths = new int[] { 69, 0, 131, 145, 0, 0, 0 };
		gbl_buchInventarPanel.rowHeights = new int[] { 23, 0, 0 };
		gbl_buchInventarPanel.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_buchInventarPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		buchInventarPanel.setLayout(gbl_buchInventarPanel);

		// Ab hier "Suche"
		txtSuche = new JTextField();
		txtSuche.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent arg0)
			{
				if (txtSuche.getText().contains("Suche"))
				{
					txtSuche.setText("");
				}
			}
		});
		txtSuche.setToolTipText("Geben Sie hier die Exemplar Nummer, den Titel des Buches oder den Namen des Kundes ein, nach dem Sie suchen möchten");
		txtSuche.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent arg0)
			{
				search();
			}
		});
		txtSuche.setText("Suche");
		GridBagConstraints gbc_txtSuche = new GridBagConstraints();
		gbc_txtSuche.gridwidth = 2;
		gbc_txtSuche.insets = new Insets(0, 5, 5, 5);
		gbc_txtSuche.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSuche.gridx = 1;
		gbc_txtSuche.gridy = 0;
		buchInventarPanel.add(txtSuche, gbc_txtSuche);
		txtSuche.setColumns(10);

		// Ab hier "Selektiertes Anzeigen"
		ImageIcon iconSelektiertesAnzeigen = new ImageIcon("icons/book.png");
		btnSelektiertesAnzeigen = new JButton("Selektiertes Anzeigen", iconSelektiertesAnzeigen);
		btnSelektiertesAnzeigen.setToolTipText("Zeigt das in der untenstehenden Tabelle ausgewählte Exemplar in einer Detailansicht an");
		btnSelektiertesAnzeigen.setEnabled(false);
		btnSelektiertesAnzeigen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int selected[] = table.getSelectedRows();
				for (int i : selected)
				{
					Loan loan = tableModel.getLoanAtRow(table.convertRowIndexToModel(i));
					LoanDetail loanDetail = new LoanDetail(library, loan);
				}
			}
		});

		// Ab hier "Nur �berf�llige"
		chckbxNurUeberfaellige = new JCheckBox("Nur überfällige");
		chckbxNurUeberfaellige.setToolTipText("Es werden nur überfällige Exemplare angezeigt");
		chckbxNurUeberfaellige.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent arg0)
			{
				// 1 = Selected, 2 = Not Selected
				// remove all entries
				// deleteTableRows(table);

				if (arg0.getStateChange() == 1)
				{ // If hook is set, add only
					addOverdueLoans(); // entries with available
					// Copies
					// (Number of Copies -
					// Number of Lent Copies)

				} else
				{ // if hook is not set, add all Books
					sorter.setRowFilter(null);
				}
			}

		});

		GridBagConstraints gbc_chckbxNurUeberfaellige = new GridBagConstraints();
		gbc_chckbxNurUeberfaellige.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNurUeberfaellige.gridx = 3;
		gbc_chckbxNurUeberfaellige.gridy = 0;
		buchInventarPanel.add(chckbxNurUeberfaellige, gbc_chckbxNurUeberfaellige);
		GridBagConstraints gbc_btnSelektierteAnzeigen = new GridBagConstraints();
		gbc_btnSelektierteAnzeigen.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSelektierteAnzeigen.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelektierteAnzeigen.gridx = 4;
		gbc_btnSelektierteAnzeigen.gridy = 0;
		buchInventarPanel.add(btnSelektiertesAnzeigen, gbc_btnSelektierteAnzeigen);

		// Ab hier "Neues Buch hinzuf�gen"
		ImageIcon iconNeueAusleihe = new ImageIcon("icons/book_go.png");
		btnNeueAusleihe = new JButton("Neue Ausleihe erfassen", iconNeueAusleihe);
		btnNeueAusleihe.setToolTipText("Öffnet ein Fenster um eine neue Ausleihe zu tätigen");
		btnNeueAusleihe.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LoanDetail loanDetail = new LoanDetail(library);
			}
		});
		GridBagConstraints gbc_btnNeueAusleihe = new GridBagConstraints();
		gbc_btnNeueAusleihe.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNeueAusleihe.insets = new Insets(0, 0, 5, 0);
		gbc_btnNeueAusleihe.gridx = 5;
		gbc_btnNeueAusleihe.gridy = 0;
		buchInventarPanel.add(btnNeueAusleihe, gbc_btnNeueAusleihe);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		buchInventarPanel.add(scrollPane, gbc_scrollPane);

		// Ab hier JTable
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				if (table.getSelectedRows().length > 0)
				{
					btnSelektiertesAnzeigen.setEnabled(true);
				} else
				{
					btnSelektiertesAnzeigen.setEnabled(false);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					int selected[] = table.getSelectedRows();
					for (int i : selected)
					{
						Loan loan = tableModel.getLoanAtRow(table.convertRowIndexToModel(i));
						LoanDetail loanDetail = new LoanDetail(library, loan);
					}
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);

		books = library.getBooks();
		tableModel = new TableModelLoanMaster(library, header);
		table.setModel(tableModel);

		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(80);

		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);

	}

	private void search()
	{
		sorter = new TableRowSorter<TableModelLoanMaster>(tableModel);
		table.setRowSorter(sorter);
		RowFilter<TableModelLoanMaster, Object> rf = null;
		List<RowFilter<TableModelLoanMaster, Object>> filters = new ArrayList<RowFilter<TableModelLoanMaster, Object>>();
		// If current expression doesn't parse, don't update.
		try
		{
			RowFilter<TableModelLoanMaster, Object> rfID = RowFilter.regexFilter("(?i)^.*" + txtSuche.getText() + ".*", 1);
			RowFilter<TableModelLoanMaster, Object> rfTitel = RowFilter.regexFilter("(?i)^.*" + txtSuche.getText() + ".*", 2);
			RowFilter<TableModelLoanMaster, Object> rfKunde = RowFilter.regexFilter("(?i)^.*" + txtSuche.getText() + ".*", 4);
			filters.add(rfID);
			filters.add(rfTitel);
			filters.add(rfKunde);
			rf = RowFilter.orFilter(filters);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}

	private void updateStats()
	{
		lblAnzahlAusleihen.setText("Anzahl Bücher: " + books.size());
		lblAktuellAusgeliehen.setText("Anzahl Exemplare: " + library.getCopies().size());
	}

	private void addOverdueLoans()
	{

		sorter = new TableRowSorter<TableModelLoanMaster>(tableModel);
		table.setRowSorter(sorter);
		RowFilter<TableModelLoanMaster, Object> rf = null;
		// If current expression doesn't parse, don't update.
		try
		{
			rf = RowFilter.regexFilter("(?i)^.*Fällig.*", 0);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		updateFields();
	}

	private void updateFields()
	{
		this.books = library.getBooks();
		updateStats();
	}

}
