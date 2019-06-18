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
        name = "SprintsMapping",
        entities = {
        @EntityResult(
        		entityClass = Sprints.class,
               fields = {
        				@FieldResult(name = "time", column = "date_time"),
        				@FieldResult(name = "sprintName", column = "name")
        				})})
@NamedNativeQuery(name="getSprints",
resultSetMapping="SprintsMapping", 
query="SELECT date_trunc('hour', s.start_date) as date_time, s.name as name\n" + 
		"FROM jira_sprints AS s\n" + 
		"WHERE s.start_date > NOW() - interval '120 days'\n" + 
		"GROUP BY date_trunc('hour', s.start_date), s.name\n" + 
		"ORDER BY date_time DESC")
public class Sprints implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Sprints() {		
	}
	
	@Id
	private String sprintName;
	private Date time;

	public String getSprintName() {
		return sprintName;
	}
	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	 	

	

}
