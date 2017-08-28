package servicios;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

import org.osoa.sca.annotations.Reference;
import interfaces.ServicioResolverSopa;

public class TableroImpl implements Runnable{

	public final int NUM_NODOS=1;//1 3 4 5 6
	@Reference
	public ServicioResolverSopa fuente_1;
	/*@Reference
	public ServicioResolverSopa fuente_2;
	@Reference
	public ServicioResolverSopa fuente_3;
	@Reference
	public ServicioResolverSopa fuente_4;
	@Reference
	public ServicioResolverSopa fuente_5;
	@Reference
	public ServicioResolverSopa fuente_6;
	*/
	private Hilo[] nodos;
	private char[][] board;
	private TreeSet<String> diccionario;
	private ArrayList<String> solucion;
	private Scanner scanner;
	private long t1, t2;
	
	private void inicializarData(){
		String ruta = "";
		try {
			t1=System.currentTimeMillis();//time 1
	 		scanner = new Scanner(System.in);
	 		System.out.println("Por favor digite la ruta del archivo .txt con los datos correspondientes");
	 		ruta = scanner.nextLine();
	 		scanner = new Scanner(new File(ruta));
	 		//data
	 		String[] size = scanner.nextLine().split("x");
			scanner.nextLine();//linea en blanco
			int filas = Integer.parseInt(size[1]); //largo de segundo
			int columnas = Integer.parseInt(size[0]);//ancho de primero
			board = new char[filas][columnas];
			for (int i = 0; i < filas; i++){
				String line = scanner.nextLine();
				board[i] = line.replaceAll(",","").toUpperCase().toCharArray();			
			}
			scanner.nextLine();//linea en blanco		
			//leer palabras
			diccionario = new TreeSet<String>();
			while(scanner.hasNext())
				diccionario.add(scanner.nextLine().toUpperCase());
			//test
			t2=System.currentTimeMillis();
			System.out.println("Tiempo de lectura e inicializacion = "+(t2-t1)+" milisegundos");
			printBoard();
			System.out.println("Se agrego un board con "+board.length+" filas y "+board[0].length+" columnas");
			solucion = new ArrayList<String>();
	 	} catch (Exception e) {
	 		System.out.println("error, no scanner! con ruta = '"+ruta+"'");
	 		System.exit(1);
	 	}
	}
	
	private void printBoard(){
		System.out.println("------------BOARD------------");
		System.out.println(board[0].length+" "+board.length);
		for (int row=0; row<board.length; row++){
			for (int col = 0; col < board[row].length; col++)
				System.out.print(board[row][col]+" ");
			System.out.println();
		}
	}
	
	public synchronized void agregarASolucion(ArrayList<String> solves){
		solucion.addAll(solves);
		if(solucion.size()==diccionario.size()){
			t2=System.currentTimeMillis();
			System.out.println("Tiempo de solucionamiento = "+(t2-t1)+" milisegundos");
			Collections.sort(solucion);
			System.out.println("------------SOLUCION------------");
			for (int x = 0; x < solucion.size(); x++)
				System.out.println(solucion.get(x));
		}
	}
	
	private void distribuir(){
		int division = diccionario.size()/NUM_NODOS;
		System.out.println("Se particiono el diccionario para "+NUM_NODOS+" nodos, c/u con "+division+" palabras");
		System.out.println("Pero como la division no es exacta, la ultima fuente " +
						"va a tener "+diccionario.size()%NUM_NODOS+" palabra(s) mas");
		//long t1=System.currentTimeMillis();
		ArrayList<String> a = new ArrayList<String>(diccionario);/**/
		int pasadas = 0;
		t1=System.currentTimeMillis();
		/*Iterator<String> it =diccionario.iterator();
		boolean asignada = false;
		String w = it.next();
	    while(it.hasNext()){
	    	if(asignada)
	    		w = it.next();*/
			for (int x = 0; x < NUM_NODOS; x++) {
				TreeSet<String> t = new TreeSet<String>();
				if(x<NUM_NODOS-1){
					for (int x1 = (x)*division; x1 < (x+1)*division; x1++){
						t.add(a.get(x1));
						pasadas++;
					}
				}else{
					for (int x1 = (x)*division; x1 < a.size(); x1++){
						t.add(a.get(x1));
						pasadas++;
					}
				}
				Hilo nuevo=nodos[x];
				int id = nuevo.getIdentificador();
				nuevo = new Hilo(this, id, t, board);
				nodos[x] = nuevo;
				nodos[x].start(); //nodos[id-1]
				System.out.println("Inicio hilo de nodo"+(x+1));
			/*}
	    	Object[] inactivo=darNodoInactivo();
			if(inactivo!=null){
				Hilo hiloInactivo=(Hilo)inactivo[0];
				Integer indiceInactivo=(Integer)inactivo[1];
				Hilo nuevo=new Hilo(this,hiloInactivo.getIdentificador(),w,board);
				nodos[indiceInactivo]=nuevo;
				nuevo.start();
				asignada = true;
				pasadas++;
			}
			else
				asignada = false;*/
	    }
	    System.out.println("Se pasaron "+pasadas+" palabras");
	    t2=System.currentTimeMillis();
		System.out.println("Tiempo de distribucion = "+(t2-t1)+" milisegundos");
	}
	
	private Object[] darNodoInactivo(){
		Object[] respuesta=null;
		boolean continuar=true;
		for(int i=0;i<nodos.length && continuar;i++){
			Hilo h=nodos[i];
			if(!h.estaActivo()){
				respuesta=new Object[2];
				respuesta[0]=h;
				respuesta[1]=i;
				continuar=false;
			}
		}
		return respuesta;
	}

	@Override
	public void run() {
		inicializarData();
		iniciarNodos();
		distribuir();
	}
	
	private void iniciarNodos(){
		nodos=new Hilo[NUM_NODOS];
		for(int i=0;i<nodos.length;i++){
			nodos[i]=new Hilo(i+1);
		}
	}
	
	public ServicioResolverSopa getfuente_1() {
		return fuente_1;
	}
/*
	public ServicioResolverSopa getfuente_2() {
		return fuente_2;
	}
	
	public ServicioResolverSopa getfuente_3() {
		return fuente_3;
	}

	public ServicioResolverSopa getfuente_4() {
		return fuente_4;
	}
	
	public ServicioResolverSopa getfuente_5() {
		return fuente_5;
	}

	public ServicioResolverSopa getfuente_6() {
		return fuente_6;
	}*/
	
	public char[][] getBoard() {
		return board;
	}
	
	public TreeSet<String> getDiccionario() {
		return diccionario;
	}
	
	public ArrayList<String> getSolucion() {
		return solucion;
	}
	
	public void actualizarNodos(int pos){
		nodos[pos].detener();
	}
}
