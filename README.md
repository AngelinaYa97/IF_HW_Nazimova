# Автотесты для Jira (Selenide + Allure + JUnit 5)

## Описание
Проект содержит UI-автотесты для проверки функциональности Jira (edujira.ifellow.ru):
- Авторизация пользователя
- Открытие проекта `Test`
- Проверка статуса и версии задачи
- Создание бага с заполнением всех полей
- Проверка счётчика задач в проекте
- Перевод задачи в статус "Выполнено"/"Готово"

## Архитектура

Проект реализован с использованием паттерна Page Object.

Особенности:
- Все элементы находятся с помощью XPath
- Каждый шаг теста содержит assert-проверку
- Тесты разделены от PageObject классов
- Конфигурация вынесена в config.properties

## Технологии
- Java 25
- Maven
- Selenide 7.8.1
- JUnit 5
- Allure 2.27.0
- AspectJ 1.9.24

## Структура проекта
```text
homework/
├── src/
│   ├── main/java/...      (Page Object классы)
│   ├── test/java/...      (тесты)
│   └── test/resources/    (config.properties)
├── .gitignore
├── pom.xml
└── README.md
```
## Запуск тестов и генерация Allure-отчёта
### Запуск из консоли
```bash
# 1. Запустить тесты
mvn clean test

# 2. Сгенерировать Allure-отчёт (папка target/site/allure-maven-plugin)
mvn allure:report

# 3. Открыть отчёт во временном веб-сервере
mvn allure:serve
```
### Запуск из IntelliJ IDEA
Lifecycle → clean → test → Plugins → allure → allure:serve

## Результаты
- Отчёт Allure с шагами @Step доступен после выполнения mvn allure:serve
- Скриншоты падений и логи сохраняются автоматически

## ⚙️ Конфигурация

### Файл:

src/test/resources/config.properties

### Пример:

```properties
base.url=https://edujira.ifellow.ru
username=AT5
password=Qwerty123
issue.key=TEST-295185
  ```

## Предварительные требования
- Установлены **JDK 25** и **Maven**

## Требования к репозиторию

В корне проекта должны находиться только:

- src/
- pom.xml
- README.md
- .gitignore

## Итог

Проект позволяет:

- Использовать паттерн Page Object для поддержки масштабируемости тестов
- Запускать автотесты через Maven (mvn clean test)
- Генерировать Allure-отчёты из консоли
- Проверять ключевые UI-сценарии Jira
- Поддерживать чистую структуру репозитория