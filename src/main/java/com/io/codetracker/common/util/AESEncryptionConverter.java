package com.io.codetracker.common.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
@Component
public class AESEncryptionConverter implements AttributeConverter<String,String>{

   private final TextEncryptor encryptor;

    public AESEncryptionConverter(
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
