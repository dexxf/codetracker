package com.io.codetracker.infrastructure.auth.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Converter
@Component
public class TokenEncryptionConverter implements AttributeConverter<String, String> {

    private final TextEncryptor encryptor;

    public TokenEncryptionConverter(
            @Value("${encryption.password}") String password,
            @Value("${encryption.salt}") String salt) {
        this.encryptor = Encryptors.text(password, salt);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        return encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return encryptor.decrypt(dbData);
    }
}
