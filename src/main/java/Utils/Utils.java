package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static Date getSimpleDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(date);
        try {
            Date simpleDate= formatter.parse(formattedDate);
            return simpleDate;
        } catch (ParseException e) {
            return null;
        }
    }
}
