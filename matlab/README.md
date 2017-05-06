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
	"beta" : 0.9500,
	"epochs" : 10,
	"error" : 0.1000,
	"inputs" : 2,
	"injectionProbability" : 0.0000,
	"layerSizes" : [2, 5, 2],
	"learningRate" : 0.1500,
	"momentum" : 0.0000,
	"patternNoise" : 0.0000,
	"problem" : "../res/benchmarks/boolean/add.data",
	"trainRatio" : 0.7500,
	"transfers" : ["sign", "sign", "sign"],
	"vanishingLimit" : 0.0000,
	"weightNoise" : 0.0000,
	"graph" : false
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

* `injectionProbability`: probabilidad de inyectar ruido en los patrones de
entrada durante la fase de entrenamiento.

* `layerSizes`: un vector de largo _N_ especificando la cantidad de neuronas
en cada una de las _N_ capas. Las capas son totalmente conexas entre sí, pero
la arquitectura global es _feed-forward_ y no recurrente. La cantidad de
salidas se corresponde con el tamaño de la última capa.

* `learningRate`: la tasa de aprendizaje de la red neuronal. Un valor alto
acelera el entrenamiento, pero puede ocasionar oscilaciones indefinidas.

* `momentum`: especifica el momento de inercia de aprendizaje sobre el proceso
de actualización de pesos, durante la aplicación de _back-propagation_.

* `patternNoise`: la cantidad máxima de ruido a inyectar en los patrones de
entrada durante el proceso de entrenamiento.

* `problem`: el archivo del cual extraer la especificación de la función que
se desea aproximar. Los predictores deben disponerse en columnas,
opcionalmente con una primera fila de _headers_.

* `trainRatio`: la proporción de instancias a utilizar durante la fase de
entrenamiento. Si la proporción es _P_, entonces la proporción del conjunto de
testeo será de _(1 - P)_.

* `transfers`: este vector debe poseer el mismo largo que _layerSizes_, ya que
permite establecer las funciones de activación utilizadas en cada capa. Las
funciones disponibles son `heaviside`, `sign`, `linear`, `tanh` y `sigmoid`.

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
