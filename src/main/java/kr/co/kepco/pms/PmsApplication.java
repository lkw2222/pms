package kr.co.kepco.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmsApplication.class, args);
	}

}
