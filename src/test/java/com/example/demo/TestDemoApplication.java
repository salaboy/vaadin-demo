package com.example.demo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.SpringApplication;

public class TestDemoApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.from(DemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
