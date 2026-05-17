package co.edu.uco.aurora.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication // Quitamos los excludes para que Spring Security funcione con todo su poder nativo
@ComponentScan(basePackages = "co.edu.uco.aurora") // Obligamos a Spring a escanear TODOS los paquetes del proyecto
public class AuroraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuroraApplication.class, args);
	}
}