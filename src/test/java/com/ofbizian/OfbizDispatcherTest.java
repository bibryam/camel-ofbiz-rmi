package com.ofbizian;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultMessage;
import org.junit.Before;
import org.junit.Test;
import org.ofbiz.service.rmi.RemoteDispatcher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Bilgin Ibryam
 */

public class OfbizDispatcherTest {

    private RemoteDispatcher remoteDispatcher;
    private OfbizDispatcher underTest;
    private Exchange exchange;
    private DefaultMessage message;

    @Before
    public void setup() {
        remoteDispatcher = mock(RemoteDispatcher.class);
        underTest = new OfbizDispatcher(remoteDispatcher);
        exchange = mock(Exchange.class);
        message = new DefaultMessage();
        when(exchange.getIn()).thenReturn(message);
    }

    @Test
    public void executesRemoveService() throws Exception {
        message.setHeader(OfbizDispatcher.SERVICE_NAME, "testService");
        message.setHeader(OfbizDispatcher.PARAM_PREFIX + "id", 123);

        underTest.process(exchange);

        HashMap<String, Object> params = createParameterMap("id", 123);
        verify(remoteDispatcher).runSync("testService", params);
    }

    private HashMap<String, Object> createParameterMap(String key, int value) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(key, value);
        return params;
    }
}
