package com.springdemo.db_project2.service;

import com.springdemo.db_project2.dao.*;
import com.springdemo.db_project2.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("generalService")
public class GeneralService {
    @Resource
    private EnterpriseDao enterpriseDao;
    @Resource
    private SupplyCenterDao supplyCenterDao;
    @Resource
    private ModelDao modelDao;
    @Resource
    private StaffDao staffDao;
    @Resource
    private InventoryDao inventoryDao;
    @Resource
    private ContractDao contractDao;
    @Resource
    private OrdersDao ordersDao;
    @Resource
    private InventoryService inventoryService;

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
     */
    public String stockIn(String supplyCenter, String productModel, Integer supplyStuff,
                          String date, Integer purchasePrice, Integer quantity) {

        // check validation of the input data
        List<Staff> staffs = staffDao.queryByNumber(supplyStuff);
        if (staffs.size() == 0) {
            return "Error: Staff does not exist!";
        }
        boolean valid = false;
        for (Staff staff : staffs) {
            if (staff.getSupplyCenter().equals(supplyCenter) && staff.getType().equals("Supply Staff")) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            return "Error: Invalid staff!";
        }
        List<SupplyCenter> supplyCenters = supplyCenterDao.selectByName(supplyCenter);
        if (supplyCenters.size() == 0) {
            return "Error: Supply center does not exist!";
        }
        List<Model> models = modelDao.queryByModel(productModel);
        if (models.size() == 0) {
            return "Error: Product model does not exists!";
        }

        // if the same product model in the same supply center has existed, we update the quantity
        List<Inventory> inventories = inventoryDao.selectByCenterAndModel(supplyCenter,productModel);
        String [] dateSplit = date.split("/");
        LocalDate date1 = LocalDate.of(Integer.parseInt(dateSplit[0]),Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
        if (inventories.size() != 0 ) {
            Integer q = inventories.get(0).getQuantity();
            inventoryDao.updateQuantity(q,quantity,date1,productModel,supplyCenter);
            return "Successfully updated one stock!";
        }
        // else, we add a new stock
        Inventory inventory = new Inventory();
        inventory.setSupplyCenter(supplyCenter);
        inventory.setProductModel(productModel);
        inventory.setSupplyStaff(supplyStuff);

        inventory.setDate(date1);
        inventory.setQuantity(quantity);
        inventory.setSales(0);
        inventory.setPurchasePrice(purchasePrice);
        inventoryDao.insert(inventory);
        return "Successfully added one stock!";
    }

    /**
     * Import stocks from the original data.
     *
     * @return stock in information
     */
    public String stockInAll() {
        int cnt = 0;
        String fileName = "tables/in_stoke_test.csv";

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
                if (stockIn(supplyCenter,model,stuffNumber,date,price,quantity).equals("Successfully added one stock!"))
                    cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Successfully added " + cnt + " stocks!\n";
    }

    /**
     * Add a single order
     *
     * @param contract_num contract number
     * @param enterprise enterprise name
     * @param product_model product model
     * @param sold once sales
     * @param contract_manager manager number
     * @param contract_date contract date
     * @param estimated_delivery_date estimated delivery date
     * @param lodgement_date lodgement date
     * @param salesman_num salesman number
     * @param contract_type contract type
     * @return successfully added or not
     */
    public String placeOrder(String contract_num, String enterprise, String product_model, Integer sold,
                             Integer contract_manager, String contract_date, String estimated_delivery_date,
                             String lodgement_date, Integer salesman_num, String contract_type) {
        // check validation of the input data
        Inventory inventory = inventoryService.selectByEnterpriseAndModel(enterprise, product_model);
        Integer quantity = inventory.getQuantity();
        Integer sales = inventory.getSales();
        if (quantity < sold) return "Stocks not enough!";
        List<Staff> staffs = staffDao.queryByNumber(salesman_num);
        if (staffs.size() == 0) return "Salesman does not exist!";
        if (!staffs.get(0).getType().equals("Salesman")) return "Not a salesman!";

        // add new order
        Orders orders = new Orders();
        orders.setContractNum(contract_num);
        orders.setEnterprise(enterprise);
        orders.setProductModel(product_model);
        orders.setQuantity(sold);
        orders.setContractManager(contract_manager);
        String[] dateSplit;
        LocalDate date1 = null;
        LocalDate date2 = null;
        LocalDate date3 = null;
        if (contract_date != null && !contract_date.equals("")) {
            dateSplit = contract_date.split("-");
            date1 = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
                    Integer.parseInt(dateSplit[2]));
            orders.setContractDate(date1);
        }
        if (estimated_delivery_date != null && !estimated_delivery_date.equals("")) {
            dateSplit = estimated_delivery_date.split("-");
            date2 = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
                    Integer.parseInt(dateSplit[2]));
            orders.setEstimatedDeliveryDate(date2);
        }
        if (lodgement_date != null && !lodgement_date.equals("")) {
            dateSplit = lodgement_date.split("-");
            date3 = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
                    Integer.parseInt(dateSplit[2]));
            orders.setLodgementDate(date3);
        }
        orders.setSalesmanNum(salesman_num);
        orders.setContractType(contract_type);
        ordersDao.insert(orders);

        // add new contract if not added before
        List<Contract> contracts = contractDao.selectByNum(contract_num);
        if (contracts.size() == 0) {
            Contract contract = new Contract();
            contract.setContractNum(contract_num);
            contract.setEnterprise(enterprise);
            contract.setContractManager(contract_manager);
            contract.setContractDate(date1);
            contract.setEstimatedDeliveryDate(date2);
            contract.setLodgementDate(date3);
            contract.setContractType(contract_type);
            contractDao.insert(contract);
        }

        // update the quantity
        inventoryDao.updateBySold(sold,quantity,sales,product_model,enterprise);
        return "Successfully added one order!";
    }

    /**
     * Add all orders in the test data
     *
     * @return place order information
     */
    public String placeAll() {
        int cnt = 0;
        String fileName = "tables/task2_test_data_final_public.tsv";

        try (BufferedReader infile = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] data;
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                data = line.split("\t",-1);
                String contract_num = data[0];
                String enterprise = data[1];
                String product_model = data[2];
                Integer sold = Integer.parseInt(data[3]);
                Integer contract_manager = Integer.parseInt(data[4]);
                String contract_date = data[5];
                String estimated_delivery_date = data[6];
                String lodgement_date = data[7];
                Integer salesman_num = Integer.parseInt(data[8]);
                String contract_type = data[9];
                if (placeOrder(contract_num,enterprise,product_model,sold, contract_manager,
                        contract_date,estimated_delivery_date, lodgement_date,salesman_num,
                        contract_type).equals("Successfully added one order!"))
                    cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Successfully placed " + cnt + " orders!\n";
    }

    /**
     * update order
     *
     * @param contract_num contract number
     * @param product_model product model
     * @param salesman_num salesman number
     * @param quantity new quantity
     * @param estimated_delivery_date new estimated delivery date
     * @param lodgement_date new lodgement date
     * @return successfully updated or not
     */
    public String updateOrder(String contract_num, String product_model, Integer salesman_num, Integer quantity,
                              String estimated_delivery_date, String lodgement_date) {
        // check validation of input data
        List<Orders> orders = ordersDao.selectByContractAndModelAndSalesman(contract_num,product_model,salesman_num);
        if (orders.size() == 0)  return "Invalid salesman!";
        Orders orders1 = orders.get(0);
        Integer id = orders1.getId();
        Integer q = orders1.getQuantity();
        List<Inventory> inventories = inventoryDao.selectByModelAndContractNum(product_model,contract_num);
        if (inventories.size() == 0) return "No stock corresponding to the order!";
        Inventory inventory = inventories.get(0);
        Integer newQuantity = inventory.getQuantity() - (quantity - q);
        Integer newSales = inventory.getSales() + (quantity - q);
        if (newQuantity < 0 ) return "No enough stock to update!";

        // update the order or delete it if quantity = 0
        if (quantity == 0) ordersDao.deleteById(id);
        else {
            String [] dateSplit = estimated_delivery_date.split("-");
            LocalDate date1 = LocalDate.of(Integer.parseInt(dateSplit[0]),Integer.parseInt(dateSplit[1]),
                    Integer.parseInt(dateSplit[2]));
            dateSplit = lodgement_date.split("-");
            LocalDate date2 = LocalDate.of(Integer.parseInt(dateSplit[0]),Integer.parseInt(dateSplit[1]),
                    Integer.parseInt(dateSplit[2]));
            ordersDao.updateQuantityAndDates(quantity,date1,date2,contract_num,product_model,salesman_num);
        }
        // update the corresponding stock
        inventoryDao.updateQuantityByModelAndContract(newQuantity,newSales,product_model,contract_num);

        return "Successfully updated one order!";
    }

    /**
     * update orders from test data
     *
     * @return update information
     */
    public String updateAll() {
        int cnt = 0;
        String fileName = "tables/update_final_test.tsv";

        try (BufferedReader infile = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] data;
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                data = line.split("\t",-1);
                String contract_num = data[0];
                String product_model = data[1];
                Integer salesman_num = Integer.parseInt(data[2]);
                Integer quantity = Integer.parseInt(data[3]);
                String estimated_delivery_date = data[4];
                String lodgement_date = data[5];

                if (updateOrder(contract_num,product_model,salesman_num,quantity,
                        estimated_delivery_date,lodgement_date).equals("Successfully updated one order!"))
                    cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Successfully updated " + cnt + " orders!\n";
    }

    /**
     * delete order
     *
     * @param contract_num contract number
     * @param salesman_num salesman number
     * @param seq sequence of the order for a given salesman and contract
     * @return successfully deleted or not
     */
    public String deleteOrder(String contract_num, Integer salesman_num, Integer seq) {
        Orders orders = ordersDao.selectByContractAndSalesmanAndSeq(contract_num,salesman_num,seq);
        // check validation of input data
        if (orders == null) return "Invalid salesman!";
        Integer id = orders.getId();
        Integer quantity = orders.getQuantity();
        String model = orders.getProductModel();

        // update stock and delete order
        List<Inventory> inventories = inventoryDao.selectByModelAndContractNum(model,contract_num);
        if (inventories.isEmpty()) return "No stock corresponding to the order!";
        Inventory inventory = inventories.get(0);
        Integer newQuantity = inventory.getQuantity() + quantity;
        Integer newSales = inventory.getSales() - quantity;
        inventoryDao.updateQuantityByModelAndContract(newQuantity,newSales,model,contract_num);
        ordersDao.deleteById(id);

        return "Successfully deleted one order!";
    }

    /**
     * delete orders from the test data
     *
     * @return delete information
     */
    public String deleteAll() {
        int cnt = 0;
        String fileName = "tables/delete_final.tsv";

        try (BufferedReader infile = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] data;
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                data = line.split("\t",-1);
                String contract_num = data[0];
                Integer salesman_num = Integer.parseInt(data[1]);
                Integer seq = Integer.parseInt(data[2]);

                if (deleteOrder(contract_num,salesman_num,seq).equals("Successfully deleted one order!"))
                    cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Successfully deleted " + cnt + " orders!\n";
    }

    /**
     * Add a single order without validation check
     *
     * @param contract_num contract number
     * @param enterprise enterprise name
     * @param product_model product model
     * @param sold once sales
     * @param contract_manager manager number
     * @param contract_date contract date
     * @param estimated_delivery_date estimated delivery date
     * @param lodgement_date lodgement date
     * @param salesman_num salesman number
     * @param contract_type contract type
     * @return successfully added or not
     */
    public String rudePlaceOrder(String contract_num, String enterprise, String product_model, Integer sold,
                             Integer contract_manager, String contract_date, String estimated_delivery_date,
                             String lodgement_date, Integer salesman_num, String contract_type) {

        // add new order
        Orders orders = new Orders();
        orders.setContractNum(contract_num);
        orders.setEnterprise(enterprise);
        orders.setProductModel(product_model);
        orders.setQuantity(sold);
        orders.setContractManager(contract_manager);
        String[] dateSplit;
        if (contract_date != null && !contract_date.equals("")) {
            dateSplit = contract_date.split("/");
            LocalDate date1 = LocalDate.of(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[0]),
                    Integer.parseInt(dateSplit[1]));
            orders.setContractDate(date1);
        }
        if (estimated_delivery_date != null && !estimated_delivery_date.equals("")) {
            dateSplit = estimated_delivery_date.split("/");
            LocalDate date2 = LocalDate.of(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[0]),
                    Integer.parseInt(dateSplit[1]));
            orders.setEstimatedDeliveryDate(date2);
        }
        if (lodgement_date != null && !lodgement_date.equals("")) {
            dateSplit = lodgement_date.split("/");
            LocalDate date3 = LocalDate.of(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[0]),
                    Integer.parseInt(dateSplit[1]));
            orders.setLodgementDate(date3);
        }
        orders.setSalesmanNum(salesman_num);
        orders.setContractType(contract_type);
        ordersDao.insert(orders);

        return "Boldly added one order!";
    }

    /**
     * import test data for auto update
     *
     * @return import information
     */
    public String importUpdateTest() {
        int cnt = 0;
        String fileName = "tables/test.csv";

        try (BufferedReader infile = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] data;
            while ((line = infile.readLine()) != null) {
                data = line.split(",",-1);
                String contract_num = data[0];
                String enterprise = data[1];
                String product_model = data[2];
                Integer sold = Integer.parseInt(data[3]);
                Integer contract_manager = Integer.parseInt(data[4]);
                String contract_date = data[5];
                String estimated_delivery_date = data[6];
                String lodgement_date = data[7];
                Integer salesman_num = Integer.parseInt(data[8]);
                String contract_type = data[9];
                if (rudePlaceOrder(contract_num,enterprise,product_model,sold, contract_manager,
                        contract_date,estimated_delivery_date, lodgement_date,salesman_num,
                        contract_type).equals("Boldly added one order!"))
                    cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Boldly placed " + cnt + " orders!\n";
    }

}
