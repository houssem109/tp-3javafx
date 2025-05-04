package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Window;

public class AddBookControlor implements Initializable {

	@FXML
	private TextField tfLastName;
	@FXML
	private TextField tfFirstName;
	@FXML
	private TextField tfEmail;
	@FXML
	private Button addBtn;
	@FXML
	private Button exportBtn;
	@FXML
	private Button importBtn;
	@FXML
	private Button removeBtn;
	@FXML
	private Button quitBtn;

	@FXML
	private TableView<Person> table;
	@FXML
	private TableColumn<Person, String> emailCol;
	@FXML
	private TableColumn<Person, String> firstNameCol;
	@FXML
	private TableColumn<Person, String> lastNameCol;

	private DataClass data;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		data = new DataClass();
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

		ObservableList<Person> observableList = FXCollections.observableArrayList();
		table.setItems(observableList);

		addBtn.setOnAction(event -> ajouter(observableList));
		removeBtn.setOnAction(event -> remove());
		importBtn.setOnAction(event -> importer());
		exportBtn.setOnAction(event -> exporter());
		quitBtn.setOnAction(event -> quiter());
	}

	public void ajouter(ObservableList<Person> list) {
	    Window owner = addBtn.getScene().getWindow();
	    String nom = tfLastName.getText().trim();
	    String prenom = tfFirstName.getText().trim();
	    String email = tfEmail.getText().trim();
	    
	    if (!nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty()) {
	        if (Person.isEmailAdress(email)) {
	            Person newPerson = new Person(nom, prenom, email);
	            list.add(newPerson);
	            
	            tfLastName.clear();
	            tfFirstName.clear();
	            tfEmail.clear();
	        } else {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Email Error!");
	            alert.setHeaderText(null);
	            alert.setContentText("Format d'email invalide");
	            alert.initOwner(owner);
	            alert.show();
	        }
	    } else {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Form Error!");
	        alert.setHeaderText(null);
	        alert.setContentText("Remplire tous les champs");
	        alert.initOwner(owner);
	        alert.show();
	    }
	}

	private void quiter() {
		Platform.exit();

	}

	private void exporter() {
		ObservableList<Person> itemsToExport = table.getItems();
		Window owner = exportBtn.getScene().getWindow();

		if (itemsToExport.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Export Erreur!");
			alert.setHeaderText(null);
			alert.setContentText("La table ne contient aucune donnée à exporter.");
			alert.initOwner(owner);
			alert.show();
			return;
		}

		List<Person> exportList = new ArrayList<>(itemsToExport);

		if (data.getExportList() == null) {
			data.setExportList(new ArrayList<>());
		}

		data.setExportList(exportList);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Export Réussi");
		alert.setHeaderText(null);
		alert.setContentText("Les données ont été exportées avec succès.");
		alert.initOwner(owner);
		alert.show();
	}

	private void importer() {
		Window owner = importBtn.getScene().getWindow();
		ObservableList<Person> currentItems = table.getItems();
		List<Person> importList = data.getImportList();

		if (importList.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Import Erreur!");
			alert.setHeaderText(null);
			alert.setContentText("Il n'y a aucune donnée disponible à importer.");
			alert.initOwner(owner);
			alert.show();
			return;
		}

		for (Person person : importList) {
			boolean isDuplicate = false;
			for (Person existingPerson : currentItems) {
				if (existingPerson.getEmail().equals(person.getEmail())) {
					isDuplicate = true;
					break;
				}
			}

			if (!isDuplicate) {
				currentItems.add(person);
			}
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Import Réussi");
		alert.setHeaderText(null);
		alert.setContentText("Les données ont été importées avec succès.");
		alert.initOwner(owner);
		alert.show();
	}

	private void remove() {
		Window owner = removeBtn.getScene().getWindow();
		Person selectedPerson = table.getSelectionModel().getSelectedItem();

		if (selectedPerson != null) {
			table.getItems().remove(selectedPerson);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Suppression Réussie");
			alert.setHeaderText(null);
			alert.setContentText("La personne a été supprimée avec succès.");
			alert.initOwner(owner);
			alert.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Suppression Erreur!");
			alert.setHeaderText(null);
			alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
			alert.initOwner(owner);
			alert.show();
		}
	}

}
