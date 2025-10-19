package com.microservice.product.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.sql.Connection;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
    private final DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        var resource = new ClassPathResource("db/seeder/V1_dummy_data.sql");
        if (!resource.exists()) {
            System.out.println("⚠️ Seeder SQL not found, skipping seeder.");
            return;
        }

        System.out.println("Executing seeder from: " + resource.getURL());
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, resource);
            System.out.println("✅ Seeder executed successfully!");
        } catch (Exception e) {
            System.err.println("❌ Failed to execute seeder: " + e.getMessage());
        }
    }

}
