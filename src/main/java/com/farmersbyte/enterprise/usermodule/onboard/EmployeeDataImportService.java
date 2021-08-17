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
public class EmployeeDataImportService {

    public static final String DATA_JSON = "data/employees/FPOEmployeeData.json";
    public static final String CONTENT = "content";
    @Autowired private UserRepository userRepository;
    @Autowired private SetupLogService setupLogService;
    @Autowired private ResourceHelper resourceHelper;
    @Autowired private Hashing hashing;

    public void refreshData() {
        refreshEmployeeData();
    }

    public void refreshEmployeeData() {
        Map<String, Object> contentMap = resourceHelper.getFileDataAsMap(DATA_JSON);
        String currentHashValue = hashing.hashString(contentMap.toString());
        if (setupLogService.hashChanged(DATA_JSON, currentHashValue)) {
            List<EmployeeImportDto> farmerImportDtos = getData(contentMap);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hash = passwordEncoder.encode("FarmersByte123!");
            List<User> users = farmerImportDtos.stream().map(importDto -> {
                User user = User.builder().build();
                user.setName(importDto.getName());
                user.setUserName(importDto.getUserName());
                user.setPwdHash(hash);
                user.setPreferredLanguage("en");
                user.setDisplayName(importDto.getFirstName());
                user.setOrganizationId(new ObjectId(importDto.getOrganizationId()));
                user.setId(UUID.fromString(importDto.getId()));
                user.setGender(importDto.getGender());
                user.setUserType(UserType.EMPLOYEE);
                user.setFamilyName(importDto.getLastName());
                user.setGivenName(importDto.getFirstName());
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
            userRepository.deleteAll(users);
            userRepository.saveAll(users);
            setupLogService.create(DATA_JSON, currentHashValue);
        }
    }

    private List<EmployeeImportDto> getData(Map<String, Object> contentMap) {
        ObjectMapper mapper = new ObjectMapper();
        String organizationId = (String)contentMap.get("organizationId");
        return ((List<Object>) contentMap.get(CONTENT))
                .stream()
                .map(entityMap -> mapper.convertValue(entityMap, EmployeeImportDto.class))
                .map(category -> {
                    category.setOrganizationId(organizationId);
                    return category;
                })
                .collect(Collectors.toList());
    }
}
