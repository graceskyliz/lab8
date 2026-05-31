package com.example.reward.reward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.reward")
public class RewardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RewardServiceApplication.class, args);
    }
}
