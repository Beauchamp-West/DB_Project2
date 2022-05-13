import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class APIs {
    /*
    四个基本表的增删查改
    一次性导入所有数据的功能
    一次性输出所有数据的功能，格式按照section 4
    stockin内容，向inventory内导入数据

    */
    /*
    String operation = "";
        PreparedStatement prep = con.prepareStatement(operation);
        prep.execute();
    */
    static Connection con = null;
    static Statement statement = null;

    public static void setConnection(Connection conn) throws SQLException {
        con = conn;
        statement = con.createStatement();
    }

    public static void selectAll(String table) throws SQLException {
        String operation = "select * from " + table + ";";
        PreparedStatement prep = con.prepareStatement(operation);
        prep.execute();
    }

    public static void insertSupply_center(String inputData) throws SQLException {
        inputData = preprocess(inputData);
        String[] data = inputData.split(",");
        data[1] = revert(data[0]);
        String operation = "insert into supply_center (name) values ('" + data[1] + "');";
        PreparedStatement prep = con.prepareStatement(operation);
        prep.execute();
    }

    public static void selectSupply_center(String name) throws SQLException {
        String operation = "select * from supply_center where name = '" + name + "';";
        PreparedStatement prep = con.prepareStatement(operation);
        prep.execute();
    }

    public static void updateSupply_center(String originData, String newData) throws SQLException {
        String operation = "update supply_center set name = '" + newData + "' where name = '" + originData + "';";
        PreparedStatement prep = con.prepareStatement(operation);
        prep.execute();
    }

    public static void deleteSupply_center(String name) throws SQLException {
        String operation = "delete from supply_center where name = '" + name + "';";
        PreparedStatement prep = con.prepareStatement(operation);
        prep.execute();
    }

    public static void stockIn(String inputData) throws Exception {
        inputData = preprocess(inputData);
        String[] data = inputData.split(",");
        data[0] = revert(data[0]);
        String operation = "insert into inventory (supply_center, product_model, supply_staff," +
                " date, purchase_price, quantity, sales) values(?,?,?,?,?,?,?)";
        String check1 = "select supply_center from staff where number = " + data[2] + ";";
        String check2 = "select type from staff where number = " + data[2] + ";";
        String check3 = "select * from supply_center where name = '" + data[0] + "';";
        String check4 = "select * from model where model = '" + data[1] + "';";
        String check5 = "select * from staff where number = " + data[2] + "';";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(check1);
        if (!Objects.equals(data[0], resultSet.getString("supply_center"))) {
            throw new Exception("supply center mismatched");
        }
        resultSet = statement.executeQuery(check2);
        if (!Objects.equals("supply_staff", resultSet.getString("type"))) {
            throw new Exception("staff type invalid");
        }
        resultSet = statement.executeQuery(check3);
        if (resultSet == null) {
            throw new Exception("supply center does not exist");
        }
        resultSet = statement.executeQuery(check4);
        if (resultSet == null) {
            throw new Exception("product model does not exist");
        }
        resultSet = statement.executeQuery(check5);
        if (resultSet == null) {
            throw new Exception("staff does not exist");
        }
        PreparedStatement prep = con.prepareStatement(operation);
        prep.setString(1, data[0]);//supply_center
        prep.setString(2, data[1]);//product_model
        prep.setInt(3, Integer.parseInt(data[2]));//supply_staff
        prep.setDate(4, dateManipulate(data[3]));//date
        prep.setInt(5, Integer.parseInt(data[4]));//purchase_price
        prep.setInt(6, Integer.parseInt(data[5]));//quantity
        prep.setInt(7, 0);//sales
        prep.execute();
    }

    public static void placeOrder(String inputData) throws Exception {
        String[] data = inputData.split(",");
        String operation1 = "insert into orders (contract_num, enterprise, product_model, quantity," +
                " contract_manager, contract_date, estimated_delivery_date, lodgement_date," +
                " salesman_num, contract_type) values (?,?,?,?,?,?,?,?,?,?)";
        String operation2 = "insert into orders (contract_num, enterprise," +
                " contract_manager, contract_date, estimated_delivery_date, lodgement_date," +
                " contract_type) values (?,?,?,?,?,?,?)";
        String check1 = "select quantity, sales from inventory where product_model = '" + data[2] + "';";
        String check2 = "select type from staff where number = " + data[8] + ";";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(check1);
        int quantity = resultSet.getInt("quantity");
        int sales = resultSet.getInt("sales");
        int sold = Integer.parseInt(data[3]);
        if (quantity > sold) {
            throw new Exception("stocks are not enough");
        }
        resultSet = statement.executeQuery(check2);
        if (!Objects.equals(resultSet.getString("type"), "Salesman")) {
            throw new Exception("staff type invalid");
        }
        PreparedStatement prep = con.prepareStatement(operation1);
        prep.setString(1, data[0]);//contract number
        prep.setString(2, data[1]);//enterprise
        prep.setString(3, data[2]);//product model
        prep.setInt(4, Integer.parseInt(data[3]));//quantity of model
        prep.setInt(5, Integer.parseInt(data[4]));//manager's number
        prep.setDate(6, dateManipulate(data[5]));//contract date
        prep.setDate(7, dateManipulate(data[6]));//estimated delivery date
        prep.setDate(8, dateManipulate(data[7]));//lodgement date
        prep.setInt(9, Integer.parseInt(data[8]));//salesman's number
        prep.setString(10, data[9]);
        prep.execute();
        prep = con.prepareStatement(operation2);
        prep.setString(1, data[0]);//contract number
        prep.setString(2, data[1]);//enterprise
        prep.setInt(3, Integer.parseInt(data[4]));//manager's number
        prep.setDate(4, dateManipulate(data[5]));//contract date
        prep.setDate(5, dateManipulate(data[6]));//estimated delivery date
        prep.setDate(6, dateManipulate(data[7]));//lodgement date
        prep.setString(7, data[9]);
        prep.execute();
        String update = "update inventory set quantity = " + (quantity - sold) + ",set sales = " + (sold + sales)
                + " where product_model = '" + data[2] + "';";
        prep = con.prepareStatement(update);
        prep.execute();
    }


    public static void updateOrder(String contract_num, String model, int salesman_num, int newQuantity, String newEstimate_delivery_date, String newLodgement_date) throws Exception {
        //订单数量更新关系与库存变化关系有点迷，是订单内数量增多，从库存中挪一部分出来，导致库存减少？
        // 那么相应的订单内数量减少，库存内数量增加
        //那么如何解释订单最终数量为0的时候代表订单完成？
        //订单的数量增多或减少分别对应了什么行为？
        String check = "select * from orders where contract_num = ''" + contract_num +
                "' and product_model = '" + model + "' and salesman_num = '" + salesman_num + "';";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(check);
        if (resultSet == null) {
            throw new Exception("no such order");
        }
        int id = resultSet.getInt("id");
        int quantityInOrder = resultSet.getInt("quantity");
        String selectInventory = "select quantity from inventory where product_model = '" + model + "';";
        resultSet = statement.executeQuery(selectInventory);
        if (resultSet == null) {
            throw new Exception("no such model");
        }
        int quantityInInventory = resultSet.getInt("quantity");
        int newQuantityInInventory = quantityInInventory - (newQuantity - quantityInOrder);

        String operation = "update orders ";
        if (newQuantity >= 0) operation += "set quantity = " + newQuantity;
        if (newEstimate_delivery_date != null) {
            if (operation.length() > 15) operation += ",";
            operation += "set estimate_delivery_date = " + dateManipulate(newEstimate_delivery_date);
        }
        if (newLodgement_date != null) {
            if (operation.length() > 15) operation += ",";
            operation += "set lodgement_date = " + dateManipulate(newLodgement_date);
        }
        statement.execute(operation);
        operation = "update inventory set quantity = " + newQuantityInInventory + ", sales = " + (newQuantity - quantityInOrder) +
                "where product_model = '" + model + "';";
        statement.execute(operation);

        if (newQuantity == 0) {
            deleteOrder(id);
        }
    }

    public static void deleteOrder(String contract_num, int salesman, int id) throws Exception {//id表示题目中的那个id
        String operation = "select * from (" +
                "select *, row_number() as rnb over(order by estimate_delivery_date, product_model) " +
                "from orders where contract_num = '" + contract_num + "' and salesman_num =" +
                salesman + ") where rnb = " + id + ";";
        ResultSet resultSet = statement.executeQuery(operation);
        if (resultSet == null) {
            throw new Exception("no such order");
        }
        int quantity = resultSet.getInt("quantity");
        int idOrder = resultSet.getInt("id");
        operation = "update inventory set quantity = quantity + " + quantity + " where id = " + idOrder + ";";
        statement.execute(operation);
        deleteOrder(idOrder);
    }

    private static void deleteOrder(int id) throws SQLException {//id表示在表orders中的订单编号id，而不是题目中的那个id
        //you should make sure the order exists
        String operation = "delete from orders where id = " + id;
        statement.execute(operation);
    }

    public static void getAllStaffCount() throws SQLException {
        String operation = "select count(*) as cnt, type from staff group by type;";
        ResultSet resultSet = statement.executeQuery(operation);
        StringBuilder sb = new StringBuilder();
        while (resultSet.next()) {
            sb.append(String.format("%-8s",resultSet.getString("type"))).append(resultSet.getInt("cnt")).append("\n");
        }
        System.out.println(sb);
    }

    public static void getContractCount() throws SQLException {
        String operation = "select count(*) as cnt from contract;";
        ResultSet resultSet = statement.executeQuery(operation);
        int res = resultSet.getInt("cnt");
        System.out.println("total number of contract: " + res);
    }

    public static void getOrderCount() throws SQLException {
        String operation = "select count(*) as cnt from orders;";
        ResultSet resultSet = statement.executeQuery(operation);
        int res = resultSet.getInt("cnt");
        System.out.println("total number of order");
    }

    public static void getNeverSoldProductCount() throws SQLException {
        String operation = "select count(*) as cnt from inventory join orders on inventory.product_model = orders.product_model " +
                "where inventory.quantity > 0 and inventory.sales = 0;";
        ResultSet resultSet = statement.executeQuery(operation);
        int res = resultSet.getInt("cnt");
        System.out.println("never-sold products: "+res);
    }

    public static void getFavoriteProductModel() throws SQLException {
        String operation = "select product_model, maximum from(select max(sales) as maximum from inventory) as t " +
                "join inventory on inventory.sales = t.maximum;";
        ResultSet resultSet = statement.executeQuery(operation);
        System.out.println(resultSet.getString("product_model") + "   " + resultSet.getInt("maximum"));
    }

    public static void getAvgStockByCenter() throws SQLException {
        String operation = "select round(sum(quantity)*1.0/count(*),1) as average, supply_center from inventory group by supply_center;";
        ResultSet resultSet = statement.executeQuery(operation);
        StringBuilder sb = new StringBuilder();
        while (resultSet.next()){
            sb.append(String.format("%-45s",resultSet.getString("supply_center"))).append(String.format("%.1f\n",resultSet.getDouble("average")));
        }
        System.out.println(sb);
    }

    public static void getProductByNumber(String product_number) throws SQLException {
        String operation = "select supply_center, model.model as model, product_model, purchase_price,quantity \n" +
                "from inventory join model on product_model = name where model.model = '"+product_number+"';";
        ResultSet resultSet = statement.executeQuery(operation);
        StringBuilder sb = new StringBuilder();
        while (resultSet.next()){
            sb.append(String.format("supply center: %-45s",resultSet.getString("sc")))
                    .append(String.format("product: %-100s",resultSet.getString("model")))
                    .append(String.format("product model: %-100s",resultSet.getString("name")))
                    .append("price: ").append(resultSet.getInt("purchase_price"))
                    .append("quantity: ").append(resultSet.getInt("quantity")).append("\n");
        }
        System.out.println(sb);
    }

    public static void getContractInfo(String contract_number) throws SQLException {
        String operation = "select contract_num, manager_name, enterprise, sc, t2.product_model as model, \n" +
                "salesman_name, quantity, estimated_delivery_date, lodgement_date from \n" +
                "(select contract_num, manager_name, enterprise, t1.supply_center as sc, \n" +
                "product_model, name as salesman_name, estimated_delivery_date, lodgement_date from \n" +
                "(select contract_num, name as manager_name, enterprise, supply_center, product_model,\n" +
                "salesman_num, estimated_delivery_date, lodgement_date\n" +
                "from orders join staff on orders.contract_manager = staff.id) as t1 \n" +
                "join staff on t1.salesman_num = staff.name) as t2 \n" +
                "join inventory on t2.product_model = inventory.product_model  \n" +
                "where contract_num = '"+contract_number+"';";
        ResultSet resultSet = statement.executeQuery(operation);
        StringBuilder sb = new StringBuilder();
        sb.append("contract number: ").append(resultSet.getString("contract_num")).append("\n");
        sb.append("contract manager: ").append(resultSet.getString("manager_name")).append("\n");
        sb.append("enterprise: ").append(resultSet.getString("enterprise")).append("\n");
        sb.append("supply center: ").append(resultSet.getString("sc")).append("\n");
        while (resultSet.next()){
            sb.append(String.format("product model: %-100s", resultSet.getString("model")))
                    .append(String.format("salesman: %-40s", resultSet.getString("salesman_name")))
                    .append(String.format("quantity: %-6d", resultSet.getInt("quantity")))
                    .append("estimated delivery date: ").append(resultSet.getDate("estimated_delivery_date"))
                    .append("lodgement date: ").append(resultSet.getDate("lodgement_date"));
        }
        System.out.println(sb);
    }

    private static String preprocess(String str) {
        return str.replace("Hong Kong, Macao and Taiwan regions of China", "hongkongmacaoandtaiwanregionsofchina");
    }

    private static String revert(String str) {
        return str.replace("hongkongmacaoandtaiwanregionsofchina", "Hong Kong, Macao and Taiwan regions of China");
    }

    private static java.sql.Date dateManipulate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date udate = df.parse(date);
        return new Date(udate.getTime());
    }
}
