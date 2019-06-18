package com.eaads.phosphor_service.models.github;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name = "TopCommitterMapping", entities = {
		@EntityResult(entityClass = TopCommitter.class, fields = {
				@FieldResult(name = "committer", column = "committer"),
				@FieldResult(name = "totalCommits", column = "num_commits") }) })
@NamedNativeQuery(name = "getTopCommitter", resultSetMapping = "TopCommitterMapping", 
query = "SELECT c.committer as committer,\n"
		+ "COUNT(*) AS num_commits\n" + "FROM github_commits AS c\n"
		+ "WHERE c.committed_date > NOW() - interval '15 days'\n" + "GROUP BY committer\n"
		+ "order by num_commits DESC LIMIT 25")
public class TopCommitter {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	String committer;

	public String getCommitter() {
		return committer;
	}

	public void setCommitter(String committer) {
		this.committer = committer;
	}

	int totalCommits;

	public int getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(int totalCommits) {
		this.totalCommits = totalCommits;
	}

}
