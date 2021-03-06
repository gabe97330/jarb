package org.jarb.utils.spring;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ClassUtils;

/**
 * Bean post processor that adds an {@link Advisor} to beans post initialization.
 * 
 * @author Jeroen van Schagen
 * @since 17-05-2011
 */
public abstract class AdvisorAddingBeanPostProcessor extends ProxyConfig implements BeanPostProcessor, BeanClassLoaderAware {
    private static final long serialVersionUID = 912342245657548924L;
    /** Required to potentially create a proxy for our bean **/
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    /**
     * Retrieve the advisor instance that should be added to our beans during initialization.
     * @return advistor instance that should be added
     */
    protected abstract Advisor getAdvisor();

    /**
     * {@inheritDoc}
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Object result = bean;
        if (!(bean instanceof AopInfrastructureBean)) { // Ignore infastructure
            final Advisor advisor = getAdvisor();
            if (AopUtils.canApply(advisor, AopUtils.getTargetClass(bean))) {
                if (bean instanceof Advised) {
                    ((Advised) bean).addAdvisor(advisor);
                } else {
                    // Convert bean into a proxy with advisor logic
                    ProxyFactory proxyFactory = new ProxyFactory(bean);
                    proxyFactory.copyFrom(this);
                    proxyFactory.addAdvisor(advisor);
                    result = proxyFactory.getProxy(this.beanClassLoader);
                }
            }
        }
        return result;
    }

}
