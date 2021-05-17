package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FilmsController implements Initializable{

    @FXML
    private Button btnEffacer;
    
    @FXML
    private TableView<Films> filmTable;

    @FXML
    private TableColumn<Films, String> tempsColumn;

    @FXML
    private Button btnModifier;

    @FXML
    private TableColumn<Films, String> nomColumn;

    @FXML
    private TableColumn<Films, String> directeurColumn;

    @FXML
    private ComboBox<String> cboGenre;

    @FXML
    private TextField txtTemps;
    
    @FXML
    private TextField txtDirecteur;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnClear;

    @FXML
    private TableColumn<Films, String> genreColumn;

    @FXML
    private TextField txtNom;
    
    // liste pour les genres - cette liste permettra de donner les valeurs du comboBox

 	private ObservableList<String>  list=(ObservableList<String>) FXCollections.observableArrayList("Comédie","Action","Horror","Sci-fi","Amour");      
 	
 	//Placer les étudiants dans une observable list
 		public ObservableList<Films> filmData=FXCollections.observableArrayList();
 
 	//Créer une méthode pour accéder à la liste des films
 	public ObservableList<Films> getfilmData()
 	{
 		return filmData;
 	}
 	
 	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		cboGenre.setItems(list);
		//attribuer les valeurs aux colonnes du tableView
		nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		directeurColumn.setCellValueFactory(new PropertyValueFactory<>("directeur"));
		genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
		tempsColumn.setCellValueFactory(new PropertyValueFactory<>("temps"));
		filmTable.setItems(filmData);

		btnModifier.setDisable(true);;
		btnEffacer.setDisable(true);
		btnClear.setDisable(true);


		showFilms(null);
		//Mettre à hour l'affichage d'un film sélectionné
		filmTable.getSelectionModel().selectedItemProperty().addListener((
				observable, oldValue, newValue)-> showFilms(newValue));

	}

 	
 	
 	
 	// Ajouter un film
 	@FXML
 	void ajouter()
 	{
 		//Vérifier si un champ n'est pas vide
 		if(noEmptyInput())
 		{
 			Films tmp=new Films();
 			tmp=new Films();
 			tmp.setNom(txtNom.getText());
 			tmp.setDirecteur(txtDirecteur.getText());
 			tmp.setTemps(Double.parseDouble(txtTemps.getText()));
 			tmp.setGenre(cboGenre.getValue());
 			filmData.add(tmp);
 			clearFields();	
 		}


 	}

 	//Effacer le contenu des champs
 	@FXML
 	void clearFields()
 		{
 			cboGenre.setValue(null);
 			txtDirecteur.setText("");
 			txtNom.setText("");
 			txtTemps.setText("");
 		}

 	//Afficher les films
 	public void showFilms(Films film)
 		{ 
 			if(film !=null)
 			{
 				cboGenre.setValue(film.getGenre());
 				txtDirecteur.setText(film.getDirecteur());
 				txtNom.setText(film.getNom());
 				txtTemps.setText(Double.toString(film.getTemps()));
 				btnModifier.setDisable(false);;
 				btnEffacer.setDisable(false);
 				btnClear.setDisable(false);

 			}
 			else
 			{
 				clearFields();

 			}
 		}

	//Mise  à jour d'un film
	@FXML
	public void updateFilm()
	{
		//Vérifier si un champ n'est pas vide
		if(noEmptyInput())
		{
			Films film= filmTable.getSelectionModel().getSelectedItem();

			film.setDirecteur(txtDirecteur.getText());
			film.setNom(txtNom.getText());
			film.setTemps(Double.parseDouble(txtTemps.getText()));
			film.setGenre(cboGenre.getValue());
			filmTable.refresh();
		}
	}
 	
	//Effacer un film
	@FXML
	public void deleteFilm()
	{
		int selectedIndex = filmTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >=0)
		{
			Alert alert=new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Effacer");
			alert.setContentText("confirmer la suppression");
			Optional<ButtonType> result=alert.showAndWait();
			if(result.get()==ButtonType.OK)
				filmTable.getItems().remove(selectedIndex);
		}

	}
 	
	public void verifNum() // méthode pour trouver des input non numériques
	{
		txtTemps.textProperty().addListener((observable,oldValue,newValue)->
		{
			if(!newValue.matches("^[0-9](\\.[0-9]+)?$"))

			{
				txtTemps.setText(newValue.replaceAll("[^\\d*\\.", "")); // si le input est non numérique ça le remplace
			}
		});
	}
 	
	// Vérifier champs vides
	private boolean noEmptyInput()
		{
			String errorMessage="";
			if(txtDirecteur.getText().trim().equals(""))
			{
				errorMessage+="Le champ nom ne doit pas être vide! \n";
			}
			if(txtNom.getText()==null||txtDirecteur.getText().length()==0)
			{
				errorMessage+="Le champ nom ne doit pas être vide! \n";
			}
			if(txtTemps.getText()==null||txtDirecteur.getText().length()==0)
			{
				errorMessage+="Le champ nom ne doit pas être vide! \n";
			}
			if(cboGenre.getValue()==null)
			{
				errorMessage+="Le champ nom ne doit pas être vide! \n";
			}
			if(errorMessage.length()==0)
			{
				return true;
			}
			else
			{
				Alert alert=new Alert(AlertType.ERROR);
				alert.setTitle("Champs manquants");
				alert.setHeaderText("Completer les champs manquants");
				alert.setContentText(errorMessage);
				alert.showAndWait();
				return false; 
			}


		}

	// Afficher	les statistiques	
	@FXML
	void handleStats()
		{
			try {
				FXMLLoader loader=new FXMLLoader(Main.class.getResource("TempsStat.FXML"));
				 AnchorPane pane=loader.load();
				 Scene scene=new Scene(pane);
			   	 TempsStat tempsstat=loader.getController();
				 tempsstat.SetStats(filmData);
				 Stage stage=new Stage();
				 stage.setScene(scene);
				 stage.setTitle("Statistiques");
				 stage.show();
			} catch (IOException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	
	
	//SAUVEGARDE DE DONNÉES!!!

	//Récupérer le chemin (path) des fichiers si ça existe
	public File getFilmFilePath()
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			String filePath = prefs.get("filPath", null);

			if(filePath !=null)
			{
				return new File(filePath);
			}
			else
			{
				return  null;
			}

		}

	//Attribuer un chemin de fichiers	
	public void setFilmFilePath(File file)
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			if(file !=null)
			{
				prefs.put("filePath",  file.getPath());
			}
			else
			{
				prefs.remove("filePath");
			}
		}
	
	//Prendre les données de type XML et les convertir en données de type javaFX
	public void loadFilmDataFromFile(File file)
		{
			try {
				JAXBContext context = JAXBContext.newInstance(FilmListWrapper.class);
				Unmarshaller um = context.createUnmarshaller();

				FilmListWrapper wrapper = (FilmListWrapper) um.unmarshal(file);
				filmData.clear();
				filmData.addAll(wrapper.getFilm());
				setFilmFilePath(file);
				// Donner le titre du fichier chargé
				Stage pStage=(Stage) filmTable.getScene().getWindow();
				pStage.setTitle(file.getName());

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("les données n'ont pas été trouvées");
				alert.setContentText("Les données ne pouvaient pas être trouvées dans le fichier : \n" +file.getPath());
				alert.showAndWait();
			}
		}

	//Prendre les données de type JavaFx et les convertir en type XML
	public void saveFilmDataToFile(File file) {
			try {
				JAXBContext context = JAXBContext.newInstance(FilmListWrapper.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				FilmListWrapper wrapper = new FilmListWrapper();
				wrapper.setFilm(filmData);

				m.marshal(wrapper, file);

				//Sauvegarde dans le registre
				setFilmFilePath(file);
				// Donner le titre du fichier sauvegardé
				Stage pStage=(Stage) filmTable.getScene().getWindow();
				pStage.setTitle(file.getName());

			} catch (Exception e) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Données non sauvegardées");
				alert.setContentText("Les données ne pouvaient pas être sauvegardées dans le fichier:\n" + file.getPath());
				alert.showAndWait(); 
			}
		}
	
	//Commencer un nouveau
	@FXML
	private void handleNew()
		{
			getfilmData().clear();
			setFilmFilePath(null);	
		}
	
	/*
	 * Le FileChooser permet à l'usager de choisir le fichier à ouvrir
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();

		// Permettre un filtre sur l'extension du fichier à chercher
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		//Montrer le dialogue
		File file = fileChooser.showOpenDialog(null);

		if(file !=null) {
			loadFilmDataFromFile(file);
		}

	}

	/*
	 * Sauvegarde le fichier correspondant à le film actif
	 * S'il n y a pas de fichier, le meny saugvegarder sous va s'afficher
	 */
	@FXML
	private void handleSave() {
		File filmFile = getFilmFilePath();
		if (filmFile !=null) {
			saveFilmDataToFile(filmFile);
		} else {
			handleSaveAs();
		}
	}

	/*
	 * Ouvrir le FileChooser pour trouver le chemin
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		//Sauvegarde
		File file = fileChooser.showSaveDialog(null);

		if(file !=null) {
			//Vérification de l'extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			saveFilmDataToFile(file);
		}

	}

}

	
	

