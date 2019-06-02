package io.project.app.util;

import com.google.gson.GsonBuilder;


public class GsonConverter {
  
    private static String getDateFormat(){
        return "yyyy-MM-dd'T'HH:mm:ss.SSS";
    }
    
   
    public static <T> String toJson(T object) {
        // create json string
        return new GsonBuilder().setPrettyPrinting().setDateFormat(getDateFormat()).enableComplexMapKeySerialization().serializeNulls().create().toJson(object);
    }
    
   
    public static <T> T fromJson(String json, Class<T> classType) {
       
        return new GsonBuilder().setDateFormat(getDateFormat()).enableComplexMapKeySerialization().create().fromJson(json, classType);
    }
    
   
    public static <From, To> To adapt(From object, Class<To> targetType){
        return fromJson(toJson(object), targetType);
    }
}
