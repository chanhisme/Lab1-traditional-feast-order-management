package Utilities;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static final String CATEGORY_APPETIZER = "Khai vị";
    public static final String CATEGORY_MAIN = "Món chính";
    public static final String CATEGORY_DESSERT = "Tráng miệng";

    public static final String PREFIX_APPETIZER = "+ Khai vị:";
    public static final String PREFIX_MAIN = "+ Món chính:";
    public static final String PREFIX_DESSERT = "+ Tráng miệng:";

    public static final String FILE_CUSTOMER = "customer.txt";
    public static final String FILE_ORDER = "order.txt";
    public static final String FILE_FEAST_MENU = "feastMenu.txt";

    public static final String CUSTOMER_ID_REGEX = "^[CGK]\\d{4}$";
    public static final String CUSTOMER_PHONE_REGEX = "^0\\d{9}$";
    public static final String CUSTOMER_NAME_REGEX = "^(?=.{2,25}$)\\p{L}+(?: \\p{L}+)*$";
    public static final String CUSTOMER_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static final String DOMAIN_EMAIL = "^@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$";

//    Điều kiện	Java date
//
//    < 28/9	date.isBefore (startDate)
//
//date <= 28/9	!date.isAfter(startDate)
//date == 28/9	date.isEqual(startDate)
//date >= 28/9	!date.isBefore(startDate)
//date > 28/9	date.isAfter(startDate)
}
