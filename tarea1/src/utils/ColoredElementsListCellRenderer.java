package utils;

import java.awt.Component;
import java.util.List;
import java.awt.Color;
import javax.swing.*;

@SuppressWarnings("serial")
public class ColoredElementsListCellRenderer extends DefaultListCellRenderer {
    private List<String> coloredElements;
    private Color color;

    // Constructor que recibe los elementos a colorear
    public ColoredElementsListCellRenderer(List<String> coloredElements, Color color) {
        this.coloredElements = coloredElements;
        this.color = color;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // Obtener el componente predeterminado
        Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        // Cambiar el color de fondo si el elemento está en el conjunto de elementos a colorear
        if (coloredElements.contains(value)) {
        	comp.setBackground(this.color); // Color especial para elementos seleccionados
        } else {
            comp.setBackground(Color.WHITE); // Color predeterminado
        }

        // Si está seleccionado, sobreescribir el color para mostrar correctamente la selección
        if (isSelected) {
            comp.setBackground(list.getSelectionBackground());
            comp.setForeground(list.getSelectionForeground());
        } else {
            comp.setForeground(Color.BLACK);
        }

        return comp;
    }
}

