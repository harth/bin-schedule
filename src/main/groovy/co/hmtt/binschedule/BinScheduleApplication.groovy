package co.hmtt.binschedule

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BinScheduleApplication {

    static void main(String[] args) {
        SpringApplication.run(BinScheduleApplication, args)
    }

}
