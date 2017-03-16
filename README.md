![...](res/images/header.jpg)

# General Problem Solver

Se implementó un servidor proxy para el protocolo **_XXX_**, utilizando
**_Java SE 8 Release_**. El mismo soporta múltiples clientes, aprovechando
todos los procesadores disponibles en el sistema (*multi-threading*), y
permite la administración en forma remota. Además, expone un servicio de
monitoreo por el cual se pueden obtener métricas y estadísticas de
funcionamiento.

Se hizo especial hincapié en la *performance* del sistema, por lo que se
desarrolló sobre una arquitectura *event-based* utilizando **_Java NIO_**, la
cual se integra en una capa de transporte sin estado almacenado
(*stateless*), e independiente de la capa de aplicación.

La infraestructura depende ampliamente de **_Google™ Guice_**, para realizar
inyección de dependencias (*DI*), y reducir el acoplamiento entre los diversos
componentes que integran el servidor.

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
`proxy.xml`, con la siguiente estructura, lo que permite modificar
selectivamente cada parámetro de operación sin reconstruir el proyecto:

```
#!xml

	<?xml version = "1.0" encoding = "UTF-8"?>
	<proxyConfiguration>
		<!-- Listening Addresses -->
		<listenAddress>0.0.0.0</listenAddress>
		<adminListenAddress>localhost</adminListenAddress>
		<!-- Binding Ports -->
		<listenPort>1024</listenPort>
		<adminListenPort>1025</adminListenPort>
		<!-- Buffer Sizes -->
		<proxyBufferSize>8192</proxyBufferSize>
		<adminBufferSize>8192</adminBufferSize>
		<!-- Performance -->
		<workers>-1</workers>
		<inactivityTimeout>300000</inactivityTimeout>
		<lazyIntervalDetection>1000</lazyIntervalDetection>
		<shutdownTimeout>2000</shutdownTimeout>
		<aggressiveShutdownTimeout>1000</aggressiveShutdownTimeout>
	</proxyConfiguration>

```

Los parámetros aquí utilizados representan los valores por defecto. Para más
información sobre el significado de cada propiedad y los valores que admite,
consulte la documentación (*Capítulo 4: Interfaz*, y
*Apéndice C: Configuración*).

### Autores

El sistema fue desarrollado por los siguientes autores:

* (*00.000*) Juan Dantur
* (*00.000*) Ariel Debrouvier
* (*53.396*) Agustín Golmar
