package com.eaads.phosphor_service.utils;

import org.apache.log4j.Logger;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersRequest;

public class SSMParams {
	private static final Logger logger = Logger.getLogger(SSMParams.class);
	
	private String dbName;
	private String dbUser;
	private String dbPass;
	private String dbServer;
	private String dbPort;

	public SSMParams() {
		dbName = System.getProperty("PHOSPHOR_v1_DB", getParametersFromSSMByName("PHOSPHOR_v1_DB"));
		dbUser = System.getProperty("PHOSPHOR_v1_DB_USER", getParametersFromSSMByName("PHOSPHOR_v1_DB_USER"));
		dbPass = System.getProperty("PHOSPHOR_v1_DB_PASSWORD", getParametersFromSSMByName("PHOSPHOR_v1_DB_PASSWORD"));
		dbServer = System.getProperty("PHOSPHOR_v1_DB_SERVER", getParametersFromSSMByName("PHOSPHOR_v1_DB_SERVER"));
		dbPort = System.getProperty("PHOSPHOR_v1_DB_PORT", getParametersFromSSMByName("PHOSPHOR_v1_DB_PORT"));
	}

	public SSMParams(String dbName, String dbUser, String dbPass, String dbServer) {
		this.dbName = dbName;
		this.dbName = dbUser;
		this.dbPass = dbPass;
		this.dbServer = dbServer;
	}

	public String getParametersFromSSMByName(String parameterKeys) {
		try {
			AWSSimpleSystemsManagement simpleSystemsManagementClient = AWSSimpleSystemsManagementClientBuilder
					.standard().build();
			GetParametersRequest parameterRequests = new GetParametersRequest();
			parameterRequests.withNames(parameterKeys).setWithDecryption(Boolean.valueOf(true));
			return simpleSystemsManagementClient.getParameters(parameterRequests).getParameters().get(0).getValue();

		} catch (Exception e) {
			logger.debug(e.getMessage());
			return e.toString();
		}
	}

	public String getParameterFromSSMByName(String parameterKey) {
		try {
			AWSSimpleSystemsManagement simpleSystemsManagementClient = AWSSimpleSystemsManagementClientBuilder
					.standard().withCredentials(new ProfileCredentialsProvider()).build();
			GetParameterRequest parameterRequest = new GetParameterRequest();
			parameterRequest.withName(parameterKey).setWithDecryption(Boolean.valueOf(true));
			GetParameterResult parameterResult = simpleSystemsManagementClient.getParameter(parameterRequest);

			return parameterResult.getParameter().getValue();

		} catch (Exception e) {
			logger.debug(e.getMessage());
			return e.toString();
		}
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPass() {
		return dbPass;
	}

	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}

	public String getDbServer() {
		return dbServer;
	}

	public void setDbServer(String dbServer) {
		this.dbServer = dbServer;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

}
