package DatabazeStudentu;

import java.sql.*;
import java.util.List;

public class SQL { 

    private static final String URL = "jdbc:sqlite:studenti.db";

    public static void inicializujDatabazi() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS studenti (
                    id TEXT PRIMARY KEY,
                    jmeno TEXT,
                    prijimeni TEXT,
                    rok INTEGER,
                    obor TEXT,
                    znamky TEXT
                );
            """;

            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("Chyba při vytváření databáze: " + e.getMessage());
        }
    }

    public static void ulozVsechnyStudenty(List<Student> studenti) {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("DELETE FROM studenti");
     
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO studenti VALUES (?, ?, ?, ?, ?, ?)");

            for (Student s : studenti) {
                ps.setString(1, s.getId());
                ps.setString(2, s.getJmeno());
                ps.setString(3, s.getPrijimeni());
                ps.setInt(4, s.getRokNarozeni());
                ps.setString(5, s instanceof TelekomunikacniStudent ? "Telekomunikace" : "Kyberbezpečnost");
                ps.setString(6, s.getZnamky().toString());

                ps.executeUpdate();
            }

            System.out.println("SQL databáze byla aktualizována podle paměti.");
        } catch (Exception e) {
            System.out.println("Chyba při ukládání do databáze: " + e.getMessage());
        }
    }

    public static void nactiVsechnyStudenty(StudentDatabaze databaze) {
        try (Connection conn = DriverManager.getConnection(URL);
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM studenti")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String jmeno = rs.getString("jmeno");
                String prijimeni = rs.getString("prijmeni");
                int rok = rs.getInt("rok");
                String obor = rs.getString("obor");
                String znamky = rs.getString("znamky").replace("[", "").replace("]", "").trim();

                Student s;
                if ("Telekomunikace".equalsIgnoreCase(obor)) {
                    s = new TelekomunikacniStudent(id, jmeno, prijimeni, rok);
                } else {
                    s = new KyberbezpecnostniStudent(id, jmeno, prijimeni, rok);
                }

                List<Integer> znamkyList = Student.parseZnamky(znamky);
                for (int z : znamkyList) {
                    s.pridejZnamku(z);
                }

                databaze.pridatStudenta(s);
                databaze.aktualizujPosledniId(id);
            }

            System.out.println("Studenti načteni z SQL "
            		+ "databáze.");
        } catch (Exception e) {
            System.out.println("Chyba při načítání z databáze: " + e.getMessage());
        }
    }
}
