package com.io.codetracker.application.user.port.out;

import java.io.IOException;

public interface CloudinaryPort {
    String uploadProfilePicture(byte[] file, String publicId) throws IOException;
    void deleteImageByPublicId(String publicId) throws IOException;
}