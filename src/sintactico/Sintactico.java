package sintactico;

import lexico.Lexico;
import lexico.TipoSimbolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Sintactico {

    //atributos
    private Lexico lexico;
    private String entrada;
    private int fila, columna, accion;
    private Stack<ElementoPila> pila;
    private NoTerminal nt;
    private Nodo nodo;

    private JTable tablaResultados;
    private int[][] tabla = new int[95][46];
    public int[] idReglas = new int[52];
    public int[] lonReglas = new int[52];
    public String[] strReglas = new String[52];

    //constructor
    public Sintactico(JTable tablaResultados) {
        cargarArchivo();
        entrada = "";
        lexico = new Lexico("");
        pila = new Stack<ElementoPila>();
        this.tablaResultados = tablaResultados;
    }//fin del constructor

    //constructor parametrizado
    public Sintactico(String entrada) {
        cargarArchivo();
        this.entrada = entrada;
        lexico = new Lexico(entrada);
        pila = new Stack<ElementoPila>();
    }//fin del constructor parametrizado

    public void setEntrada(String entrada) {
        this.entrada = entrada;
        lexico = new Lexico(entrada);
        pila.clear();
    }

    public String getEntrada() {
        return entrada;
    }

    public void analisisSintactico() {
        DefaultTableModel modeloTabla = (DefaultTableModel)tablaResultados.getModel();
        Object[] filas = new Object[3];

        //System.out.println("Cuajo: " + );

        accion = 0;
        pila.push(new Terminal(TipoSimbolo.PESOS, "$"));
        pila.push(new Estado(0));
        lexico.siguienteSimbolo();

        while(true)
        {
            fila = pila.peek ().getId();
            columna = lexico.getTipo();
            accion = tabla[fila] [columna];


            ElementoPila[] elementos = new ElementoPila[pila.size()];
            String elementosEnPila = "";
            for (int i = 0; i < elementos.length; i++) {
                elementos[i] = pila.get(i);
            }

            //reversar
            Collections.reverse(Arrays.asList(elementos));

            for (int i = elementos.length - 1; i >= 0; i--) {
                elementosEnPila += elementos[i].getElemento();
            }



            filas[0] = elementosEnPila;
            filas[1] = lexico.getSimbolo();
            filas[2] = accion;

            modeloTabla.addRow(filas);
            tablaResultados.setModel(modeloTabla);
            //System.out.printf("%50s%10s%10s\n", elementosEnPila, lexico.simbolo, accion);

            if(accion > 0) // desplazamiento
            {
                pila.push(new Terminal(lexico.getTipo(), lexico.getSimbolo()));
                pila.push(new Estado(accion));
                lexico.siguienteSimbolo();

            }//fin de if

            else if(accion < 0) //aceptacion reduccion
            {
                if(accion == -1)
                {
                    fila = pila.peek().getId();
                    columna = lexico.getTipo();
                    accion = tabla[fila][columna];
                    break;
                }//fin de if

                int regla = -(accion + 2);
                int reglaAux = (regla + 1);

                switch(reglaAux)
                {


                    case 36: //<Termino> ::= id
                        nodo = new Identificador(pila);
                        break;

                    case 37: //<Termino> ::= entero
                        nodo = new Entero(pila);
                        break;

                    case 46: //<Expresion> ::= <Termino> * <Termino>
                        nodo = new Multiplicacion(pila);
                        break;

                    case 47:  //<Expresion> ::= <Termino> + <Termino>
                        nodo = new Suma(pila);
                        break;

                    case 52: //<Expresion> ::= <Termino>
                        pila.pop();
                        nodo = pila.pop().getNodo();
                        break;

                    default:
                        for(int i = 0; i < lonReglas[regla] * 2; i++){
                            pila.pop();
                        }
                        break;
                }//fin de switch

                fila = pila.peek().getId();
                columna = idReglas[regla];
                accion = tabla[fila] [columna];

                nt = new NoTerminal(idReglas[regla], strReglas[regla]);
                nt.setNodo(nodo);

                pila.push(nt);
                pila.push(new Estado(accion));
            }//fin de else if

            if(accion == 0)
                return;



        }//fin de while

    }//fin del metodo analisisSintactico


    private void cargarArchivo() {
        try {
            FileReader fr = new FileReader("compilador.lr");
            BufferedReader br = new BufferedReader(fr);
            String linea = "";
            int contadorLinea = 0;

            while ((linea = br.readLine()) != null) {
                String arreglo[] = linea.split("\\s+");


                for (int i = 0; i < arreglo.length && contadorLinea < 53; i++) {
                    if (contadorLinea > 0) {
                        if (i == 0) {
                            idReglas[contadorLinea - 1] = Integer.valueOf(arreglo[i]);
                        }
                        if (i == 1) {
                            lonReglas[contadorLinea - 1] = Integer.valueOf(arreglo[i]);
                        }
                        if (i == 2) {
                            strReglas[contadorLinea - 1] = arreglo[i];
                        }

                    }//fin de if
                }//fin de for

                for (int i = 0; i < arreglo.length; i++) {
                    if (contadorLinea > 53) {
                        tabla[contadorLinea - 54][i] = Integer.valueOf(arreglo[i]);
                    }
                }
                contadorLinea++;
            }//fin de while

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void Gramatica_3()
    {
        // <Expresion> ::= <Expresion> opMul <Expresion>
        // <Expresion> ::= <Expresion> opSuma <Expresion>
        // <Expresion> ::= <Termino>
        // <Termino> ::= id
        // <Termino> ::= entero
        DefaultTableModel modeloTabla = (DefaultTableModel)tablaResultados.getModel();
        Object[] filas = new Object[3];

        int[] idReglas = { 5, 5, 5, 6, 6 };
        int[] lonReglas = { 3, 3, 1, 1, 1 };
        String[] strReglas = { "<Expresion1>", "<Expresion2>", "<Expresion3>", "<Termino4>", "<Termino5>" };

        //Tabla de la grámatica
        int[][] tablaLR =
                {
                        {3, 4,  0,  0,  0, 1, 2},
                        {0, 0,  5,  6, -1, 0, 0},
                        {0, 0, -4, -4, -4, 0, 0},
                        {0, 0, -5, -5, -5, 0, 0},
                        {0, 0, -6, -6, -6, 0, 0},
                        {3, 4,  0,  0,  0, 7, 2},
                        {3, 4,  0,  0,  0, 8, 2},
                        {0, 0, -2, -2, -2, 0, 0},
                        {0, 0,  5, -3, -3, 0, 0}
                };

        accion = 0;
        pila.push(new Terminal(TipoSimbolo.PESOS, "$"));
        pila.push(new Estado(0));
        lexico.siguienteSimbolo();

        //System.out.printf("%50s%10s%10s\n", "Elementos en pila", "Simbolo", "Accion");

        while(true)
        {
            fila = pila.peek ().getId();
            columna = lexico.getTipo();
            accion = tablaLR [fila] [columna];


            ElementoPila[] elementos = new ElementoPila[pila.size()];
            String elementosEnPila = "";
            for (int i = 0; i < elementos.length; i++) {
                elementos[i] = pila.get(i);
            }

            //reversar
            Collections.reverse(Arrays.asList(elementos));

            for (int i = elementos.length - 1; i >= 0; i--) {
                elementosEnPila += elementos[i].getElemento();
            }



            filas[0] = elementosEnPila;
            filas[1] = lexico.getSimbolo();
            filas[2] = accion;

            modeloTabla.addRow(filas);
            tablaResultados.setModel(modeloTabla);
            //System.out.printf("%50s%10s%10s\n", elementosEnPila, lexico.simbolo, accion);

            if(accion > 0) // desplazamiento
            {
                pila.push(new Terminal(lexico.getTipo(), lexico.getSimbolo()));
                pila.push(new Estado(accion));
                lexico.siguienteSimbolo();

            }//fin de if

            else if(accion < 0) //aceptacion reduccion
            {
                if(accion == -1)
                {
                    fila = pila.peek().getId();
                    columna = lexico.getTipo();
                    accion = tablaLR[fila][columna];

                    //Console.WriteLine("{0, 60}", "Aceptacion");

                    break;
                }//fin de if

                int regla = -(accion + 2);
                int reglaAux = (regla + 1);

                switch(reglaAux)
                {

                    case 1: //<Expresion> ::= <Termino> * <Termino>
                        nodo = new Multiplicacion(pila);
                        break;

                    case 2:  //<Expresion> ::= <Termino> + <Termino>
                        nodo = new Suma(pila);
                        break;

                    case 3: //<Expresion> ::= <Termino>
                        pila.pop();
                        nodo = pila.pop().getNodo();
                        break;

                    case 4: //<Termino> ::= id
                        nodo = new Identificador(pila);
                        break;

                    case 5: //<Termino> ::= entero
                        nodo = new Entero(pila);
                        break;

                    default:
                        for(int i = 0; i < lonReglas[regla] * 2; i++){
                            pila.pop();
                        }
                        break;
                }//fin de switch

                fila = pila.peek().getId();
                columna = idReglas[regla];
                accion = tablaLR[fila] [columna];

                nt = new NoTerminal(idReglas[regla], strReglas[regla]);
                nt.setNodo(nodo);

                pila.push(nt);
                pila.push(new Estado(accion));
            }//fin de else if

            if(accion == 0)
                return;



        }//fin de while

    }//fin del metodo Gramatica 3


}//fin de la clase Sintactico