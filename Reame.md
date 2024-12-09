# Telegram Бот для Погоды

## Описание

Telegram Бот для Погоды — это проект,
который позволяет пользователям получать информацию о погоде в любом городе через Telegram.
Бот поддерживает команды для получения текущей погоды,
истории запросов и другие функции.

## Функциональность

- Получение текущей погоды в указанном городе.
- История запросов погоды.

## Технологии

- **Spring Boot**
- **MySQL**
- **Telegram Bots**: library for creating Telegram Bots.

## Шаги установки

1. **Клонируйте репозиторий:**

    ```sh
    git clone https://github.com/Binarer/WeatherBot.git
    cd your-repo
    ```

2. **Настройте application.properties:**

   Создайте базу данных MySQL и настройте подключение в файле `application.properties`.

    ```properties
    spring.datasource.url=jdbc\:mysql://localhost:3306/
    spring.datasource.username=
    spring.datasource.password=
    ```
    Добавьте ключ API для получения погоды.

    ```properties
    weather.api.key=api_key
    weather.api.url=https://api.openweathermap.org/data/2.5/weather
    ```
   
    Добавьте токен и имя вашего Telegram бота.
    ```properties
    bot.token=BOT_TOKEN
    bot.name=BOT_NAME
    ```
3. **Соберите проект:**

   ```sh
   ./gradlew build
   ```

4. **Запустите приложение:**

    ```sh
    ./gradlew bootRun
    ```