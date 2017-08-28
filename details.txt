
Comandos.txt
/home/frascati/Desktop/Sopa/Version 1/Forma 2/Servidor/data/3.txt
Con 2 = 415 milisegundos

cd "/home/frascati/Desktop/Sopa/Version 1/Forma 2/Servidor/" 
frascati compile src Tablero
frascati run servidorCom -libpath Tablero.jar -s r -m run

cd "/home/frascati/Desktop/Sopa/Version 1/Forma 2/Cliente2/" 
frascati compile src fuente
frascati run fuenteCom1 -libpath fuente.jar
frascati run fuenteCom2 -libpath fuente.jar
frascati run fuenteCom3 -libpath fuente.jar
frascati run fuenteCom4 -libpath fuente.jar
frascati run fuenteCom5 -libpath fuente.jar
frascati run fuenteCom6 -libpath fuente.jar


Estrategia usada.txt
La solucion implementada se basa, desde el punto de vista de la arquitectura de hardware, en el modelo de distribucion MIMD y desde el punto de vista de la arquitectura de software en el patron de arquitectura de repositorios. El Tablero hace las veces de procesador de control que por medio de una memoria de programa (archivo .txt con datos de entrada) distribuye las instrucciones a los procesadores de datos (Fuentes) que ejecutan el mismo programa pero con datos distintos.

Entonces, el Tablero lee los datos de entrada en el .txt e inicializa el board de la sopa de letras y el diccionario de palabras a buscar, luego a divide el diccionario en partes iguales entre las fuentes de conocimiento que se esten usando,  y a cada nodo de procesamiento (Fuente) le entrega la parte del diccionario que le corresponde por medio de un TreeSet. La Fuente se encarga de buscar y hallar las palabras de su diccionario y va almacenando sus respuestas en en ArrayList, luego de haber resuelto todas las palabras le entrega al Tablero estas respuestas. 

Por ultimo el tablero conjunta todas las soluciones y muestra por consola la solucion total.

La topologia usada por nuestra arquiterua es UMA, debido a que los procesadores se comunican leyendo y escribiendo en una memoria comun (Tablero).


Instrucciones para adicionar fuentes.txt
2) Agregar las referencias necesarias al composite del proyecto 'Servidor':
Ejm:
<reference name="fuente_3">
		<frascati:binding.rmi host="localhost" serviceName="rmiService1" port="1094"/>
</reference>
*Para el resto de composites se usan los siguientes atributos (reference name="fuente_n"):
<frascati:binding.rmi host="localhost" serviceName="rmiService4" port="1096"/>
<frascati:binding.rmi host="localhost" serviceName="rmiService5" port="1098"/>
<frascati:binding.rmi host="localhost" serviceName="rmiService6" port="2000"/>

3)Modificar en la clase TableroImpl del proyecto 'Servidor':
	a-)variable int NUM_NODOS, se establece en el numero de nodos requeridos.
	b-)agregar las referencias requeridas.
	Ejm:
	@Reference
	public Servicio fuente_3;
	c-)agregar los metodos getFuente_n() correspondientes

4)Modificar en la en la clase Hilo del proyecto 'Servidor':
	a-) Dentro del metodo run(), agregar los ifs correspondientes.
	Ejm:
	if(identificador==3){
		respuesta=pizarra.getFuente_3().resolver(board, diccionario);	
		}


Instrucciones.txt
*Basado en la màquina virtual del Linux (Ubuntu), usando un Tablero y 2 Fuentes.
1)Abrir una nueva terminal (Linux)/cmd (Windows).
2)Dirigirse a la carpeta donde se encuentra el proyecto de la Fuente (Cliente 2)
3)Digitar: frascati compile src fuente
4)Digitar: frascati run fuenteCom1 -libpath fuente.jar

5)Abrir una nueva terminal (Linux)/cmd (Windows).
6)Dirigirse a la carpeta donde se encuentra el proyecto de la Fuente (Cliente 2)
7)Digitar: frascati run fuenteCom2 -libpath fuente.jar

8)Abrir una nueva terminal (Linux)/cmd (Windows).
9)Dirigirse a la carpeta donde se encuentra el proyecto del Tablero (Servidor).
10)Digitar: frascati compile src Tablero
11)Digitar: frascati run servidorCom -libpath Tablero.jar -s r -m run
12)Ingresar la ruta del archivo .txt con los datos de entrada. 

13)Por consola (del Tablero) se indicara la solucion final y el tiempo total de procesamiento. En cada consola (de Fuente) aparecera las palabras que resolvio y el tiempo usado para hacerlo.