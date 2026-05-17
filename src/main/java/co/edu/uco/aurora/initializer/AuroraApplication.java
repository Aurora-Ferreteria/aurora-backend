package co.edu.uco.aurora.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // <-- Agregar import

@SpringBootApplication
@ComponentScan(basePackages = "co.edu.uco.aurora")
// 1. Obliga a Spring a escanear tus interfaces JpaRepository en esa subcarpeta
@EnableJpaRepositories(basePackages = "co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa")
// 2. Obliga a Spring a mapear las tablas/entidades que están dentro de la subcarpeta entity
@EntityScan(basePackages = "co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity")
public class AuroraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuroraApplication.class, args);
	}
}
