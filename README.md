Список команд
1) NEWPRODUCT <name> - Создать товар - на вход подается уникальное
наименование товара
2) PURCHASE <name> <amount> <price> <date> - Закупить товар - на вход подается
наименование товара, кол-во закупленного товара, цена единицы товара и дата
закупки
3) DEMAND <name> <amount> <price> <date> - Продать товар - на вход подается
наименование товара, кол-во проданного товара, цена единицы товара и дата
продажи
4) SALESREPORT <name> <date> - Рассчитать прибыльность - на вход подается
наименование товара и дата. Результат - прибыль на указанную дату - выводится
в стандартный поток вывода
Пример потока команд
NEWPRODUCT iphone
NEWPRODUCT iphone
PURCHASE iphone 1 1000 01.01.2017
PURCHASE iphone 2 2000 01.02.2017
DEMAND iphone 2 5000 01.03.2017
SALESREPORT iphone 02.03.2017

программа должна вывести:
OK
ERROR
OK
OK
OK
7000
Особенности объектной модели
● нельзя завести 2 раза товар с одинаковым наименованием
● перед тем как покупать/продавать товар, его необходимо создать
● цены товара должны быть больше нуля
Функциональные требования
● команды должны валидировать входные параметры
● программа не должна "упасть" на некорректных данных
