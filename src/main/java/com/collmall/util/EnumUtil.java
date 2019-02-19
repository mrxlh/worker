package com.collmall.util;


import com.collmall.constant.EnumMessage;
import com.collmall.model.EnumModel;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工具类
 * @author  xulihui
 * @date 2019-01-19
 */
public class EnumUtil {

    public static ConcurrentHashMap<String,List<EnumModel>> map=new ConcurrentHashMap<>();

    public  static  String defautPackage="com.collmall.constant";


    public  static List<EnumModel> getByName(String shortName){
        if (StringUtils.isEmpty(shortName)) {
            return new ArrayList<>();
        }
        String[] infos = shortName.trim().split("_");
        shortName = infos[0];
        String methodName = "values";
        if (infos.length == 2) {
            methodName = infos[1];
        }

        String packageName=defautPackage+"."+shortName;
        List<EnumModel> enumModels=new ArrayList<>();

        if(map.containsKey(shortName)){
            return map.get(shortName);
        }
        try{
            Class<?> cls= Class.forName(packageName);
            Method method = cls.getMethod(methodName);
            EnumMessage[] list = (EnumMessage[])method.invoke(null, null);

            for(EnumMessage msg : list){
                EnumModel model=new EnumModel();
                model.setCode(msg.getCode());;
                model.setName(msg.getName());
                enumModels.add(model);
            }
        }
        catch (Exception ex){

        }

        return enumModels;
    }
}
