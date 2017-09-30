package com.wisdom.user.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
public class ConfigConstant {

    @Value("${config.loginTokenPeriod}")
    public String loginTokenPeriod;

    @Value("${config.practitionerDomain}")
    public String practitionerDomain;

    @Value("${config.elderVersion}")
    public String elderVersion;

    @Value("${config.practitionerVersion}")
    public String practitionerVersion;

    public String getLoginTokenPeriod() {
        return loginTokenPeriod;
    }

    public void setLoginTokenPeriod(String loginTokenPeriod) {
        this.loginTokenPeriod = loginTokenPeriod;
    }

    public String getPractitionerDomain() {
        return practitionerDomain;
    }

    public void setPractitionerDomain(String practitionerDomain) {
        this.practitionerDomain = practitionerDomain;
    }

    public String getElderVersion() {
        return elderVersion;
    }

    public void setElderVersion(String elderVersion) {
        this.elderVersion = elderVersion;
    }

    public String getPractitionerVersion() {
        return practitionerVersion;
    }

    public void setPractitionerVersion(String practitionerVersion) {
        this.practitionerVersion = practitionerVersion;
    }
}
