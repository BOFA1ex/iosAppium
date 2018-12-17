package com.bofa.appium.container;

import com.bofa.appium.excute.step.Execute;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.container
 * @date 2018/12/13
 */
public class ApplicationContext {

    private static ApplicationContext context = new ApplicationContext();

    public static ApplicationContext newInstance() {
        return context;
    }

    private final Logger log = LoggerFactory.getLogger(ApplicationContext.class);

    private HashMap<String, Bean> map = new HashMap<>(16);

    private HashMap<String, Object> beanFactory = new HashMap<>(16);


    public void putBean(Object object) {
        Bean bean = new Bean();
        bean.setClassName(object.getClass().getName());
        bean.setName(object.toString());
        setMap(bean);

        if(object.getClass().getName().matches(".*Ec$")){
            log.info("object desc : " + ((Execute) object).getName());
        }
        beanFactory.put(object.getClass().getName(), object);
    }

    public void setMap(Bean bean) {
        map.put(bean.getClassName(), bean);
    }

    /**
     * xml 注入
     *
     * @param bean
     * @return
     */
    private Object createBean(Bean bean) {
        Class claz = null;
        Object beanObj = null;
        try {
            claz = Class.forName(bean.getClassName());
        } catch (ClassNotFoundException e) {
            log.error(" can not find this class " + bean.getClassName());
        }

        try {
            assert claz != null;
            beanObj = claz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(" can not find this construction " + bean.getClassName());
        }

        if (bean.getProperties() != null) {
            HashMap<String, String[]> parmMap = new HashMap<>(16);
            Object finalBeanObj = beanObj;
            bean.getProperties().forEach(
                    property -> {
                        if (StringUtils.isNotBlank(property.getValue())) {
                            parmMap.put(property.getName(), new String[]{property.getValue()});
                            try {
                                BeanUtils.populate(finalBeanObj, parmMap);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                log.error("check out your properties name \n" + e.getLocalizedMessage());
                            }
                        }
                        if (StringUtils.isNotBlank(property.getRef())) {
                            Object existBean = beanFactory.get(property.getRef());
                            // inject to container
                            if (existBean == null) {
                                existBean = createBean(map.get(property.getRef()));

                                if ((StringUtils.compare((map.get(property.getRef())).getScope(), "singleton")) == 0) {
                                    beanFactory.put(property.getRef(), existBean);
                                }
                                try {
                                    // BeanUtils set property
                                    BeanUtils.setProperty(finalBeanObj, property.getName(), existBean);
                                } catch (Exception e) {
                                    log.error("您的bean的属性" + property.getName()
                                            + "没有对应的set方法");
                                }
                            }
                        }
                    }
            );
        }
        return beanObj;
    }

    public Object getBean(String name) {
        if(name.matches(".*[<].*[>]")){
            name = name.substring(0,name.indexOf("<"));
        }
        Object o = beanFactory.get(name);
        if (o == null) {
            log.error("beanFactory can not fina bean where name : " + name);
            o = createBean(map.get(name));
        }
        return o;
    }


    public List<Object> getBeans() {
        ArrayList<Object> tmp = Lists.newArrayList();

        beanFactory.forEach(
                (s, o) -> {
                    tmp.add(o);
                }
        );
        return tmp;
    }

    @Data
    class Bean {

        private String name;

        private String className;

        private String scope;

        private List<Property> properties;

        @Data
        class Property {

            private String name;

            private String ref;

            private String value;
        }
    }
}
