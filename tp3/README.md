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
	"weapons" : "../res/benchmarks/test/armas.tsv"
}

```

Cada parámetro especifica:

* `...`: .

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
