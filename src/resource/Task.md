Требования:

1) Калькулятор должен поддерживать операции: сложение (SUM), вычитание (SUB), умножение (MUL) и деление (DIV).

2) Уровень вложенности операций не ограничен.

3) В качестве операндов используются неотрицательные целые числа.

4) Результат расчета выражения - вещественное число.

5) Калькулятор должен обрабатывать произвольное число выражений.

6) Входящие данные передаются в XML файле, валидном схеме SimpleCalculator.xsd.

7) Результат работы приложения записывается в XML файл, валидный схеме SimpleCalculator.xsd.

8) Основной класс приложения должен имплементировать интерфейс SimpleCalculator.java. Метод calculate должен реализовывать логику обработки входящего файла, где file задает путь до входящего файла, а resultFile - путь до файла с результатом вычислений.

 

Плюсом будет:

1) Использование maven в качестве build system

2) Использование logging framework'а (jul, log4j, slf4j, logback)

3) Использование testing framework'а (testng, junit)

4) Executable jar как результат сборки проекта (который можно запустить командой java -jar {jar_file} {list of arguments})

 

Комментарии:

1) Нет ограничения на способ задать путь до входящего файла и до файла с результатом (через аргумент командной строки, через графический интерфейс и т.д.).

2) Проект будет оцениваться комплексно: функциональность, алгоритм работы, качество и стиль написания кода, соблюдение naming conventions и т.д.