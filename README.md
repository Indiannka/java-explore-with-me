# java-explore-with-me

 *Сервис предоставляет возможность создававать любые меорприятия и регистрировать заявки на участие в них.*

# Архитектура проекта

- ewm-service   - REST сервис, который содержит основную логику работу с приложением
- stats-client  - встраиваемый в основное приложение клиент статистики для отправки запросов в сервис статистики
- starts-server - REST сервис для хранения и возврата статистики просмотров событий
 
# Запуск
 
- mvn clean install
- docker-compose build
- docker-compose up

# Ссылка на PR

 https://github.com/Indiannka/java-explore-with-me/pull/2