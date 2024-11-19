package com.mansa.user.Util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.*;
import java.util.Base64;

public class RSAKeyGenerator {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        saveKeyToFile("user-service/src/main/resources/keys/publicKey.key", publicKey,"PUBLIC");
        saveKeyToFile("user-service/src/main/resources/keys/privateKey.key", privateKey,"PRIVATE");
        System.out.println("Public Key (Base64):");
        System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        System.out.println("Private Key (Base64):");
        System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
    }
    public static void saveKeyToFile(String filename, Key key,String type) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {
            writer.write("-----BEGIN "+type+" KEY-----\n");
            writer.write(Base64.getEncoder().encodeToString(key.getEncoded()));
            writer.write("\n-----END "+type+" KEY-----\n");
        }
    }
}