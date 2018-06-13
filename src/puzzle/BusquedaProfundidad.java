package puzzle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Proyecto de S.I 1 de Puzzle 28-11-15
 * @author Alejandro Alaniz J.
 */
class Nodo{
 public Nodo(int[][] estado){
  this.estado = estado;
  this.hijo = new ArrayList<Nodo>();
 } 
 //get and set for state and children

 public int[][] getEstado(){ //metodo para sacar en el estado que se encuntra el algoritmo
  return estado;
 }

 public void setEstado(int[][] estado){ //metodo para mostrar el conjunto de estados
  this.estado = estado;
 }

 public void prependhijo(Nodo nodo){
  this.hijo.add(0, nodo);
 }

 public void appendhijo(Nodo nodo){
  this.hijo.add(nodo);
 }

 public ArrayList<Nodo> gethijo(){ //metodo para sacar el siguiente nodo (hijo)
  return this.hijo; 
 }

 public void seriehijo(ArrayList<Nodo> hijo){
  this.hijo = hijo;
 }
 
 public String to_string(){
  String resultado = "";
   for(int[] y : estado){
    for(int x : y){
     resultado += x + " ";
    }
    resultado += "\n";
   }
  return resultado;
 }
 private int[][] estado;
 private ArrayList<Nodo> hijo;
}
class Resolver{ // en esta clase estan almacenados todos los metodos para resolver el puzzle con el algoritmo de profundidad
 public Resolver(int[][] init_estado, int[][] meta_estado){
  in = new Scanner(System.in);
  Nodo a = new Nodo(init_estado);
  this.raiz = a;
  this.meta_estado = meta_estado;
  this.lista_abierta = new ArrayList<Nodo>();
  this.lista_cerrada = new ArrayList<Nodo>();
  this.estados_nuevos = 0;
} 
 /*
 Busqueda en Profundidad
 */

 public void resolver_profundidad(){
  int total =0;
  boolean ter = false;
  Nodo nodo;
  this.raiz.seriehijo(new ArrayList<Nodo>());
  this.lista_abierta = new ArrayList<Nodo>(); 
  this.lista_cerrada = new ArrayList<Nodo>(); 
  this.lista_abierta.add(this.raiz);
  while(!this.lista_abierta.isEmpty() && !ter){ //Revisa que se cumpla la condicion de que si ya se resolvio o si no manda llamar a otro metodo
   nodo =  this.lista_abierta.remove(0);
   System.out.println("Total de:"+estados_nuevos + " Estados" );
   estados_nuevos=0;
  System.out.println("--------------------------------------");
   System.out.println("Usandose:\n" + nodo.to_string());
  System.out.println("--------------------------------------");
  this.lista_cerrada.add(nodo);
   //we need to see if node is inside closed list to ignore it
   if (Arrays.deepEquals( nodo.getEstado(), this.meta_estado) ){
    ter = true;
   }
   else{
    expandir_nodo_profundidad(nodo); 
   }
    total++;
    System.out.println(total);
   }  
  System.out.println("Total movimientos: " + total);
}
 public void expandir_nodo_profundidad(Nodo nodo){
  int[] indice_vacio = new int[2];
  ArrayList<Nodo> nodo_hijo = new ArrayList<Nodo>();
  Nodo hijo;
  int[][] estado = nodo.getEstado();
  for(int fila_vacia=0; fila_vacia < estado.length; fila_vacia++){
   for(int columna_vacia=0;columna_vacia < estado[fila_vacia].length; columna_vacia++){
    if(estado[fila_vacia][columna_vacia] == 0){
     indice_vacio[0] = fila_vacia;
    indice_vacio[1] = columna_vacia;
    }
   }
  }
  int fila = indice_vacio[0];
  int columna = indice_vacio[1];
  //System.out.println("x is " +empty_index[1] +"\ny is " + empty_index[0]);
  int valor_actual_arriba, valor_actual_abajo, valor_actual_izquierda, valor_actual_derecha;
  if(fila!=0){ 
   hijo = new Nodo(clonar_estado(estado));
   valor_actual_arriba = hijo.getEstado()[fila-1][columna];
   hijo.getEstado()[fila][columna] = valor_actual_arriba;
   hijo.getEstado()[fila-1][columna] = 0;
   nodo_hijo.add(0,hijo);
  }
  if(fila!=2){ 
   hijo = new Nodo(clonar_estado(estado));
   valor_actual_abajo = hijo.getEstado()[fila+1][columna];
   hijo.getEstado()[fila][columna] = valor_actual_abajo;
   hijo.getEstado()[fila+1][columna] = 0;
   nodo_hijo.add(0,hijo);
  }
  if(columna!=0){ 
   hijo= new Nodo(clonar_estado(estado));
   valor_actual_izquierda = hijo.getEstado()[fila][columna-1];
   hijo.getEstado()[fila][columna] = valor_actual_izquierda;
   hijo.getEstado()[fila][columna-1] = 0;
   nodo_hijo.add(0,hijo);
  }
  if(columna!=2){ 
   hijo= new Nodo(clonar_estado(estado));
   valor_actual_derecha = hijo.getEstado()[fila][columna+1];
   hijo.getEstado()[fila][columna] = valor_actual_derecha;
   hijo.getEstado()[fila][columna+1] = 0;
   nodo_hijo.add(0,hijo);
  }
  ArrayList<Nodo> printable_hijo = new ArrayList<Nodo>();
  //#node_children has each node expansion of the node parameter
  //#node_children has priorities. The first element has lower priority than the last one
  for (Nodo achild : nodo_hijo){
   if(!cerrar_has_hijo(achild)){
    this.lista_abierta.add(0, achild);
    this.estados_nuevos++;
    printable_hijo.add(0,achild);
   }
  }
  int printable_hijo_size = printable_hijo.size(); //imprime el contador de lineas que lleva el puzzle 3x3 
  int contador_lineas=0;
  String[] lineas = {"","",""};
  for (Nodo achild : printable_hijo){
   lineas[0] += " " + Arrays.toString(achild.getEstado()[0]);
   lineas[1] += " " + Arrays.toString(achild.getEstado()[1]);
   lineas[2] += " " + Arrays.toString(achild.getEstado()[2]);
   contador_lineas++;
  }
  System.out.println("Estados Nuevos");
  for (String linea : lineas){
  System.out.println(linea);
  }
  }

 /*
Busqueda en Profundidad
 */

 public boolean cerrar_has_hijo(Nodo hijo){
  for(Nodo cerrar : this.lista_cerrada){
   if(Arrays.deepEquals(cerrar.getEstado(), hijo.getEstado()) ){
    return true;
   }
  }
  return false;
 }
  public int[][] clonar_estado(int[][] estado){
  int [][] clonar = new int[estado.length][];
  for(int i = 0; i < estado.length; i++)
  {clonar[i] = estado[i].clone();}
    return clonar;
 }
 private Nodo raiz;
 private int[][] meta_estado;
 private ArrayList<Nodo> lista_abierta;
 private ArrayList<Nodo> lista_cerrada;
 private Scanner in;
 private int estados_nuevos;
}
public class BusquedaProfundidad{
 public static void main(String args[]){
  int[][] estadoinicial = {{2,8,3},{1,6,4},{7,0,5}};
  int[][] meta = {{1,2,3},{4,5,6},{7,8,0}}; //nunca cambia
  Resolver resolver = new Resolver(estadoinicial, meta);
  resolver.resolver_profundidad();
}
}