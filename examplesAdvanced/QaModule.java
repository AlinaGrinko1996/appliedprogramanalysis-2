package com.my.MyTask.services;

import java.io.IOException;

import org.apache.tapestry5.*;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

/**
 * This module is automatically included as part of the Tapestry IoC Registry if <em>tapestry.execution-mode</em>
 * includes <code>qa</code> ("quality assurance").
 */
public class QaModule
{
    public static void bind(ServiceBinder binder)
    {
        // Bind any services needed by the QA team to produce their reports
        // binder.bind(MyServiceMonitorInterface.class, MyServiceMonitorImpl.class);
    }


    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, false);

        configuration.add(SymbolConstants.APPLICATION_VERSION, "0.0.1.-SNAPSHOT-QA");
    }
}
