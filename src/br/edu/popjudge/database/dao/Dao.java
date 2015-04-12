package br.edu.popjudge.database.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<T> {
	
	public void insert (T value) throws SQLException;

    public ArrayList<T> getAll() throws SQLException;

    public T get (int id) throws SQLException;

    public boolean delete (int id) throws SQLException;

    public void update(T value) throws SQLException;
}