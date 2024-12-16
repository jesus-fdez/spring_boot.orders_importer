package importation.shared.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("importation.*")
@EnableJpaRepositories("importation.*")
@EntityScan("importation.*")
@SpringBootApplication
public class EspublicoApplication
{
    public static void main(String[] args)
    {
	SpringApplication.run(EspublicoApplication.class, args);
    }
}
