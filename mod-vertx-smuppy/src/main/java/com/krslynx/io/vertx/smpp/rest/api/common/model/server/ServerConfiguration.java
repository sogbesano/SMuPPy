package com.krslynx.io.vertx.smpp.rest.api.common.model.server;

import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppServerConfiguration;
import com.krslynx.io.vertx.smpp.rest.api.common.model.ValidatedModel;

public class ServerConfiguration extends ValidatedModel {

    // TODO: SSL

    /**
     * The name to identify the SMPP server
     */
    public String name = "SMuPPyServer";

    /**
     * The port of the SMPP server
     */
    public int port = 2775;

    /**
     * Bind timeout, if exceeded, binds will be aborted.
     */
    public long bindTimeout = 5000l;

    /**
     * The SMPP System ID parameter.
     */
    public String systemId = "smuppyserv";

    /**
     * if true, <= 3.3 for interface version normalizes to version 3.3, >= 3.4 normalizes to version 3.4
     */
    public boolean autoNegotiateInterfaceVersion = true;

    /**
     * The interface version, defaults to 3.4
     */
    public byte interfaceVersion = SmppConstants.VERSION_3_4;

    /**
     * The max connection size, or number of connections/sessions this server will expect to handle. Corresponds
     * to the number of worker threads handling reading data from sockets, defaults to 100
     */
    public int maxConnectionSize = SmppConstants.DEFAULT_SERVER_MAX_CONNECTION_SIZE;

    /**
     * Whether or not Non-Blocking Sockets are enabled, defaults to true
     */
    public boolean nonBlockingSocketsEnabled = SmppConstants.DEFAULT_SERVER_NON_BLOCKING_SOCKETS_ENABLED;

    /**
     * Whether or not to reuse the address, defaults to true
     */
    public boolean reuseAddress = SmppConstants.DEFAULT_SERVER_REUSE_ADDRESS;

    // TODO: Add JMX support

    /**
     * The default window size, defaults to 1
     */
    public int defaultWindowSize = SmppConstants.DEFAULT_WINDOW_SIZE;

    /**
     * The default window wait timeout, defaults to 60,000l
     */
    public long defaultWindowWaitTimeout = SmppConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;

    /**
     * The default request expiry timeout, defaults to -1
     */
    public long defaultRequestExpiryTimeout = SmppConstants.DEFAULT_REQUEST_EXPIRY_TIMEOUT;

    /**
     * The default window monitor interval, defaults to -1
     */
    public long defaultWindowMonitorInternal = SmppConstants.DEFAULT_WINDOW_MONITOR_INTERVAL;

    /**
     * Whether session counters are enabled
     */
    public boolean defaultSessionCountersEnabled = false;

    public SmppServerConfiguration getChServerConfig() {

        SmppServerConfiguration smppServerConfiguration = new SmppServerConfiguration();

        smppServerConfiguration.setName(this.name);
        smppServerConfiguration.setPort(this.port);
        smppServerConfiguration.setBindTimeout(this.bindTimeout);
        smppServerConfiguration.setSystemId(this.systemId);
        smppServerConfiguration.setAutoNegotiateInterfaceVersion(this.autoNegotiateInterfaceVersion);
        smppServerConfiguration.setInterfaceVersion(this.interfaceVersion);
        smppServerConfiguration.setMaxConnectionSize(this.maxConnectionSize);
        smppServerConfiguration.setNonBlockingSocketsEnabled(this.nonBlockingSocketsEnabled);
        smppServerConfiguration.setReuseAddress(this.reuseAddress);
        smppServerConfiguration.setDefaultWindowSize(this.defaultWindowSize);
        smppServerConfiguration.setDefaultWindowWaitTimeout(this.defaultWindowWaitTimeout);
        smppServerConfiguration.setDefaultRequestExpiryTimeout(this.defaultRequestExpiryTimeout);
        smppServerConfiguration.setDefaultWindowMonitorInterval(this.defaultWindowMonitorInternal);
        smppServerConfiguration.setDefaultSessionCountersEnabled(this.defaultSessionCountersEnabled);

        return smppServerConfiguration;
    }

    @Override
    public boolean isValid() {

        validateField(name, "name");
        validateField(port, "port");

        if(!(port >= 1 && port <= 65535)) {
            addError(CRITICAL_MSG + ", port range must be >= 1 and <= 65535");
            this.valid = false;
        }

        validateField(bindTimeout, "bindTimeout");
        validateField(systemId, "systemId");
        validateField(autoNegotiateInterfaceVersion, "autoNegotiateInterfaceVersion");
        validateField(interfaceVersion, "interfaceVersion");
        validateField(maxConnectionSize, "maxConnectionSize");
        validateField(nonBlockingSocketsEnabled, "nonBlockingSocketsEnabled");
        validateField(reuseAddress, "reuseAddress");
        validateField(defaultWindowSize, "defaultWindowSize");
        validateField(defaultWindowWaitTimeout, "defaultWindowWaitTimeout");
        validateField(defaultRequestExpiryTimeout, "defaultRequestExpiryTimeout");
        validateField(defaultWindowMonitorInternal, "defaultWindowMonitorInternal");
        validateField(defaultSessionCountersEnabled, "defaultSessionCountersEnabled");

        return this.valid;
    }
}
