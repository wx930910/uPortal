/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */
package org.jasig.portal.spring.locator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portal.spring.PortalApplicationContextLocator;
import org.jasig.services.persondir.IPersonAttributeDao;
import org.springframework.context.ApplicationContext;

/**
 * @author Eric Dalquist
 * @version $Revision$
 * @deprecated code that needs an IPersonAttributeDao should use direct dependency injection where possible
 */
@Deprecated
public class PersonAttributeDaoLocator extends AbstractBeanLocator<IPersonAttributeDao> {
    public static final String BEAN_NAME = "personAttributeDao";
    
    private static final Log LOG = LogFactory.getLog(PersonAttributeDaoLocator.class);
    private static AbstractBeanLocator<IPersonAttributeDao> locatorInstance;

    public static IPersonAttributeDao getPersonAttributeDao() {
        AbstractBeanLocator<IPersonAttributeDao> locator = locatorInstance;
        if (locator == null) {
            LOG.info("Looking up bean '" + BEAN_NAME + "' in ApplicationContext due to context not yet being initialized");
            final ApplicationContext applicationContext = PortalApplicationContextLocator.getApplicationContext();
            applicationContext.getBean(PersonAttributeDaoLocator.class.getName());
            
            locator = locatorInstance;
            if (locator == null) {
                LOG.warn("Instance of '" + BEAN_NAME + "' still null after portal application context has been initialized");
                return (IPersonAttributeDao)applicationContext.getBean(BEAN_NAME, IPersonAttributeDao.class);
            }
        }
        
        return locator.getInstance();
    }

    public PersonAttributeDaoLocator(IPersonAttributeDao instance) {
        super(instance, IPersonAttributeDao.class);
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.spring.locator.AbstractBeanLocator#getLocator()
     */
    @Override
    protected AbstractBeanLocator<IPersonAttributeDao> getLocator() {
        return locatorInstance;
    }

    /* (non-Javadoc)
     * @see org.jasig.portal.spring.locator.AbstractBeanLocator#setLocator(org.jasig.portal.spring.locator.AbstractBeanLocator)
     */
    @Override
    protected void setLocator(AbstractBeanLocator<IPersonAttributeDao> locator) {
        locatorInstance = locator;
    }
}
