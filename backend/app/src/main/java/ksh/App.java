// TODO: global uncaught exception handler
package ksh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * main class of the backend
 */
@SpringBootApplication
class App {

	/**
	 * main method of the backend, which runs the Spring application
	 * 
	 * @param args commandline arguments (passed via gradle)
	 */
    public static void main(final String[] args) {
        SpringApplication.run(App.class, args);
    }
}
