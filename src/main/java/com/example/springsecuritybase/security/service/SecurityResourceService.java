package com.example.springsecuritybase.security.service;

import com.example.springsecuritybase.domain.entity.Resources;
import com.example.springsecuritybase.repository.ResourcesRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityResourceService {

    private ResourcesRepository resourcesRepository;

    public SecurityResourceService(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllResources();

        resourcesList.forEach(re -> { //모든 리소스

            List<ConfigAttribute> configAttributeList = new ArrayList<>();

            re.getRoleSet().forEach(role -> { //리소스마다 매칭되는 권한들 가져오기
                configAttributeList.add(new SecurityConfig(role.getRoleName())); //SecurityConfig : ConfigAttribute 구현체
                result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList); //AntPathRequestMatcher : RequestMatcher 구현체
            });

        });
        return result;
    }
}
