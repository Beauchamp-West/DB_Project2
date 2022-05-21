import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class API {

    static Connection con = null;
    static Statement statement = null;
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
    public static void updateOrder(String contract_num, String model, int salesman_num, int newQuantity, String newEstimate_delivery_date, String newLodgement_date) throws Exception {
        String check = "select * from orders where contract_num = ''" + contract_num +
                "' and product_model = '" + model + "' and salesman_num = '" + salesman_num + "';";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(check);
        if (resultSet == null) {
            throw new Exception("no such order");
        }
        int id = resultSet.getInt("id");
        int quantityInOrder = resultSet.getInt("quantity");
        String selectInventory = "select quantity from inventory where product_model = '" + model + "' and supply_center = " +
                "(select supply_center from enterprise join orders on orders.enterprise = enterprise.name where contract_num = '" +contract_num + "');"  ;
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
                " where product_model = '" + model + "' and supply_center = " +
                "(select supply_center from enterprise join orders on orders.enterprise = enterprise.name where contract_num = '" +contract_num + "');"  ;
        statement.execute(operation);
        if (newQuantity == 0) {
            deleteOrder(id);
        }
    }
}
