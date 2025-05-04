package DatabazeStudentu;

public class TelekomunikacniStudent extends Student{

	public TelekomunikacniStudent(String id, String jmeno, String prijimeni, int rokNarozeni) {
		super(id, jmeno, prijimeni, rokNarozeni);
	}
	
	@Override
	public String specialniDovednost( ) {
		return "Morseovka: " + textMorseovka(jmeno + " " + prijimeni);
	}
	
	private String textMorseovka(String text) {
		text = text.toUpperCase();
		StringBuilder morseovka = new StringBuilder(); 
		for(char c : text.toCharArray() ) {
			morseovka.append(morseovyZnaky(c)).append(" ");
		}
		return morseovka.toString().trim();
	}
	
	private String morseovyZnaky(char c) {
		switch(c) {
		case 'A': return ".-";
		case 'B': return "-...";
		case 'C': return "-.-.";
		case 'D': return "-..";
		case 'E': return ".";
		case 'F': return "..-.";
		case 'G': return "--.";
		case 'H': return "....";
		case 'I': return "..";
		case 'J': return ".---";
		case 'K': return "-.-";
		case 'L': return ".-..";
		case 'M': return "--";
		case 'N': return "-.";
		case 'O': return "---";
		case 'P': return ".--.";
		case 'Q': return "--.-";
		case 'R': return ".-.";
		case 'S': return "...";
		case 'T': return "-";
		case 'U': return "..-";
		case 'V': return "...-";
		case 'W': return ".--";
		case 'X': return "-..-";
		case 'Y': return "-.--";
		case 'Z': return "--..";
		case ' ': return " ";
		default: return "?";
		
		}
	}
}
