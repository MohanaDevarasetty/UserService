//package com.farmersbyte.enterprise.usermodule.user.security;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Base64;
//import java.util.Optional;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class PasswordBase64Hash {
//
//    private byte[] hashValue;
//    private byte[] saltValue;
//
//    public static String serialize(PasswordBase64Hash input) {
//        String h = Base64.getEncoder().encodeToString(input.getHashValue());
//        String s = Base64.getEncoder().encodeToString(input.getSaltValue());
//        return h + ":" + s;
//    }
//
//    public static Optional<PasswordBase64Hash> deserialize(String input) {
//        String[] parts = input.split(":");
//        if (parts.length == 2) {
//            PasswordBase64Hash passwordBase64Hash = new PasswordBase64Hash();
//            passwordBase64Hash.setHashValue(Base64.getDecoder().decode(parts[0]));
//            passwordBase64Hash.setSaltValue(Base64.getDecoder().decode(parts[1]));
//            return Optional.of(passwordBase64Hash);
//        }
//        return Optional.empty();
//    }
//}
