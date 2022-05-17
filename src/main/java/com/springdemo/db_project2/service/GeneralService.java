package com.springdemo.db_project2.service;

import com.springdemo.db_project2.dao.*;
import com.springdemo.db_project2.entity.Inventory;
import com.springdemo.db_project2.entity.Model;
import com.springdemo.db_project2.entity.Staff;
import com.springdemo.db_project2.entity.SupplyCenter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("generalService")
public class GeneralService {
    @Resource
    private EnterpriseService enterpriseService;
    @Resource
    private SupplyCenterService supplyCenterService;
    @Resource
    private ModelService modelService;
    @Resource
    private StaffService staffService;
    @Resource
    private InventoryService inventoryService;
    @Resource
    private ContractService contractService;
    @Resource
    private OrdersService ordersService;

    /**
     * Add a single stock to the inventory.
     *
     * @param supplyCenter supply center name
     * @param productModel product model
     * @param supplyStuff stuff number
     * @param date date
     * @param purchasePrice purchase price
     * @param quantity quantity
     * @return successfully added or not
     * @throws ParseException invalid date format
     */
    public boolean stockIn(String supplyCenter, String productModel, Integer supplyStuff,
                          String date, Integer purchasePrice, Integer quantity) throws ParseException {

        // check validation of the input data
        List<Staff> staffs = staffService.queryByNumber(supplyStuff);
        if (staffs.size() == 0) {
            return false;
        }
        boolean valid = false;
        for (Staff staff : staffs) {
            if (staff.getSupplyCenter().equals(supplyCenter) && staff.getType().equals("Supply Staff")) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            return false;
        }
        List<SupplyCenter> supplyCenters = supplyCenterService.selectByName(supplyCenter);
        if (supplyCenters.size() == 0) {
            return false;
        }
        List<Model> models = modelService.queryByModel(productModel);
        if (models.size() == 0) {
            return false;
        }
        Inventory inventory = new Inventory();
        inventory.setSupplyCenter(supplyCenter);
        inventory.setProductModel(productModel);
        inventory.setSupplyStaff(supplyStuff);
        String [] dateSplit = date.split("/");
        LocalDate date1 = LocalDate.of(Integer.parseInt(dateSplit[0]),Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
        inventory.setDate(date1);
        inventory.setQuantity(quantity);
        inventory.setSales(0);
        inventory.setPurchasePrice(purchasePrice);
        inventoryService.insert(inventory);
        return true;
    }

    /**
     * Import stocks from the original data.
     *
     * @return stock in information
     */
    public String stockInAll() {
        int cnt = 0;
        String fileName = "tables/task1_in_stoke_test_data_publish.csv";

        try (BufferedReader infile = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] data;
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String supplyCenter = data[1].replace("\"","");
                String model = data[2];
                Integer stuffNumber = Integer.parseInt(data[3]);
                String date = data[4];
                Integer price = Integer.parseInt(data[5]);
                Integer quantity = Integer.parseInt(data[6]);
                if (stockIn(supplyCenter,model,stuffNumber,date,price,quantity)) cnt++;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return "Successfully added " + cnt + " stocks!\n";
    }
}
