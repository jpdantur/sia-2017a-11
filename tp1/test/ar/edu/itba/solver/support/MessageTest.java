
	package ar.edu.itba.solver.support;

	import static org.junit.Assert.*;

	import org.junit.Test;

	import ar.edu.itba.solver.support.Message;

	public class MessageTest {

		@Test
		public void allMessagesAreNotEmpty() {

			for (final Message message : Message.values())
				assertTrue(0 < message.getMessage().length());
		}

		@Test
		public void allMessagesAreShort() {

			for (final Message message : Message.values())
				assertTrue(message.getMessage().length() < 80);
		}
	}
