![...](res/images/header.jpg)

# General Problem Solver

Se implementó un sistema de resolución de problemas general (*GPS*), bajo
**_Java SE 8 Release_**. El mismo se utiliza para resolver de forma automática
instancias del conocido juego **_Fill Zone_**.

En particular, los mecanismos utilizados hacen referencia a los métodos de
búsqueda más conocidos, tanto *no-informados* como *informados*.

El motor principal se construyó aumentando una sistema *GPS* similar ofrecido
por la cátedra, el cual puede encontrarse en:

	[Alan Pierri](https://github.com/apierri/GeneralProblemSolver)

## Instalación

Para construir el proyecto es necesario que el sistema posea un compilador
**_Maven 3_**. Luego, sobre el directorio raíz del proyecto ejecute:

	mvn install

Encontrará en el directorio raíz el correspondiente *\*.jar* con todas las
dependencias, y en el subdirectorio *target*, uno sin ellas (más liviano). Si
desea eliminar los archivos generados durante la compilación, ejecute (en el
mismo directorio raíz):

	mvn clean

Esto no eliminará el *\*.jar* con dependencias, sólo el directorio *target*.

## Ejecución

Para la ejecución no es necesario construir un documento **_XML_** de
configuración (en este caso se utilizará la configuración por defecto). En el
directorio raíz, ejecute:

	java -jar solver-1.0-full.jar

Si lo desea, puede generar un documento *XML* en el directorio raíz denominado
`solver.xml`, con la siguiente estructura, lo que permite modificar
selectivamente cada parámetro de operación sin reconstruir el proyecto:

```
#!xml

	<?xml version = "1.0" encoding = "UTF-8"?>
	<solverConfiguration>
		<!-- Parámetros generales -->
		<strategy>ar.edu.itba.solver.strategy.DFS</listenAddress>
		<heuristic>none</heuristic>
		<cost>none</cost>
		<!-- Especificación del juego -->
		<board>/res/benchmarks/board4x5_6.sia</board>
	</solverConfiguration>

```

Los parámetros aquí utilizados representan los valores por defecto. El
conjunto de valores admitidos en cada *tag* es:

* Strategy: DFS, BFS, Iterative Deepening DFS, Greedy, A*.
* Heuristic: "none", H1, H2.
* Cost: "none", F1.
* Board: la ruta a un archivo en formato _*.sia_ describiendo un juego de NxM
celdas y K colores.

El formato _*.sia_ utilizado es modo texto (*UTF-8 encoding*), y se compone de
un *header* inicial con las dimensiones del tablero y de la paleta de colores
(en este orden: filas, columnas, colores), separadas por espacios. En las
siguientes líneas, el tablero propiamente dicho, descrito como una secuencia
de números enteros separados por espacios y numerados de *0* a *(K - 1)*:

```
#!csv

	4 5 6
	0 3 4 3 1
	5 5 5 4 2
	2 2 1 0 0
	3 2 3 4 4

```

En este ejemplo, el tablero posee una dimensión de *4x5* celdas (*4* filas y
*5* columnas), y una paleta de *6* colores, por lo cual las sucesivas celdas
se deben numerar de *0* a *5*.

### Autores

El sistema fue desarrollado por los siguientes autores:

* (*00.000*) Juan Dantur
* (*00.000*) Ariel Debrouvier
* (*53.396*) Agustín Golmar
