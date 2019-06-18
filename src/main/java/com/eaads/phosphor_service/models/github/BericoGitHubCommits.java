package com.eaads.phosphor_service.models.github;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
        name = "BericoCommitsMapping",
        entities = {
        @EntityResult(
        		entityClass = BericoGitHubCommits.class,
               fields = {
        				@FieldResult(name = "name", column = "name_t"),
        				@FieldResult(name = "count", column = "count_t")
        				})})
@NamedNativeQuery(name="getBericoCommits",
resultSetMapping="BericoCommitsMapping", 
query="SELECT date_trunc('minute', committed_date) AS time,\n" + 
		"count(*) as commits, committer\n" + 
		"FROM\n" + 
		"github_commits\n" + 
		"WHERE\n" + 
		"committed_date > NOW() - interval '15 days'\n" + 
		"GROUP BY date_trunc('minute', committed_date), committer\n" + 
		"order by time DESC, COMMITS")
public class BericoGitHubCommits implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public BericoGitHubCommits() {		
	}
	
	@Id
	private String name;
	private int count;
	 	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
