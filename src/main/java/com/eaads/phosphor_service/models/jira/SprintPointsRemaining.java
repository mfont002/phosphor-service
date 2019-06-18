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
        name = "PointsRemainingMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintPointsRemaining.class,
               fields = {
        				@FieldResult(name = "pointsRemaining", column = "coalesce")
        				})})
@NamedNativeQuery(name="getPointsRemaining",
resultSetMapping="PointsRemainingMapping", 
query="SELECT coalesce(SUM(i.points), 0 )\n" + 
		"FROM jira_issues AS i\n" + 
		"INNER JOIN jira_sprints AS s ON s.id = i.sprint_id\n" + 
		"WHERE s.state = 'active' AND i.status != 'Completed'")
public class SprintPointsRemaining implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintPointsRemaining() {		
	}
	
	@Id
	private double pointsRemaining;

	public double getPointsRemaining() {
		return pointsRemaining;
	}

	public void setPointsRemaining(double pointsRemaining) {
		this.pointsRemaining = pointsRemaining;
	}

	


}
