# Hotel Management System / Система управления отелем

**Русский** | **English**

Простая веб-приложение для управления небольшим отелем или хостелом.  
Позволяет администратору заселять и выселять гостей, рассчитывать стоимость проживания и вести историю бронирований.

A simple web application for managing a small hotel or hostel.  
Allows the administrator to check-in / check-out guests, calculate accommodation costs and view booking history.

## Основные возможности | Key Features

- Просмотр списка всех номеров и их текущего статуса  
- Заселение гостей (check-in) с указанием имени и количества суток  
- Выселение гостей (check-out) с автоматическим расчётом стоимости  
- Просмотр полной истории бронирований (все заезды и выезды)  
- Поддержка двух режимов базы данных: H2 (в памяти — для быстрого тестирования) и MySQL  
- Простой и понятный интерфейс на русском языке  

- View list of all rooms and their current status  
- Check-in guests (specify name and number of nights)  
- Check-out guests with automatic cost calculation  
- View full booking history (all check-ins and check-outs)  
- Support for two database modes: H2 (in-memory — for quick testing) and MySQL  
- Simple and clear interface in Russian  

## Технологический стек | Tech Stack

- Java 17  
- Spring Boot 3.2+  
- Spring Data JPA + Hibernate  
- Thymeleaf + Bootstrap 5  
- Maven  
- База данных: H2 (in-memory) / MySQL  
- Lombok  
```
