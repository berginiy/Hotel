package com.hotel.hotel_management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db",
		"spring.jpa.hibernate.ddl-auto=none"
})
class HotelManagementApplicationTests {

	@Test
	void contextLoads() {
	}

}