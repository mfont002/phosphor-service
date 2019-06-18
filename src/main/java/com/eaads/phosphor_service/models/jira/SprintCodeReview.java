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
        name = "CodeReviewMapping",
        entities = {
        @EntityResult(
        		entityClass = SprintCodeReview.class,
               fields = {
        				@FieldResult(name = "codeReview", column = "code_review"),
        				})})
@NamedNativeQuery(name="getCodeReview",
resultSetMapping="CodeReviewMapping", 
query="SELECT SUM(points) AS code_review\n" + 
		"FROM jira_issues AS i \n" + 
		"INNER JOIN jira_sprints AS s ON s.id = i.sprint_id\n" + 
		"WHERE s.state = 'active' AND i.status = 'Code Review'")
public class SprintCodeReview implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SprintCodeReview() {		
	}
	
	@Id
	private double codeReview;

	public double getCodeReview() {
		return codeReview;
	}

	public void setCodeReview(double codeReview) {
		this.codeReview = codeReview;
	}

	

}
