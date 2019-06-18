package com.eaads.phosphor_service.repository;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import com.eaads.phosphor_service.AppConfig;
import com.eaads.phosphor_service.models.github.CommitsByRepo;
import com.eaads.phosphor_service.models.github.EaadsTeamCommits;
import com.eaads.phosphor_service.models.github.TopCommitter;
import com.eaads.phosphor_service.models.jira.SprintBurndown;
import com.eaads.phosphor_service.models.jira.SprintCodeReview;
import com.eaads.phosphor_service.models.jira.SprintPointsCompleted;
import com.eaads.phosphor_service.models.jira.SprintPointsInProgress;
import com.eaads.phosphor_service.models.jira.SprintPointsRemaining;
import com.eaads.phosphor_service.models.jira.SprintPointsToDo;
import com.eaads.phosphor_service.models.jira.SprintRecommendedTargetVelocity;
import com.eaads.phosphor_service.models.jira.SprintStoryPointVelocity;
import com.eaads.phosphor_service.models.jira.SprintTopPointResolvers;
import com.eaads.phosphor_service.models.jira.Sprints;

public class DataAccess {

	static AbstractApplicationContext cont = new AnnotationConfigApplicationContext(AppConfig.class);
	static EntityManagerFactory em = cont.getBean(EntityManagerFactory.class);

	public DataAccess() {
	}

	public List<?> queryResult(String val) {
		List<?> result = null;
		
		if (val.equals("repocommits")) {
			Query q = em.createEntityManager().createNamedQuery("getCommitsByRepo", CommitsByRepo.class);
			result = q.getResultList();
		} else if (val.equals("topcommitter")) {
			Query q = em.createEntityManager().createNamedQuery("getTopCommitter", TopCommitter.class);
			result = q.getResultList();
		} else if (val.equals("eaadscommits")) {
			Query q = em.createEntityManager().createNamedQuery("getEaadsCommits", EaadsTeamCommits.class);
			result = q.getResultList();
		} else if (val.equals("sprintburndown")) {
			Query q = em.createEntityManager().createNamedQuery("getSprintBurndown", SprintBurndown.class);
			result = q.getResultList();
		} else if (val.equals("codereview")) {
			Query q = em.createEntityManager().createNamedQuery("getCodeReview", SprintCodeReview.class);
			result = q.getResultList();
		} else if (val.equals("pointscompleted")) {
			Query q = em.createEntityManager().createNamedQuery("getPointsCompleted", SprintPointsCompleted.class);
			result = q.getResultList();
		} else if (val.equals("pointsinprogress")) {
			Query q = em.createEntityManager().createNamedQuery("getPointsInProgress", SprintPointsInProgress.class);
			result = q.getResultList();
		} else if (val.equals("pointsremaining")) {
			Query q = em.createEntityManager().createNamedQuery("getPointsRemaining", SprintPointsRemaining.class);
			result = q.getResultList();
		} else if (val.equals("pointstodo")) {
			Query q = em.createEntityManager().createNamedQuery("getPointsToDo", SprintPointsToDo.class);
			result = q.getResultList();
		} else if (val.equals("targetvelocity")) {
			Query q = em.createEntityManager().createNamedQuery("getTargetVelocity", SprintRecommendedTargetVelocity.class);
			result = q.getResultList();
		} else if (val.equals("sprints")) {
			Query q = em.createEntityManager().createNamedQuery("getSprints", Sprints.class);
			result = q.getResultList();
		} else if (val.equals("pointvelocity")) {
			Query q = em.createEntityManager().createNamedQuery("getStoryPointVelocity", SprintStoryPointVelocity.class);
			result = q.getResultList();
		} else if (val.equals("topresolvers")) {
			Query q = em.createEntityManager().createNamedQuery("getTopPointResolver", SprintTopPointResolvers.class);
			result = q.getResultList();
		}

		return result;
	}

	public <T> boolean createModelResult(T mod) {
		Boolean res = false;
		return res;
	}
	
	public <T> boolean updateModelResult(T mod) {
		Boolean res = false;
		return res;
	}
}
