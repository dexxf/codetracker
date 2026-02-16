package com.io.codetracker.infrastructure.classroom.id;

import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;
import com.io.codetracker.domain.classroom.service.CodeGenerator;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ClassCodeGenerator implements CodeGenerator {

    private static final String CHAR_POOL = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int RANDOM_PART_LENGTH = 6;
    private static final int TIME_SUFFIX_LENGTH = 3;
    private static final int CODE_LENGTH = RANDOM_PART_LENGTH + TIME_SUFFIX_LENGTH;
    private static final int MAX_ATTEMPTS = 1000;

    private final SecureRandom random;
    private final ClassroomDomainRepository repository;

    public ClassCodeGenerator(ClassroomDomainRepository repository,
                              @Qualifier("clSecureRandom") SecureRandom random) {
        this.repository = repository;
        this.random = random;
    }

    @Override
    public String generateCode() {
        int attempts = 0;
        String code;

        do {
            if (++attempts > MAX_ATTEMPTS) {
                throw new IllegalStateException(
                    "Unable to generate unique classroom code after " + MAX_ATTEMPTS + " attempts"
                );
            }
            code = generateRandomCode();
        } while (repository.existsByActiveCode(code));

        return code;
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }

        long seconds = System.currentTimeMillis() / 1000;
        sb.append(String.format("%0" + TIME_SUFFIX_LENGTH + "d", seconds % 1000));

        return sb.toString();
    }
}
