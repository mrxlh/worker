package com.collmall.util;


import com.collmall.constant.EnumMessage;
import com.collmall.model.EnumModel;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 枚举工具类
 * @author  xulihui
 * @date 2019-01-19
 */
public class EnumUtil {

    public  static  String defautPackage="com.collmall.constant";

    public  static List<EnumModel> getByName(String shortName){
        if (StringUtils.isEmpty(shortName)) {
            return new ArrayList<>();
        }
        String methodName = "values";

        String packageName=defautPackage+"."+shortName;
        List<EnumModel> enumModels=new ArrayList<>();

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
        catch (Exception ex){}

        return enumModels;
    }
}
