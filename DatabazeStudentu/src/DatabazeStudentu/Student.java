package DatabazeStudentu;

import java.util.ArrayList;
import java.util.List;

public abstract class Student {

	protected String id; 
	protected String jmeno;
	protected String prijimeni;
	protected int rokNarozeni;
	protected List<Integer> znamky; 
	
	public Student(String id, String jmeno, String prijimeni, int rokNarozeni) {
		this.id = id;
		this.jmeno= jmeno; 
		this.prijimeni = prijimeni;
		this.rokNarozeni = rokNarozeni;
		this.znamky = new ArrayList<>();
	}
	
	public void pridejZnamku(int znamka) {
		if(znamka >= 1 && znamka <= 5) {
			znamky.add(znamka);
		}
	}
	
	public double getStudijniPrumer() {
		if(znamky.isEmpty()) {
			return 0.0;
		}
		double soucet = 0;
		for(int znamka : znamky) {
			soucet += znamka;
		}
		return soucet / znamky.size(); 
	}
	
	public String getId() {
		return id;
	}
	
	public String getJmeno() {
		return jmeno;
	}
	
	public String getPrijimeni() {
		return prijimeni;
	}
	
	public int getRokNarozeni() {
		return rokNarozeni;
	}
	
	public List<Integer> getZnamky() {
	    return znamky;
	}
	
	public abstract String specialniDovednost();
	
	@Override 
	public String toString() {
		return "ID: " + id + ", Jméno: " + jmeno + ", Přijímení: " + prijimeni + ", Rok narozeni: " + rokNarozeni + ", Průměr: " + getStudijniPrumer();
	}
	
	public static List<Integer> parseZnamky(String znamkyText) { 
	    List<Integer> znamky = new ArrayList<>();
	    if (znamkyText != null && !znamkyText.isBlank()) {
	        znamkyText = znamkyText.replace("[", "").replace("]", "").trim();
	        for (String z : znamkyText.split(",")) {
	            try {
	                znamky.add(Integer.parseInt(z.trim()));
	            } catch (NumberFormatException ignored) {}
	        }
	    }
	    return znamky;
	}
}
