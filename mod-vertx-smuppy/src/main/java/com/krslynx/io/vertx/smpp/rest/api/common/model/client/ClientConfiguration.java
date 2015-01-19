package com.krslynx.io.vertx.smpp.rest.api.common.model.client;

import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.krslynx.io.vertx.smpp.rest.api.common.model.client.smpp.type.Address;
import com.krslynx.io.vertx.smpp.rest.api.common.model.client.smpp.type.BindType;
import com.krslynx.io.vertx.smpp.rest.api.common.model.client.smpp.type.LoggingOptions;
import com.krslynx.io.vertx.smpp.rest.api.common.model.ValidatedModel;

public class ClientConfiguration extends ValidatedModel {

    // TODO: SSL Support

    /**
     * Behavioural setting to set the name of the SMPP link.
     */
    public String name;

    /**
     * The hostname of the SMPP interface.
     */
    public String host;

    /**
     * The port of the SMPP interface.
     */
    public int port;

    /**
     * Connection timeout, if exceeded, the connection will be aborted.
     */
    public long connectTimeout = 10000l;

    /**
     * The SMPP System ID parameter.
     */
    public String systemId;

    /**
     * The SMPP password parameter.
     */
    public String password;

    /**
     * The SMPP System Type parameter.
     */
    public String systemType;

    /**
     * The address of the SMPP link, includes information about TON/NPI and MSISDN Address Range field.
     */
    public Address address = new Address();

    /**
     * The type of bind. Defaults to Transceiver.
     */
    public BindType bindType = BindType.TRANSCEIVER;

    /**
     * The Window Size, defaults to 1.
     */
    public int windowSize = SmppConstants.DEFAULT_WINDOW_SIZE;

    /**
     * The default interface version, defaults to 3.4
     */
    public byte interfaceVersion = SmppConstants.VERSION_3_4;

    /**
     * The bind timeout, defaults to 5000l.
     */
    public long bindTimeout = SmppConstants.DEFAULT_BIND_TIMEOUT;

    /**
     * Allows for PDUs and Bytes to be toggled for logging, significantly improves or degrades performance.
     */
    public LoggingOptions logOptions = new LoggingOptions();

    /**
     * The Window Wait Timeout, defaults to 60,000l
     */
    public long windowWaitTimeout = SmppConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;

    /* if > 0, then activated */

    /**
     * The Request Expiry Timeout, defaults to -1.
     */
    public long requestExpiryTimeout = SmppConstants.DEFAULT_REQUEST_EXPIRY_TIMEOUT;

    /**
     * The Window Monitor Interval, defaults to -1.
     */
    public long windowMonitorInterval = SmppConstants.DEFAULT_WINDOW_MONITOR_INTERVAL;

    /**
     * The Write Timeout, defaults to 0.
     */
    public long writeTimeout = SmppConstants.DEFAULT_WRITE_TIMEOUT;

    /**
     * Whether counters are enabled.
     */
    public boolean countersEnabled = false;

    public SmppSessionConfiguration getChSessionConfig() {

        SmppSessionConfiguration smppSessionConfiguration = new SmppSessionConfiguration();

        smppSessionConfiguration.setName(name);
        smppSessionConfiguration.setWindowSize(windowSize);

        switch (bindType) {
            case TRANSCEIVER:
                smppSessionConfiguration.setType(SmppBindType.TRANSCEIVER);
            case RECEIVER:
                smppSessionConfiguration.setType(SmppBindType.RECEIVER);
            case TRANSMITTER:
                smppSessionConfiguration.setType(SmppBindType.TRANSMITTER);
            default:
                smppSessionConfiguration.setType(SmppBindType.TRANSCEIVER);
        }

        smppSessionConfiguration.setSystemId(systemId);
        smppSessionConfiguration.setPassword(password);
        smppSessionConfiguration.setSystemType(systemType);
        smppSessionConfiguration.setInterfaceVersion(interfaceVersion);

        final com.cloudhopper.smpp.type.Address addrRange = new com.cloudhopper.smpp.type.Address();
        addrRange.setTon(this.address.ton);
        addrRange.setNpi(this.address.npi);
        addrRange.setAddress(this.address.address);

        smppSessionConfiguration.setAddressRange(addrRange);

        smppSessionConfiguration.setBindTimeout(bindTimeout);

        final com.cloudhopper.smpp.type.LoggingOptions loggingOptions = new com.cloudhopper.smpp.type.LoggingOptions();
        loggingOptions.setLogBytes(this.logOptions.logBytes);
        loggingOptions.setLogPdu(this.logOptions.logPdu);
        smppSessionConfiguration.setLoggingOptions(loggingOptions);

        smppSessionConfiguration.setWindowWaitTimeout(windowWaitTimeout);
        smppSessionConfiguration.setRequestExpiryTimeout(requestExpiryTimeout);
        smppSessionConfiguration.setWindowMonitorInterval(windowMonitorInterval);
        smppSessionConfiguration.setWriteTimeout(writeTimeout);
        smppSessionConfiguration.setCountersEnabled(countersEnabled);

        return smppSessionConfiguration;
    }

    @Override
    public boolean isValid() {

        validateField(name, "name");
        validateField(host, "host");
        validateField(port, "port");

        if(!(port >= 1 && port <= 65535)) {
            addError(CRITICAL_MSG + ", port range must be >= 1 and <= 65535");
            this.valid = false;
        }

        validateField(systemId, "systemId");
        validateField(systemType, "systemType");

        validateField(address, "address");

        /*
        TODO: decide how to present these errors/warnings to the end user
         */
        if(!address.isValid()) {
            this.valid = false;
        }

        return valid;
    }


}
