package org.example

import org.example.event.EventEntity
import org.example.event.EventRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.time.Clock

@SpringBootApplication
@EntityScan(basePackageClasses = [EventEntity::class])
@EnableJpaRepositories(basePackageClasses = [EventRepository::class])
class DemoApplication {
    @Bean
    fun clock(): Clock = Clock.systemUTC()
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    runApplication<DemoApplication>()
}
