package ch.ase21.backend;

import ch.ase21.backend.startup.Scores;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

  public static void main(String[] args){
    SpringApplication.run(BackendApplication.class, args);
    Scores.computeScores();
  }

  @Bean
  public WebMvcConfigurer configurer(){
    return new WebMvcConfigurer(){
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/graphql")
            .allowedOrigins("*");
      }
    };
  }
}
