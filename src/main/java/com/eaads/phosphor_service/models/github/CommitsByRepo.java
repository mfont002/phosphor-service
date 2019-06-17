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
        name = "CommitsByRepoMapping",
        entities = {
        @EntityResult(
        		entityClass = CommitsByRepo.class,
               fields = {
        				@FieldResult(name = "name", column = "name_t"),
        				@FieldResult(name = "count", column = "count_t")
        				})})
@NamedNativeQuery(name="getCommitsByRepo",
resultSetMapping="CommitsByRepoMapping", 
query="select r.name as name_t, count(c.branch_id) as count_t\n" + 
		"FROM github_commits AS c\n" + 
		"INNER JOIN github_branches AS b ON b.id = c.branch_id\n" + 
		"INNER JOIN github_repos AS r ON r.id = b.repo_id\n" + 
		"WHERE committed_date > NOW() - interval '20 days'\n" + 
		"GROUP BY name_t\n" + 
		"ORDER BY COUNT_t DESC LIMIT 10")
public class CommitsByRepo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public CommitsByRepo() {		
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
