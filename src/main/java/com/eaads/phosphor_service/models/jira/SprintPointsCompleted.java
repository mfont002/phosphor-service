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
        name = "CompletedPointsMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintPointsCompleted.class,
               fields = {
            		   @FieldResult(name = "completedPoints", column = "completed")
        				})})
@NamedNativeQuery(name="getCompletedPoints",
resultSetMapping="CompletedPointsMapping", 
query="SELECT SUM(points) AS completed\n" + 
		"FROM jira_issues AS i\n" + 
		"INNER JOIN jira_sprints AS s ON s.id = i.sprint_id\n" + 
		"WHERE s.state = 'active' AND i.status = 'Completed'")
public class SprintPointsCompleted implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintPointsCompleted() {		
	}
	
	@Id
	private double completedPoints;

	public double getCompletedPoints() {
		return completedPoints;
	}

	public void setCompletedPoints(double completedPoints) {
		this.completedPoints = completedPoints;
	}


}
