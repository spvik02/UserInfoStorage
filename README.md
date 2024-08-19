Java 17

**Требования**

Приложение, которое предоставляет следующие возможности:

* Создать пользователя со следующими параметрами: имя, фамилия, e-mail, роли, мобильные телефоны - и сохранить его в файл.

Количество ролей и телефонов от 1 до 3-х. При попытке ввести некорректное кол-во записей выводить сообщение о том, что кол-во неверно и дать повторить попытку ввода.

Телефоны должны быть в виде 375*********, к примеру, | 37500 1234567 |

Email в виде *****@.*, к примеру, | any@email.com |
* Редактировать уже существующего пользователя.
* Удалить пользователя.
* Получать информацию о пользователе, его ролях и телефонах (вывод на консоль).

---
**Дополнительные комментарии**

Данные о пользователях хранятся в csv-файле. 
* Настроить путь можно в файле src/main/java/edu/vsp/configuration/CsvConfiguration.java
* По пути src/main/resources/users.csv хранится файл с несколькими пользователями

При запуске приложения в консоль выводится сообщение с указанием возможных действий.

![image](https://github.com/user-attachments/assets/c82d644a-6b22-43cc-8fac-d2123d167a6d)

При создании пользователя необходимо ввести все необходимые данные о нем. 
* При вводе некорректных данных (неверное количество записей для ролей и телефонов, неверный формат почты, роли или телефона) запрос на ввод данных будет повторен.
* Адрес электронной почты был использован как идентификатор для поиска пользователя. Значение этого поля должно быть уникальным. При дублировании этого поля будет выброшено исключени DuplicateEmailException
* Номера телефонов и роли необходимо вводить разделяя их пробелом.

При удалении и получении информации о пользователе необходимо будет ввести адрес электронной почты для поиска пользователя.

При редактировании пользователя будет предложен выбор полей для редактирования. Если поле не было изменено, то сохранится его старое значение.
