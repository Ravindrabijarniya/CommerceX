package com.ravindra.commercex;

import org.springframework.boot.SpringApplication;

public class TestCommercexApplication {

	public static void main(String[] args) {
		SpringApplication.from(CommercexApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
