
	package ar.edu.itba.solver.support;

	import java.io.IOException;
	import java.nio.charset.Charset;
	import java.nio.charset.StandardCharsets;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.util.List;
	import java.util.stream.Stream;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import com.google.inject.Singleton;

	import ar.edu.itba.solver.engine.gps.api.GPSProblem;
	import ar.edu.itba.solver.problem.FillZone;

	import static java.util.stream.Collectors.toList;

		/**
		* <p>Esta clase permite leer y validar una especificación del problema
		* <i>Fill Zone</i> en formato <b>*.sia</b>, y generar un estado que lo
		* represente.</p>
		*/

	@Singleton
	public final class SIAReader {

		// Logger:
		private static final Logger logger
			= LoggerFactory.getLogger(SIAReader.class);

		// Charset UTF-8:
		private static final Charset charset
			= StandardCharsets.UTF_8;

		/**
		* <p>Carga un problema desde una especificación en una estructura
		* genérica especial.</p>
		*
		* @param filename
		*	La ruta hacia el archivo en formato <i>*.sia</i> que posee la
		*	especificación del problema.
		*
		* @return Devuelve la especificación del problema.
		*
		* @throws IOException
		*	En caso de no poder abrir o encontrar el archivo que contiene la
		*	especificación del problema.
		*/

		public GPSProblem loadProblem(final String filename)
				throws IOException {

			logger.info("Cargando la especificación ({}).", filename);

			final Path path = Paths.get(filename);
			final int [] header = getHeader(path);
			final int [][] board = getBoard(path, header[0]);

			return new FillZone(header, board);
		}

		/**
		* <p>Se encarga de obtener las dimensiones del juego y el tamaño de la
		* paleta de colores, los cuáles se encuentran en el <i>header</i> del
		* archivo de especificación <b>*.sia</b>.</p>
		*
		* @param path
		*	El <i>Path</i> que identifica la especificación del problema.
		*
		* @return Un array conteniendo las 3 dimensiones en este orden: filas,
		*	columnas y colores.
		*
		* @throws IOException
		*	En caso de no poder abrir la especificación.
		*/

		private int [] getHeader(final Path path)
				throws IOException {

			return Files.lines(path, charset)
					.findFirst()
					.map(SIAReader::toIntArray)
					.filter(header -> header.length == 3)
					.orElse(new int[]{0, 0, 0});
		}

		/**
		* <p>Obtiene la información del tablero de juego, es decir, la matriz
		* de colores. Las dimensiones de la matriz deben corresponderse con
		* las dimensiones especificadas en el <i>header</i>.</p>
		*
		* @param path
		*	El <i>Path</i> que identifica la especificación del juego.
		* @param rows
		*	La cantidad de filas de la matriz del problema.
		*
		* @return Un array bidimensional que representa el tablero de colores.
		*
		* @throws IOException
		*	En caso de no poder abrir la especificación.
		*/

		private int [][] getBoard(final Path path, final int rows)
				throws IOException {

			final int [][] board = new int[rows][];
			int row = 0;

			final List<String> lines = Files.lines(path, charset)
					.skip(1)
					.collect(toList());

			for (final String line : lines)
				board[row++] = toIntArray(line);

			return board;
		}

		private static final int [] toIntArray(final String line) {

			return Stream.of(line.split("\\s"))
					.mapToInt(Integer::parseUnsignedInt)
					.toArray();
		}
	}
