package simu.model;
import javax.persistence.*;
 
@Entity
@Table(name="tallennettava")
public class Tallennettava {
	private static int mones = 1;
	@Id
	@Column(name ="ID")
	private int ID;
	@Column(name ="sisaanPaasseetPoisJaaneetSuhde1")
	private double sisaanPaasseetPoisJaaneetSuhde1;
	@Column(name ="sisaanPaasseet1")
	private int sisaanPaasseet1;
	@Column(name ="poisJaaneet1")
	private int poisJaaneet1;
	@Column(name ="asetusOdotusaika1")
	private int asetusOdotusaika1;
	@Column(name ="asetusKesto1")
	private int asetusKesto1;
	@Column(name ="sisaanPaasseetPoisJaaneetSuhde2")
	private double sisaanPaasseetPoisJaaneetSuhde2;
	@Column(name ="sisaanPaasseet2")
	private int sisaanPaasseet2;
	@Column(name ="poisJaaneet2")
	private int poisJaaneet2;
	@Column(name ="asetusOdotusaika2")
	private int asetusOdotusaika2;
	@Column(name ="asetusKesto2")
	private int asetusKesto2;
	@Column(name ="asiakkaanOdotusAVG")
	private double asiakkaanOdotusAVG;
	
	public Tallennettava(int sisaanPaasseet1, int poisJaaneet1, int asetusOdotusaika1, int asetusKesto1, 
			int sisaanPaasseet2, int poisJaaneet2, int asetusOdotusaika2, int asetusKesto2, double asiakkaanOdotusAVG) {
		ID = mones;
		mones++;
		this.sisaanPaasseetPoisJaaneetSuhde1 = (double)sisaanPaasseet1/poisJaaneet1;
		this.sisaanPaasseet1 = sisaanPaasseet1;
		this.poisJaaneet1 = poisJaaneet1;
		this.asetusOdotusaika1 = asetusOdotusaika1;
		this.asetusKesto1 = asetusKesto1;
		this.sisaanPaasseetPoisJaaneetSuhde2 = (double)sisaanPaasseet2/poisJaaneet2;
		this.sisaanPaasseet2 = sisaanPaasseet2;
		this.poisJaaneet2 = poisJaaneet2;
		this.asetusOdotusaika2 = asetusOdotusaika2;
		this.asetusKesto2 = asetusKesto2;
		this.asiakkaanOdotusAVG = asiakkaanOdotusAVG;
	}
	
	public Tallennettava() {};

	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		this.ID=id;
	}

	public double getSisaanPaasseetPoisJaaneetSuhde1() {
		return sisaanPaasseetPoisJaaneetSuhde1;
	}

	public void setSisaanPaasseetPoisJaaneetSuhde1(double sisaanPaasseetPoisJaaneetSuhde1) {
		this.sisaanPaasseetPoisJaaneetSuhde1 = sisaanPaasseetPoisJaaneetSuhde1;
	}

	public int getSisaanPaasseet1() {
		return sisaanPaasseet1;
	}

	public void setSisaanPaasseet1(int sisaanPaasseet1) {
		this.sisaanPaasseet1 = sisaanPaasseet1;
	}

	public int getPoisJaaneet1() {
		return poisJaaneet1;
	}

	public void setPoisJaaneet1(int poisJaaneet1) {
		this.poisJaaneet1 = poisJaaneet1;
	}

	public int getAsetusOdotusaika1() {
		return asetusOdotusaika1;
	}

	public void setAsetusOdotusaika1(int asetusOdotusaika1) {
		this.asetusOdotusaika1 = asetusOdotusaika1;
	}

	public int getAsetusKesto1() {
		return asetusKesto1;
	}

	public void setAsetusKesto1(int asetusKesto1) {
		this.asetusKesto1 = asetusKesto1;
	}

	public double getSisaanPaasseetPoisJaaneetSuhde2() {
		return sisaanPaasseetPoisJaaneetSuhde2;
	}

	public void setSisaanPaasseetPoisJaaneetSuhde2(double sisaanPaasseetPoisJaaneetSuhde2) {
		this.sisaanPaasseetPoisJaaneetSuhde2 = sisaanPaasseetPoisJaaneetSuhde2;
	}

	public int getSisaanPaasseet2() {
		return sisaanPaasseet2;
	}

	public void setSisaanPaasseet2(int sisaanPaasseet2) {
		this.sisaanPaasseet2 = sisaanPaasseet2;
	}

	public int getPoisJaaneet2() {
		return poisJaaneet2;
	}

	public void setPoisJaaneet2(int poisJaaneet2) {
		this.poisJaaneet2 = poisJaaneet2;
	}

	public int getAsetusOdotusaika2() {
		return asetusOdotusaika2;
	}

	public void setAsetusOdotusaika2(int asetusOdotusaika2) {
		this.asetusOdotusaika2 = asetusOdotusaika2;
	}

	public int getAsetusKesto2() {
		return asetusKesto2;
	}

	public void setAsetusKesto2(int asetusKesto2) {
		this.asetusKesto2 = asetusKesto2;
	}

	public double getAsiakkaanOdotusAVG() {
		return asiakkaanOdotusAVG;
	}

	public void setAsiakkaanOdotusAVG(double asiakkaanOdotusAVG) {
		this.asiakkaanOdotusAVG = asiakkaanOdotusAVG;
	}

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
