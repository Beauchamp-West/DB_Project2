package com.springdemo.db_project2.provider;

import com.springdemo.db_project2.entity.Enterprise;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Enterprise(复杂数据库操作方法提供层)
 */
public class EnterpriseProvider {
    public String importEnterprises(Map<String,List<Enterprise>> map) {
        List<Enterprise> enterpriseList = map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into enterprise ");
        sb.append("(name,country,city,supply_center,industry) values ");
        MessageFormat mf = new MessageFormat(
                "("
                        + "#'{'list[{0}].name},"
                        + "#'{'list[{0}].country},"
                        + "#'{'list[{0}].city},"
                        + "#'{'list[{0}].supplyCenter},"
                        + "#'{'list[{0}].industry}"
                        + ")"
        );
        for (int i = 0; i < enterpriseList.size();i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < enterpriseList.size()-1) sb.append(",");
        }
        return sb.toString();
    }
}
