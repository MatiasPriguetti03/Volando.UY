package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PresentacionUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean validarCamposRequeridos(JInternalFrame frame, String errorMessage, Component... components) {
        for (Component component : components) {
        	if (estaVacio(component)) {
                mostrarError(frame, errorMessage);
                return false;
        	}
//            if (component instanceof JTextField) {
//                if (estaVacio((JTextField) component)) {
//                    mostrarError(frame, errorMessage);
//                    return false;
//                }
//            } else if (component instanceof JTextArea) {
//                if (estaVacio((JTextArea) component)) {
//                    mostrarError(frame, errorMessage);
//                    return false;
//                }
//            }
        }
        return true;
    }

    public static boolean validarSeleccionComboBox(JInternalFrame frame, JComboBox<?> comboBox, String errorMessage) {
        if (comboBox.getSelectedItem() == null || comboBox.getSelectedItem() == "") {
            mostrarError(frame, errorMessage);
            return false;
        }
        return true;
    }

    public static boolean estaVacio(Component component) {
        if (component instanceof JTextField) {
            return ((JTextField) component).getText().trim().isEmpty();
        } else if (component instanceof JTextArea) {
            return ((JTextArea) component).getText().trim().isEmpty();
        } else if (component instanceof JList){
        	return ((JList) component).getSelectedValue() == null;
        } else if (component instanceof JDateChooser){
        	return ((JDateChooser) component).getDate() == null;
        } else if (component instanceof JTable){
        	return ((JTable) component).getSelectedRow() == -1;
        } else {
        	return true;
        }
    }

    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    
    public static String parseLocalDate(LocalDate date) throws DateTimeParseException {
    	String formattedTime = date.format(DATE_FORMATTER);
    	
        return formattedTime;
    }
    
    public static LocalTime parseTime(String timeStr) throws DateTimeParseException {
    	DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
    	return LocalTime.parse(timeStr, format);
    }
    
    public static String parseLocalTime(LocalTime time) throws DateTimeParseException {
    	DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
    	String formattedTime = time.format(format);
        return formattedTime;
    }

    public static void mostrarError(JInternalFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarExito(JInternalFrame frame, String message) { 
        JOptionPane.showMessageDialog(frame, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarInformacion(JInternalFrame frame, String message) {  
        JOptionPane.showMessageDialog(frame, message, "Atención", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean mostrarConfirmDialog(JInternalFrame frame, String message) {  ;
        int result = JOptionPane.showConfirmDialog(frame, message, "Confirmar", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    public static void enableComponents(boolean enable, JComponent... components) {  
        for (JComponent component : components) {
            component.setEnabled(enable);
        }
    }

    public static void limpiarCamposTexto(Component... components) {
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            } else if (component instanceof JTextArea) {
                ((JTextArea) component).setText("");
            } else if (component instanceof JFormattedTextField) {
                ((JFormattedTextField) component).setText("");
            } else if (component instanceof JDateChooser) {
                ((JDateChooser) component).setDate(null);
            } else if (component instanceof JPasswordField) {
                ((JPasswordField) component).setText(null);
            }
        }
    }
    
    public static void cargarComboBox(String[] lista, JComboBox<String> comboBox) {
		DefaultComboBoxModel<String> model;
		String[] opciones = new String[lista.length+1];
		
		int ind = 1;
		for(String opcion: lista) {
			opciones[ind] = opcion;
			ind++;
		}
		opciones[0] = "";
		
        model = new DefaultComboBoxModel<String>(opciones);
        comboBox.setModel(model);
	}
    
    public static void limpiarComboBoxes(JComboBox<?>... comboBoxes) {
    	for(JComboBox<?> combB : comboBoxes)
    		if (combB.getSelectedItem() == null || combB.getSelectedItem() == "") {
    			((JComboBox<?>) combB).removeAllItems();
    			//((JComboBox<?>) combB).setSelectedItem("");
    		}
    }
    
    public static void limpiarTablasyListas(Component... components) {
        for (Component component : components) {
            if (component instanceof JList) {
            	((JList) component).clearSelection();
            	DefaultListModel<String> model = new DefaultListModel<>();
                ((JList) component).setModel(model);
            } else if (component instanceof JTable) {
            	DefaultTableModel model = (DefaultTableModel) ((JTable)component).getModel();
            	while (model.getRowCount() > 0) {
	      			model.removeRow(0);
	      		}
            } 
        }
    }
    
    public static void ajustarAlturaFilasTabla(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int maxHeight = 16; // Altura mínima por fila

            for (int column = 0; column < table.getColumnCount(); column++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                int cellHeight = comp.getPreferredSize().height;
                maxHeight = Math.max(maxHeight, cellHeight); // Obtener la altura máxima de la fila
            }

            table.setRowHeight(row, maxHeight); // Ajustar la altura de la fila
        }
    }
}