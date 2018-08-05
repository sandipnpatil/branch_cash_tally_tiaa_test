package com.tiaa.test.branch_cash_tally;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileProcessorTest {

	@Autowired
	private FileProcessor processor;

	@Test
	public void testProcessInputFiles() {
		processor.processInputFiles();

		File matchFile = new File("D:/output/match.json");
		File noMatchFile = new File("D:/output/notmatched.json");
		Assert.assertTrue(matchFile.exists());
		Assert.assertTrue(noMatchFile.exists());
	}

}
