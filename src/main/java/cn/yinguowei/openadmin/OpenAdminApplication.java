package cn.yinguowei.openadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinguowei 2018/4/12
 */
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class OpenAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenAdminApplication.class, args);
	}
}

