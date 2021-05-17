package application;

public class Films 
{
	private String nom;
	private String directeur;
	private String genre;
	private Double temps;
	
	
	
	//constructeur vide
		public Films()
		{
			this(null,null);
		}
		
	//Constructeur avec 2 parmètres
		public Films(String nom, String directeur)
		{
			this.nom=nom;
			this.directeur=directeur;
			this.genre="";
			this.temps=0.0;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getDirecteur() {
			return directeur;
		}

		public void setDirecteur(String directeur) {
			this.directeur = directeur;
		}

		public String getGenre() {
			return genre;
		}

		public void setGenre(String genre) {
			this.genre = genre;
		}

		public Double getTemps() {
			return temps;
		}

		public void setTemps(Double temps) {
			this.temps = temps;
		}
	
		
	

}
