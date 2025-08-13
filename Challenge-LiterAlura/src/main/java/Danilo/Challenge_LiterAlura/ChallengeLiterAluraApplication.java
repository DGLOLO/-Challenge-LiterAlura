package Danilo.Challenge_LiterAlura;

import Danilo.Challenge_LiterAlura.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://gutendex.com/books");
		System.out.println(json);
	}
}



