package org.template.core.mesassging;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.JMSException;
import javax.jms.Session;

public class CustomMessageListenerContainer extends DefaultMessageListenerContainer {

    public CustomMessageListenerContainer() {
        super();
    }

    @Override
    protected void rollbackOnExceptionIfNecessary(Session session, Throwable ex) {
        //do nothing, use default visibility timeout
    }

    @Override
    protected void rollbackIfNecessary(Session session) throws JMSException {
        super.rollbackIfNecessary(session);
    }
}
