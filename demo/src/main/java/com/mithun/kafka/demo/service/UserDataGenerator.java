package com.mithun.kafka.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class UserDataGenerator {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Random rnd = new Random();
    private final WebClient webClient;

    public UserDataGenerator(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8080")
                // No default auth/cookies/headers → "anonymous" requests
                .build();
    }

    // Runs every 30 seconds. Spring cron has 6 fields: second minute hour day-of-month month day-of-week
    // Adjust as needed (e.g., "0 */1 * * * *" = every minute at second 0)
    @Scheduled(cron = "*/5 * * * * *")
    public void sendRandomUser() {
        try {
            var requestBody = generateRandomUserJson();

            // Async version using WebClient
            log.info("Sending request to user creation endpoint asynchronously. Request Details : {}", requestBody);
            webClient.post()
                    .uri("/users/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody) // auto JSON via Jackson
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(response -> {
                        log.info("Successfully sent request to create user. Timestamp : {} Body: {}",LocalDateTime.now(), response);
                    })
                    .doOnError(err -> log.error("Error sending user data: ", err))
                    // Trigger the request
                    .onErrorResume(e -> Mono.empty())
                    .subscribe();

            //Sync version using java.net.http.HttpClient
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("http://localhost:8080/users/create"))
//                    .header("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString(generateRandomUserJson()))
//                    .build();
//
//            log.info("Sending request to user creation endpoint. Request Details {}", request.toString());
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            log.info("{} Status: {}, Body: {}",LocalDateTime.now(), response.statusCode(), response.body());
        } catch (Exception e) {
            log.error("Error occurred while sending random user event. Timestamp : {}, Exception Details: ", LocalDateTime.now(), e);
        }
    }

    private String generateRandomUserJson() {
        String userId = String.valueOf(10000000 + rnd.nextInt(89999999)); // 8 digits
        String name = randomName();
        String email = randomEmail(name);
        String gender = rnd.nextBoolean() ? "Male" : "Female";
        String phone1 = randomPhoneNL();
        String phone2 = randomPhoneNL();
        String address = randomAddress();

        // Manual JSON build (safe because we control values; no quoted embedded chars are generated)
        return String.format("""
            {
                "userId": "%s",
                "name": "%s",
                "email": "%s",
                "gender": "%s",
                "phoneNumbers": [
                    "%s",
                    "%s"
                ],
                "address": "%s"
            }
            """, userId, name, email, gender, phone1, phone2, address);
    }

    private String randomName() {
        String[] firstNames = {"Ralph", "Richard", "Benjamin", "Andrew", "Mark", "Mathew", "Peter", "Thomas", "Matt", "Kevin"};
        String[] lastNames  = {"Ben", "Remo", "Parkson", "Franklin", "Parker", "Anthony", "Chris", "Tom", "Lewis", "Brown"};
        String first = firstNames[rnd.nextInt(firstNames.length)];
        String last  = lastNames[rnd.nextInt(lastNames.length)];
        int suffix   = rnd.nextInt(100); // adds variability
        return first + " " + last + suffix;
    }

    private String randomEmail(String name) {
        String normalized = name.toLowerCase().replaceAll("[^a-z0-9]", "");
        String[] domains = {"gmail.com", "outlook.com", "yahoo.com", "proton.me"};
        String domain = domains[rnd.nextInt(domains.length)];
        int salt = 1000 + rnd.nextInt(9000);
        return normalized + salt + "@" + domain;
    }

    private String randomPhoneNL() {
        // NL mobile format: +316######## (8 digits after +316)
        long num = 10000000L + ThreadLocalRandom.current().nextLong(90000000L);
        return "+316" + num;
    }

    private String randomAddress() {
        String[] streets = {"Merelstraat", "Kerkstraat", "Stationsweg", "Laan van Meerdervoort", "Utrechtseweg", "Kanaleneiland", "Nieuwegracht", "Biltstraat"};
        String[] cities  = {"Utrecht", "Hilversum", "Amsterdam", "Rotterdam", "Den Haag", "Eindhoven"};
        String street = streets[rnd.nextInt(streets.length)];
        int houseNr   = 1 + rnd.nextInt(200);
        String city   = cities[rnd.nextInt(cities.length)];
        return String.format("%s, %d, %s", street, houseNr, city);
    }
}
