    package ua.delivery.model;
    
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Nested;
    import ua.delivery.exception.InvalidDataException;
    import ua.delivery.parser.DeliveryFileParser;
    
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.List;
    
    import static org.junit.jupiter.api.Assertions.*;
    
    public class DeliveryTest {

// TODO
//        @Test
//        @DisplayName("Delivery parse form json test")
//        void test2ParseFromJSON(){
//            String jsonExample = "{\n" +
//                    "  \"delivery\": [\n" +
//                    "    {\n" +
//                    "      \"order\": {\n" +
//                    "        \"customer\" : {\n" +
//                    "          \"firstName\": \"Peter\",\n" +
//                    "          \"lastName\": \"Quill\",\n" +
//                    "          \"address\": \"St. Oxford 1\"\n" +
//                    "        },\n" +
//                    "        \"menuItem\": [\n" +
//                    "          {\n" +
//                    "            \"name\": \"Pasta\",\n" +
//                    "            \"price\": 10,\n" +
//                    "            \"category\": \"Italic\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"name\": \"Burger\",\n" +
//                    "            \"price\": 20,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          }\n" +
//                    "        ],\n" +
//                    "        \"date\": \"09-Oct-2025\"\n" +
//                    "      },\n" +
//                    "      \"staff\": {\n" +
//                    "        \"firstName\": \"Iron\",\n" +
//                    "        \"lastName\": \"Man\",\n" +
//                    "        \"address\": \"St. Oxford 1\"\n" +
//                    "      },\n" +
//                    "      \"date\": \"09-Oct-2025\"\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "      \"order\": {\n" +
//                    "        \"customer\": {\n" +
//                    "          \"firstName\": \"Bruce\",\n" +
//                    "          \"lastName\": \"Wayne\",\n" +
//                    "          \"address\": \"St. Central 2\"\n" +
//                    "        },\n" +
//                    "        \"menuItem\": [\n" +
//                    "          {\n" +
//                    "            \"name\": \"Fried chicken\",\n" +
//                    "            \"price\": 15,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"name\": \"Joker\",\n" +
//                    "            \"price\": 30,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          }\n" +
//                    "        ],\n" +
//                    "        \"date\": \"09-Oct-2025\"\n" +
//                    "      },\n" +
//                    "      \"staff\": {\n" +
//                    "        \"firstName\": \"Iron\",\n" +
//                    "        \"lastName\": \"Man\",\n" +
//                    "        \"address\": \"St. Oxford 1\"\n" +
//                    "      },\n" +
//                    "      \"date\": \"09-Oct-2025\"\n" +
//                    "    }\n" +
//                    "  ]\n" +
//                    "}";
//            List<Delivery> deliveries = new ArrayList<>();
//            Object tmp = DeliveryFileParser.parseJson(jsonExample);
//            deliveries = DeliveryFileParser.deliveries;
//            String exepectedString =
//                    "Delivery{" +
//                        "Order='Order{" +
//                            "Customer='Customer{" +
//                                "firstName='Peter', lastName='Quill', address='St. Oxford 1}', " +
//                            "MenuItems='[" +
//                                "Cuisine{Name='Pasta', Price='10.0', Category='Italic'}, " +
//                                "Cuisine{Name='Burger', Price='20.0', Category='American'}]', " +
//                            "Date='Thu Oct 09 00:00:00 EEST 2025}', " +
//                        "Staff='Staff{" +
//                            "firstName='Iron', lastName='Man', address='St. Oxford 1', rankOfDelivery='1.0'}', " +
//                        "deliveryTime=Thu Oct 09 00:00:00 EEST 2025}\n" +
//                    "Delivery{" +
//                        "Order='Order{" +
//                            "Customer='Customer{" +
//                                    "firstName='Bruce', lastName='Wayne', address='St. Central 2}', " +
//                            "MenuItems='[" +
//                                "Cuisine{Name='Fried chicken', Price='15.0', Category='American'}, " +
//                                "Cuisine{Name='Joker', Price='30.0', Category='American'}]', " +
//                            "Date='Thu Oct 09 00:00:00 EEST 2025}', " +
//                        "Staff='Staff{" +
//                            "firstName='Iron', lastName='Man', address='St. Oxford 1', rankOfDelivery='1.0'}', " +
//                        "deliveryTime=Thu Oct 09 00:00:00 EEST 2025}";
//            String tmpStaff1 = "Staff{firstName='Iron', lastName='Man', address='St. Oxford 1', rankOfDelivery='1.0'}";
//            String tmpStaff2 = String.valueOf(deliveries.get(1).getDeliverer());
//            String actualString = deliveries.get(0).toString() + "\n" + deliveries.get(1).toString();
//            assertEquals(tmpStaff1, tmpStaff2,
//                    ()->String.format("Delivery has to be %s, but was: %s", tmpStaff1, tmpStaff2));
//        }

// TODO
//        @Test
//        @DisplayName("Delivery parse from invalid json")
//        void testParseInvalidJson() {
//            String jsonExample = "{\n" +
//                    "  \"delivery\": [\n" +
//                    "    {\n" +
//                    "      \"order\": {\n" +
//                    "        \"customer\" : {\n" +
//                    "          \"firstName\": \"Peter\",\n" +
//                    "          \"lastName\": \"Quill\",\n" +
//                    "          \"address\": \"St. Oxford 1\"\n" +
//                    "        },\n" +
//                    "        \"menuItem\": [\n" +
//                    "          {\n" +
//                    "            \"name\": \"Pasta\",\n" +
//                    "            \"price\": 10,\n" +
//                    "            \"category\": \"Italic\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"name\": \"Burger\",\n" +
//                    "            \"price\": 20,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          }\n" +
//                    "        ],\n" +
//                    "        \"date\": \"09-Oct-2025\"\n" +
//                    "      },\n" +
//                    "      \"abracadabra\": {\n" + // there invalid key
//                    "        \"firstName\": \"Iron\",\n" +
//                    "        \"lastName\": \"Man\",\n" +
//                    "        \"address\": \"St. Oxford 1\"\n" +
//                    "      },\n" +
//                    "      \"date\": \"09-Oct-2025\"\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "      \"order\": {\n" +
//                    "        \"customer\": {\n" +
//                    "          \"firstName\": \"Bruce\",\n" +
//                    "          \"lastName\": \"Wayne\",\n" +
//                    "          \"address\": \"St. Central 2\"\n" +
//                    "        },\n" +
//                    "        \"menuItem\": [\n" +
//                    "          {\n" +
//                    "            \"name\": \"Fried chicken\",\n" +
//                    "            \"price\": 15,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"name\": \"Joker\",\n" +
//                    "            \"price\": 30,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          }\n" +
//                    "        ],\n" +
//                    "        \"date\": \"09-Oct-2025\"\n" +
//                    "      },\n" +
//                    "      \"staff\": {\n" +
//                    "        \"firstName\": \"Iron\",\n" +
//                    "        \"lastName\": \"Man\",\n" +
//                    "        \"address\": \"St. Oxford 1\"\n" +
//                    "      },\n" +
//                    "      \"date\": \"09-Oct-2025\"\n" +
//                    "    }\n" +
//                    "  ]\n" +
//                    "}";
//            InvalidDataException ex = assertThrows(InvalidDataException.class,
//                    () -> DeliveryFileParser.parseJson(jsonExample));
//        }

// TODO
//        @Test
//        @DisplayName("JSON parse check 2")
//        void testParseFromJSON(){
//            String jsonExample = "{\n" +
//                    "  \"delivery\": [\n" +
//                    "    {\n" +
//                    "      \"order\": {\n" +
//                    "        \"customer\" : {\n" +
//                    "          \"firstName\": \"Peter\",\n" +
//                    "          \"lastName\": \"Quill\",\n" +
//                    "          \"address\": \"St. Oxford 1\"\n" +
//                    "        },\n" +
//                    "        \"date\": \"09-Oct-2025\",\n" +
//                    "        \"menuItem\": [\n" +
//                    "          {\n" +
//                    "            \"name\": \"Pasta\",\n" +
//                    "            \"price\": 10,\n" +
//                    "            \"category\": \"Italic\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"name\": \"Burger\",\n" +
//                    "            \"price\": 20,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          }\n" +
//                    "        ],\n" +
//                    "      },\n" +
//                    "      \"staff\": {\n" +
//                    "        \"firstName\": \"Iron\",\n" +
//                    "        \"lastName\": \"Man\",\n" +
//                    "        \"address\": \"St. Oxford 1\"\n" +
//                    "      },\n" +
//                    "      \"date\": \"09-Oct-2025\"\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "      \"order\": {\n" +
//                    "        \"customer\": {\n" +
//                    "          \"firstName\": \"Bruce\",\n" +
//                    "          \"lastName\": \"Wayne\",\n" +
//                    "          \"address\": \"St. Central 2\"\n" +
//                    "        },\n" +
//                    "        \"menuItem\": [\n" +
//                    "          {\n" +
//                    "            \"name\": \"Fried chicken\",\n" +
//                    "            \"price\": 15,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"name\": \"Joker\",\n" +
//                    "            \"price\": 30,\n" +
//                    "            \"category\": \"American\"\n" +
//                    "          }\n" +
//                    "        ],\n" +
//                    "        \"date\": \"09-Oct-2025\"\n" +
//                    "      },\n" +
//                    "      \"staff\": {\n" +
//                    "        \"firstName\": \"Iron\",\n" +
//                    "        \"lastName\": \"Man\",\n" +
//                    "        \"address\": \"St. Oxford 1\"\n" +
//                    "      },\n" +
//                    "      \"date\": \"09-Oct-2025\"\n" +
//                    "    }\n" +
//                    "  ]\n" +
//                    "}";
//            List<Delivery> deliveries = new ArrayList<>();
//            Object tmp = DeliveryFileParser.parseJson(jsonExample);
//            deliveries = DeliveryFileParser.deliveries;
//            String exepectedString =
//                    "Delivery{" +
//                            "Order='Order{" +
//                            "Customer='Customer{" +
//                            "firstName='Peter', lastName='Quill', address='St. Oxford 1}', " +
//                            "MenuItems='[" +
//                            "Cuisine{Name='Pasta', Price='10.0', Category='Italic'}, " +
//                            "Cuisine{Name='Burger', Price='20.0', Category='American'}]', " +
//                            "Date='Thu Oct 09 00:00:00 EEST 2025}', " +
//                            "Staff='Staff{" +
//                            "firstName='Iron', lastName='Man', address='St. Oxford 1', rankOfDelivery='1.0'}', " +
//                            "deliveryTime=Thu Oct 09 00:00:00 EEST 2025}\n" +
//                            "Delivery{" +
//                            "Order='Order{" +
//                            "Customer='Customer{" +
//                            "firstName='Bruce', lastName='Wayne', address='St. Central 2}', " +
//                            "MenuItems='[" +
//                            "Cuisine{Name='Fried chicken', Price='15.0', Category='American'}, " +
//                            "Cuisine{Name='Joker', Price='30.0', Category='American'}]', " +
//                            "Date='Thu Oct 09 00:00:00 EEST 2025}', " +
//                            "Staff='Staff{" +
//                            "firstName='Iron', lastName='Man', address='St. Oxford 1', rankOfDelivery='1.0'}', " +
//                            "deliveryTime=Thu Oct 09 00:00:00 EEST 2025}";
//            String tmpStaff1 = "Staff{firstName='Iron', lastName='Man', address='St. Oxford 1', rankOfDelivery='1.0'}";
//            String tmpStaff2 = String.valueOf(deliveries.get(1).getDeliverer());
//            String actualString = deliveries.get(0).toString() + "\n" + deliveries.get(1).toString();
//            assertEquals(tmpStaff1, tmpStaff2,
//                    ()->String.format("Delivery has to be %s, but was: %s", tmpStaff1, tmpStaff2));
//        }
    
        @Nested
        @DisplayName("Constructor Tests")
        class ConstructorTests {
    
            @Test
            @DisplayName("Default constructor should create delivery with null fields")
            void testConstructor() {
                Calendar cal = Calendar.getInstance();
                cal.set(2025, Calendar.NOVEMBER, 29);
                Date orderDate = cal.getTime();
                Customer customer = new Customer("Alex", "Vasilenko", "      St. center 13                   ");
                MenuItem[] menuItems = new MenuItem[2];
                menuItems[0] = new MenuItem("Pizza", 10, "Italic");
                menuItems[1] = MenuItem.createMenuItem("Burger", 20, "American");
                Order order = Order.createOrder(customer, menuItems, orderDate);
                Staff staff = new Staff("Robert", "Polson", "St. Central 1");
                Delivery delivery = new Delivery(order, staff, orderDate);
    
                assertEquals(order, delivery.getOrder(), "Expected order to be set after valid data");
                assertEquals(staff, delivery.getDeliverer(), "Expected staff to be set after valid data");
                assertEquals(orderDate, delivery.getDeliveryTime(), "Expected delivery time to be set after valid data");
            }
    
            @Test
            @DisplayName("Constructor should not set invalid fields")
            void testConstructorWithInvalidData() {
                InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                    new Delivery(null, null, null);
                });
                assertTrue(exception.getMessage().contains("must be set"));
            }
        }
    }
