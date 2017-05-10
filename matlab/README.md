![...](res/images/header.jpg)

# Nerve® System

Se desarrolló un sistema _perceptrón multi-capa_ capaz de aproximar una
función de entrada. Para ello, se dispone una red neuronal totalmente
configurable para la cual se aplica un proceso de aprendizaje supervisado
exhaustivo. Luego del entrenamiento, la red es capaz de inferir la evaluación
de la función en cualquier punto de manera aproximada y sin supervisión.

Para la implementación, se decidió utilizar el framework
**_MathWorks® Matlab_**, ya que facilita la aplicación de cálculos
matriciales, y la visualización de información.

## Instalación

El proyecto no requiere ninguna instalación en particular ya que se despliega
directamente sobre el intérprete de _Matlab_.

## Ejecución

Dentro del directorio *matlab* (ubicado en la raíz del proyecto), se debe
construir un archivo de configuración `nerve.json`, con la siguiente
estructura (en formato _JSON_):

```
#!javascript

{
	"beta" : 0.8500,
	"disjoint" : true,
	"epochs" : 100,
	"error" : 0.0010,
	"granularity" : 0.02,
	"graph" : false,
	"graphError" : true,
	"injectionProbability" : 0.0000,
	"inputs" : 2,
	"layerSizes" : [20, 15, 10, 1],
	"learningRate" : 0.2500,
	"learningRateDecrement" : 0.0000,
	"learningRateIncrement" : 0.0000,
	"minSteps" : 0,
	"momentum" : 0.1000,
	"patternNoise" : 0.0000,
	"problem" : "../res/benchmarks/terrain11.data",
	"processor" : "min-max",
	"trainRatio" : 0.6500,
	"transfers" : ["tanh", "tanh", "tanh", "tanh"],
	"vanishingLimit" : 0.0100,
	"weightNoise" : 0.0000
}

```

Cada parámetro especifica:

* `beta`: este parámetro se utiliza para modificar el escalamiento de las
funciones de activación _tanh_ y _exp_.

* `disjoint`: permite utilizar conjuntos de entrenamiento y testeo disjuntos,
los cuales son seleccionados al azar al iniciar el sistema.

* `epochs`: la cantidad de veces que la red neuronal es entrenada mediante el
conjunto de entrada.

* `error`: especifica el error máximo admitido durante el test de predicción
del conjunto de datos de entrada.

* `granularity`: especifica la granularidad al graficar la función original y
la función aproximada por la red.

* `graph`: indica si se debe generar una representación de la función
aproximada utilizando la red neuronal final, luego del entrenamiento.

* `graphError`: especifica si es necesario visualizar la variación de las
curvas de error de entrenamiento y testeo. Esto permite identificar si la red
ingresó en un estado de _overfitting_.

* `injectionProbability`: probabilidad de inyectar ruido en los patrones de
entrada durante la fase de entrenamiento.

* `inputs`: la cantidad de entradas presentes en el perceptrón. Debe
corresponderse con la cantidad de predictores especificados en el set de datos
de entrenamiento.

* `layerSizes`: un vector de largo _N_ especificando la cantidad de neuronas
en cada una de las _N_ capas. Las capas son totalmente conexas entre sí, pero
la arquitectura global es _feed-forward_ y no recurrente. La cantidad de
salidas se corresponde con el tamaño de la última capa.

* `learningRate`: la tasa de aprendizaje de la red neuronal. Un valor alto
acelera el entrenamiento, pero puede ocasionar oscilaciones indefinidas.

* `learningRateDecrement`: la proporción de decremento adaptativo de la tasa
de aprendizaje.

* `learningRateIncrement`: el incremento adaptativo de la tasa de aprendizaje.

* `minSteps`: la cantidad de pasos que se tendrán en cuenta para adaptar la
tasa de aprendizaje.

* `momentum`: especifica el momento de inercia de aprendizaje sobre el proceso
de actualización de pesos, durante la aplicación de _back-propagation_.

* `patternNoise`: la cantidad máxima de ruido a inyectar en los patrones de
entrada durante el proceso de entrenamiento.

* `problem`: el archivo del cual extraer la especificación de la función que
se desea aproximar. Los predictores deben disponerse en columnas,
opcionalmente con una primera fila de _headers_.

* `processor`: indica el tipo de pre/post procesamiento a realizar sobre el
conjunto de datos. Actualmente se permiten las siguientes transformaciones:
`nothing`, `bits` y `min-max`.

* `trainRatio`: la proporción de instancias a utilizar durante la fase de
entrenamiento. Si la proporción es _P_, entonces la proporción del conjunto de
testeo será de _(1 - P)_.

* `transfers`: este vector debe poseer el mismo largo que _layerSizes_, ya que
permite establecer las funciones de activación utilizadas en cada capa. Las
funciones disponibles son `heaviside`, `sign`, `linear`, `tanh` y `exp`.

* `vanishingLimit`: constante mínima aditiva para evitar el
_Vanishing Gradient Problem_, con lo cual se mitiga la probabildiad de
saturación de una neurona al limitar la magnitud mínima del gradiente
descendente.

* `weightNoise`: la cantidad máxima de ruido a inyectar durante la generación
inicial de la matriz de pesos. Si posee un valor _W_, entonces los pesos se
inicializarán aleatoriamente con un valor dentro del intervalo _(-W, W)_.

Luego de construir el archivo de configuración, dentro del sub-directorio
*matlab/src*, y desde la aplicación _Matlab_, ejecute:

	Nerve.run();

## Casos de Uso

[...]

### Autores

El sistema fue desarrollado por el _Grupo 11_, compuesto por los siguientes
autores:

* (*54.623*) Juan Dantur
* (*55.382*) Ariel Debrouvier
* (*53.396*) Agustín Golmar
