package com.eaads.phosphor_service.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
//import com.amazonaws.services.lambda.runtime.events.CloudFrontEvent.Request;
import com.eaads.phosphor_service.AppConfig;
import com.eaads.phosphor_service.http.Request;
import com.eaads.phosphor_service.http.Response;
import com.eaads.phosphor_service.repository.DataAccess;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DevOpsMetricsLambda implements RequestHandler<Request, Response> {
	private static final Logger logger = Logger.getLogger(DevOpsMetricsLambda.class);
	static AbstractApplicationContext cont = new AnnotationConfigApplicationContext(AppConfig.class);
	DataAccess da = new DataAccess();

	@SuppressWarnings("unchecked")
	public Response<Object> handleRequest(Request input, Context context) {
		Response<Object> res = new Response<Object>();
		try {
			Request req = input;
			HashMap<String, String> hm = new HashMap<String, String>();
			if (req.getHttpMethod().equals("GET")) {
				if (req.getPathParameters() != null) {
					String val = (String) req.getPathParameters().get("name");
					List<?> mod = da.queryResult(val);
					res.setBody(mod);
					hm.put("queryHeader", "pathParam");

				} else if (req.getQueryStringParameters() != null) {
					Integer val = (Integer) req.getQueryStringParameters().get("id");
					List<?> mod = da.queryResult(val.toString());
					res.setBody(mod);
					hm.put("queryHeader", "queryString");
				}
				res.setHeaders(hm);
				res.setStatusCode(200);
				res.bodyToString();
				res.setIsBase64Encoded(false);

			} else if ((req.getHttpMethod().equals("PUT"))) {
				if (req.getBody() != null) {
					da.updateModelResult(req.getBody());
				}
			} else if ((req.getHttpMethod().equals("POST"))) {
				if (req.getBody() != null) {
					da.createModelResult(req.getBody());
				}
			} else if ((req.getHttpMethod().equals("DELETE"))) {

			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return res;
	}
}
