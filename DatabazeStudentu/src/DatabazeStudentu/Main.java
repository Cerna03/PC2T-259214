package DatabazeStudentu;

import java.util.Scanner;
import java.util.List;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class Main {

	public static void main(String[] args) {
	
		Scanner scanner = new Scanner(System.in);
		StudentDatabaze databaze = new StudentDatabaze();
		
		SQL.inicializujDatabazi();
		SQL.nactiVsechnyStudenty(databaze);

		
		while (true) { 
			System.out.println("\n=== Menu ===");
			System.out.println("1. Přidat studenta");
			System.out.println("2. Odstranit studenta");
			System.out.println("3. Přidat znamku studentovi");
			System.out.println("4. Vyhledat studenta podle ID");
			System.out.println("5. Spustit dovednost studenta");
			System.out.println("6. Výpis všech studentů podle přijímení");
			System.out.println("7. Celkový průměr dle oboru");
			System.out.println("8. Počet studentů podle oboru");
			System.out.println("9. Uložit studenta do souboru");
			System.out.println("10. Načíst studenta ze souboru");
			System.out.println("11. Uložit vše do SQL databáze a ukončit program");
			System.out.println("12. Zavřít databázi");
			System.out.println("Zvol možnost: ");
			
			int volba; 
			
			try {
				volba = scanner.nextInt();
				scanner.nextLine(); 
			} catch (InputMismatchException e) {
				System.out.println("Neplatný vstup. Zadejte číslo.");
				scanner.nextLine();
				continue;
			}
			
			switch (volba) {
				case 1: 
					System.out.println("Zvol obor: 1 = Telekomunikace, 2 = Kyberbezpečnost");
					int obor = scanner.nextInt();
					scanner.nextLine();
					
					System.out.println("Zadej jméno: ");
					String jmeno = scanner.nextLine(); 
					
					System.out.println("Zadej přijímení: ");
					String prijimeni = scanner.nextLine();
					
					System.out.println("Zadej rok narozeni: ");
					int rokNarozeni = scanner.nextInt();
					scanner.nextLine();
					
					String noveId = databaze.vygenerujNoveId();
					Student novyStudent;
					
					if(obor == 1) {
						novyStudent = new TelekomunikacniStudent(noveId, jmeno, prijimeni, rokNarozeni);
					} else if(obor == 2) {
						novyStudent = new KyberbezpecnostniStudent(noveId, jmeno, prijimeni, rokNarozeni);
					} else {
						System.out.println("Neplatný obor.");
						break;
					}
					
					databaze.pridatStudenta(novyStudent);
					System.out.println("Student přidán. ID: " +noveId);
					
					break;
					
				case 2: 
					System.out.println("Zadej ID studenta: ");
					String vymazat = scanner.nextLine();
					
					if(databaze.odstranitStudenta(vymazat)) {
						System.out.println("Student odstraněn.");
					} else {
						System.out.println("Student s tímto ID nebyl nalezen.");
					}
					break;
					
				case 3: 
					System.out.println("Zadej ID studenta: ");
					String idZnamka = scanner.nextLine();
					
					Student studentZnamka = databaze.najitStudenta(idZnamka);
					
					if(studentZnamka != null) {
						System.out.println("Zadej známku (1 - 5): ");
						int znamka = scanner.nextInt();
						scanner.nextLine();
						
						if(znamka >= 1 && znamka <= 5) {
							studentZnamka.pridejZnamku(znamka);
							System.out.println("Známka přidána.");
						} else {
							System.out.println("Neplatná volba. Známka musí být mezi (1 - 5).");
						} 
					} else {
						System.out.println("Student s tímto ID nebyl nalezen.");
					}
					break;
					
				case 4: 
					System.out.println("Zadej ID studenta: ");
					String hledaneId = scanner.nextLine();
					
					Student nalezen = databaze.najitStudenta(hledaneId);
					
					if(nalezen != null) {
						System.out.println("Student nalezen: ");
						System.out.println("ID " +nalezen.getId());
						System.out.println("Jméno: " +nalezen.getJmeno());
						System.out.println("Přijímení: " +nalezen.getPrijimeni());
						System.out.println("Rok narození: " +nalezen.getRokNarozeni());
						System.out.println("Studijní průměr: " +nalezen.getStudijniPrumer());
					} else {
						System.out.println("Student s tímto ID nenalezen.");
					}
					break;
					
				case 5:
					System.out.println("Zadej ID studenta: ");
					String dovednost = scanner.nextLine();
					
					Student studentDovednost = databaze.najitStudenta(dovednost);
					
					if(studentDovednost != null) {
						String vystup = studentDovednost.specialniDovednost();
						System.out.println(vystup);
					} else {
						System.out.println("Student s tímto ID nebyl nalezen.");
					}
					break;
					
				case 6: 
					System.out.println("=== Seznam všech studentů podle příjímení ===");
					
					List<Student> serazeniStudenti = databaze.getStudentiPodlePrijimeni();
					
					for (Student s : serazeniStudenti) {
						System.out.println(s);
					}
					break;
					
				case 7: 
					double prumerTelekomunikace = databaze.getPrumerOboru(TelekomunikacniStudent.class);
					double prumerKyberbezpecnost = databaze.getPrumerOboru(KyberbezpecnostniStudent.class);
					
					System.out.println("Průměrná známka (Telekomunikace): \n" +prumerTelekomunikace);
					System.out.println("Průměrná známka (Kyberbezpečnost): \n" +prumerKyberbezpecnost);
					
					break;
					
				case 8: 
					int pocetTelekomunikace = databaze.getPocetStudentu(TelekomunikacniStudent.class);
					int pocetKyberbezpecnost = databaze.getPocetStudentu(KyberbezpecnostniStudent.class);
					
					System.out.println("Počet studentů (Telekomunikace): " +pocetTelekomunikace);
					System.out.println("Počet studentů (Kyberbezpečnost): " +pocetKyberbezpecnost);
					
					break;
					
				case 9: 
					System.out.println("Zadej ID studenta pro uložení do souboru: ");
					String ulozitId = scanner.nextLine();
					
					Student s = databaze.najitStudenta(ulozitId);
					if(s != null) {
						try(PrintWriter out = new PrintWriter("student" + ulozitId + ".txt")) {
							String oborStudenta = (s instanceof TelekomunikacniStudent) ? "Telekomunikace" : "Kyberbezpečnost";
							
							out.println("ID, Jméno, Přijímení, Rok narození, Obor, Průměr, Známky");
							out.println(
									s.getId() + "," +
									s.getJmeno() + "," +
									s.getPrijimeni() + "," +
									s.getRokNarozeni() + "," +
									oborStudenta + "," +
									s.getStudijniPrumer() + "," +
									s.getZnamky()
							);
							
							System.out.println("Student uložen do souboru student" + ulozitId + ".txt");
						} catch(Exception e) {
							System.out.println("Chyba při ukládání studenta");
						}
					} else {
						System.out.println("Student s tímto ID nebyl nalezen.");
					}
					break;
					
				case 10:
					System.out.println("Zadej ID studenta pro načtení ze souboru: ");
					String nacistId = scanner.nextLine();
					
				    try (Scanner sc = new Scanner(new java.io.File("student" + nacistId + ".txt"))) {
				        if(sc.hasNextLine()) sc.nextLine();

				        if(sc.hasNextLine()) {
				            String radek = sc.nextLine();
				            String[] data = radek.split(",", 7);

				            String nactiId = data[0];
				            String nactiJmeno = data[1];
				            String nactiPrijimeni = data[2];
				            int nactiRokNarozeni = Integer.parseInt(data[3]);
				            String nactiOborStudenta = data[4];
				            String nactiZnamky = data[6].replace("[", "").replace("]", "").trim();

				            Student student;
				            if (nactiOborStudenta.equalsIgnoreCase("Telekomunikace")) {
				                student = new TelekomunikacniStudent(nactiId, nactiJmeno, nactiPrijimeni, nactiRokNarozeni);
				            } else if (nactiOborStudenta.equalsIgnoreCase("Kyberbezpečnost")) {
				                student = new KyberbezpecnostniStudent(nactiId, nactiJmeno, nactiPrijimeni, nactiRokNarozeni);
				            } else {
				                System.out.println("Neznámý obor: " + nactiOborStudenta + " — načtení přerušeno.");
				                break;
				            }

				            List<Integer> znamkyList = Student.parseZnamky(nactiZnamky);
				            for (int z : znamkyList) {
				                student.pridejZnamku(z);
				            }

				        databaze.pridatStudenta(student);
				        databaze.aktualizujPosledniId(nactiId);

				        System.out.println("Student načten ze souboru student" + nactiId + ".txt.");
				        }
				    } catch (Exception e) {
				        System.out.println("Chyba při načítání. ");
				    }
				    break;

				case 11:
				    System.out.println("Ukládám změny do SQL databáze...");
				    SQL.ulozVsechnyStudenty(databaze.getVsechnyStudenty());
				    System.out.println("Hotovo. Program ukončen.");
				    scanner.close();
				    System.exit(0);
				    
				    break;

				case 12: 
					System.out.println("Program ukončen bez uložení do SQL databáze.");
					scanner.close(); 
					System.exit(0);
					
					break;
					
				default:
					System.out.println("Neplatná volba.");
			}
		}
	}
}
