# ProMaSy - Procurement Management System

Основне завдання ProMaSy — спрощення процесу створення та відслідковування стану замовлень, які створює організація для закупівель матеріалів, обладнання та послуг. Всередині ProMaSy можливо створювати фінансові рахунки та визначати їх як джерела фінансування створених замовлень, вести облік залишку коштів на цих рахунках та формувати звіти за використаними коштами. Модуль, що відповідає за створення замовлень, спрощує процедуру формування замовлення, допомагає у виборі джерел фінансування, коду Єдиного закупівельного словника (набуття чинності: з 01.01.2016р. [Наказ МЕРіТУ № 1474 від 19.11.2015р](http://zakon5.rada.gov.ua/laws/show/z1461-15)), відповідних виробників та постачальників цього замовлення. Створене замовлення можливо роздрукувати у вигляді визначеної форми.

Всі введені дані зберігаються на сервері та, у разі вимкнення сервера, будуть недоступні на користувацькому персональному комп’ютері (ПК).

## Системні вимоги

### Системні вимоги до сервера з базою даних

Мінімальні вимоги до сервера відповідають [мінімальним вимогам системи керуванням баз даних PostgreSQL](https://www.postgresql.org/docs/current/static/install-requirements.html).
Для роботи програми необхідно мати на сервері встановлене програмне забезпечення PostgreSQL не нижче версії 9.6. Останню версію завжди можливо завантажити з [офіційного сайту PostgreSQL](https://www.postgresql.org/download/)

### Системні вимоги до користувацького ПК

Для роботи програми необхідна встановлена віртуальна машина Java не нижче версії 8.
Останню версію завжди можливо завантажити з [офіційного сайту Java](https://www.java.com/en/download/manual.jsp)

**УВАГА!** Для встановлення Java Вам необхідно мати адміністраторські привілеї на ПК.

Для встановлення та використання ProMaSy адміністраторські привілеї не потрібні.

#### Мінімальні вимоги:

Аналогічні [вимогам Java 8](https://www.java.com/en/download/help/sysreq.xml)

#### Рекомендовані вимоги:

- Операційна система: Windows 7, 8, 10
- Архітектура системи: 32- або 64-біт
- Процесор: архітектури х86 з частотою від 1ГГц
- Оперативна пам’ять: від 1 Гб
- Вільне місце на диску: від 300 Мб

## Налаштування

### Налаштування сервера

1. Встановіть обрану версію системи керуванням баз даних PostgreSQL (на версіях нижче 9.6 робота програми не гарантується). В процесі встановлення вам буде запропоновано ввести логін та пароль адміністратора та порт доступу до бази даних. Збережіть їх у надійному місці!
1. Перевірте доступність вибраного порту з локальної мережі організації, де буде використовуватися ProMaSy.
1. Відкрйте командний рядок (наприклад Win+R → cmd → OK) та перейдіть до теки з PostgreSQL\bin\ (наприклад ```cd c:\Program Files\PostgreSQL\9.6\bin\```).
1. Створіть структуру таблиць для роботи з ProMaSy з використанням файлу [db_clean.sql](/sql/db_clean.sql)

  - введіть команду ```psql -h адреса_сервера -p порт -d назва_бази_даних -U логін_адміністратора -f шлях_до_файлу```
    Наприклад: ```psql -h 127.0.0.1 -p 5432 -d postgres -U postgres -f D:\db_clean.sql```
  - введіть пароль адміністраторського акаунту
  - дочекайтеся появи напису «All done!» (Орієнтовний час завершення — 2 хв)

### Налаштування при першому запуску програми для адміністратора

**УВАГА!** Для успішного завершення цього етапу Вам знадобляться наступні дані:

а) дані адміністратора:

  - ПІБ
  - електронна пошта
  - внутрішній телефон
  - логін і пароль
  - повна назва організації, відділу та підрозділу до якого належить адміністратор

б) відомості про організацію:

  - повна назва організації
  - повна адреса
  - телефон
  - факс
  - електронна пошта
  - ЄДРПОУ

Введені дані можливо буде пізніше змінити з адміністраторського акаунту.

Наведені налаштування можливо проводити на сервері або будь-якому ПК з доступом до локальної мережі організації.

1. Встановіть віртуальну машину Java не нижче версії 8.
1. Розпакуйте архів з ProMaSy до будь-якої теки, яка не потребує адміністраторських привілеїв для доступу (наприклад ```D:\``` або ```C:\Users\<ім’я користувача>\```).
1. Відкрийте командний рядок (наприклад Win+R → cmd → OK) та перейдіть до теки з ProMaSy (наприклад “cd d:\promasy”).
1. Запустіть команду ```java -jar ProMaSy-ххх.jar --config --log``` (де ххх — версія ProMaSy). Це призведе до запуску ProMaSy в режимі конфігурації настройок доступу до сервера із збереженням логу роботи програми.
1. У вікні настройок введіть IP адресу сервера (за замовчуванням localhost), порт доступу до бази даних (за замовчуванням 5432), назву бази даних (за замовчуванням postgres). Назву схеми змінювати не потрібно, підтримка схем з іншими назвами поки відсутня.

**ВАЖЛИВО!** Ні в якому випадку не змінюйте логін та пароль доступу до бази даних на адміністраторські, оскільки це може становити небезпеку для цілісності даних при подальшому використанні програми.

1. Після натиснення кнопки “ОК” ProMaSy запропонує створити акаунт адміністратора.
1. Для створення акаунту адміністратора натисніть “Так” у діалоговому вікні та в наступному вікні введіть необхідні персональні дані адміністратора.
1. Для того щоб внести дані про організацію натисніть “+” та в наступному вікні натисніть “+” навпроти поля “Організація”.
1. У вікні додавання даних про організацію введіть необхідні дані та натисніть “Створити”.
1. Для створення відділів. Виберіть з випадаючого списку в полі “Організація” необхідну організацію та в полі “Відділ” введіть назву відділу. Для створення нового відділу натисніть кнопку “+” навпроти поля “Відділ”. Після створення відділу до нього автоматично призначається новий підрозділ з назвою “<підрозділ відсутній>”.
1. Для створення підрозділів. Виберіть з випадаючого списку в полі “Відділ” необхідний відділ та в полі “Підрозділ” введіть назву підрозділу. Для створення нового підрозділу натисніть кнопку “+” навпроти поля “Підрозділ”.
1. Після створення структури організації (або хоча б організації, відділу та підрозділу де працює адміністратор) закрийте вікно створення структури організації. У вікні створення адміністратора необхідно вибрати відповідність до цієї організації.
1. Завершити створення акаунту адміністратора необхідно натисненням кнопки “Створити користвувача”.
1. Після створення акаунту адміністратора Вам буде запропоновано повторно завантажити ProMaSy та увійти до програми із зареєстрованими логіном та паролем.
1. Програму можливо використовувати запускаючи файл ProMaSy-ххх.jar (де ххх — версія ProMaSy) в теці з програмою. Якщо при наступному запуску ProMaSy не зможе з’єднатися з сервером, буде запропоновано повторно ввести параметри з’єднання.

**ВАЖЛИВО!** Ні в якому випадку не змінюйте логін та пароль доступу до бази даних на адміністраторські, оскільки це може становити небезпеку для цілісності даних при подальшому використанні програми.

**ВАЖЛИВО!** Ви можете додати згенерований файл settings.ser з налаштуваннями доступу до сервера до архіву з ProMaSy для спрощення процесу налаштування ProMaSy користувачами.

### Налаштування при першому запуску програми для користувача

**УВАГА!** Перед початком налаштування перевірте наявність в архіві ProMaSy файла settings.ser або чи маєте Ви надані адміністратором IP адресу та порт доступу до бази даних сервера.

**УВАГА!** Якщо Ви ще не маєте власного логіна і пароля доступу, для успішного завершення цього етапу Вам знадобляться наступні дані:

  - ПІБ
  - електронна пошта
  - внутрішній телефон
  - логін і пароль
  - повна назва організації, відділу та підрозділу до якого Ви належите

1. Встановіть віртуальну машину Java не нижче версії 8 (якщо не маєте адміністраторських привілеїв на ПК зверніться до локального адміністратора).
1. Розпакуйте архів з ProMaSy до будь-якої теки, яка не потребує адміністраторських привілеїв для доступу (наприклад ```D:\``` або ```C:\Users\<ім’я користувача>```).
1. Запустіть в створеній теці файл ProMaSy-ххх.jar (де ххх — версія ProMaSy).
1. Якщо з’явиться помилка про відсутність доступу до бази даних змініть у вікні, що з’явилось, IP адресу та/або порт доступу до бази даних, які були надані адміністратором. Для збереження налаштувань та повторного з’єднання натисніть “ОК”.
1. У вікні вводу даних входу Ви введіть отриманий логін і пароль та натисніть “Вхід”. Після цього з’явиться головне вікно програми. В такому випадку налаштування є завершеним. Якщо Ви не маєте власних логіна та пароля входу натисніть на кнопку зі знаком “+” - “Створити нового користувача”.
1. Введіть необхідні персональні дані та вигадані логін і пароль користувача, виберіть з випадаючого списку належність до організації та її підрозділів. Для створення облікового запису натисніть кнопку “Створити користувача”. При вдалому створенні облікового запису Ви побачите інформаційне вікно з підтверженням.
1. Для входу до системи повторно запустіть ProMaSy-ххх.jar (де ххх — версія ProMaSy), введіть Ваші логін та пароль та натисніть “Вхід”.

## Оновлення

**УВАГА!** Перед початком налаштування перевірте наявність в архіві з оновленням ProMaSy файла settings.ser або чи маєте Ви, надані адміністратором, IP адресу та порт доступу до бази даних сервера.

Оновлення встановлюються у ручному режимі та не потребують адміністраторських привілеїв на ПК.

1. Повністю видаліть теку зі старою версією ProMaSy.
1. Розпакуйте архів з оновленням ProMaSy до будь-якої теки, яка не потребує адміністраторських привілеїв для доступу (наприклад ```D:\``` або ```C:\Users\ім’я користувача```).
1. Для входу до системи запустіть ProMaSy-ххх.jar (де ххх — версія ProMaSy), введіть Ваші логін та пароль та натисніть “Вхід”.

## Параметри запуску з командного рядку

ProMaSy, як і будь-який Java аплет, може бути запущений з командного рядку. При цьому до командного рядку будуть виведені всі логи роботи програми та розгорнута інформація про виявлені помилки в роботі.
Для цього в командному рядку введіть:

```java -jar ProMaSy-ххх.jar``` (де ххх — версія ProMaSy)

Окрім того ProMaSy також може бути запущена з додатковими аргументами командного рядку. Доступні наступні аргументи:

```-c``` або ```--config``` — запускає вікно налаштувань доступу до бази даних одразу після запуску програми

```-g``` або ```--generate``` — програма запускається в режимі створення та модифікації таблиць. Варто використовувати з командою --config, де необхідно задавати логін і пароль адміністратора БД

```-s``` або ```--statistics``` — після завершення сеансу роботи програми відображає статистику звернення програми до БД. Знижує швидкість роботи програми!

```-l``` або ```--log``` — після завершення сеансу роботи програми зберігає лог роботи у окремий текстовий файл в теці з програмою

Приклад використання додаткових аргументів:

Запуск програми з відображенням вікна конфігурування доступу до БД та збереженням логу програми до текстового файлу:
```java -jar ProMaSy-ххх.jar --config --log```

## Автори

Зі списком авторів можливо ознайомитися у файлі [AUTHORS.txt](/src/main/resources/AUTHORS.txt)

## Ліцензія

Програмне забезпечення Procurement Management System або Система керування закупівлями (“ProMaSy”) розповсюджується під ліцензією Apache, версія 2.0 (“Ліцензія”); Ви можете використовувати це програмне забезпечення лише у відповідності до Ліцензії. Ви можете переглянути Ліцензію у файлі [LICENSE.md](LICENSE.md) або отримати копію за посиланням http://www.apache.org/licenses/LICENSE-2.0

Якщо не передбачено чинним законодавством або не погоджено в письмовій формі, програмне забезпечення, що поширюються за Ліцензією, поширюється у існуючому вигляді "ЯК Є", БЕЗ БУДЬ-ЯКИХ ГАРАНТІЙ І УМОВ, виражених або неявних
