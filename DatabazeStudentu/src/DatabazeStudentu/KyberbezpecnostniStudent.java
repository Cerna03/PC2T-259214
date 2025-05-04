package DatabazeStudentu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KyberbezpecnostniStudent extends Student {

	public KyberbezpecnostniStudent(String id, String jmeno, String prijimeni, int rokNarozeni) {
		super(id, jmeno, prijimeni, rokNarozeni);
	}
	
	@Override
	public String specialniDovednost( ) {
		return "Hash: " + vypocitejHash(jmeno + prijimeni);
	}
	
	private String vypocitejHash(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(text.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x",b));
			}
			return sb.toString();
		} catch(NoSuchAlgorithmException e) { 
			return "CHyba hashování";
		}
	}
}
