![...](res/images/header.jpg)

# Genetic System©

Se desarrolló un _**RCGA**_ (_Real-Coded Genetic Algorithm_), para optimizar
el equipamiento de un personaje en un videojuego, maximizando su desempeño. El
personaje asignado fue el _Defensor N° 2_. Para la implementación, se decidió
utilizar el framework **_MathWorks® Matlab_**, ya que facilita la aplicación
de cálculos matriciales, y la visualización de información.

El algoritmo permite obtener la configuración final de ítems, los valores de
cada uno de ellos (extraidos desde la base de datos proporcionada), y la
altura más óptima para el personaje diseñado. Así mismo, computa el desempeño
máximo percibido con esta configuración.

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

	"output" : "../res/benchmarks/output.json",

	"attackDefenseRate" : 0.1,

	"itemStrength" : 1.1,
	"itemAgility": 0.8,
	"itemExpertise": 0.8,
	"itemResistance" : 1.1,
	"itemHealth": 1.1,

	"population" : 300,
	"generations": 10000,
	"cutOffThreshold" : 176,
	"contentAssert" : false,
	"contentAssertSteps" : 1000,
	"structAssert" : false,
	"structAssertRatio" : 0.75,

	"selection" : 50,
	"selectionMethod": ["deterministicTournament", "elite"],
	"selectionMethodRate": 0.7,
	"temperature" : 1000,
	"tempReductionRate" : 1,

	"crossoverProbability": 0.85,
	"crossoverMethod": "anular",

	"mutationProbability" : 0.07,

	"replacement": 1,
	"replacementMethod": ["deterministicTournament", "elite"],
	"replacementMethodRate": 0.7,

	"graphFitness" : true,
	"graphRateLimit" : true
}

```

Donde cada parámetro especifica:

* `armors`, `boots`, `gauntlets`, `helmets`, `weapons`: especifican la ruta
hacia las bases de datos (en formato _TSV_) de las pecheras, botas, guantes,
cascos y armas, respectivamente. Nótese que la ruta debe especificarse de
forma absoluta, o de forma relativa con respecto al directorio _src_ en el
cual se despliega el algoritmo.

* `output`: especifica el nombre de un archivo de salida en formato _JSON_ en
el cual almacenar la población final, lo que permite reiniciar el algoritmo
desde un punto conocido y alcanzado en una iteración anterior. También permite
(configurando una corrida de _0_ generaciones), generar una población al azar
con la cual inyectar sucesivas pruebas desde la misma instancia inicial.

* `attackDefenseRate`: porcentaje de desempeño que se le asignará al ataque y
la defensa. El mismo se elige en base al personaje: si se toma un valor _P_,
el desempeño del mismo dependerá de una proporción _P_ de ataque y _(1 - P)_
de defensa.

* `itemStrength`, `itemAgility`, `itemExpertise`, `itemResistance`,
`itemHealth`: los multiplicadores de los ítems para el _Defensor N° 2_.

* `population`: la cantidad de inviduos que tendrá la población.
* `generations`: la cantidad de ciclos o generaciones máximas a ejecutar.
* `cutOffThreshold`: el límite máximo de adaptabilidad requerido antes de
finalizar la ejecución.
* `contentAssert`: indica si se debe aplicar una suspensión en la ejecución
debido a que la máxima adaptación no progresa.
* `contentAssertSteps`: la cantidad de generaciones en las cuales el máximo no
debe progresar antes de finalizar la ejecución.
* `structAssert`: indica si se debe aplicar una suspensión cuando el código
genético de los cromosomas no varía substancialmente de generación en
generación.
* `structAssertRatio`: la proporción de individuos que no deben progresar a lo
largo de las generaciones, antes de aplicar el corte.

* `selection`: la cantidad de padres que se elegirán para cruzar, es decir, el
parámetro _k_.
* `selectionMethod`: vector con los 2 métodos mediante los cuales se
seleccionarán los cromosomas en la etapa de cruza. Las opciones son:
`boltzmann`, `deterministicTournament`, `elite`, `probabilisticTournament`,
`ranking`, `roulette` ó `universal`.
* `selectionMethodRate`: proporción _A_ de elegidos con el primer método de
selección. Una proporción _(1 - A)_ se elige mediante el segundo método.
* `temperature`: temperatura inicial para el método de _Boltzmann_.
* `tempReductionRate`: proporción en la cual se reduce la temperatura en cada
generación, es decir, si la temperatura inicial es _T_ y la proporción es _R_,
entonces en cada ciclo la misma se reduce por un factor _T x R_.

* `crossoverProbability`: la probabilidad de aplciar una cruza entre _2_
cromosomas.
* `crossoverMethod`: el método que se utilizará en la cruza. Puede ser
`anular`, `singlepoint`, `twopoint` ó `uniform`.

* `mutationProbability`: la probabilidad de que se aplique la mutación de un
gen (nótese que se aplica para cada gen y no para el cromosoma completo).

* `replacement`: el método de reemplazo (`1`, `2` ó `3`), según el mecanismo
especificado en la teoría por la cátedra.
* `replacementMethod`: vector con los _2_ métodos mediante los cuales se
seleccionarán los cromosomas en la etapa de reemplazo. Las opciones
disponibles son equivalentes a las que se encuentran en el método de selección
(_selectionMethod_).
* `replacementMethodRate`: proporción _B_ de elegidos con el primer método de
selección para reemplazo. Una proporción _(1 - B)_ se elige mediante el
segundo método.

* `graphFitness`: permite graficar la variación de adaptación máxima y
promedio en tiempo real, mientras el algoritmo se ejecuta ciclo a ciclo.
* `graphRateLimit`: limita la cantidad de _FPS_ utilizados en el gráfico de
_fitness_, lo que permite reducir el costo de este proceso, e incrementar la
fluidez percibida cuando el número de generaciones es elevado.

Para el personaje asignado, la configuración presentada más arriba se
corresponde con la óptima hallada. Luego de construir este archivo, dentro del
sub-directorio *src*, y desde la aplicación _Matlab_, ejecute:

	Genetic.load(lazyness);

Donde _**lazyness**_ indica si el set de datos debe cargarse nuevamente
(_**false**_), o si debe reutilizarse el mismo conjunto de una llamada previa
al método _Genetic.load(...)_ (_**true**_). Esto permite evitar la carga de un
gran volumen de datos cada vez que se ejecuta una instancia del algoritmo
(quizás durante la modificación de algún parámetro de ejecución). Luego de
cargar la configuración, ejecute el algoritmo:

	Genetic.run();

Para utilizar una población inicial específica (desde una archivo _JSON_),
especifique la ruta hacia el archivo (de forma absoluta, o de forma relativa
al directorio _src_):

	Genetic.run('../res/benchmarks/output.json');

Si desea eliminar la configuración instalada y el set de datos cargado,
ejecute:

	Genetic.reset();

### Autores

El sistema fue desarrollado por el _Grupo 11_, compuesto por los siguientes
autores:

* (*54.623*) Juan Dantur
* (*55.382*) Ariel Debrouvier
* (*53.396*) Agustín Golmar
