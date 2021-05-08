package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class TempsStat {


	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private CategoryAxis xAxis;

	private ObservableList<String> intervalTemps=FXCollections.observableArrayList();

	@FXML
	private void initialize()
	{
		intervalTemps.add("0-10");
		intervalTemps.add("10-20");
		intervalTemps.add("20-30");
		intervalTemps.add("30-40");
		intervalTemps.add("50-60");
		xAxis.setCategories(intervalTemps);

	}

	public void SetStats(List<Films> etudiants)
	{
		// Compter les films appartenant à la même tranche de temps

		int[] tempsCounter= new int[6]; // Tableau pour les nombres des tranches de temps

		for(Films e:film)
		{
			double temps= e.getTemps();

			if(temps<=10)
				tempsCounter[0]++;
			else
				if(temps<=20)
					tempsCounter[1]++;
				else
					if(temps<=30)
						tempsCounter[2]++;
					else
						if(temps<=40)
							tempsCounter[3]++;
						else
							if(temps<=50)
								tempsCounter[4]++;
							else
									tempsCounter[5]++;
		}
		XYChart.Series<String, Integer> series=new XYChart.Series<>();
		series.setName("temps"); // Légende pour le graphique
		//Création du graphique
		for(int i =0; i<tempsCounter.length;i++)
		{
			series.getData().add(new XYChart.Data<>(intervalTemps.get(i), tempsCounter[i]));
			
		}
		barChart.getData().add(series);
		
	}







}
