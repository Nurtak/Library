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

import domain.Book;
import domain.Library;

public class BookMasterBooksTab extends JPanel implements Observer
{
	private JTable table;
	private JTextField txtSuche;
	private List<Book> books;
	private TableRowSorter<TableModelBookMaster> sorter;
	private TableModelBookMaster tableModel;
	private Library library;
	private JCheckBox chckbxNurVerfgbare;
	private JScrollPane scrollPane;
	private JLabel lblAnzahlBuecher;
	private JLabel lblAnzahlExemplare;
	private JButton btnSelektiertesAnzeigen;
	private JButton btnNeuesBuch;

	public BookMasterBooksTab(Library library)
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
		GridBagLayout gbl_inventarStatistikenPanel = new GridBagLayout();
		gbl_inventarStatistikenPanel.columnWidths = new int[] { 56, 81, 0 };
		gbl_inventarStatistikenPanel.rowHeights = new int[] { 14, 0 };
		gbl_inventarStatistikenPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_inventarStatistikenPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		inventarStatistikenPanel.setLayout(gbl_inventarStatistikenPanel);

		lblAnzahlBuecher = new JLabel("Anzahl Bücher: " + library.getBooks().size());
		GridBagConstraints gbc_lblAnzahlBuecher = new GridBagConstraints();
		gbc_lblAnzahlBuecher.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAnzahlBuecher.insets = new Insets(0, 0, 0, 5);
		gbc_lblAnzahlBuecher.gridx = 0;
		gbc_lblAnzahlBuecher.gridy = 0;
		inventarStatistikenPanel.add(lblAnzahlBuecher, gbc_lblAnzahlBuecher);

		lblAnzahlExemplare = new JLabel("Anzahl Exemplare: " + (library.getBooks().size() + library.getCopies().size()));
		GridBagConstraints gbc_lblAnzahlExemplare = new GridBagConstraints();
		gbc_lblAnzahlExemplare.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAnzahlExemplare.gridx = 1;
		gbc_lblAnzahlExemplare.gridy = 0;
		inventarStatistikenPanel.add(lblAnzahlExemplare, gbc_lblAnzahlExemplare);

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
		txtSuche.setToolTipText("Geben Sie hier den Namen, Author oder Verlag eines Buches ein, dass Sie suchen möchten");
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
		btnSelektiertesAnzeigen = new JButton("Buchdetail anzeigen", iconSelektiertesAnzeigen);
		btnSelektiertesAnzeigen.setToolTipText("Zeigt das in der untenstehenden Tabelle ausgewählte Buch in einer Detailansicht an");
		btnSelektiertesAnzeigen.setEnabled(false);
		btnSelektiertesAnzeigen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int selected[] = table.getSelectedRows();
				for (int i : selected)
				{
					Book book = tableModel.getBookAtRow(table.convertRowIndexToModel(i));
					BookDetail bookDetail = new BookDetail(book, library);
				}
			}
		});

		// Ab hier "Nur Verf�gbare"
		chckbxNurVerfgbare = new JCheckBox("Nur Verfügbare");
		chckbxNurVerfgbare.setToolTipText("Falls markiert, werden nur B�cher mit verfügbaren Exemplaren angezeigt");
		chckbxNurVerfgbare.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent arg0)
			{
				// 1 = Selected, 2 = Not Selected
				// remove all entries
				// deleteTableRows(table);
				if (arg0.getStateChange() == 1)
				{ 
					// If hook is set, add only entries with available Copies
					// (Number of Copies - Number of Lent Copies)
					addAvailableBooks();
				} else
				{ // if hook is not set, add all Books
					sorter.setRowFilter(null);
				}
			}
		});

		GridBagConstraints gbc_chckbxNurVerfgbare = new GridBagConstraints();
		gbc_chckbxNurVerfgbare.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNurVerfgbare.gridx = 3;
		gbc_chckbxNurVerfgbare.gridy = 0;
		buchInventarPanel.add(chckbxNurVerfgbare, gbc_chckbxNurVerfgbare);
		GridBagConstraints gbc_btnSelektierteAnzeigen = new GridBagConstraints();
		gbc_btnSelektierteAnzeigen.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSelektierteAnzeigen.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelektierteAnzeigen.gridx = 4;
		gbc_btnSelektierteAnzeigen.gridy = 0;
		buchInventarPanel.add(btnSelektiertesAnzeigen, gbc_btnSelektierteAnzeigen);

		// Ab hier "Neues Buch hinzuf�gen"

		ImageIcon iconBookAdd = new ImageIcon("icons/book_add.png");
		btnNeuesBuch = new JButton("Neues Buch hinzufügen", iconBookAdd);
		btnNeuesBuch.setToolTipText("Öffnet ein Fenster um ein neues Buch zu erfassen");
		btnNeuesBuch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				BookAdd bookAdd = new BookAdd(library);
			}
		});

		GridBagConstraints gbc_btnNeuesBuch = new GridBagConstraints();
		gbc_btnNeuesBuch.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNeuesBuch.insets = new Insets(0, 0, 5, 0);
		gbc_btnNeuesBuch.gridx = 5;
		gbc_btnNeuesBuch.gridy = 0;
		buchInventarPanel.add(btnNeuesBuch, gbc_btnNeuesBuch);

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
						Book book = tableModel.getBookAtRow(table.convertRowIndexToModel(i));
						BookDetail bookDetail = new BookDetail(book, library);
					}
				}
			}
		});

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);

		books = library.getBooks();
		tableModel = new TableModelBookMaster(library, new String[] { "Verfügbar", "Name", "Autor", "Verlag" });
		table.setModel(tableModel);

		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(80);

		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);

		sorter = new TableRowSorter<TableModelBookMaster>(tableModel);
		table.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);
		sorter.toggleSortOrder(1);
	}

	/**
	 * Adds only Available Books from the Library to the Inventary Table
	 */
	private void addAvailableBooks()
	{

		sorter = new TableRowSorter<TableModelBookMaster>(tableModel);
		table.setRowSorter(sorter);
		RowFilter<TableModelBookMaster, Object> rf = null;
		List<RowFilter<TableModelBookMaster, Object>> filters = new ArrayList<RowFilter<TableModelBookMaster, Object>>();
		// If current expression doesn't parse, don't update.
		try
		{
			rf = RowFilter.regexFilter("[^0]", 0);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}

	private void search()
	{
		RowFilter<TableModelBookMaster, Object> rf = null;
		List<RowFilter<TableModelBookMaster, Object>> filters = new ArrayList<RowFilter<TableModelBookMaster, Object>>();
		// If current expression doesn't parse, don't update.
		try
		{
			RowFilter<TableModelBookMaster, Object> rfTitel = RowFilter.regexFilter("(?i)^.*" + txtSuche.getText() + ".*", 1);
			RowFilter<TableModelBookMaster, Object> rfAutor = RowFilter.regexFilter("(?i)^.*" + txtSuche.getText() + ".*", 2);
			RowFilter<TableModelBookMaster, Object> rfVerlag = RowFilter.regexFilter("(?i)^.*" + txtSuche.getText() + ".*", 3);
			filters.add(rfAutor);
			filters.add(rfTitel);
			filters.add(rfVerlag);
			rf = RowFilter.orFilter(filters);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}

	private void updateStats()
	{
		lblAnzahlBuecher.setText("Anzahl Bücher: " + books.size());
		lblAnzahlExemplare.setText("Anzahl Exemplare: " + (library.getBooks().size() + library.getCopies().size()));
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
