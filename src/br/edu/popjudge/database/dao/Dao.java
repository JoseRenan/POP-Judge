package br.edu.popjudge.database.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
	
	public int insert (T value) throws SQLException;

    public List<T> getAll() throws SQLException;

    public T get (int id) throws SQLException;

    public boolean delete (int id) throws SQLException;

    public void update(T value) throws SQLException;
    
    public void truncate() throws SQLException;
}
