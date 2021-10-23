import java.util.Scanner;

import simu.model.ITallennettavaDAO;
import simu.model.Tallennettava;
import simu.model.TallennettavaAccessObject;



public class TempMain {
	static ITallennettavaDAO DAO = new TallennettavaAccessObject();
	static Scanner scanner = new Scanner(System.in);

	public static void listaa() {
		System.out.println("Tallennettuna seuraavat entryt:");
		Tallennettava[] lista = DAO.readAll();
		for (int i = 0; i < lista.length; i++) {
			System.out.println(lista[i]);
		}
	}

	public static void lisää() {
		DAO.createEntry(new Tallennettava(5,10,50,50,10,25,10,50,10.0));
	}

	public static void päivitä() {
		DAO.updateEntry(new Tallennettava(10,15,50,50,10,25,10,50,10.0));
	}

	public static void poista() {
		DAO.deleteEntry(2);	
	}

	public static void main(String[] args) {
		char valinta;
		final char CREATE = 'C', READ = 'R', UPDATE = 'U', DELETE = 'D', QUIT = 'Q';
		System.out.println("CREATE = 'C', READ = 'R', UPDATE = 'U', DELETE = 'D', QUIT = 'Q'");

		do {
			valinta = (scanner.nextLine().toUpperCase()).charAt(0);
			switch (valinta) {
			case READ:
				listaa();
				break;
			case CREATE:
				lisää();
				break;
			case UPDATE:
				päivitä();
				break;
			case DELETE:
				poista();
				break;
			}
		} while (valinta != QUIT);
	}
}
