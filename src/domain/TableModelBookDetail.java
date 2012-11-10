package domain;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TableModelBookDetail extends AbstractTableModel {

	Library library;
	List<Copy> copies;
	String[] header;

	public TableModelBookDetail(Library library, List<Copy> copies, String[] header) {
		this.library = library;
		this.copies = copies;
		this.header = header;
		
		fireTableDataChanged();
	}

	public Object getValueAt(int row, int colum) {
		Copy copy = copies.get(row);
		switch (colum) {
		case 0:
			return copy.getInventoryNumber();
		case 1:
			System.out.println(library.getLoanOfCopy(copy));
			if(library.getLoanOfCopy(copy) != null){
				return "Ausgeliehen, noch " + library.getLoanOfCopy(copy).getDaysOfLoanDuration() + " Tage bis zur R�ckgabe";				
			}
			else{
				return "Verf�gbar";
			}
		default:
			return null;
		}
	}
	

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return copies.size();
	}
	
	public void addRow(List<Copy> copies){
		this.copies = copies;
		fireTableDataChanged();
		fireTableRowsInserted(0, getRowCount());
	}

}
