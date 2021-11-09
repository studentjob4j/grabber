package ru.job4.template;

/**
 * Шаблонизатор
 * @author Shegai Evgenii
 * @since 09.11.2021
 * @version 1.0
 */

import java.util.Map;

public interface Generator {

    String produce(String template, Map<String, String> args);
}
