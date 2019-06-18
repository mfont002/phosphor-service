package com.eaads.phosphor_service.models.github;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
        name = "EaadsCommitsMapping",
        entities = {
        @EntityResult(
        		entityClass = EaadsTeamCommits.class,
               fields = {
        				@FieldResult(name = "time", column = "time"),
        				@FieldResult(name = "commits", column = "commits"),
        				@FieldResult(name = "repoName", column = "repo_name"),
        				@FieldResult(name = "branchName", column = "branch_name"),
        				@FieldResult(name = "committer", column = "committer")
        				})})
@NamedNativeQuery(name="getEaadsCommits",
resultSetMapping="EaadsCommitsMapping", 
query="SELECT date_trunc('minute', committed_date) AS time,\n" + 
		"count(*) as commits, r.name AS repo_name, b.name AS branch_name, committer\n" + 
		"FROM github_commits c\n" + 
		"INNER JOIN github_branches b on b.id = c.branch_id\n" + 
		"INNER JOIN github_repos r on r.id = b.repo_id\n" + 
		"WHERE committed_date > NOW() - interval '20 days'\n" + 
		"GROUP BY committer, time, repo_name, branch_name\n" + 
		"ORDER BY time DESC, repo_name")
public class EaadsTeamCommits implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public EaadsTeamCommits() {		
	}
	
	private Date time;
	private String repoName;
	private String branchName;
	private String committer;
	
	@Id
	private int commits;
	public int getCommits() {
		return commits;
	}
	public void setCommits(int commits) {
		this.commits = commits;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCommitter() {
		return committer;
	}
	public void setCommitter(String committer) {
		this.committer = committer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	

}
