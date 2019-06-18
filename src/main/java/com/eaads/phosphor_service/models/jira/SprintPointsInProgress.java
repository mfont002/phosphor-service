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
        name = "PointsInProgressMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintPointsInProgress.class,
               fields = {
        				@FieldResult(name = "pointsInProgress", column = "in_progress")
        				})})
@NamedNativeQuery(name="getPointsInProgress",
resultSetMapping="PointsInProgressMapping", 
query="SELECT SUM(points) AS in_progress\n" + 
		"FROM jira_issues AS i\n" + 
		"INNER JOIN jira_sprints AS s ON s.id = i.sprint_id\n" + 
		"WHERE s.state = 'active' AND i.status = 'In Progress'")
public class SprintPointsInProgress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintPointsInProgress() {		
	}
	
	@Id
	private double pointsInProgress;

	public double getPointsInProgress() {
		return pointsInProgress;
	}

	public void setPointsInProgress(double pointsInProgress) {
		this.pointsInProgress = pointsInProgress;
	}


	

}
