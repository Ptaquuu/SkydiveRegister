package pl.edu.wat.skydive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.wat.skydive.reflection.FieldInformation;
import pl.edu.wat.skydive.reflection.Reflection;

@SpringBootApplication
public class SkydiveRegisterApplication {

	public static void main(String[] args) throws Exception {
		FieldInformation fieldInformation = new FieldInformation();
		Reflection.apply(fieldInformation.getFieldName(),fieldInformation.getFieldType());
		SpringApplication.run(SkydiveRegisterApplication.class, args);
	}

}
