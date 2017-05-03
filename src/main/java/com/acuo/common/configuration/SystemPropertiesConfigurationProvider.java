package com.acuo.common.configuration;

import com.acuo.common.app.AppId;
import com.acuo.common.app.Configuration;
import com.acuo.common.app.Environment;

import javax.inject.Provider;

import static com.acuo.common.util.PropertiesHelper.ACUO_CONFIG_APPID;
import static com.acuo.common.util.PropertiesHelper.ACUO_CONFIG_ENV;


public class SystemPropertiesConfigurationProvider implements Provider<Configuration> {

    @Override
    public Configuration get() {
        String id = System.getProperty(ACUO_CONFIG_APPID);
        String env = System.getProperty(ACUO_CONFIG_ENV);
        AppId appId = AppId.of(id);
        return Configuration.builder(appId).with(Environment.lookup(env)).build();
    }
}