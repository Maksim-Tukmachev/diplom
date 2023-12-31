# Дипломный проект профессии «Тестировщик ПО»

# Веб-сервис "Путешествие дня"

Дипломный проект состоит в автоматизации тестирования комплексного сервиса, который взаимодействует с СУБД и API банка.

Суть приложения заключается в предоставлении возможности покупки тура по заданной цене с использованием двух способов оплаты:

1. Оплата с помощью дебетовой карты.
2. Оплата с помощью кредитной карты.

Само приложение не обрабатывает данные карт, а передает их банковским сервисам:

1. Сервису платежей (Payment Gate).
2. Кредитному сервису (Credit Gate).

Приложение сохраняет информацию о способе оплаты и успешности платежа в собственной СУБД.

Тестовые сценарии будут включать в себя симуляцию платежей с использованием дебетовых и кредитных карт, проверку правильности обработки платежей со стороны банковских сервисов, а также проверку сохранения информации о платежах в СУБД приложения.

Результатом дипломного проекта будет полный комплект автоматизированных тестов, покрывающих все основные функциональные и нефункциональные требования сервиса, а также отчет о выполненных тестах и обнаруженных проблемах.

---

## Начало работы

Github - склонировать проект себе на ПК для последующего запуска и тестирования.

Для запуска тестов на вашем ПК должно быть установлено следующее ПО:

- IntelliJ IDEA
- Git
- Docker Desktop
- Google Chrome (или другой браузер)

---

### Установка и запуск

1. Запустить контейнеры: MySQL, Node.js
    ```
    docker-compose up --build
    ```

2. Запустить SUT
    ```
    java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
    ```

3. Запустить тесты
    ```
    ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
    ```

#### Для работы с Postgres

1. Запустить контейнеры: Postgres, Node.js
    ```
    docker-compose up --build  
    ```

2. Запустить SUT
    ```
    java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
    ```

3. Запустить тесты
    ```
    ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
    ```

#### Allure

Для формирования отчета в Allure необходимо выполнить команды
```
./gradlew clean test allureReport 
./gradlew allureServe
```
## Документация

- [План автоматизации тестирования](https://github.com/Maksim-Tukmachev/diplom/blob/main/Documentation/Plan.md)
