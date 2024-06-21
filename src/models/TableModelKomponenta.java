/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import controller.ClientController;
import domain.Komponenta;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mihajlo.marcetic
 */
public class TableModelKomponenta extends AbstractTableModel implements Runnable
{

    private ArrayList<Komponenta> lista;
    private String[] kolone = {"ID", "Naziv komponente", "Opis"};
    private String parametar = "";
    
        public TableModelKomponenta() {
        try {
            lista = ClientController.getInstance().getAllKomponenta();
        } catch (Exception ex) {
            Logger.getLogger(TableModelKomponenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Komponenta k = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return k.getKomponentaID();
            case 1:
                return k.getNazivKomponente();
            case 2:
                return k.getOpis();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    
    public Komponenta getSelectedKomponenta(int row) {
        return lista.get(row);
    }
    
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(10000);
                refreshTable();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TableModelKomponenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            lista = ClientController.getInstance().getAllKomponenta();
            if (!parametar.equals("")) {
                ArrayList<Komponenta> novaLista = new ArrayList<>();
                for (Komponenta k : lista) {
                    if (k.getNazivKomponente().toLowerCase().contains(parametar.toLowerCase()))
                    {
                        novaLista.add(k);
                    }
                }
                lista = novaLista;
            }

            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
