package com.deng.mall.utils;

import com.deng.common.utils.String2DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class WebAppConfig {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public  void initEditableVaildation(){
        ConfigurableWebBindingInitializer initializer= (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService()!=null){
            GenericConversionService genericConversionService= (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new String2DateConverter());
        }
    }

}
