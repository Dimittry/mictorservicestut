package com.microservicestut.infrastructure;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerEndpointConfig;

public class SingletonEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private Endpoint singletonInstance;

    public SingletonEndpointConfigurator(Endpoint sigletonInstance) {
        this.singletonInstance = sigletonInstance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if(endpointClass != this.singletonInstance.getClass()) {
            throw new UnsupportedOperationException("This SingletonEndpointConfigurator only creates "
                    + "endpoint of class " + this.singletonInstance.getClass() + ","
                    + endpointClass + " is not supported.");
        }
        return (T) singletonInstance;
    }
}
