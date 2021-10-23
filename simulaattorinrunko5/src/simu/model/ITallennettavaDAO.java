package simu.model;


public interface ITallennettavaDAO {
	public boolean createEntry(Tallennettava v);
	public Tallennettava readEntry(int id);
	public Tallennettava[] readAll();
	public boolean updateEntry(Tallennettava v);
	public boolean deleteEntry(int id);
}
