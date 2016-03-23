package sintactico;

import java.util.Stack;

public class Arbol {


}//fin de la clase Arbol

class Nodo extends Arbol{
    //atributos
    public String simbolo;

    //constructor sin parametros
    public Nodo() {

    }//fin del constructor sin parametros

    //constructor parametrizado
    public Nodo(Nodo nodo) {

    }//fin del constrcutor parametrizado

    public void muestra() {
    }

}//fin de la clase Nodo

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

    Nodo nodoIzquierdo;
    Nodo nodoDerecho;

    //constructor
    public Suma(Stack<ElementoPila> pila) {
        pila.pop();
        //simbolo = ((Terminal)pila.Pop()).Elemento;
        nodoDerecho = new Nodo(pila.pop().getNodo());
        pila.pop();
        simbolo = pila.pop().getElemento();
        pila.pop();
        nodoIzquierdo = new Nodo(pila.pop().getNodo());

    }//fin del constructor

}//fin de la clase Suma

class Multiplicacion extends Nodo {

    Nodo nodoDerecho;
    Nodo nodoIzquierdo;

    //constructor
    public Multiplicacion(Stack<ElementoPila> pila) {
        pila.pop();
        nodoDerecho = new Nodo(pila.pop().getNodo());
        pila.pop();
        simbolo = pila.pop().getElemento();
        pila.pop();
        nodoIzquierdo = new Nodo(pila.pop().getNodo());

    }//fin del constructor
}//fin de la clase Multiplicacion