/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */
package org.jasig.portal.spring.locator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portal.spring.PortalApplicationContextLocator;
import org.jasig.portal.utils.cache.CacheFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author Eric Dalquist
 * @version $Revision$
 * @deprecated code that needs an CacheFactory should use direct dependency injection where possible
 */
@Deprecated
public class CacheFactoryLocator extends AbstractBeanLocator<CacheFactory> {
    public static final String BEAN_NAME = "cacheFactory";
    
    private static final Log LOG = LogFactory.getLog(CacheFactoryLocator.class);
    private static AbstractBeanLocator<CacheFactory> locatorInstance;

    public static CacheFactory getCacheFactory() {
        AbstractBeanLocator<CacheFactory> locator = locatorInstance;
        if (locator == null) {
            LOG.info("Looking up bean '" + BEAN_NAME + "' in ApplicationContext due to context not yet being initialized");
            final ApplicationContext applicationContext = PortalApplicationContextLocator.getApplicationContext();
            applicationContext.getBean(CacheFactoryLocator.class.getName());
            
            locator = locatorInstance;
            if (locator == null) {
                LOG.warn("Instance of '" + BEAN_NAME + "' still null after portal application context has been initialized");
                return (CacheFactory)applicationContext.getBean(BEAN_NAME, CacheFactory.class);
            }
        }
        
        return locator.getInstance();
    }

    public CacheFactoryLocator(CacheFactory instance) {
        super(instance, CacheFactory.class);
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.spring.locator.AbstractBeanLocator#getLocator()
     */
    @Override
    protected AbstractBeanLocator<CacheFactory> getLocator() {
        return locatorInstance;
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.spring.locator.AbstractBeanLocator#setLocator(org.jasig.portal.spring.locator.AbstractBeanLocator)
     */
    @Override
    protected void setLocator(AbstractBeanLocator<CacheFactory> locator) {
        locatorInstance = locator;
    }
}
