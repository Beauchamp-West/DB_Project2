package com.springdemo.db_project2.provider;

import com.springdemo.db_project2.entity.Model;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Model(复杂数据库操作方法提供层)
 */
public class ModelProvider {
    public String importModels(Map map) {
        List<Model> modelList = (List<Model>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into model ");
        sb.append("(number,model,name,unit_price) values ");
        MessageFormat mf = new MessageFormat(
                "("
                        + "#'{'list[{0}].number}"
                        + "#'{'list[{0}].model}"
                        + "#'{'list[{0}].name}"
                        + "#'{'list[{0}].unitPrice}"
                        + ")"
        );
        for (int i = 0; i < modelList.size();i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < modelList.size()-1) sb.append(",");
        }
        return sb.toString();
    }
}
