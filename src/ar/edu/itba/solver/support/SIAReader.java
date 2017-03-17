
	package ar.edu.itba.solver.support;

	import java.io.IOException;
	import java.nio.charset.Charset;
	import java.nio.charset.StandardCharsets;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.util.NoSuchElementException;
	import java.util.Optional;
	import java.util.stream.Stream;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import com.google.inject.Singleton;

		/**
		* <p>Esta clase permite leer y validar una especificación del juego
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
		* <p> ... FALTA COMPLETAR! ... </p>
		*
		* @param filename
		*	La ruta hacia el archivo en formato <i>*.sia</i> que posee la
		*	especificación del juego.
		*
		* @return Opcionalmente el estado que representa el juego a resolver,
		*	o un objeto vacío si hubo un error durante la apertura o
		*	validación del mismo.
		*/

		public Optional<Object> loadGame(final String filename) {

			logger.info("Cargando el juego... ({})", filename);

			final Path game = Paths.get(filename);
			final int [] header = getHeader(game);
			final int [][] board = getBoard(game);

			logger.info("Header: {} {} {}", header[0], header[1], header[2]);

			return Optional.empty();
		}

		/**
		* <p>Se encarga de obtener las dimensiones del juego y el tamaño de la
		* paleta de colores, los cuáles se encuentran en el <i>header</i> del
		* archivo de especificación <b>*.sia</b>.</p>
		*
		* @param game
		*	El <i>Path</i> que identifica la especificación del juego.
		*
		* @return Un array conteniendo las 3 dimensiones en este orden: filas,
		*	columnas y colores.
		*/

		private int [] getHeader(final Path game) {

			try (final Stream<String> lines = Files.lines(game, charset)) {

				return lines.findFirst().map(line -> {

					return Stream
							.of(line.split("\\s"))
							.mapToInt(Integer::parseUnsignedInt)
							.toArray();
				}).filter(header -> header.length == 3).get();
			}
			catch (final IOException exception) {

				logger.error("El archivo no existe o no se puede abrir.");
			}
			catch (final NoSuchElementException exception) {

				logger.error("Especificación del juego inválida.");
			}
			return new int[]{0, 0, 0};
		}

		private int [][] getBoard(final Path game) {

			try (final Stream<String> lines = Files.lines(game, charset)) {

				lines.skip(1).map(line -> {

					return Stream
							.of(line.split("\\s"))
							.mapToInt(Integer::parseUnsignedInt)
							.toArray();
				}).forEachOrdered(row -> {

					for (final int cell : row)
						System.out.print(cell + " ");
					System.out.println("");
				});
			}
			catch (final IOException exception) {

				logger.error("El archivo no existe o no se puede abrir.");
			}
			catch (final NoSuchElementException exception) {

				logger.error("Especificación del juego inválida.");
			}
			return new int[][]{{0}};
		}
	}
