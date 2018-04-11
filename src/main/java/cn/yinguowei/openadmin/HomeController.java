package cn.yinguowei.openadmin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinguowei 2018/4/12
 */
@RestController
public class HomeController {
	@GetMapping("/")
	public String home() {
		return "Hello, OpenAdmin!";
	}
}
