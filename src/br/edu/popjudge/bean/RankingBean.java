package br.edu.popjudge.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.popjudge.database.dao.RankingDAO;
import br.edu.popjudge.domain.UserRank;

@ManagedBean
@ViewScoped
public class RankingBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserRank userRank;

	public UserRank getUserRank() {
		return userRank;
	}

	public void setUserRank(UserRank userRank) {
		this.userRank = userRank;
	}

	public ArrayList<UserRank> getRanking() throws SQLException {

		ArrayList<UserRank> list = new RankingDAO().getAll();

		list.sort(new Comparator<UserRank>() {

			@Override
			public int compare(UserRank o1, UserRank o2) {
				if (o1.getSumAccepted() > o2.getSumAccepted())
					return -1;
				else if (o1.getSumAccepted() < o2.getSumAccepted())
					return 1;
				else if (o1.getSumTries() < o2.getSumTries())
					return -1;
				else if (o1.getSumTries() > o2.getSumTries())
					return 1;
				else if (o1.getSumTime() < o2.getSumTime())
					return -1;
				else if (o1.getSumTime() > o2.getSumTime())
					return 1;
				return 0;
			}
		});

		return list;
	}
}