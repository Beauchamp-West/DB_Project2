package com.springdemo.db_project2.provider;

import com.springdemo.db_project2.entity.SupplyCenter;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * SupplyCenter(复杂数据库操作方法提供层)
 */
public class SupplyCenterProvider {
    public String importCenters(Map map) {
        List<SupplyCenter> supplyCenterList = (List<SupplyCenter>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into supply_center ");
        sb.append("(name) values ");
        MessageFormat mf = new MessageFormat(
                "(" + "#'{'list[{0}].name}" + ")"
        );
        for (int i = 0; i < supplyCenterList.size();i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < supplyCenterList.size()-1) sb.append(",");
        }
        return sb.toString();
    }
}
