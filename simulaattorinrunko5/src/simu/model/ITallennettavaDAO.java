package simu.model;

/**
 * Interface-luokka tietokantaan tallennettavien objectien käsittelyä varten,
 * käsittää perus "CRUD" metodit
 * 
 * @author Sampo Bredenberg, Ville Haapamäki, Lassi Piispanen
 * @version 1.0
 */
public interface ITallennettavaDAO {

	/**
	 * Tallentaa syötetyn objektin tietokantaan
	 * 
	 * @param v Tallennettava objekti
	 * @return palauttaa boolean onnistumisesta riippuen
	 */
	public boolean createEntry(Tallennettava v);

	/**
	 * lukee yksittäisen tallennetun objektin syötetyn id avulla
	 * 
	 * @param id luettavan objektin id
	 * @return Palauttaa koko tallennetun objektin
	 */
	public Tallennettava readEntry(int id);

	/**
	 * Lukee koko tietokannan sisällön
	 * 
	 * @return palauttaa koko tietokannan sisällön object array -muodossa
	 */
	public Tallennettava[] readAll();

	/**
	 * Päivittää tietokantaan tallennetun objektin
	 * 
	 * @param v päivitettävä objekti uusilla arvoilla
	 * @return palauttaa boolean onnistumisesta riippuen
	 */
	public boolean updateEntry(Tallennettava v);

	/**
	 * Poistaa tietokantaan tallennetun objektin
	 * 
	 * @param id poistettavan objektin id
	 * @return palauttaa boolean onnistumiseta riippuen
	 */
	public boolean deleteEntry(int id);
}
