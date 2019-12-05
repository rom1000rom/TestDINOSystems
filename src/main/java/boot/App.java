package boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**Класс представляет собой java-конфигурацию Spring Context а также точку входа
 * в Spring Boot приложение.
 @author Артемьев Р.А.
 @version 02.12.2019 */
@SpringBootApplication
public class App implements WebMvcConfigurer
{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }

}