package com.eaads.phosphor_service.models.jira;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
        name = "TopPointResolverMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintTopPointResolvers.class,
               fields = {
        				@FieldResult(name = "assignee", column = "assignee"),
        				@FieldResult(name = "num", column = "num"),
        				@FieldResult(name = "points", column = "points")
        				})})
@NamedNativeQuery(name="getTopPointResolver",
resultSetMapping="TopPointResolverMapping", 
query="SELECT ji.assignee, COUNT(*) AS num, \n" + 
		"sum(ji.points) AS points\n" + 
		"FROM jira_issues ji\n" + 
		"INNER JOIN jira_sprints js ON ji.sprint_id = js.id\n" + 
		"WHERE js.state = 'active' AND ji.status = 'Completed' \n" + 
		"AND ji.assignee IS NOT NULL AND (ji.updated > NOW() - interval '7 days')\n" + 
		"GROUP BY ji.assignee\n" + 
		"ORDER BY points DESC, num DESC")
public class SprintTopPointResolvers implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintTopPointResolvers() {		
	}
	
	@Id
	private String assignee;
	private int num;
	private double points;

	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	
	

}
