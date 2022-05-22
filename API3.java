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


    public static void selectOrder(String contractNum, String enterprise, String model,
                                   String manager, String contractDate, String estimatedDeliveryDate,
                                   String lodgementDate, String salesman, String contractType) throws SQLException, ParseException {
        //没有设置根据quantity来选择的原因是因为感觉实际不会出现根据产品数量来查询订单的这种操作
        //如果没有的话，相应的参数传入null就行
        String operation = "select * from orders ";
        if (contractNum != null) {
            operation += " where ";
            operation += " contract_num = '" + contractNum + "' ";
        }
        if (enterprise != null) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " enterprise = '" + enterprise + "' ";
        }
        if (model != null) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " product_model = '" + model + "' ";
        }
        if (manager != null) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " contract_manager = '" + manager + "'";
        }
        if (contractDate != null) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " contract_date = '" + dateManipulate(contractDate) + "' ";
        }
        if (estimatedDeliveryDate != null) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " estimated_delivery_date = '" + dateManipulate(estimatedDeliveryDate) + "' ";
        }
        if (lodgementDate != null) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " lodgement_date = '" + lodgementDate + "' ";
        }if (salesman != null) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " salesman_num = '" + salesman + "' ";
        }if (contractType != null) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " contract_type = '" + contractType + "' ";
        }
        operation += ";";
        statement.execute(operation);
    }

}
