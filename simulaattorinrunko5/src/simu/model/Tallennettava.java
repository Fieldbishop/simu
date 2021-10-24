package simu.model;

import javax.persistence.*;

/**
 * Luokka tietokantaan tallenettavista objekteista
 * 
 * @author Sampo Bredenberg, Ville Haapamäki, Lassi Piispanen
 * @version 1.0
 */
@Entity
@Table(name = "tallennettava")
public class Tallennettava {
	/**
	 * Apumuuttuja tallennettavien ajojen id numerointia ylläpitämään
	 */
	private static int mones = 1;

	/**
	 * Tallennettavan ajon ID
	 */
	@Id
	@Column(name = "ID")
	private int ID;

	/**
	 * Suhde palveluun ehtineille ja myöhästyneille saliin 1
	 */
	@Column(name = "sisaanPaasseetPoisJaaneetSuhde1")
	private double sisaanPaasseetPoisJaaneetSuhde1;

	/**
	 * Saliin 1 ehtineiden asiakkaiden summa
	 */
	@Column(name = "sisaanPaasseet1")
	private int sisaanPaasseet1;

	/**
	 * Salista 1 myöhästyneiden asiakkaiden summa
	 */
	@Column(name = "poisJaaneet1")
	private int poisJaaneet1;

	/**
	 * Asetus salin 1 tapahtumien väliinjäävälle luppoajalle
	 */
	@Column(name = "asetusOdotusaika1")
	private int asetusOdotusaika1;

	/**
	 * Asetus salin 1 tapahtumien kestolle
	 */
	@Column(name = "asetusKesto1")
	private int asetusKesto1;

	/**
	 * Suhde palveluun ehtineille ja myöhästyneille saliin 2
	 */
	@Column(name = "sisaanPaasseetPoisJaaneetSuhde2")
	private double sisaanPaasseetPoisJaaneetSuhde2;

	/**
	 * Saliin 2 ehtineiden asiakkaiden summa
	 */
	@Column(name = "sisaanPaasseet2")
	private int sisaanPaasseet2;

	/**
	 * Salista 2 myöhästyneiden asiakkaiden summa
	 */
	@Column(name = "poisJaaneet2")
	private int poisJaaneet2;

	/**
	 * Asetus salin 2 tapahtumien väliinjäävälle luppoajalle
	 */
	@Column(name = "asetusOdotusaika2")
	private int asetusOdotusaika2;

	/**
	 * Asetus salin 2 tapahtumien kestolle
	 */
	@Column(name = "asetusKesto2")
	private int asetusKesto2;

	/**
	 * Asiakkaiden käyttämä odotusajan keskiarvo systeemissä
	 */
	@Column(name = "asiakkaanOdotusAVG")
	private double asiakkaanOdotusAVG;

	/**
	 * Objektin konstruktori jossa asetetaan instanssimuuttujat
	 * 
	 * @param sisaanPaasseet1    ehtineet, sali1
	 * @param poisJaaneet1       myöhästyneet, sali1
	 * @param asetusOdotusaika1  tapahtumien väli, sali1
	 * @param asetusKesto1       tapahtumien kesto, sali1
	 * @param sisaanPaasseet2    ehtineet, sali2
	 * @param poisJaaneet2       myöhästyneet, sali2
	 * @param asetusOdotusaika2  tapahtumien väli, sali2
	 * @param asetusKesto2       tapahtumien kesto, sali2
	 * @param asiakkaanOdotusAVG odotuskeskiarvo
	 */
	public Tallennettava(int sisaanPaasseet1, int poisJaaneet1, int asetusOdotusaika1, int asetusKesto1,
			int sisaanPaasseet2, int poisJaaneet2, int asetusOdotusaika2, int asetusKesto2, double asiakkaanOdotusAVG) {
		ID = mones;
		mones++;
		this.sisaanPaasseetPoisJaaneetSuhde1 = (double) sisaanPaasseet1 / poisJaaneet1;
		this.sisaanPaasseet1 = sisaanPaasseet1;
		this.poisJaaneet1 = poisJaaneet1;
		this.asetusOdotusaika1 = asetusOdotusaika1;
		this.asetusKesto1 = asetusKesto1;
		this.sisaanPaasseetPoisJaaneetSuhde2 = (double) sisaanPaasseet2 / poisJaaneet2;
		this.sisaanPaasseet2 = sisaanPaasseet2;
		this.poisJaaneet2 = poisJaaneet2;
		this.asetusOdotusaika2 = asetusOdotusaika2;
		this.asetusKesto2 = asetusKesto2;
		this.asiakkaanOdotusAVG = asiakkaanOdotusAVG;
	}

