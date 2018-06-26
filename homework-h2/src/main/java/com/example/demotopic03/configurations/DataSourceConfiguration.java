package com.example.demotopic03.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean("dataSource")
    @Profile("postgres")
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("1234");
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/btb_class");

        return  driverManagerDataSource;
    }

    @Bean("dataSource")
    @Profile("h2")
    public DataSource inMemoryDB(){
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        embeddedDatabaseBuilder.addScript("db/schema.sql");
        //for insert data into tb_book
        embeddedDatabaseBuilder.addScript("db/data.sql");
        embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.H2);
        return embeddedDatabaseBuilder.build();
    }
}
