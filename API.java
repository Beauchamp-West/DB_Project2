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
    public static void stockIn(String inputData) throws Exception {
        inputData = preprocess(inputData);
        String[] data = inputData.split(",");
        data[0] = revert(data[0]);
        String operation = "insert into inventory (supply_center, product_model, supply_staff," +
                " date, purchase_price, quantity, sales) values(?,?,?,?,?,?,?)";
        String check = "select * from inventory where product_model = '" + data[1] + "' and supply_center = '" + data[2] + "';";
        String check1 = "select supply_center from staff where number = " + data[2] + ";";
        String check2 = "select type from staff where number = " + data[2] + ";";
        String check3 = "select * from supply_center where name = '" + data[0] + "';";
        String check4 = "select * from model where model = '" + data[1] + "';";
        String check5 = "select * from staff where number = " + data[2] + "';";
        Statement statement = con.createStatement();
        int state = 0;
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
        resultSet = statement.executeQuery(check);
        if (resultSet != null) {
            String update = "update inventory set quantity = quantity + " + data[5] + " and date = "+ dateManipulate(data[3]) +
                    " where product_model =  '" + data[1] + "' and supply_center = '" + data[0] + "';";
            statement.execute(update);
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
}
