package servicios;

import java.util.ArrayList;
import java.util.TreeSet;

public class Hilo extends Thread{
	
	private int identificador;
	private TableroImpl pizarra;
	private TreeSet<String> diccionario;
	private char[][] board;
	private boolean activo;
	
	public Hilo(int identificador){
		this.identificador=identificador;
		activo = false;
	}
	
	public Hilo(TableroImpl pizarra, int identificador, TreeSet<String> diccionario, char[][] board){
		this.pizarra=pizarra;
		this.diccionario=diccionario;
		this.identificador=identificador;
		this.board=board;
		activo=true;
	}
	
	public Hilo(TableroImpl pizarra, int identificador, String word, char[][] board){
		this.pizarra=pizarra;
		this.identificador=identificador;
		this.board=board;
		activo=true;
		TreeSet<String> t = new TreeSet<String>();
		t.add(word);
		diccionario = t;
	}
	
	@Override
	public void run(){
		ArrayList<String> solves = new ArrayList<String>();
		//Cambiar si aumentan los nodos
		if(identificador==1){
		solves=pizarra.getfuente_1().resolver(board, diccionario);
		}
		/*if(identificador==2){
		solves=pizarra.getfuente_2().resolver(board, diccionario);
		}
		if(identificador==3){
		solves=pizarra.getfuente_3().resolver(board, diccionario);
		}
		if(identificador==4){
		solves=pizarra.getfuente_4().resolver(board, diccionario);
		}
		if(identificador==5){
		solves=pizarra.getfuente_5().resolver(board, diccionario);
		}
		if(identificador==6){
		solves=pizarra.getfuente_6().resolver(board, diccionario);
		}*/
		try{
		pizarra.agregarASolucion(solves);
		pizarra.actualizarNodos(identificador-1);
		}
		catch(Exception e){
			//stop();
			//System.out.println("Error = "+e.getMessage());
		}
	}
	
	public int getIdentificador(){
		return identificador;
	}
	
	public void detener(){
		activo=false;
	}
	
	public boolean estaActivo(){
		return activo;
	}
}