package sintactico;

import java.util.Stack;

class Nodo
{
    //atributos
    Nodo nodoIzquierdo;
    public String simbolo;
    Nodo nodoDerecho;

    //constructor sin parametros
    public Nodo() {

    }//fin del constructor sin parametros

    //constructor parametrizado
    public Nodo(String simbolo) {
        this.simbolo = simbolo;
        nodoIzquierdo = nodoDerecho = null;
    }//fin del constrcutor parametrizado

    public void mostrar(){
        System.out.println(nodoDerecho.simbolo + nodoIzquierdo.simbolo);
    }

}//fin de la clase Nodo

class Programa extends Nodo
{
    public Programa(Stack<ElementoPila> pila){
        simbolo = "<Programa>";
        pila.pop();

    }
}//fin de la clase Programa

class Identificador extends Nodo {

    //constructor
    public Identificador(Stack<ElementoPila> pila) {
        pila.pop();
        simbolo = ((Terminal) pila.pop()).getElemento();
    }//fin del constructor

}//fin de la clase Identificador

class Entero extends Nodo {

    //constructor
    public Entero(Stack<ElementoPila> pila) {
        pila.pop();
        simbolo = ((Terminal) pila.pop()).getElemento();
    }//fin del constrcutor

}//fin de la clase Entero

class Suma extends Nodo {

    String operador;


    //constructor
    public Suma(Stack<ElementoPila> pila) {

        simbolo = "<Expresion>";

        pila.pop();
        //simbolo = ((Terminal)pila.Pop()).Elemento;
        nodoDerecho = (pila.pop().getNodo());
        pila.pop();
        operador = pila.pop().getElemento();
        pila.pop();
        nodoIzquierdo = (pila.pop().getNodo());

        System.out.println(nodoDerecho.toString() + " " + operador + " " + nodoIzquierdo.toString());



    }//fin del constructor

}//fin de la clase Suma

class Multiplicacion extends Nodo {

    String operador;

    //constructor
    public Multiplicacion(Stack<ElementoPila> pila) {
        simbolo = "<Expresion>";
        pila.pop();
        nodoDerecho = (pila.pop().getNodo());
        pila.pop();
        operador = pila.pop().getElemento();
        pila.pop();
        nodoIzquierdo = (pila.pop().getNodo());

        System.out.println(nodoDerecho.toString() + " " + operador + " " + nodoIzquierdo.toString());

    }//fin del constructor
}//fin de la clase Multiplicacion