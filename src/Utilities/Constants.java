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

}
