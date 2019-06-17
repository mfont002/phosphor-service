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
        name = "StoryPointVelocityMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintStoryPointVelocity.class,
               fields = {
        				@FieldResult(name = "pointVelocity", column = "coalesce")
        				})})
@NamedNativeQuery(name="getStoryPointVelocity",
resultSetMapping="StoryPointVelocityMapping", 
query="SELECT\n" + 
		"(SELECT coalesce(SUM(i.points), 0 )\n" + 
		" FROM jira_issues AS i\n" + 
		" INNER JOIN jira_sprints AS s ON s.id = i.sprint_id\n" + 
		" WHERE s.state = 'active' AND i.status = 'Completed' \n" + 
		" AND i.resolution_date \n" + 
		" BETWEEN CURRENT_DATE - 2 AND CURRENT_DATE - 1) + \n" + 
		" (SELECT coalesce(SUM(i.points), 0 )\n" + 
		"  FROM jira_issues AS i\n" + 
		"  INNER JOIN jira_sprints AS s ON s.id = i.sprint_id\n" + 
		"  WHERE s.state = 'active' AND i.status = 'Completed' \n" + 
		"  AND i.resolution_date > CURRENT_DATE - 1) / 2 AS velocity")
public class SprintStoryPointVelocity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintStoryPointVelocity() {		
	}
	
	@Id
	private double pointVelocity;

	public double getPointVelocity() {
		return pointVelocity;
	}

	public void setPointVelocity(double pointVelocity) {
		this.pointVelocity = pointVelocity;
	}
	

	

}
