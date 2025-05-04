package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
	private String nom;
	private String prenom;
	private String email;
	
	
	
	public Person(String nom, String prenom, String email) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
	}

	  public static boolean isEmailAdress(String email) {
	        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
	        Matcher m = p.matcher(email.toUpperCase());
	        return m.matches();
	    }

	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	@Override
	public String toString() {
		return "Person [nom=" + nom + ", prenom=" + prenom + ", email=" + email + "]";
	}
	
	
	
	
}
