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
        name = "BurnDownMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintBurndown.class,
               fields = {
        				@FieldResult(name = "time", column = "time"),
        				@FieldResult(name = "points", column = "Points")
        				})})
@NamedNativeQuery(name="getSprintBurndown",
resultSetMapping="BurnDownMapping", 
query="SELECT date_trunc('minute', b.timestamp) AS time,\n" + 
		"max(b.value) AS Points\n" + 
		"FROM\n" + 
		"jira_burndown AS b\n" + 
		"WHERE b.timestamp > NOW() - interval '25 days'\n" + 
		"GROUP BY time\n" + 
		"ORDER BY time DESC")
public class SprintBurndown implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintBurndown() {		
	}
	
	@Id
	private Date time;
	private double points;

	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}


	

}
