![...](res/images/header.jpg)

# Genetic Algorithms

...

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
	"armors" : "../res/benchmarks/test/pecheras.tsv",
	"boots" : "../res/benchmarks/test/botas.tsv",
	"gauntlets" : "../res/benchmarks/test/guantes.tsv",
	"helmets" : "../res/benchmarks/test/cascos.tsv",
	"weapons" : "../res/benchmarks/test/armas.tsv",

	"attackDefenseRate" : 0.1,

	"itemStrength" : 1.1,
	"itemAgility": 0.8,
	"itemExpertise": 0.8,
	"itemResistance" : 1.1,
	"itemHealth": 1.1,

	"crossoverMethod": "singlepoint",
	"crossoverProbability": 0.9,
	"generations": 1000,
	"mutationProbability" : 0.01,
	"population" : 100,
	"replacement": 2,
	"replacementMethod": ["roulette", "universal"],
	"replacementMethodRate": 0.5,
	"selection" : 20,
	"selectionMethod": ["roulette", "elite"],
	"selectionMethodRate": 0.6,
	"temperature" : 1.0,

	"graphRateLimit" : true,
	"graphFitness" : true
}

```

Cada parámetro especifica:

* `crossoverMethod`: el método que se utilizará en la cruza. Puede ser `onepoint`, `twopoint`, `uniform` o `anular`.
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
 * `temperature`:
 * `tempReductionRate`:
 * `graphRateLimit`:
 * `graphFitness`:

Luego de construir el archivo de configuración, dentro del sub-directorio
*src*, y desde la aplicación _Matlab_, ejecute:

	Genetic.load(lazyness);

Donde **lazyness** indica si el set de datos debe cargarse nuevamente
(_false_), o debe reutilizarse el mismo conjunto de una llamada previa al
método _load_. Esto permite evitar cargar un gran volumen de datos cada vez
que se ejecuta una instancia del algoritmo (debido quizás a la modificación de
algún parámetro de ejecución). Luego de cargar la configuración, ejecute el
algoritmo:

	Genetic.run();

Si desea eliminar la configuración instalada y el set de datos cargado,
ejecute:

	Genetic.reset();

### Autores

El sistema fue desarrollado por el _Grupo 11_, compuesto por los siguientes
autores:

* (*54.623*) Juan Dantur
* (*55.382*) Ariel Debrouvier
* (*53.396*) Agustín Golmar
