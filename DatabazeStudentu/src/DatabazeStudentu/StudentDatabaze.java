package DatabazeStudentu;

import java.util.*; 

public class StudentDatabaze { 

	private Map<String, Student> studenti; 
	 
	public StudentDatabaze() {
		studenti = new HashMap<>(); 
	}
	
	private int posledniId = 0; 
	
	public String vygenerujNoveId() {
		posledniId++;
		return String.valueOf(posledniId); 
	}

	public void aktualizujPosledniId(String idText) {
		int id = Integer.parseInt(idText);
	    if (id > posledniId) {
	    	posledniId = id;
	    }   	
	}
	
	public void pridatStudenta(Student student) { 
		studenti.put(student.getId(), student);
	}
	
	public boolean odstranitStudenta(String id) { 
		return studenti.remove(id) != null;
	}
	
	public Student najitStudenta(String id) { 
		return studenti.get(id);
	}
	
	public List<Student> getVsechnyStudenty() { 
	    return new ArrayList<>(studenti.values());
	}
	
	public List<Student> getStudentiPodlePrijimeni() { 
		List<Student> seznam = new ArrayList<>(studenti.values());
		seznam.sort(Comparator.comparing(Student::getPrijimeni));
		return seznam;
	}
	
	public double getPrumerOboru(Class<?>obor) { 
		double soucet = 0;
		int pocet = 0;
		for (Student s : studenti.values()) {
			if(obor.isInstance(s)) {
				soucet += s.getStudijniPrumer();
				pocet++;
			}
		}
		return pocet == 0 ? 0.0 : soucet / pocet;
	}
	
	public int getPocetStudentu(Class<?>obor) { 
		int pocet = 0;
		for (Student s : studenti.values()) {
			if(obor.isInstance(s)) {
				pocet++;
			}
		}
		return pocet;
	}
}