	/**
	 * Tyhjä konstruktori tallentamista helpottamaan
	 */
	public Tallennettava() {
	};

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return tallennettavan id
	 */
	public int getID() {
		return ID;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param id tallennettavan id
	 */
	public void setID(int id) {
		this.ID = id;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return salin 1 ehtineet/myöhästyneet suhde
	 */
	public double getSisaanPaasseetPoisJaaneetSuhde1() {
		return sisaanPaasseetPoisJaaneetSuhde1;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param sisaanPaasseetPoisJaaneetSuhde1 salin 1 ehtineet/myöhästyneet suhde
	 */
	public void setSisaanPaasseetPoisJaaneetSuhde1(double sisaanPaasseetPoisJaaneetSuhde1) {
		this.sisaanPaasseetPoisJaaneetSuhde1 = sisaanPaasseetPoisJaaneetSuhde1;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali1 ehtineet määrä
	 */
	public int getSisaanPaasseet1() {
		return sisaanPaasseet1;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param sisaanPaasseet1 sali1 ehtineet määrä
	 */
	public void setSisaanPaasseet1(int sisaanPaasseet1) {
		this.sisaanPaasseet1 = sisaanPaasseet1;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali1 myöhästyneet määrä
	 */
	public int getPoisJaaneet1() {
		return poisJaaneet1;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param poisJaaneet1 sali1 myöhästyneetä määrä
	 */
	public void setPoisJaaneet1(int poisJaaneet1) {
		this.poisJaaneet1 = poisJaaneet1;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali1 tapahtumien väliajan asetus
	 */
	public int getAsetusOdotusaika1() {
		return asetusOdotusaika1;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param asetusOdotusaika1 sali1 tapahtumien väliajan asetus
	 */
	public void setAsetusOdotusaika1(int asetusOdotusaika1) {
		this.asetusOdotusaika1 = asetusOdotusaika1;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali1 tapahtumankesto-asetus
	 */
	public int getAsetusKesto1() {
		return asetusKesto1;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param asetusKesto1 sali1 tapahtumankesto-asetus
	 */
	public void setAsetusKesto1(int asetusKesto1) {
		this.asetusKesto1 = asetusKesto1;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali2 ehtineet/myöhästyneet suhde
	 */
	public double getSisaanPaasseetPoisJaaneetSuhde2() {
		return sisaanPaasseetPoisJaaneetSuhde2;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param sisaanPaasseetPoisJaaneetSuhde2 sali2 ehtineet/myöhästyneet suhde
	 */
	public void setSisaanPaasseetPoisJaaneetSuhde2(double sisaanPaasseetPoisJaaneetSuhde2) {
		this.sisaanPaasseetPoisJaaneetSuhde2 = sisaanPaasseetPoisJaaneetSuhde2;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali2 ehtineet
	 */
	public int getSisaanPaasseet2() {
		return sisaanPaasseet2;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param sisaanPaasseet2 sali2 ehtineet
	 */
	public void setSisaanPaasseet2(int sisaanPaasseet2) {
		this.sisaanPaasseet2 = sisaanPaasseet2;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali2 myöhästyneet
	 */
	public int getPoisJaaneet2() {
		return poisJaaneet2;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param poisJaaneet2 sali2 myöhästyneet
	 */
	public void setPoisJaaneet2(int poisJaaneet2) {
		this.poisJaaneet2 = poisJaaneet2;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali2 tapahtuman väli asetus
	 */
	public int getAsetusOdotusaika2() {
		return asetusOdotusaika2;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param asetusOdotusaika2 sali2 tapahtuman väli asetus
	 */
	public void setAsetusOdotusaika2(int asetusOdotusaika2) {
		this.asetusOdotusaika2 = asetusOdotusaika2;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return sali2 tapahtuman keston asetus
	 */
	public int getAsetusKesto2() {
		return asetusKesto2;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param asetusKesto2 sali2 tapahtuman keston asetus
	 */
	public void setAsetusKesto2(int asetusKesto2) {
		this.asetusKesto2 = asetusKesto2;
	}

	/**
	 * palauttaa instanssimuuttujan
	 * 
	 * @return asiakkaiden odotusajan keskiarvo
	 */
	public double getAsiakkaanOdotusAVG() {
		return asiakkaanOdotusAVG;
	}

	/**
	 * asettaa instanssimuuttujan
	 * 
	 * @param asiakkaanOdotusAVG asiakkaiden odotusajan keskiarvo
	 */
	public void setAsiakkaanOdotusAVG(double asiakkaanOdotusAVG) {
		this.asiakkaanOdotusAVG = asiakkaanOdotusAVG;
	}

	/**
	 * toStringmetodi database testailua varten
	 */
	@Override
	public String toString() {
		return "Tallennettava [ID=" + ID + ", sisaanPaasseetPoisJaaneetSuhde1=" + sisaanPaasseetPoisJaaneetSuhde1
				+ ", sisaanPaasseet1=" + sisaanPaasseet1 + ", poisJaaneet1=" + poisJaaneet1 + ", asetusOdotusaika1="
				+ asetusOdotusaika1 + ", asetusKesto1=" + asetusKesto1 + ", sisaanPaasseetPoisJaaneetSuhde2="
				+ sisaanPaasseetPoisJaaneetSuhde2 + ", sisaanPaasseet2=" + sisaanPaasseet2 + ", poisJaaneet2="
				+ poisJaaneet2 + ", asetusOdotusaika2=" + asetusOdotusaika2 + ", asetusKesto2=" + asetusKesto2
				+ ", asiakkaanOdotusAVG=" + asiakkaanOdotusAVG + "]";
	}

}
