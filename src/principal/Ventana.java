package principal;

import lexico.Lexico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class Ventana extends JFrame implements ActionListener {

    //atributos
    private JPanel panelContenedor;
    private JTextArea txtFuente;
    private JButton botonAnalizar;
    private JButton botonAbrir;
    private JTable tablaResultados;
    private JScrollPane desplazamientoTabla;
    private JScrollPane desplazamientoTexto;

    public Ventana() {
        super("Analizador Lexico");

        panelContenedor = new JPanel();
        setLayout(null);

        txtFuente = new JTextArea();
        txtFuente.setBounds(50, 50, 500, 500);
        add(txtFuente);

        desplazamientoTexto = new JScrollPane(txtFuente);
        desplazamientoTexto.setBounds(50, 50, 500, 500);
        add(desplazamientoTexto);

        botonAnalizar = new JButton("Analizar");
        botonAnalizar.setBounds(100, 600, 80, 30);
        botonAnalizar.addActionListener(this);
        add(botonAnalizar);

        botonAbrir = new JButton("Abrir");
        botonAbrir.setBounds(200, 600, 80, 30);
        botonAbrir.addActionListener(this);
        add(botonAbrir);

        tablaResultados = new JTable(new DefaultTableModel(new Object[][]{},
                new String[]{
                        "Simbolo", "Tipo",}));
        tablaResultados.setBounds(800, 50, 400, 500);
        add(tablaResultados);

        desplazamientoTabla = new JScrollPane(tablaResultados);
        desplazamientoTabla.setBounds(800, 50, 400, 500);
        add(desplazamientoTabla);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);

    }//fin del constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAnalizar) {
            limpiar();
            analizar();
        }
        else if(e.getSource() == botonAbrir)
        {
            abrir();
        }
    }//fin del metodo actionPerformed

    public void analizar() {

        DefaultTableModel modeloTabla = (DefaultTableModel)tablaResultados.getModel();
        Object[] filas = new Object[2];

        String fuente = txtFuente.getText();

        Lexico lexico = new Lexico();
        lexico.entrada(fuente);

        while (lexico.getSimbolo().compareTo("$") != 0)
        {
            lexico.siguienteSimbolo();

            filas[0] = lexico.getSimbolo();
            filas[1] = lexico.tipoACad(lexico.getTipo());

            modeloTabla.addRow(filas);
            tablaResultados.setModel(modeloTabla);
        }//fin de while

    }//fin del metodo analizar


    private void limpiar()
    {
        DefaultTableModel modeloTabla = (DefaultTableModel)tablaResultados.getModel();

        while(modeloTabla.getRowCount() > 0)
        {
            modeloTabla.removeRow(0);
        }
    }

    private void abrir()
    {
        try
        {
            JFileChooser seleccionarArchivo = new JFileChooser();
            seleccionarArchivo.showOpenDialog(null);

            FileReader fr = new FileReader(seleccionarArchivo.getSelectedFile().getPath());
            BufferedReader br = new BufferedReader(fr);
            String linea = "";

            while((linea = br.readLine()) != null)
            {
                txtFuente.append(linea + "\n");
            }

            fr.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}//fin de la clase Ventana