package com.krslynx.io.vertx.smpp.rest.api.common.model.client.smpp.type;

import com.krslynx.io.vertx.smpp.rest.api.common.model.ValidatedModel;

public class Address extends ValidatedModel {

    /**
     * The Type of Number
     */
    public byte ton = -1;

    /**
     * The Numbering Plan Indicator
     */
    public byte npi = -1;

    /**
     * The address of mt/mo SMS
     */
    public String address = "441234567890";

    @Override
    public boolean isValid() {
        if(!(ton != -1 && npi != -1)) {
            this.addError(CRITICAL_MSG + ", please ensure TON and NPI are set.");
            this.valid = false;
        }

        validateField(address, "address");

        /*
        TODO: decide if this makes the model invalid
         */
        if(this.address.equalsIgnoreCase("441234567890")) {
            this.addWarning(WARNING_MSG + ", please ensure that you set the address to something other than the default.");
        }

        return this.valid;
    }
}
