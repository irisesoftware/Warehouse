package in.irise.soft.util;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {

	public String genPwd() {
		return UUID.randomUUID()
				.toString()
				.replaceAll("-", "")
				.substring(0, 8);
	}
	
	public String getOtp() {
		return String.format(
				"%04d", 
				new Random().nextInt(10000)
				);
	}
}
