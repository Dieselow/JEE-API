package fr.esgi.jee.api.authentication.domain;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

;

@Service
public class AuthenticationService {

    public String hash(String toHash) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(toHash.getBytes());
       return new String(messageDigest.digest());
    }
}
