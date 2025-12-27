package com.s4r.ghorbari.web;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGeneratorTest {

    @Test
    public void generatePasswordHashes() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String[] passwords = {"111111", "222222", "333333", "444444"};

        System.out.println("\n=== BCrypt Password Hashes for Liquibase ===\n");

        for (String pwd : passwords) {
            String hash = encoder.encode(pwd);
            System.out.println("Password: " + pwd);
            System.out.println("Hash: " + hash);
            System.out.println();
        }

        System.out.println("Copy these hashes to: core/src/main/resources/db/changelog/changes/3-seed_users.xml\n");
    }
}
