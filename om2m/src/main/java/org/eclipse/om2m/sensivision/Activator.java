package org.eclipse.om2m.sensivision;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.eclipse.om2m.sensivision.controller.LifeCycleManager;
import org.eclipse.om2m.sensivision.controller.Controller;


public class Activator implements BundleActivator {
	
	 /** Logger */
    private static Log logger = LogFactory.getLog(Activator.class);
    /** SCL service tracker */
    private ServiceTracker<Object, Object> cseServiceTracker;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        logger.info("Register IpeService..");
        bundleContext.registerService(InterworkingService.class.getName(), new Router(), null);
        logger.info("IpeService is registered.");

        cseServiceTracker = new ServiceTracker<Object, Object>(bundleContext, CseService.class.getName(), null) {
            public void removedService(ServiceReference<Object> reference, Object service) {
                logger.info("CseService removed");
            }

            public Object addingService(ServiceReference<Object> reference) {
                logger.info("CseService discovered");
                CseService cseService = (CseService) this.context.getService(reference);
                Controller.setCse(cseService);
                new Thread(){
                    public void run(){
                        try {
                        	LifeCycleManager.start();
                        } catch (Exception e) {
                            logger.error("IpeMonitor Sample error", e);
                        }
                    }
                }.start();
                return cseService;
            }
        };
        cseServiceTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        logger.info("Stop Ipe Sample");
        try {
        	LifeCycleManager.stop();
        } catch (Exception e) {
            logger.error("Stop IPE Sample error", e);
        }
    }
	
}
