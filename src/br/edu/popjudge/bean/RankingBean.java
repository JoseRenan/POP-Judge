package br.edu.popjudge.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.popjudge.domain.UserRank;
import br.edu.popjudge.service.RankingService;

@ManagedBean
@ViewScoped
public class RankingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 304688492372361003L;

	private UserRank userRank;
	private RankingService rankingService;

	public RankingBean() {
		this.rankingService = new RankingService();
	}

	public UserRank getUserRank() {
		return userRank;
	}

	public void setUserRank(UserRank userRank) {
		this.userRank = userRank;
	}

	public ArrayList<UserRank> getRanking() throws SQLException {
		return rankingService.getRanking();
	}
}