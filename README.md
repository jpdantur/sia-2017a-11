![...](res/images/header.jpg)

# General Problem Solver

Se implementó un sistema de resolución de problemas general (*GPS*), bajo
**_Java SE 8 Release_**. El mismo se utiliza para resolver de forma automática
instancias del conocido juego **_Fill Zone_**.

En particular, los mecanismos utilizados hacen referencia a los métodos de
búsqueda más conocidos, tanto *no-informados* como *informados*.

El motor principal se construyó aumentando un sistema *GPS* similar ofrecido
por la cátedra, el cual puede encontrarse en:

* [@AlanPierri - General Problem Solver [master branch]](
https://github.com/apierri/GeneralProblemSolver
)

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
	<strategy>GREEDY</strategy>
	<heuristic>distinct</heuristic>
	<print>false</print>
	<!-- Especificación del juego -->
	<problem>./res/benchmarks/medium30x30_5.sia</problem>
</solverConfiguration>

```

Los parámetros aquí utilizados representan los valores por defecto. El
conjunto de valores admitidos en cada *tag* es:

* `strategy`: la estrategia de búsqueda a utilizar, puede ser no-informada
(_DFS_, _BFS_, _IDDFS_), o informada (_GREEDY_, _ASTAR_).

* `heuristic`: la función heurística a utilizar (_distinct_, _frontier_,
_graph_), durante una búsqueda informada, o _none_ si no aplica.

* `print`: indica si los pasos intermedios se deben mostrar en caso de hallar
una solución, y por lo tanto, admite los valores _true_ o _false_.

* `problem`: la ruta a un archivo en formato *\*.sia* describiendo un juego de
_NxM_ celdas y _K_ colores. El archivo se validará previamente a desplegar el
algoritmo seleccionado.

El formato ***\*.sia*** utilizado es modo texto (*UTF-8 encoding*), y se
compone de un *header* inicial con las dimensiones del tablero y de la paleta
de colores (en este orden: filas, columnas, colores), separadas por espacios.

En las siguientes líneas, el tablero propiamente dicho, descrito como una
secuencia de números enteros separados por espacios y numerados de *0* a
*(K - 1)*:

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

## Casos de Uso

A continuación, se disponen 2 ejemplos de configuración. El primero permite
utilizar una búsqueda desinformada (*blind search*) para resolver un problema
de dimensión _15x15_, con una paleta de _5_ colores:

```
#!xml

<?xml version = "1.0" encoding = "UTF-8"?>
<solverConfiguration>
	<!-- Parámetros generales -->
	<strategy>DFS</strategy>
	<heuristic>none</heuristic>
	<print>true</print>
	<!-- Especificación del juego -->
	<problem>./res/benchmarks/medium15x15_5.sia</problem>
</solverConfiguration>

```

En este caso se aplicó el algoritmo _DFS_ sin heurística, especificando el
requerimiento de visualizar la solución, una vez hallada.

En el siguiente ejemplo, se utiliza una estrategia informada (*heuristic
search*), en particular, *GREEDY* con heurística *distinct* para resolver un
problema de gran magnitud:

```
#!xml

<?xml version = "1.0" encoding = "UTF-8"?>
<solverConfiguration>
	<!-- Parámetros generales -->
	<strategy>GREEDY</strategy>
	<heuristic>distinct</heuristic>
	<print>false</print>
	<!-- Especificación del juego -->
	<problem>./res/benchmarks/heavy100x100_10.sia</problem>
</solverConfiguration>

```

En algunos segundos, el sistema logra resolver una matriz de _100x100_, bajo
una paleta de _10_ colores, un problema intratable utilizando búsquedas
desinformadas. Se decide no imprimir la solución debido a su extensión.

### Autores

El sistema fue desarrollado por el _Grupo 11_, compuesto por los siguientes
autores:

* (*54.623*) Juan Dantur
* (*55.382*) Ariel Debrouvier
* (*53.396*) Agustín Golmar