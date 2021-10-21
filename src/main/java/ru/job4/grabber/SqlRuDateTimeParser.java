package ru.job4.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Shegai Evgenii
 * @since 21.10.2021
 * @version 1.0
 * Преобразование даты из String to LocalDateTime
 */

public class SqlRuDateTimeParser implements DateTimeParser {

    private Map<String, String> months = new HashMap<>();

    public SqlRuDateTimeParser() {
        createMonths();
    }

    public Map<String, String> getMonths() {
        return months;
    }

    public void createMonths() {
        this.months.put("янв", "01");
        this.months.put("фев", "02");
        this.months.put("мар", "03");
        this.months.put("апр", "04");
        this.months.put("май", "05");
        this.months.put("июн", "06");
        this.months.put("июл", "07");
        this.months.put("авг", "08");
        this.months.put("сен", "09");
        this.months.put("окт", "10");
        this.months.put("ноя", "11");
        this.months.put("дек", "12");
    }

    /**
     * получаем часы в первой ячейке и во второй минуты если value = 1
     * получили объект LocalTime
     * получаем дату
     * @param parse строка содержащая дату
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime parse(String parse) {
        String[] timer = getTime(parse, 1);
        LocalTime time = LocalTime.of(Integer.parseInt(timer[0]), Integer.parseInt(timer[1]));
        LocalDate date = getDate(parse, 0);
        return LocalDateTime.of(date, time);
    }

    /**
     * метод делит строку по запятой и возвращает время без даты если value = 1 и только дату
     *  если value = 0
     * @param temp
     * @param value
     * @return string
     */
    private String splitString(String temp, int value) {
        String[] array = temp.split(", ");
        return array[value];
    }

    private LocalDate getDate(String parse, int value) {
        StringBuilder builder = new StringBuilder();
        builder.append("20");
        String date = splitString(parse, value);
        LocalDate result;
        if (date.contains("сегодня")) {
            result = LocalDate.now();
        } else if (date.contains("вчера")) {
            result = LocalDate.now().minusDays(1);
        } else {
            String[] arr = date.split(" ");
            // получаем месяц в цифрах по ключу слово
            arr[1] = months.get(arr[1]);
            // если год не равен 21 тогда его меняем на 20 + число из массива arr
            if (!arr[2].equals("21")) {
                builder.append(arr[2]);
                arr[2] = builder.toString();
                // иначе записываем в ячейку текущий год в формате 2021
            } else {
                Calendar calendar = Calendar.getInstance();
                arr[2] = String.valueOf(calendar.get(Calendar.YEAR));
            }
            result = LocalDate.of(Integer.parseInt(arr[2]),
                    Integer.parseInt(arr[1]), Integer.parseInt(arr[0]));
        }
        return result;
    }

    /**
     * метод пригимает строку время 00:00 и возвращает массив
     * @param parse
     * @param value
     * @return String[]
     */
    private String[] getTime(String parse, int value) {
        String date = splitString(parse, value);
        return date.split(":");
    }

    public String removeCharT(LocalDateTime value) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(value);
    }

    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        // Получаю элементы по указанным селектам
        Elements row2 = doc.select(".altCol");
        // получаю по атрибуту - текст и помещаю его в лист
        for (int i = 0; i < row2.size(); i++) {
            String text = row2.get(i).getElementsByAttribute("style").text();
            // текст содержится в каждой не четной ячейке листа
            if (i % 2 == 1) {
                list.add(text);
            }
        }
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        List<LocalDateTime> time = new ArrayList<>();
        for (String value : list) {
            // парсим String в LocalDateTime и помещаем его в list
            LocalDateTime val = parser.parse(value);
            time.add(val);
        }
        time.stream().forEach(x -> System.out.println(x));
        list.clear();
        for (LocalDateTime value : time) {
            list.add(parser.removeCharT(value));
        }
        list.stream().forEach(x -> System.out.println(x));
    }
}
