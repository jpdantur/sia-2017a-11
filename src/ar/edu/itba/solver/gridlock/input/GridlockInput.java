package ar.edu.itba.solver.gridlock.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import ar.edu.itba.solver.gridlock.GridlockState;
import ar.edu.itba.solver.gridlock.Piece;
import ar.edu.itba.solver.gridlock.Piece.Orientation;
import ar.edu.itba.solver.gridlock.Position;

public class GridlockInput {
	
	private final static String MAIN_PIECE_KEY = "main_piece";
	private final static String PIECES_KEY = "pieces";
	private final static String SIDE_KEY = "side";
	
	private final static String PIECE_INITIAL_X_KEY = "x";
	private final static String PIECE_INITIAL_Y_KEY = "y";
	private final static String PIECE_LENGTH_KEY = "length";
	private final static String PIECE_ORIENTATION_KEY = "orientation";
	
	private final static String PIECE_ORIENTATION_VERTICAL = "vertical";
	private final static String PIECE_ORIENTATION_HORIZONTAL = "horizontal";
	
	private final static String[] REQUIRED_KEYS = {MAIN_PIECE_KEY, PIECES_KEY, SIDE_KEY};
	private final static String[] PIECE_REQUIRED_KEYS = {PIECE_INITIAL_X_KEY, PIECE_INITIAL_Y_KEY, PIECE_LENGTH_KEY, PIECE_ORIENTATION_KEY};
	
	private static Reader openFile(final String path) {
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader(path));
			
//			String line;
//			while ((line = inputStream.readLine()) != null) {
//				System.out.println(line);
//			}
		} catch (IOException e) {
			System.err.println("error abriendo el archivo");
			e.printStackTrace();
			throw new IllegalStateException("error abriendo el archivo");
		}
		
		return inputStream;
	}
	
	private static JSONObject getMainJSONNode(final Reader reader) {
		
		final JSONTokener tokener = new JSONTokener(reader);
		JSONObject root = null;
		try {
			root = new JSONObject(tokener);
		} catch (JSONException e) {
			System.err.println("json malformado");
			e.printStackTrace();
			throw new IllegalStateException("json malformado");
		}
		return root;
	}
	
	private static void ensureRequiredKeys(final JSONObject object, final String[] keys) {
		for ( final String key : keys ) {
			try {
				if (object.get(key) == null) {
					throw new IllegalStateException(key + " cant be null");
				}
			} catch (final JSONException e) {
				System.err.println( key + " key is required");
				e.printStackTrace();
				throw new IllegalStateException(key + "key is required");
			}
		}
	}
	
	private static void ensureMainRequiredKeys(final JSONObject object) {
		ensureRequiredKeys(object, REQUIRED_KEYS);
	}
	
	private static int retrieveInt(final JSONObject object, final String key) {
		int value = 0;
		try {
			value = object.getInt(key);
		} catch (final JSONException e) {
			System.err.println(key + " must be an integer");
			e.printStackTrace();
			throw new IllegalStateException(key + " must be an integer");
		}
		return value;
	}
	
	private static String retrieveString(final JSONObject object, final String key) {
		String value = null;
		try {
			value = object.getString(key);
		} catch (final JSONException e) {
			System.err.println(key + " must be a string");
			e.printStackTrace();
			throw new IllegalStateException(key + " must be a string");
		}
		return value;
	}
	
	private static int retrieveSide(final JSONObject object) {
		return retrieveInt(object, SIDE_KEY);
	}
	
	private static void ensurePiece(final JSONObject object) {
		
		ensureRequiredKeys(object, PIECE_REQUIRED_KEYS);
		
		int x = 0;
		try {
			x = object.getInt(PIECE_INITIAL_X_KEY);
			
			if (x < 0) {
				throw new IllegalStateException("invalid x");
			}
		} catch (JSONException e) {
			System.err.println("x must be an integer");
			e.printStackTrace();
			throw new IllegalStateException("x must be an integer");
		}
		
		int y = 0;
		try {
			y = object.getInt(PIECE_INITIAL_Y_KEY);
			
			if (y < 0) {
				throw new IllegalStateException("invalid y");
			}
		} catch (JSONException e) {
			System.err.println("y must be an integer");
			e.printStackTrace();
			throw new IllegalStateException("y must be an integer");
		}
		
		int length = 0;
		try {
			length = object.getInt(PIECE_LENGTH_KEY);
			
			if (length <= 0) {
				throw new IllegalStateException("invalid length");
			}
		} catch (JSONException e) {
			System.err.println("length must be an integer");
			e.printStackTrace();
			throw new IllegalStateException("length must be an integer");
		}
		
		String orientation = null;
		try {
			orientation = object.getString(PIECE_ORIENTATION_KEY);
			
			if (!orientation.equals(PIECE_ORIENTATION_HORIZONTAL) && !orientation.equals(PIECE_ORIENTATION_VERTICAL)) {
				throw new IllegalStateException("invalid orientation");
			}
		} catch (JSONException e) {
			System.err.println("orientation must be a string");
			e.printStackTrace();
			throw new IllegalStateException("orientation must be a string");
		}
	}
	
	private static Piece makePiece(final JSONObject object) {
		
		final short initialX = (short)retrieveInt(object, PIECE_INITIAL_X_KEY);
		final short initialY = (short)retrieveInt(object, PIECE_INITIAL_Y_KEY);
		final short length = (short)retrieveInt(object, PIECE_LENGTH_KEY);
		final String orientation = retrieveString(object, PIECE_ORIENTATION_KEY);
		
		
		final Position position = new Position(initialX, initialY);
		return new Piece(position, length, orientation.equals(PIECE_ORIENTATION_HORIZONTAL) ? Orientation.HORIZONTAL : Orientation.VERTICAL);
	}
	
	private static Collection<Piece> retrievePieces(final JSONObject object) {
		Collection<Piece> pieces = new HashSet<>();
		
		JSONArray jsonArray;
		
		try {
			jsonArray = object.getJSONArray(PIECES_KEY);
			
			JSONObject o;
			for (int i = 0; i < jsonArray.length(); i++) {
				o = jsonArray.getJSONObject(i);
				
				ensurePiece(o);
				
				pieces.add(makePiece(o));
			}
			
		} catch (final JSONException e) {
			System.err.println("error reading main piece");
			e.printStackTrace();
			throw new IllegalStateException("error reading main piece");
		}
		
		return pieces;
	}
	
	private static Piece retreiveMainPiece(final JSONObject object) {
		
		JSONObject o = null;
		
		try {
			o = object.getJSONObject(MAIN_PIECE_KEY);
		} catch (final JSONException e) {
			System.err.println("error reading main piece");
			e.printStackTrace();
			throw new IllegalStateException("error reading main piece");
		}
		
		ensurePiece(o);
		
		return makePiece(o);
	}
	
	public static GridlockState getFromFile(final String path) {
		
		final JSONObject root = getMainJSONNode(openFile(path));
		
		ensureMainRequiredKeys(root);
		
		final int side = retrieveSide(root);
		
		final Piece mainPiece = retreiveMainPiece(root);
		final GridlockState state = GridlockState.createWithSide(mainPiece, side);
		
		final Collection<Piece> pieces = retrievePieces(root);
		
		for (Piece p : pieces) {
			if (!state.addPiece(p)) {
				throw new IllegalStateException("pieces overlap");
			}
		}
		
		return state;
	}
}
