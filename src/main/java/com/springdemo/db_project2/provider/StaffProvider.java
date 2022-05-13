package com.springdemo.db_project2.provider;

import com.springdemo.db_project2.entity.Staff;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Staff(复杂数据库操作方法提供层)
 */
public class StaffProvider {
    public String importStaffs(Map map) {
        List<Staff> staffList = (List<Staff>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into staff ");
        sb.append("(name,age,gender,number,supply_center,mobile_number,type) values ");
        MessageFormat mf = new MessageFormat(
                "("
                        + "#'{'list[{0}].name}"
                        + "#'{'list[{0}].age}"
                        + "#'{'list[{0}].gender}"
                        + "#'{'list[{0}].number}"
                        + "#'{'list[{0}].supplyCenter}"
                        + "#'{'list[{0}].mobileNumber}"
                        + "#'{'list[{0}].type}"
                        + ")"
        );
        for (int i = 0; i < staffList.size();i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < staffList.size()-1) sb.append(",");
        }
        return sb.toString();
    }
}
