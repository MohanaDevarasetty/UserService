//package com.farmersbyte.enterprise.usermodule.user.security;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.KeySpec;
//import java.util.ArrayList;
//import java.util.List;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.PBEKeySpec;
//
//import lombok.extern.slf4j.Slf4j;
//import org.passay.CharacterData;
//import org.passay.CharacterRule;
//import org.passay.EnglishCharacterData;
//import org.passay.LengthRule;
//import org.passay.PasswordData;
//import org.passay.PasswordGenerator;
//import org.passay.PasswordValidator;
//import org.passay.Rule;
//import org.passay.RuleResult;
//
//@Slf4j
//public class PasswordManager {
//
//    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+";
//    private static final String ERROR_GENERATING_RULE_FOR_SPECIAL_CHARACTERS =
//            "Error generating password rule for special characters";
//    private static final int SIZE = 12;
//
//    public static PasswordBase64Hash hashPassword(String password) {
//        try {
//            PasswordBase64Hash result = new PasswordBase64Hash();
//            byte[] salt = new byte[16];
//            SecureRandom random = new SecureRandom();
//            random.nextBytes(salt);
//            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            byte[] hash = factory.generateSecret(spec).getEncoded();
//            result.setHashValue(hash);
//            result.setSaltValue(salt);
//            return result;
//        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
//            log.error( "InvalidKeySpecException/NoSuchAlgorithmException during changePassword", ex);
//        }
//        return null;
//    }
//
//    public static boolean isInputPasswordCorrect(PasswordBase64Hash storedPassword, String plainTextPassword) {
//        try {
//            byte[] salt = storedPassword.getSaltValue();
//            byte[] hash = storedPassword.getHashValue();
//            KeySpec spec = new PBEKeySpec(plainTextPassword.toCharArray(), salt, 65536, 128);
//            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            byte[] testHash = factory.generateSecret(spec).getEncoded();
//            int diff = hash.length ^ testHash.length;
//            for (int i = 0; i < hash.length && i < testHash.length; i++) {
//                diff |= hash[i] ^ testHash[i];
//            }
//            return diff == 0;
//        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
//            log.error( "InvalidKeySpecException/NoSuchAlgorithmException during changePassword", ex);
//        }
//        return false;
//    }
//
//    public static List<CharacterRule> getDefaultPasswordPolicy() {
//        List<CharacterRule> result = new ArrayList<>();
//
//        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
//        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
//        lowerCaseRule.setNumberOfCharacters(2);
//        result.add(lowerCaseRule);
//
//        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
//        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
//        upperCaseRule.setNumberOfCharacters(2);
//
//        result.add(upperCaseRule);
//
//        CharacterData digitChars = EnglishCharacterData.Digit;
//        CharacterRule digitRule = new CharacterRule(digitChars);
//        digitRule.setNumberOfCharacters(2);
//
//        result.add(digitRule);
//
//        CharacterData specialChars =
//                new CharacterData() {
//                    public String getErrorCode() {
//                        return ERROR_GENERATING_RULE_FOR_SPECIAL_CHARACTERS;
//                    }
//
//                    public String getCharacters() {
//                        return SPECIAL_CHARACTERS;
//                    }
//                };
//        CharacterRule splCharRule = new CharacterRule(specialChars);
//        splCharRule.setNumberOfCharacters(2);
//
//        result.add(splCharRule);
//
//        return result;
//    }
//
//    public static String generatePassword() {
//        PasswordGenerator passwordGenerator = new PasswordGenerator();
//        return passwordGenerator.generatePassword(SIZE, getDefaultPasswordPolicy());
//    }
//
//    public static boolean validatePassword(String input) {
//        List<? extends Rule> rules = getDefaultPasswordPolicy();
//        PasswordValidator validator = new PasswordValidator(
//                        new LengthRule(12, 16), rules.get(0), rules.get(1), rules.get(2), rules.get(3));
//        RuleResult result = validator.validate(new PasswordData(input));
//        return result.isValid();
//    }
//
//    private PasswordManager() {}
//}
//
