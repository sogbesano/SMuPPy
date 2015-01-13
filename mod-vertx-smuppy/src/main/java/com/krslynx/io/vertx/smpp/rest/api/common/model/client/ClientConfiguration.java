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
    // SSL
//    private boolean ssl = false;
//    private SslConfiguration sslConfiguration = new SslConfiguration();

    public String name;

    // configuration settings
    private String host;
    private int port;
    private long connectTimeout = 10000l;

    public String systemId;
    public String password;
    public String systemType;

    public Address addressRange = new Address();

    public BindType bindType = BindType.TRANSCEIVER;
    public int windowSize = SmppConstants.DEFAULT_WINDOW_SIZE;
    public byte interfaceVersion = SmppConstants.VERSION_3_4;
    public long bindTimeout = SmppConstants.DEFAULT_BIND_TIMEOUT;

    // logging settings
    public LoggingOptions loggingOptions = new LoggingOptions();
    public long windowWaitTimeout = SmppConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;

    // if > 0, then activated
    public long requestExpiryTimeout = SmppConstants.DEFAULT_REQUEST_EXPIRY_TIMEOUT;
    public long windowMonitorInterval = SmppConstants.DEFAULT_WINDOW_MONITOR_INTERVAL;
    public long writeTimeout = SmppConstants.DEFAULT_WRITE_TIMEOUT;
    public boolean countersEnabled = false;

    public SmppSessionConfiguration getSmppSession() {

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
        addrRange.setTon(this.addressRange.ton);
        addrRange.setNpi(this.addressRange.npi);
        addrRange.setAddress(this.addressRange.address);

        smppSessionConfiguration.setAddressRange(addrRange);

        smppSessionConfiguration.setBindTimeout(bindTimeout);

        final com.cloudhopper.smpp.type.LoggingOptions loggingOptions = new com.cloudhopper.smpp.type.LoggingOptions();
        loggingOptions.setLogBytes(this.loggingOptions.logBytes);
        loggingOptions.setLogPdu(this.loggingOptions.logPdu);
        smppSessionConfiguration.setLoggingOptions(loggingOptions);

        smppSessionConfiguration.setWindowWaitTimeout(windowWaitTimeout);
        smppSessionConfiguration.setRequestExpiryTimeout(requestExpiryTimeout);
        smppSessionConfiguration.setWindowMonitorInterval(windowMonitorInterval);
        smppSessionConfiguration.setWriteTimeout(writeTimeout);
        smppSessionConfiguration.setCountersEnabled(countersEnabled);

        return smppSessionConfiguration;
    }
}
