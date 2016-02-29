package com.dataup.finance.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dataup.finance.util.PrimaryByRedis;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext*.xml")
public class AppTest {
	@Autowired
	private PrimaryByRedis primaryByRedis;
	
	@Test
	public void test() {
		System.out.println(primaryByRedis.generateEcode());
	}
}
