//package com.farmersbyte.enterprise.usermodule.user.security;
//
//import com.farmersbyte.enterprise.usermodule.user.User;
//
//import java.util.Optional;
//
//public class PasswordValidator {
//    public boolean isUserPasswordCorrect(String authHeader, User user) {
//        //TODO
//        Optional<PasswordBase64Hash> hash = PasswordBase64Hash.deserialize(user.getPwdHash());
////        return hash.isPresent() && PasswordManager.isInputPasswordCorrect(
////                hash.get(), AuthUtils.decodeAuthHeader(authHeader).get(AuthUtils.CRED));
//        return false;
//    }
//}
