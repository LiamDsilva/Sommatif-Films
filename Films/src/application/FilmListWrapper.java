package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="film")
public class FilmListWrapper 
{
	private List<Films> film;
	@XmlElement(name = "film")
	public List<Films> getFilm()
		{
			return film;
		}
	public void setEtudiants(List<Films> film)
	{
		this.film=film;
	}

}




