package utils;

import java.awt.Component;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class LineWrapCellRenderer extends JTextArea implements TableCellRenderer {

	private Border border;	
	
    public LineWrapCellRenderer() {
        setLineWrap(true);              // Habilitar el ajuste de línea
        setWrapStyleWord(true);         // Ajuste de palabra completa
        setOpaque(true);               // Permitir que el fondo se dibuje
        
        border = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230));
//        BorderFactory.createEmptyBorder(0, 1, 0, 0)
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");  // Establecer el texto
        setBorder(border);
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height); // Ajustar el tamaño

        return this;
    }
}
