# Lab №1  
## Java basics and access modifiers

### 🎯 Main point
Create basic classes of subject area **"Food Delivery"** using correct access modifiers and inheritence in Java.

---

### 📋 Task

#### Basic classes:
- `Person`  
  - Parent class with members:  
    `protected String firstName`, `lastName`, `address`
- `Customer extends Person`
- `Staff extends Person`  
  - Additional members: `private float rankOfDelivery;`
- `Restaurant`  
  - Members: `private String name`, `private String cuisine`, `private String location`
- `MenuItem`  
  - Members: `private String name`, `private float price`, `private float category`
- `Order`  
  - Members: `private Customer customer`, `private MenuItem[] menuItems`, `private Date orderDate`
- `Delivery`
  - Members: `private Order order`, `private Staff deliverer`, `private Date deliveryTime`

---

### 📦 Package `ua.university.util`

#### ✅ Util classes:
- `ValidationHelper`  
  - **package-private**  
  - Methods:
    - `isStringMatchPattern(String text, String pattern)`
    - `isNumberBetween(int number, int min, int max)`
    - `isStringLengthBetween(String text, int min, int max)`
    - `getRandomNumber(int min, int max)`
    - `isValidDate(Date date)`

- `PersonUtils`  
  - **public**  
  - uses `ValidationHelper`  
  - Methods:
    - `String capitalizeText(String str)`
    - `String formatName(String firstName, String lastName)`
    - `String formatAddress(String address))`
    - `isValidName(String name)`
    - `isValidAddress(String address)`
    - `createAddressfromName(String addressName)`

- `StaffUtils`  
  - **public**  
  - Method:
    - `isValidRanking(float ranking)`

- `RestaurantUtils`  
  - **public**
  - uses `ValidationHelper`  
  - Methods:
    - `formatRestaurantName(String name)`
    - `formatRestaurantCuisine(String cuisine)`
    - `formatRestaurantLocation(String location)`
    - `isValidRestaurantName(String restaurantName)`
    - `isValidRestaurantCuisine(String cuisine)`
    - `isValidRestaurantLocation(String location)`
      
- `MenuItemUtils`
  - **public**
  - uses `ValidationHelper`
  - Methods:
    - `capitalizeText(String str)`
    - `isValidName(String name)`
    - `isValidCategory(String category)`
    - `isValidFloat(float value)`
 
- `DeliveryUtils`
  - **public**
  - uses `ValidationHelper`
  - Method:
    - `isValidDate(Date date)`
---

### 🔐 Use of access modifiers

| Modifier          | Usage                                                 |
|-------------------|-------------------------------------------------------|
| `private`         | For the members of the class                          |
| `public`          | For getters/setters, constructors, utils              |
| `protected`       | For methods and members of parent class `Person`      |
| *package-private* | For helper classes and methods                        |

---

### 🛠️ Implementation

- constructors using `super()` in child classes
- Methods:
  - `toString()`
  - `equals()`
  - `hashCode()`
- `static factory methods`
- Validation in constructors and setters

---

### 💡 Demonstration

- Class `Main` for homework representation:
  - Create objects using different methods
  - Validation of input value
  - Formating of names and addresses
  - Access to `protected` and *package-private* elements

---



