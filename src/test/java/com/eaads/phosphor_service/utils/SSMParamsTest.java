package com.eaads.phosphor_service.utils;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class SSMParamsTest {

	@Ignore
	@Test
	public void testParams() {
		SSMParams ssmtest = new SSMParams();
		assertNotNull("no value for db server", ssmtest.getDbServer());
		assertNotNull("no value for db port", ssmtest.getDbPort());
		System.out.println(ssmtest.getDbPort());
		
	}

}
