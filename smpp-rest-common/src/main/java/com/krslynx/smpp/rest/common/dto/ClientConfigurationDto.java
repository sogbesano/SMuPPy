package com.krslynx.smpp.rest.common.dto;

import com.krslynx.annotations.model.field.Default;
import com.krslynx.annotations.model.field.Optional;
import com.krslynx.annotations.model.field.Required;

public class ClientConfigurationDto implements BaseEntity {

    @Required public String name;
    @Required public String password;

    @Optional public String optional;

    @Default public String defaulted;

}
