package simu.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * DAO luokka tietokantaan tallentamista varten implementoi interfacen
 * ITallennettavaDAO
 * 
 * @author Sampo Bredenberg, Ville Haapamäki, Lassi Piispanen
 * @version 1.0
 */
public class TallennettavaAccessObject implements ITallennettavaDAO {
	/**
	 * Ylläpitää database yhteyksiä
	 */
	private SessionFactory istuntotehdas = null;
	/**
	 * yksittäinen yhteydenotto databaseen
	 */
	private Transaction transaktio = null;

	/**
	 * Konstruktori, alustaa instuntotehtaan
	 */
	public TallennettavaAccessObject() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.err.println("SessionFactoryn luonti ei onnistunut: " + e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Java roskienkerääjän tekemän objektin tuhoamisen yhteydessä suoritettava
	 * koodi
	 */
	public void finalize() {
		try {
			if (istuntotehdas != null) {
				istuntotehdas.close();
			}
		} catch (Exception e) {
			System.err.println("SessionFactoryn sulkeminen ei onnistunut: " + e.getMessage());
		}
	}

	/**
	 * kirjoittaa objektin databaseen
	 */
	@Override
	public boolean createEntry(Tallennettava v) {
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(v);
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
				throw e;
			}
		}
		return false;
	}

	/**
	 * lukee objektin databasesta
	 */
	@Override
	public Tallennettava readEntry(int id) {
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			Tallennettava palautettava = new Tallennettava();
			istunto.load(palautettava, id);
			transaktio.commit();
			return palautettava;
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
				throw e;
			}
		}
		return null;
	}

	/**
	 * lukee koko tietokannan sisällön
	 */
	@Override
	public Tallennettava[] readAll() {
		try (Session istunto = istuntotehdas.openSession()) {
			@SuppressWarnings("unchecked")
			List<Tallennettava> palautettavat = istunto.createQuery("FROM Tallennettava").getResultList();
			Tallennettava[] temp = new Tallennettava[palautettavat.size()];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = palautettavat.get(i);
			}
			return temp;
		} catch (Exception e) {
			System.err.println("Koko databasen haku ei onnistunut: " + e.getMessage());
		}
		return null;
	}

	/**
	 * päivittää tietokantaan tallennetun objektin
	 */
	@Override
	public boolean updateEntry(Tallennettava v) {
		if (readEntry(v.getID()) != null) {
			try {
				createEntry(v);
				return true;
			} catch (Exception e) {
				System.err.println("db entryn päivitys ei onnistunut: " + e.getMessage());
			}
		} else {
			System.err.println("Ei löytynyt db entryä");
		}
		return false;
	}

	/**
	 * Poistaa tietokantaan tallennetun objektin
	 */
	@Override
	public boolean deleteEntry(int id) {
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			istunto.delete(istunto.load(Tallennettava.class, id));
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			throw e;
		}
	}
}
