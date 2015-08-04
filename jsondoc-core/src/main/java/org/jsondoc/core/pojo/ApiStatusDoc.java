package org.jsondoc.core.pojo;

import org.jsondoc.core.annotation.ApiStatus;

import java.lang.reflect.Method;

public class ApiStatusDoc {

    private boolean isPublic;
    private boolean isBeta;
    private boolean isDeprecated;

    public static ApiStatusDoc build(Method method) {
        if (method.isAnnotationPresent(ApiStatus.class)) {
            ApiStatus annotation = method.getAnnotation(ApiStatus.class);
            return new ApiStatusDoc(annotation);
        }
        return null;
    }

    public static ApiStatusDoc build(Class<?> controller) {
        if (controller.isAnnotationPresent(ApiStatus.class)) {
            ApiStatus annotation = controller.getAnnotation(ApiStatus.class);
            return new ApiStatusDoc(annotation);
        }
        return null;
    }

    public ApiStatusDoc(ApiStatus apiStatus) {
        this.isPublic = apiStatus.isPublic();
        this.isBeta = apiStatus.isBeta();
        this.isDeprecated = apiStatus.isDeprecated();
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isBeta() {
        return isBeta;
    }

    public void setIsBeta(boolean isBeta) {
        this.isBeta = isBeta;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public void setIsDeprecated(boolean isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

    public String getOneLineText() {
        StringBuilder sb = new StringBuilder();
        if(isPublic)
            sb.append("PUBLIC,");
        if(isDeprecated)
            sb.append("DEPRECATED,");
        if(isBeta)
            sb.append("BETA,");
        if(sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
