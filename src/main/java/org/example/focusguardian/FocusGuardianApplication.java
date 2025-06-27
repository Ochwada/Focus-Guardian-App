package org.example.focusguardian;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Focus Guardian Spring Boot application.
 * This app helps users track and analyze their focus habits.
 */

/**
 * This class is responsible for bootstrapping the application context,
 * configuring environment variables from a `.env` file, and launching the embedded server.</p>
 *
 * <p>It uses the {@code dotenv-java} library to load environment variables such as
 * {@code DB_PASSWORD} for secure and flexible database connection configuration.</p>
 */
@SpringBootApplication
public class FocusGuardianApplication {

    static {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // Set all required secrets as system properties
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    public static void main(String[] args) {
        SpringApplication.run(FocusGuardianApplication.class, args);
    }
}