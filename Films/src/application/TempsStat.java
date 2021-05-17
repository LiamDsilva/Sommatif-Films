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
		intervalTemps.add("100-200");
		intervalTemps.add("200-300");
		intervalTemps.add("300-400");
		intervalTemps.add("500-600");
		xAxis.setCategories(intervalTemps);

	}

	public void SetStats(List<Films> films)
	{
		// Compter les films appartenant à la même tranche de temps

		int[] tempsCounter= new int[6]; // Tableau pour les nombres des tranches de temps

		for(Films e:films)
		{
			double temps= e.getTemps();

			if(temps<=100)
				tempsCounter[0]++;
			else
				if(temps<=200)
					tempsCounter[1]++;
				else
					if(temps<=300)
						tempsCounter[2]++;
					else
						if(temps<=400)
							tempsCounter[3]++;
						else
							if(temps<=500)
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
