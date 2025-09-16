# Lab №1  
## Java basics and access modifiers

### 🎯 Main point
Create basic classes of subject area **"Food Delivery"** using correct access modifiers and inheritence in Java.

---

### 📋 Task

#### Basic classes:
- `Person`  
  - Базовий клас з полями:  
    `protected String firstName`, `lastName`, `email`
- `Student extends Person`  
  - Додає поля: `private String studentId`, `private Group group`
- `Teacher extends Person`  
  - Додає поля: `private String department`, `private String position`
- `Group`  
  - Поля: `private String number`, `private String specialty`, `private int startYear`
- `Subject`  
  - Поля: `private String title`, `private int credits`
- `Course`  
  - Поля: `private Subject subject`, `private Teacher teacher`, `private Group group`

---

### 📦 Пакет `ua.university.util`

#### ✅ Класи утиліти:
- `ValidationHelper`  
  - **package-private**  
  - Методи:
    - `isStringMatchPattern(String text, String pattern)`
    - `isNumberBetween(int number, int min, int max)`
    - `isStringLengthBetween(String text, int min, int max)`

- `PersonUtils`  
  - **public**  
  - Використовує `ValidationHelper`  
  - Методи:
    - `formatName(String firstName, String lastName)`
    - `formatEmail(String email)`
    - `generateEmailFromName(String firstName, String lastName)`

- `StudentUtils`  
  - **public**  
  - Метод:
    - `formatStudentId(String id)`

- `GroupUtils`  
  - **public**  
  - Метод:
    - `formatGroupFullNumber(Group group)`

---

### 🔐 Використання модифікаторів доступу

| Модифікатор       | Застосування                                          |
|-------------------|-------------------------------------------------------|
| `private`         | Для полів класів                                      |
| `public`          | Для getter/setter методів, конструкторів, утиліт      |
| `protected`       | Для полів та методів базового класу `Person`         |
| *package-private* | Для helper класів та допоміжних методів               |

---

### 🛠️ Реалізувати

- Конструктори з використанням `super()` у спадкоємцях
- Методи:
  - `toString()`
  - `equals()`
  - `hashCode()`
- Статичні фабричні методи (`static factory methods`)
- Валідація у конструкторах та сеттерах

---

### 💡 Демонстрація

- Клас `Main` для демонстрації роботи:
  - Створення об'єктів різними способами
  - Валідація вхідних даних
  - Форматування імен та email
  - Доступ до `protected` та *package-private* елементів

---



