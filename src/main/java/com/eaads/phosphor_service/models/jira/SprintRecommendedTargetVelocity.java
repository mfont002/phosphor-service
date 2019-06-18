package com.eaads.phosphor_service.models.jira;

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
        name = "TargetVelocityMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintRecommendedTargetVelocity.class,
               fields = {
        				@FieldResult(name = "time", column = "time"),
        				@FieldResult(name = "points", column = "Points")
        				})})
@NamedNativeQuery(name="getTargetVelocity",
resultSetMapping="TargetVelocityMapping", 
query="SELECT date_trunc('minute', b.timestamp) AS time,\n" + 
		"max(b.value) AS Points\n" + 
		"FROM\n" + 
		"jira_burndown AS b\n" + 
		"WHERE b.timestamp > NOW() - interval '7 days'\n" + 
		"GROUP BY time\n" + 
		"ORDER BY time DESC")
public class SprintRecommendedTargetVelocity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintRecommendedTargetVelocity() {		
	}
	
	@Id
	private double points;
	private Date time;

	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	 	
	

}
