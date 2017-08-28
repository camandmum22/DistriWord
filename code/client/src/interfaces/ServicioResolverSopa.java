package interfaces;

import java.util.ArrayList;
import java.util.TreeSet;

import org.osoa.sca.annotations.Service;

@Service
public interface ServicioResolverSopa {

	public ArrayList<String> resolver(char[][] board, TreeSet<String> lista);
}