
	package ar.edu.itba.solver.config;

	import java.io.File;
	import java.io.IOException;
	import java.nio.file.NoSuchFileException;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.util.Arrays;
	import java.util.Optional;

	import javax.inject.Singleton;
	import javax.xml.bind.JAXBContext;
	import javax.xml.bind.JAXBException;
	import javax.xml.bind.Marshaller;

		/**
		* <p>Permite obtener las configuraciones para cada componente en el
		* sistema, modificar las mismas o generar un archivo <b>XML</b> en
		* base a ellas (utilizando <i>JAXB</i>).</p>
		*/

	@Singleton
	public final class ConfigurationLoader {

		// Configuración del servidor proxy:
		private SolverConfiguration solverConfig
			= new SolverConfiguration();

		/**
		* <p>Permite obtener la configuración global actual del GPS.</p>
		*
		* @return Devuelve la configuración (nunca es <i>null</i>).
		*/

		public SolverConfiguration getSolverConfig() {

			return solverConfig;
		}

		/**
		* <p>Permite almacenar la configuración actual en un documento
		* <b>XML</b> especificado.</p>
		*
		* @param filename
		*	El nombre del archivo en el cual guardar la configuración actual.
		*
		* @throws IOException
		*	En caso de que se produzca un error al crear el archivo.
		* @throws JAXBException
		*	En caso de que no se pueda generar el <b>XML</b>.
		*/

		public void setProxyConfig(final String filename)
				throws IOException, JAXBException {

			setSolverConfig(filename, solverConfig);
		}

		/**
		* <p>Permite modificar la configuración, y almacenar la misma en un
		* documento <b>XML</b>.</p>
		*
		* @param filename
		*	El nombre del archivo en el cual se almacenará la configuración.
		* @param solverConfig
		*	La nueva configuración para el sistema GPS.
		*
		* @throws IOException
		*	En caso de que se produzca un error al crear el archivo.
		* @throws JAXBException
		*	En caso de que no se pueda generar el <b>XML</b>.
		*/

		public void setSolverConfig(
				final String filename,
				final SolverConfiguration solverConfig)
						throws IOException, JAXBException {

			saveResource(filename, solverConfig);
			this.solverConfig = solverConfig;
		}

		/**
		* <p>Permite cargar una nueva configuración para el sistema GPS
		* desde un archivo <b>XML</b> especificado.</p>
		*
		* @param filename
		*	El nombre del archivo, o la ruta hacia el mismo, desde el cual se
		*	obtendrá la nueva configuración.
		*
		* @return La nueva configuración, o la antigua, en caso de que se
		*	produzca un error al obtener la misma.
		*
		* @throws JAXBException
		*	En caso de que el archivo especificado no se haya podido parsear
		*	correctamente, utilizando <i>JAXB</i>.
		*/

		public SolverConfiguration loadSolverConfig(final String filename)
				throws JAXBException {

			try {

				solverConfig = loadResource(
									filename,
									SolverConfiguration.class);
			}
			catch (final IOException spurious) {}
			return solverConfig;
		}

		/**
		* <p>Permite obtener todas las rutas en las cuales un archivo de
		* configuración se podría llegar a instalar.</p>
		*
		* @param configName
		*	El nombre del archivo de configuración.
		*
		* @return Devuelve un arreglo de rutas en el sistema de archivos en
		*	las cuales se podría instalar el achivo de configuración.
		*/

		private Path [] getConfigPaths(final String configName) {

			return new Path [] {

				Paths.get(System.getProperty("user.dir"), configName),
				Paths.get(System.getProperty("user.home"), "." + configName),
			};
		}

		/**
		* <p>Intenta crear un archivo en modo escritura en las rutas
		* disponibles para los archivos de configuración.</p>
		*
		* @param configName
		*	El nombre del archivo de configuración.
		*
		* @return El archivo en el cual escribir la configuración.
		*
		* @throws NoSuchFileException
		*	Si no se pudo crear ningún archivo, en ningún lugar.
		*/

		private File setFile(final String configName)
				throws NoSuchFileException {

			for (final Path path : getConfigPaths(configName)) {

				final File file = path.toFile();
				try {

					file.createNewFile();
					if (file.canWrite()) return file;
				}
				catch (final IOException spurious) {}
			}
			throw new NoSuchFileException(configName);
		}

		/**
		* <p>Permite ubicar un archivo de configuración dentro del sistema de
		* archivos, y obtener un objeto que lo identifique.</p>
		*
		* @param configName
		*	El nombre del archivo a buscar.
		*
		* @return Devuelve un opcional con el archivo de configuración.
		*/

		private Optional<File> findFile(final String configName) {

			return Arrays.stream(getConfigPaths(configName))
					.map(Path::toFile)
					.filter(File::canRead)
					.findFirst();
		}

		/**
		* <p>Permite construir un objeto a partir de un archivo <b>XML</b>
		* especificado y una clase de molde, utilizando <b>JAXB</b>.</p>
		*
		* @param filename
		*	El nombre del archivo desde el cual generar el objeto.
		* @param cls
		*	La clase en la cual el objeto será convertido, y la que será
		*	utilizada como molde para construir el mismo.
		*
		* @return El objeto obtenido desde el archivo <b>XML</b>.
		*
		* @throws IOException
		*	En caso de que se produzca un error al abrir el archivo.
		* @throws JAXBException
		*	En caso de que no se pueda generar el objeto desde el <b>XML</b>.
		*/

		@SuppressWarnings("unchecked")
		private <T> T loadResource(final String filename, final Class<T> cls)
				throws IOException, JAXBException {

			final File file = findFile(filename)
					.orElseThrow(() -> new NoSuchFileException(filename));

			return (T) JAXBContext.newInstance(cls)
						.createUnmarshaller()
						.unmarshal(file);
		}

		/**
		* <p>Interpreta un objeto Java y lo traduce en su correspondiente
		* árbol <b>DOM</b>, el cual se almacenará en formato <b>XML</b>.</p>
		*
		* @param filename
		*	El nombre del archivo en el cual guardar el objeto recibido.
		* @param object
		*	El objeto a serializar en formato <b>XML</b>.
		*
		* @throws IOException
		*	En caso de que se produzca un error al crear el archivo.
		* @throws JAXBException
		*	En caso de que no se pueda generar el <b>XML</b>.
		*/

		private <T> void saveResource(final String filename, final T object)
				throws IOException, JAXBException {

			final File file = setFile(filename);

			final JAXBContext context
					= JAXBContext.newInstance(object.getClass());

			final Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(object, file);
		}
	}
