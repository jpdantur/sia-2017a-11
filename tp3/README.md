![...](res/images/header.jpg)

# Genetic Algorithms

Se desarrolló un **RCGA** (_Real-Coded Genetic Algorithm_), para optimizar el equipamiento
de un personaje en un videojuego, maximizando su desempeño. El personaje asignado fue el _Defensor N° 2_.

Para la implementación, se decidió utilizar el framework
**_MathWorks® Matlab_**, ya que facilita la aplicación de cálculos
matriciales, y la visualización de información.

## Instalación

El proyecto no requiere ninguna instalación en particular ya que se despliega
directamente sobre el intérprete de _Matlab_.

## Ejecución

Dentro del directorio raíz del proyecto, se debe construir un archivo de
configuración `config.json`, con la siguiente estructura (en formato _JSON_):

```
#!javascript

{
	"armors" : "../res/benchmarks/data/pecheras.tsv",
	"boots" : "../res/benchmarks/data/botas.tsv",
	"gauntlets" : "../res/benchmarks/data/guantes.tsv",
	"helmets" : "../res/benchmarks/data/cascos.tsv",
	"weapons" : "../res/benchmarks/data/armas.tsv",

	"output" : "../res/benchmarks/output.json"

	"attackDefenseRate" : 0.1,

	"itemStrength" : 1.1,
	"itemAgility": 0.8,
	"itemExpertise": 0.8,
	"itemResistance" : 1.1,
	"itemHealth": 1.1,

	"population" : 250,
	"generations": 10000,
	"cutOffThreshold" : 176,
	"contentAssert" : true,
	"contentAssertSteps" : 10000,
	"structAssert" : false,
	"structAssertRatio" : 0.9,

	"selection" : 20,
	"selectionMethod": ["boltzmann", "elite"],
	"selectionMethodRate": 0.8,
	"temperature" : 1000,
	"tempReductionRate" : 1,

	"CROSSOVERS" : "anular|singlepoint|twopoint|uniform",
	"crossoverProbability": 0.9,
	"crossoverMethod": "anular",

	"mutationProbability" : 0.025,

	"replacement": 2,
	"replacementMethod": ["probabilisticTournament", "elite"],
	"replacementMethodRate": 0.8,

	"graphRateLimit" : true,
	"graphFitness" : true
}

```

Cada parámetro especifica:

* `crossoverMethod`: el método que se utilizará en la cruza. Puede ser `singlepoint`, `twopoint`, `uniform` o `anular`.
* `crossoverProbability`: la probabilidad de realizarse una cruza.
* `generations`: la cantidad máxima de generaciones.
* `mutationProbability`: la probabilidad de que se realize una mutación en un gen.
* `population`: la cantidad de inviduos que tendrá la población.
* `replacement`: el método de reemplazo `1`, `2` o `3`.
* `replacementMethod`: vector con los 2 métodos mediante los cuales se seleccionarán los cromosomas en la etapa de reemplazo. Las opciones son: `boltzmann`, `elite`, `deterministicTournament`, `probabilisticTournament`, `ranking`  o `roulette`.
 * `replacementMethodRate`: porcentaje _B_ de elegidos con el primer método de selección para reemplazo. Un porcentaje _1-B_ se elige mediante el segundo método.
 * `selection`: la cantidad de padres que se elegirán para cruzar.
 * `selectionMethod`: vector con los 2 métodos mediante los cuales se seleccionarán los cromosomas en la etapa de cruza. Las opciones son: `boltzmann`, `elite`, `deterministicTournament`, `probabilisticTournament`, `ranking`  o `roulette`.
 * `selectionMethodRate`: porcentaje _A_ de elegidos con el primer método de selección. Un porcentaje _1-A_ se elige mediante el segundo método.
 * `temperature`: temperatura inicial para el método de Boltzmann.
 * `tempReductionRate`: proporción en la cual se reduce la temperatura en cada generación.
 * `graphRateLimit`: limita la cantidad de FPS utilizados en el gráfico de _fitness_, lo que permite reducir el costo de este proceso, e incrementar la fluidez percibida.
 * `graphFitness`: permite graficar la variación de adaptación máxima y promedio en tiempo real, mientras el algoritmo se ejecuta.
 * `output`: especifica un archivo de salida en formato _JSON_ en el cual almacenar la población final, lo que permite reiniciar el algoritmo desde un punto conocido alcanzado en una iteración anterior.

Luego de construir el archivo de configuración, dentro del sub-directorio
*src*, y desde la aplicación _Matlab_, ejecute:

	Genetic.load(lazyness);

Donde **lazyness** indica si el set de datos debe cargarse nuevamente
(_false_), o si debe reutilizarse el mismo conjunto de una llamada previa al
método _load_ (_true_). Esto permite evitar cargar un gran volumen de datos cada vez
que se ejecuta una instancia del algoritmo (debido quizás a la modificación de
algún parámetro de ejecución). Luego de cargar la configuración, ejecute el
algoritmo:

	Genetic.run();

Para utilizar una población inicial específica (desde una archivo _JSON_),
especifique la ruta hacia el archivo:

	Genetic.run("../res/benchmarks/output.json");

Si desea eliminar la configuración instalada y el set de datos cargado,
ejecute:

	Genetic.reset();

### Autores

El sistema fue desarrollado por el _Grupo 11_, compuesto por los siguientes
autores:

* (*54.623*) Juan Dantur
* (*55.382*) Ariel Debrouvier
* (*53.396*) Agustín Golmar
