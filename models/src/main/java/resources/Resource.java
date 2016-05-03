package resources;


import models.Region;

/**
 * Created by Thomas on 03/11/2015.
 * Resources are summary for method per model
 */
//TODO We have to check dependency into resource
public abstract class Resource {

    protected Class aClass;
    protected String method;
    protected Class[] parametersTypes;
    protected Object[] parameters;
    protected Region region;

    public Resource(Class aClass, String method, Class[] parametersTypes, Object[] parameters, Region region) {
        this.aClass = aClass;
        this.method = method;
        this.parametersTypes = parametersTypes;
        this.parameters = parameters;
        this.region = region;
    }

    protected Resource(Class aClass, String method, Region region){
        this(aClass,method,null,null, region);
    }

    protected Resource(Class aClass, String method, Class[] parametersTypes, Object[] parameters){
        this(aClass,method, parametersTypes,parameters,null);
    }

    public Class getaClass() {
        return aClass;
    }

    public String getMethod() {
        return method;
    }

    public Class[] getParametersTypes() {
        return parametersTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public Region getRegion() {
        return region;
    }
}
