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
        name = "PointsToDoMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintPointsToDo.class,
               fields = {
        				@FieldResult(name = "pointsToDo", column = "to_do")
        				})})
@NamedNativeQuery(name="getPointsToDo",
resultSetMapping="PointsToDoMapping", 
query="SELECT SUM(points) AS to_do\n" + 
		"FROM jira_issues AS i\n" + 
		"INNER JOIN jira_sprints AS s ON s.id = i.sprint_id\n" + 
		"WHERE s.state = 'active' AND i.status = 'To Do'")
public class SprintPointsToDo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintPointsToDo() {		
	}
	
	@Id
	private double pointsToDo;

	public double getPointsToDo() {
		return pointsToDo;
	}

	public void setPointsToDo(double pointsToDo) {
		this.pointsToDo = pointsToDo;
	}
	
	

}
