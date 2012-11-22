package tablemodel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import domain.Book;
import domain.Copy;
import domain.Library;
import domain.Loan;

public class TableModelBookDetail extends AbstractTableModel implements Observer {

	Library library;
	List<Copy> copies;
	String[] header;
	Book book;

	public TableModelBookDetail(Library library, Book book, String[] header) {
		this.library = library;
		this.book = book;
		this.copies = library.getCopiesOfBook(book);
		this.header = header;
		this.library.addObserver(this);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		switch (columnIndex) {
		case 0:
			return Long.class;
		case 1:
			return String.class;
		default:
			return null;
		}
	}

	public Object getValueAt(int row, int colum) {
		Copy copy = copies.get(row);
		switch (colum) {
		case 0:
			return copy.getInventoryNumber();
		case 1:
			Loan l = library.getLoanOfCopy(copy);
			if (l != null) {
				if (l.isLent()) { //Damit keine schon zur�ckgegebene Loans angezeigt werden
					if (!l.isOverdue()) {
						return l.getDueDateString() + " (Noch " + l.getDaysTilDue()
								+ " Tage bis zur R�ckgabe)";
					} else {
						return l.getDueDateString() + " (F�llig!)";
					}
				} else {
					return "Verf�gbar";
				}
			} else {
				return "Verf�gbar";
			}
		default:
			return null;
		}
	}

	public Copy getCopyAtRow(int row) {
		return copies.get(row);
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public int getRowCount() {
		if (copies != null) {
			return copies.size();
		} else {
			return 0;
		}
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	@Override
	public void update(Observable o, Object arg)
	{	
		this.copies = library.getCopiesOfBook(book);
		fireTableDataChanged();
	}
}
