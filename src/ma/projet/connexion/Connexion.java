package ma.projet.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	private static String login = "root";
	private static String password = "";
	private static String url = "jdbc:mysql://localhost/demoJDBC";
	private static Connection cn;
	static {
		try {
//Étape 1 : Charger le driver de la base de données cible

			Class.forName("com.mysql.cj.jdbc.Driver");
			// Étape 2 : Authentification auprès de la base de données et
			// sélectionner le schéma

			cn = DriverManager.getConnection(url, login, password);
		} catch (ClassNotFoundException ex) {
			System.out.println("Impossible de charger le driver");
		} catch (SQLException ex) {
			System.out.println("Erreur de connexion");
		}
	}

	public static Connection getCn() {
		return cn;
	}
}