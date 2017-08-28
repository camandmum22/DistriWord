package cliente;

import interfaces.ServicioResolverSopa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class ClienteImpl implements ServicioResolverSopa{
	
	private ArrayList<String> solucion;
	private long time;
	private int resueltas;
	@Override
	public ArrayList<String> resolver(char[][] board, TreeSet<String> lista){
		solucion = new ArrayList<String>();
		/*System.out.println("------------BOARD------------");
		System.out.println(board.length+" "+board[0].length);
		for (int row=0; row<board.length; row++) {
			for (int col=0; col<board[row].length; col++)
				System.out.print(board[row][col]+" ");
			System.out.println();
		}
		System.out.println();*/
		long t1=System.currentTimeMillis();
		System.out.println("------------SOLUCION PARCIAL------------");
		Iterator<String> iterator = lista.iterator();
		while (iterator.hasNext()){
			String word = iterator.next();
			boolean b = findWord(word, board.length, board[0].length, board);
	 		if(!b){
	 			String s2 = word.toLowerCase()+": No esta en la sopa de letras";
	 			System.out.println(s2);
	 			solucion.add(s2);
	 		}
		}
		long t2=System.currentTimeMillis();
		//System.out.println("Sopa Resuelta en "+(t2-t1)+" milisengundos");
		//System.out.println("Tamaño solucion = "+solucion.size());
		time+=(t2-t1);
		resueltas+=solucion.size();
		System.out.println("Sopa Resuelta en "+time+" milisengundos");
		System.out.println("Tamaño solucion = "+resueltas);
		return solucion;
	}
	
	private boolean findWord(String word, int filas, int columnas, char[][] board2) {
		boolean find = false;
		for (int row=0; row<filas && !find; row++)
			for (int col=0; col<columnas && !find; col++)
				find = findWord(word,row,col,filas,columnas,board2);
		return find;
	}
	
	private boolean findWord(String word, int row, int col, int filas, int columnas, char[][] board2) {
		boolean find = false;
		for (int drow=-1; drow<=1 && !find; drow++)
			for (int dcol=-1; dcol<=1 && !find; dcol++)
				find = findWord(word,row,col,drow,dcol,filas,columnas,board2);
		return find;
	}
	
	private boolean findWord(String word, int row, int col, int drow, int dcol, int filas, int columnas, char[][] board2) {
		for (int offset=0; offset<word.length(); offset++) {
			int targetRow = row + offset*drow;
			int targetCol = col + offset*dcol;
			
			if ((targetRow < 0) ||	(targetRow >= filas) ||
					(targetCol < 0) ||	(targetCol >= columnas))
					return false;
			
			char boardChar = board2[targetRow][targetCol];//board2.get(targetRow).toCharArray()[targetCol];
			char wordChar  = word.charAt(offset);
			if (boardChar != wordChar)
				return false;
		}
		String s2 = word.toLowerCase()+": pos ini ("+col+","+row+") - pos fin ("
				+darCoordenadasFin(col,row,word.length()-1,drow,dcol)+")";
		System.out.println(s2);
		solucion.add(s2);
		return true;
	}

	private String darCoordenadasFin(int x, int y, int lon, int drow, int dcol) {
		String[][] nombresDirecciones =
	 		{	{ "D Arriba-Izquierda" ,  "Arriba"  , "D Arriba-Derecha"   },
	 			{ "Izquierda"    ,  ""    , "Derecha"      },
	 			{ "D Abajo-Izquierda", "Abajo", "D Abajo-Derecha" }
	 		};
		String dir = nombresDirecciones[drow+1][dcol+1];
		String mensaje = "";
		if(dir.equals("D Arriba-Izquierda"))
			mensaje = (x-lon)+","+(y-lon);
		else if(dir.equals("Arriba"))
			mensaje = (x)+","+(y-lon);
		else if(dir.equals("D Arriba-Derecha"))
			mensaje = (x+lon)+","+(y-lon);
		else if(dir.equals("Izquierda"))
			mensaje = (x-lon)+","+(y);
		else if(dir.equals("Derecha"))
			mensaje = (x+lon)+","+(y);
		else if(dir.equals("D Abajo-Izquierda"))
			mensaje = (x-lon)+","+(y+lon);
		else if(dir.equals("Abajo"))
			mensaje = (x)+","+(y+lon);
		else if(dir.equals("D Abajo-Derecha"))
			mensaje = (x+lon)+","+(y+lon);
		else //dir.equals("")
			mensaje = (x)+","+(y);
		
		return mensaje;
	}
}
