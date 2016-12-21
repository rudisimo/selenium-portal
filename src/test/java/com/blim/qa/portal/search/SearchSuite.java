package com.blim.qa.portal.search;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses(value = { Test.class })
public class SearchSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(SearchByTitleTest.class);
		return suite;
	}
}
