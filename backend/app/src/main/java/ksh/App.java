// TODO: global uncaught exception handler
package ksh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class App {
    // public String getGreeting() {
    //     return "Hello World!";
    // }

    public static void main(final String[] args) {
        // System.out.println(new App().getGreeting());
        SpringApplication.run(App.class, args);
    }
}
