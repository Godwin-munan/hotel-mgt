package com.munan.hotelmgt;

import com.munan.hotelmgt.config.RsaKeyProperties;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class HotelMgtApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelMgtApplication.class, args);

//		FastDateFormat dateFormat = FastDateFormat.getInstance("yyy-MM-dd'T'HH:mm:ss");
//
//		LocalDateTime date = LocalDateTime.parse("2023-01-09T02:37:31");

//		try{
//
//			Date date_ = dateFormat.parse("2023-01-09T02:37:31");
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//
//
//		System.out.println("DATE "+date);
	}

}
