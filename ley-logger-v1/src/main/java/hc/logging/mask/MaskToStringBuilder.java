package hc.logging.mask;

import hc.logging.type.MaskField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 */
public class MaskToStringBuilder {

    private Object obj;
    private String superToString;

    public MaskToStringBuilder(Object obj) {
        this(obj, null);
    }

    public MaskToStringBuilder(Object obj, String superToString) {
        this.obj = obj;
        this.superToString = superToString;
    }

    public String toMaskString() {
        String result = "";
        return result;
    }

    /**
         public String toString() {
            return "MyAccount(super=" + super.toString() + ", name=" + this.getName() + ")";
         }

     */
    private String invokeSuperToString() {
        String r = "";
        Method method = null;
        boolean flag = false;
        return r;
    }
}
