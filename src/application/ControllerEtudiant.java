package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class ControllerEtudiant implements Initializable {
    
    @FXML
    private TextField nomField;
    
    @FXML
    private TextField prenomField;
    
    @FXML
    private RadioButton sexeF;
    
    @FXML
    private RadioButton sexeM;
    
    @FXML
    private ToggleGroup sexeGroup;
    
    @FXML
    private ComboBox<String> filiereCombo;
    
    @FXML
    private Button ajouterBtn;
    
    @FXML
    private Button supprimerBtn;
    
    @FXML
    private Button modifierBtn;
    
    @FXML
    private TableView<Etudiant> etudiantTable;
    
    @FXML
    private TableColumn<Etudiant, Integer> idColumn;
    
    @FXML
    private TableColumn<Etudiant, String> nomColumn;
    
    @FXML
    private TableColumn<Etudiant, String> prenomColumn;
    
    @FXML
    private TableColumn<Etudiant, String> sexeColumn;
    
    @FXML
    private TableColumn<Etudiant, String> filiereColumn;
    
    private EtudiantM etudiantManager = new EtudiantM();
    private ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialiser les filières dans la ComboBox
        filiereCombo.setItems(FXCollections.observableArrayList("DSI", "RSI", "SCI"));
        
        // Configurer les colonnes de la table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        filiereColumn.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        
        // Sélection par défaut
        sexeM.setSelected(true);
        filiereCombo.getSelectionModel().selectFirst();
        
        // Charger les données des étudiants
        loadEtudiants();
        
        // Configurer le listener de sélection pour permettre la modification
        etudiantTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomField.setText(newSelection.getNom());
                prenomField.setText(newSelection.getPrenom());
                if ("F".equals(newSelection.getSexe())) {
                    sexeF.setSelected(true);
                } else {
                    sexeM.setSelected(true);
                }
                filiereCombo.setValue(newSelection.getFiliere());
            }
        });
    }
    
    // Méthode pour charger/recharger les étudiants dans la TableView
    private void loadEtudiants() {
        etudiantList.clear();
        etudiantList.addAll(etudiantManager.findAll());
        etudiantTable.setItems(etudiantList);
    }
    
    // Méthode pour ajouter un étudiant
    @FXML
    private void ajouterEtudiant(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String sexe = sexeF.isSelected() ? "F" : "M";
        String filiere = filiereCombo.getValue();
        
        if (nom.isEmpty() || prenom.isEmpty() || filiere == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
            return;
        }
        
        Etudiant etudiant = new Etudiant(nom, prenom, sexe, filiere);
        
        if (etudiantManager.create(etudiant)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant ajouté avec succès.");
            clearFields();
            loadEtudiants();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'étudiant.");
        }
    }
    
    // Méthode pour supprimer un étudiant
    @FXML
    private void supprimerEtudiant(ActionEvent event) {
        Etudiant selectedEtudiant = etudiantTable.getSelectionModel().getSelectedItem();
        
        if (selectedEtudiant == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un étudiant à supprimer.");
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation de suppression");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet étudiant?");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            if (etudiantManager.delete(selectedEtudiant)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant supprimé avec succès.");
                clearFields();
                loadEtudiants();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'étudiant.");
            }
        }
    }
    
    // Méthode pour modifier un étudiant
    @FXML
    private void modifierEtudiant(ActionEvent event) {
        Etudiant selectedEtudiant = etudiantTable.getSelectionModel().getSelectedItem();
        
        if (selectedEtudiant == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un étudiant à modifier.");
            return;
        }
        
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String sexe = sexeF.isSelected() ? "F" : "M";
        String filiere = filiereCombo.getValue();
        
        if (nom.isEmpty() || prenom.isEmpty() || filiere == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
            return;
        }
        
        selectedEtudiant.setNom(nom);
        selectedEtudiant.setPrenom(prenom);
        selectedEtudiant.setSexe(sexe);
        selectedEtudiant.setFiliere(filiere);
        
        if (etudiantManager.update(selectedEtudiant)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant modifié avec succès.");
            clearFields();
            loadEtudiants();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de l'étudiant.");
        }
    }
    
    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // Méthode pour vider les champs
    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        sexeM.setSelected(true);
        filiereCombo.getSelectionModel().selectFirst();
        etudiantTable.getSelectionModel().clearSelection();
    }
}