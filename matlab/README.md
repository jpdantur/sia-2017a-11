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

La presentación se encuentra disponible en:

* [@YouTube - Artificial Neural Network [video]](
https://www.youtube.com/
)

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
	"learningRate" : 0.1500,
	"beta" : 0.6500,
	"inputs" : 2,
	"layerSizes" : [2, 5, 1],
	"transfers" : ["heaviside", "tanh", "sigmoid"],
	"problem" : "../res/benchmarks/terrain11.data",
	"epochs" : 1,
	"error" : 0.1000
}

```

Cada parámetro especifica:

* `beta`: este parámetro se utiliza para modificar el escalamiento de las
funciones de activación _tanh_ y _sigmoid_.

* `epochs`: la cantidad de veces que la red neuronal es entrenada mediante el
conjunto de entrada.

* `error`: especifica el error máximo admitido durante el test de predicción
del conjunto de datos de entrada.

* `inputs`: la cantidad de entradas presentes en el perceptrón. Debe
corresponderse con la cantidad de predictores especificados en el set de datos
de entrenamiento.

* `layerSizes`: un vector de largo _N_ especificando la cantidad de neuronas
en cada una de las _N_ capas. Las capas son totalmente conexas entre sí, pero
la arquitectura global es _feed-forward_ y no recurrente. La cantidad de
salidas se corresponde con el tamaño de la última capa.

* `learningRate`: la tasa de aprendizaje de la red neuronal. Un valor alto
acelera el entrenamiento, pero puede ocasionar oscilaciones indefinidas.

* `problem`: el archivo del cual extraer la especificación de la función que
se desea aproximar. Los predictores deben disponerse en columnas,
opcionalmente con una primera fila de _headers_.

* `transfers`: este vector debe poseer el mismo largo que _layerSizes_, ya que
permite establecer las funciones de activación utilizadas en cada capa. Las
funciones disponibles son `heaviside`, `sign`, `linear`, `tanh` y `sigmoid`.

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
