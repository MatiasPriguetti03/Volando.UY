package utils;

import java.awt.Component;
import java.util.List;
import java.awt.Color;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ColoredRowsTableCellRenderer extends DefaultTableCellRenderer {

	private List<Integer> coloredRows;
	private Color color;

    // Constructor que recibe los parámetros
    public ColoredRowsTableCellRenderer(List<Integer> coloredRows, Color color) {
        this.coloredRows = coloredRows;
        this.color = color;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Obtener el componente predeterminado
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Si la fila está en el conjunto de filas coloreadas, cambiar el fondo
        if (coloredRows.contains(row)) {
            comp.setBackground(this.color); // Color para las filas seleccionadas
        } else {
            comp.setBackground(Color.WHITE); // Color predeterminado para otras filas
        }

        // Si está seleccionada, sobreescribir para mostrar selección correctamente
        if (isSelected) {
            comp.setBackground(table.getSelectionBackground());
        }

        return comp;
    }
}
