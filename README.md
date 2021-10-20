[![Build Status](https://app.travis-ci.com/studentjob4j/grabber.svg?branch=master)](https://app.travis-ci.com/studentjob4j/grabber)
[![codecov](https://codecov.io/gh/studentjob4j/grabber/branch/master/graph/badge.svg?token=LT5573R87S)](https://codecov.io/gh/studentjob4j/grabber)
Агрегатор Java Вакансий
Система запускается по расписанию. Период запуска указывается в настройках - app.properties. 

Первый сайт будет sql.ru. В нем есть раздел job. Программа должна считывать все вакансии относящиеся к Java и записывать их в базу.

Доступ к интерфейсу будет через REST API.

 

Расширение.

1. В проект можно добавить новые сайты без изменения кода.

2. В проекте можно сделать параллельный парсинг сайтов.