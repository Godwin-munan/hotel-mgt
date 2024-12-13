package com.munan.hotelmgt;

import com.munan.hotelmgt.config.RsaKeyProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;


@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger Demo", version = "1.0", description = "Documentation APIs v1.0"),
		servers = {@Server(url = "http://localhost:8080", description = "Local Server URL")})
public class HotelMgtApplication {

	private static final Logger log = LoggerFactory.getLogger(HotelMgtApplication.class);

	private final Environment env;

	public HotelMgtApplication(Environment env) {
		this.env = env;
	}

	public static void main(String[] args) {
		SpringApplication.run(HotelMgtApplication.class, args);

//		FastDateFormat dateFormat = FastDateFormat.getInstance("yyy-MM-dd'T'HH:mm:ss");
//
//		LocalDateTime date = LocalDateTime.parse("2023-01-09T02:37:31");

//		try{
//
//			Date date_ = dateFormat.parse("F2023-01-09T02:37:31");
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//
//
//		System.out.println("DATE "+date);
	}

}
