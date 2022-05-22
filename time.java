import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class time {
    static Connection con = null;
    static Statement statement = null;
    static Timer timer = new Timer();

    public static void main(String[] args) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date now = new Date();
                Date udate = null;
                try {
                    udate = df.parse(now.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                java.sql.Date date = new java.sql.Date(udate.getTime());
                String operation = "update orders set contract_type = case " +
                        "when lodgement_date is not null and lodgement_date <= orders.estimated_delivery_date then 'Finished' " +
                        "when (lodgement_date is null and estimated_delivery_date < '" + date + "') or (lodgement_date is not null and lodgement_date > orders.estimated_delivery_date) then 'Overdue' " +
                        "when lodgement_date is null and estimated_delivery_date >= '" + date + "' then 'Unfinished' end " +
                        "where contract_type != 'Finished' or contract_type != 'Overdue';";
                try {
                    statement.execute(operation);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 1000, 10000000);
    }

    //如果上面能跑，这一段代码可以删去
    public void changeOrderStatus() throws SQLException, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        java.util.Date udate = df.parse(now.toString());
        java.sql.Date date = new java.sql.Date(udate.getTime());
        String operation = "update orders set contract_type = case " +
                "when lodgement_date is not null and lodgement_date <= orders.estimated_delivery_date then 'Finished' " +
                "when (lodgement_date is null and estimated_delivery_date < '" + date + "') or (lodgement_date is not null and lodgement_date > orders.estimated_delivery_date) then 'Overdue' " +
                "when lodgement_date is null and estimated_delivery_date >= '" + date + "' then 'Unfinished' end " +
                "where contract_type != 'Finished' or contract_type != 'Overdue';";
        statement.execute(operation);
    }
}
