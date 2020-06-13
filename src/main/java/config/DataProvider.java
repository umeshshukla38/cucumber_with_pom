package config;
import java.util.Arrays;
import java.util.List;

public class DataProvider {

    /**
	 * first position entered email is wrong & 2nd email is right one
	 * @return
	 */
	public List<String> emailList() {
		String email [] = {"abc@gmail.com", "abc@carrothr.com"};
		return Arrays.asList(email);
	}
    
}