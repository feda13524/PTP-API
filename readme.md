# ParseThePrice API
[📡 Тестовый эндпоинт](https://api.fedosik.ru/test)  
### Использование
#### Парсинг цены с сайта
```curl
curl -X POST api.fedosik.ru/price \
-H "Content-Type: application/json" \
-d "{\"url\":\"ВАША ССЫЛКА\"}"
```
#### Парсинг с помощью ИИ
```curl
curl -X POST api.fedosik.ru/parse \
-H "Content-Type: application/json" \
-d "{\"url\":\"ВАША ССЫЛКА\", \"message\":\"ЧТО ХОТИТЕ НАЙТИ\"}"
```

### Ограничения
#### Невозможен парсинг следующих сайтов из-за защиты от автоматизированного ПО:
- wildberries.ru
- ozon.ru
- market.yandex.ru
- dns-shop.ru
- avito.ru и других
<br><br>
[GitHub](https://github.com/feda13524/ParseThePrice) | [Telegram](https://t.me/feda13524)