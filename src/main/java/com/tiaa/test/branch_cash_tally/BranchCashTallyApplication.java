package com.tiaa.test.branch_cash_tally;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BranchCashTallyApplication implements CommandLineRunner {

	@Autowired
	private FileProcessor processor;

	public static void main(String[] args) {
		SpringApplication.run(BranchCashTallyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		processor.processInputFiles();

	}
}
