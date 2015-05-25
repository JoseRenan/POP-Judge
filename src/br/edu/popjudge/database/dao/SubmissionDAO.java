package br.edu.popjudge.database.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.domain.User;

@ManagedBean
public class SubmissionDAO implements Dao<Submission> {
	Connection connection;

	@Override
	public void insert(Submission value) throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO SUBMISSION(id_submission, id_user, id_problem, "
				+ "id_language, file_name, time_submission, veredict) VALUES(0, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, value.getUser().getIdUser());
		statement.setInt(2, value.getProblem().getIdProblem());
		statement.setInt(3, value.getLanguage().getIdLanguage());
		statement.setString(5, value.getTimestamp().toString());
		statement.setString(4, value.getFile().getAbsolutePath());
		statement.setString(6, value.getVeredict());

		statement.execute();

		statement.close();
		connection.close();
	}

	public int insertAndGetKey(Submission value) throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO SUBMISSION(id_submission, id_user, id_problem, "
				+ "id_language, file_name, time_submission, veredict) VALUES(0, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);

		statement.setInt(1, value.getUser().getIdUser());
		statement.setInt(2, value.getProblem().getIdProblem());
		statement.setInt(3, value.getLanguage().getIdLanguage());
		statement.setString(5, value.getTimestamp().toString());
		statement.setString(4, value.getFile().getAbsolutePath());
		statement.setString(6, value.getVeredict());

		statement.execute();

		ResultSet resultSet = statement.getGeneratedKeys();
		int key = 0;

		if (resultSet.next()) {
			key = resultSet.getInt(1);
		}

		return key;
	}

	@Override
	public ArrayList<Submission> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "SELECT * FROM SUBMISSION ORDER BY time_submission DESC";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		ArrayList<Submission> list = new ArrayList<Submission>();

		UserDAO uDAO = new UserDAO();
		ProblemDAO pDAO = new ProblemDAO();
		LanguageDAO lDAO = new LanguageDAO();

		while (resultSet.next()) {
			list.add(new Submission(resultSet.getInt("id_submission"), uDAO
					.get(resultSet.getInt("id_user")), pDAO.get(resultSet
					.getInt("id_problem")), lDAO.get(resultSet
					.getInt("id_language")), new File(resultSet
					.getString("file_name")), resultSet
					.getTimestamp("time_submission"), resultSet
					.getString("veredict")));
		}

		resultSet.close();
		statement.close();
		connection.close();

		return list;
	}

	public ArrayList<Submission> getMySubmissions() throws SQLException {
		connection = new ConnectionFactory().getConnection();

		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(true);

		int id = ((User) session.getAttribute("user")).getIdUser();

		String sql = "SELECT * FROM SUBMISSION WHERE id_user = " + id
				+ " ORDER BY time_submission DESC";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		ArrayList<Submission> list = new ArrayList<Submission>();

		UserDAO uDAO = new UserDAO();
		ProblemDAO pDAO = new ProblemDAO();
		LanguageDAO lDAO = new LanguageDAO();

		while (resultSet.next()) {
			list.add(new Submission(resultSet.getInt("id_submission"), uDAO
					.get(resultSet.getInt("id_user")), pDAO.get(resultSet
					.getInt("id_problem")), lDAO.get(resultSet
					.getInt("id_language")), new File(resultSet
					.getString("file_name")), resultSet
					.getTimestamp("time_submission"), resultSet
					.getString("veredict")));
		}

		resultSet.close();
		statement.close();
		connection.close();

		return list;
	}

	@Override
	public Submission get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = String.format(
				"SELECT * FROM SUBMISSION WHERE id_submission = %d", id);

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		Submission ret = new Submission();

		UserDAO uDAO = new UserDAO();
		ProblemDAO pDAO = new ProblemDAO();
		LanguageDAO lDAO = new LanguageDAO();

		if (resultSet.next()) {
			ret = new Submission(resultSet.getInt("id_submission"),
					uDAO.get(resultSet.getInt("id_user")), pDAO.get(resultSet
							.getInt("id_problem")), lDAO.get(resultSet
							.getInt("id_language")), new File(
							resultSet.getString("file_name")),
					resultSet.getTimestamp("time_submission"),
					resultSet.getString("veredict"));
		}

		resultSet.close();
		statement.close();
		connection.close();

		return ret;
	}

	@Override
	@Deprecated
	public boolean delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void deleteByProblem(int idProblem) throws SQLException {

		connection = new ConnectionFactory().getConnection();

		String sql = String.format("DELETE FROM SUBMISSION WHERE id_problem = %d", idProblem);

		Statement statement = connection.createStatement();

		statement.execute(sql);
		
		statement.close();
		connection.close();

	}

	@Override
	public void update(Submission value) throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "UPDATE SUBMISSION SET id_user = ?, id_problem = ?, id_language = ?, file_name = ?, "
				+ "time_submission = ?, veredict = ? WHERE id_submission = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, value.getUser().getIdUser());
		statement.setInt(2, value.getProblem().getIdProblem());
		statement.setInt(3, value.getLanguage().getIdLanguage());
		statement.setString(4, value.getFile().getName());
		statement.setTimestamp(5, value.getTimestamp());
		statement.setString(6, value.getVeredict());
		statement.setInt(7, value.getIdSubmission());

		statement.execute();

		statement.close();
		connection.close();
	}

	@Override
	public void truncate() throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "truncate table SUBMISSION";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		statement.close();

		connection.close();
	}
}
