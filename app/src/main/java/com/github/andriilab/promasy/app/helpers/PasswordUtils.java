package com.github.andriilab.promasy.app.helpers;

import com.github.andriilab.promasy.commons.logger.ILogger;
import com.github.andriilab.promasy.commons.logger.ILoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {
    private ILogger logger;

    public PasswordUtils(ILoggerFactory loggerFactory) {
        this.logger = loggerFactory.getLogger(this.getClass());
    }

    public long makeSalt() {
        return new SecureRandom().nextLong();
    }

    public String makePass(char[] pass, long salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(new String(pass).getBytes(StandardCharsets.UTF_8));
            md.update(ByteBuffer.allocate(Long.BYTES).putLong(salt).array());
            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest()) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.warnEvent(e);
            return "";
        }
    }
}
