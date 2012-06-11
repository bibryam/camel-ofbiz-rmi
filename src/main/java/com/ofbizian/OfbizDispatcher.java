package com.ofbizian;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.rmi.RemoteDispatcher;

/**
 * @author Bilgin Ibryam
 */

public class OfbizDispatcher implements Processor {
    public static final String SERVICE_NAME = "Ofbiz.ServiceName";
    public static final String PARAM_PREFIX = "Ofbiz.Param.";

    private RemoteDispatcher remoteDispatcher;

    public OfbizDispatcher(RemoteDispatcher remoteDispatcher) {
        this.remoteDispatcher = remoteDispatcher;
    }

    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        String serviceName = getServiceName(in);

        Map<String, Object> serviceParameters = getServiceParameters(in);
        Map<String, Object> serviceResult = executeService(serviceName, serviceParameters);
        setResultToMessage(in, serviceResult);
    }

    private String getServiceName(Message in) {
        return in.getHeader(SERVICE_NAME, String.class);
    }

    private Map<String, Object> getServiceParameters(Message in) {
        Map<String, Object> serviceParams = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : in.getHeaders().entrySet()) {
            if (entry.getKey().startsWith(PARAM_PREFIX)) {
                String paramName = entry.getKey().substring(PARAM_PREFIX.length());
                serviceParams.put(paramName, entry.getValue());
            }
        }
        return serviceParams;
    }

    private Map<String, Object> executeService(String serviceName, Map<String, Object> serviceParams)
            throws GenericServiceException, RemoteException {
        System.out.println("Executing service: " + serviceName + " with parameters: " + serviceParams);
        return remoteDispatcher.runSync(serviceName, serviceParams);
    }

    private void setResultToMessage(Message message, Map<String, Object> serviceResult) {
        message.setHeaders(serviceResult);
    }
}
