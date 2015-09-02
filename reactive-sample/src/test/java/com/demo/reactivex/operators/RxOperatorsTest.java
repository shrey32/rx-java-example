package com.demo.reactivex.operators;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author shrey.sharma
 *
 */
public class RxOperatorsTest {
	
	@Test
	public void test() {
		String[] args = {};
		RxOperators.main(args);
		Assert.assertEquals("Hello", "Hello");
	}
}
