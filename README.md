# Notes App Using SQLite Database
<p align="center">
  <img src="https://github.com/user-attachments/assets/8ad2fdfa-0055-4b89-9f50-f42d9e4c78cb" width="480">
</p>
SQLite is a lightweight, fast, and reliable relational database system commonly used in mobile apps. It is embedded in Android by default, making it a convenient choice for storing, manipulating, and retrieving persistent data on Android devices. SQLite does not require any setup or administration tasks, which simplifies the development process. It supports standard relational database features like tables, indexes, and transactions, making it a powerful tool for managing local data in Android applications. See more: <br>
https://developer.android.com/training/data-storage/sqlite

# SQLite Components
SQLite database in Android consists of several components that help in creating, reading, updating, and deleting data. Here's a breakdown of the key components:
<br>

### 1. Database Schema
a. **Tables**: Structured storage where data is kept in rows and columns. You define tables in SQL with columns specifying data types. <br>
b. **Columns**: Attributes of the table, each holding specific data types (like INTEGER, TEXT, REAL). <br>
c. **Primary Key**: Unique identifier for each row in a table. <br>
d. **Indices**: Speed up querying by creating indices on certain columns. <br>

### 2. SQLiteOpenHelper
a. **A helper class** that manages the creation and version management of the database. <br>
b. **onCreate()**: Called when the database is first created, where tables are defined and initialized. <br>
c. **onUpgrade()**: Called when the database version is updated. It allows the app to handle schema changes like adding new tables or columns. <br>
d. **getWritableDatabase() / getReadableDatabase()**: Methods to open or create the database for reading and writing operations. <br>

### 3. SQLiteDatabase
This class provides methods to perform database operations such as:
- insert(): To insert a new row in a table. <br>
- query(): To retrieve rows from the database. <br>
- update(): To modify existing rows. <br>
- delete(): To delete rows. <br>
- execSQL(): To execute SQL commands directly. <br>

### 4. ContentValues
A class used to store a set of key-value pairs, where keys are column names and values are the data being inserted or updated. <br>

### 5. Cursor
- Used to retrieve query results from a database. <br>
- Provides methods like moveToFirst(), getInt(), getString(), etc., to navigate and read data from the result set. <br>
- Acts as a pointer to the dataset returned by the database. <br>

### 6. SQL Queries
a. **DDL (Data Definition Language)**: SQL commands like CREATE, ALTER, DROP used for defining or modifying database schema.
b. **DML (Data Manipulation Language)**: SQL commands like INSERT, UPDATE, DELETE, SELECT used for manipulating data within the tables.

## NotesApp Interfaces
<p align="center">
  <img src="https://github.com/user-attachments/assets/70cd0548-b070-4902-acca-d110dc686c43">
</p>
