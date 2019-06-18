package com.eaads.phosphor_service.lambdas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.eaads.phosphor_service.AppConfig;
import com.eaads.phosphor_service.http.Request;
import com.eaads.phosphor_service.http.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class DevOpsMetricsLambdaTest {
	
	DevOpsMetricsLambda lambda;
	
	@Before
	public void init() {
		lambda = new DevOpsMetricsLambda();
	
	}
	
	
	@Ignore
	@Test
	public void testResp() {
		List<String> commits = new ArrayList<String>();		
		commits.add("tester");		
		Response<List<String>> res = new Response<List<String>>();	
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("x-custom-header", "Githubcommits");
		hm.put("content-type", "application/json");
		
		res.setHeaders(hm);
		res.setStatusCode(200);
		res.setBody(commits);
		res.bodyToString();
		res.setIsBase64Encoded(false);
		
		System.out.println(res.toString());
	}
	
	
	@Ignore
	@Test
	public void testTopCommmitter() throws Exception {
		Request req = new Request();
	
		Map<String, String> pathParams = new HashMap<String, String>();	
		pathParams.put("name", "topcommitter");
		
		req.setPathParameters(pathParams);
		req.setHttpMethod("GET");
		
		Response<Object> res = lambda.handleRequest(req, null);
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(res);
		
		System.out.println(result);
		
	}
	
	@Ignore
	@Test
	public void testSprintBurndown() throws Exception {
		Request req = new Request();
	
		Map<String, String> pathParams = new HashMap<String, String>();	
		pathParams.put("name", "sprintburndown");
		
		req.setPathParameters(pathParams);
		req.setHttpMethod("GET");
		
		Response<Object> res = lambda.handleRequest(req, null);
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(res);
		
		System.out.println(result);
		
	}
	

}
