package com.prueba.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 *
 * <p>Punto de entrada de la API REST de gestión de empleados y beneficios.
 * La anotación {@code @SpringBootApplication} habilita la autoconfiguración,
 * el escaneo de componentes y la configuración de la aplicación.
 */
@SpringBootApplication
public class BackendApplication {

	/**
	 * Método principal que arranca el contexto de Spring Boot.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
