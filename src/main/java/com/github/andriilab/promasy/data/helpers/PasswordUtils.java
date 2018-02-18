package com.github.andriilab.promasy.data.helpers;

import com.github.andriilab.promasy.data.controller.Logger;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.presentation.commons.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {
    public static long makeSalt() {
        return new SecureRandom().nextLong();
    }

    public static String makePass(char[] pass, long salt) {
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
            Logger.warnEvent(Utils.class, e);
            return EmptyModel.STRING;
        }
    }
}
