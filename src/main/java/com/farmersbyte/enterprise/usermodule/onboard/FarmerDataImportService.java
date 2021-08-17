package com.farmersbyte.enterprise.usermodule.onboard;

import com.farmersbyte.enterprise.usermodule.model.Address;
import com.farmersbyte.enterprise.usermodule.model.Language;
import com.farmersbyte.enterprise.usermodule.user.User;
import com.farmersbyte.enterprise.usermodule.user.UserRepository;
import com.farmersbyte.enterprise.usermodule.user.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FarmerDataImportService {

    public static final String FARMERS_JSON = "data/farmers/FarmersData.json";
    public static final String CONTENT = "content";
    @Autowired private UserRepository userRepository;
    @Autowired private SetupLogService setupLogService;
    @Autowired private ResourceHelper resourceHelper;
    @Autowired private Hashing hashing;

    public void refreshData() {
        refreshFarmersData();
    }

    public void refreshFarmersData() {
        Map<String, Object> contentMap = resourceHelper.getFileDataAsMap(FARMERS_JSON);
        String currentHashValue = hashing.hashString(contentMap.toString());
        if (setupLogService.hashChanged(FARMERS_JSON, currentHashValue)) {
            List<FarmerImportDto> farmerImportDtos = getData(contentMap);
            String organizationId = (String)contentMap.get("organizationId");
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hash = passwordEncoder.encode("FarmersByte123!");
            List<User> users = farmerImportDtos.stream().map(importDto -> {
                User user = User.builder().build();
                user.setName(importDto.getName());
                user.setUserName(importDto.getPhone());
                user.setPwdHash(hash);
                user.setPreferredLanguage("en");
                user.setDisplayName(importDto.getFirstName());
                user.setOrganizationId(new ObjectId(importDto.getOrganizationId()));
                user.setId(UUID.fromString(importDto.getId()));
                user.setGender(importDto.getGender());
                user.setFamilyName(importDto.getLastName());
                user.setGivenName(importDto.getFirstName());
                user.setUserType(UserType.FARMER);
                user.setPhone(importDto.getPhone());
                Address address = new Address();

                address.setCountry("India");
                address.setMandal(importDto.getMandal());
                address.setVillage(importDto.getVillage());
                address.setDistrict(importDto.getDistrict());
                address.setState(importDto.getState());

//                address.setCountry(Language.builder().english("India").build());
//                address.setMandal(Language.builder().english(importDto.getMandal()).build());
//                address.setVillage(Language.builder().english(importDto.getVillage()).build());
//                address.setDistrict(Language.builder().english(importDto.getDistrict()).build());
//                address.setState(Language.builder().english(importDto.getState()).build());
                user.setAddressList(Arrays.asList(address));
                return user;
            }).collect(Collectors.toList());
            userRepository.deleteAllByOrganizationId(new ObjectId(organizationId));
            userRepository.saveAll(users);
            setupLogService.create(FARMERS_JSON, currentHashValue);
        }
    }

    private List<FarmerImportDto> getData(Map<String, Object> contentMap) {
        ObjectMapper mapper = new ObjectMapper();
        String organizationId = (String)contentMap.get("organizationId");
        return ((List<Object>) contentMap.get(CONTENT))
                .stream()
                .map(entityMap -> mapper.convertValue(entityMap, FarmerImportDto.class))
                .map(category -> {
                    category.setOrganizationId(organizationId);
                    return category;
                })
                .collect(Collectors.toList());
    }
}
