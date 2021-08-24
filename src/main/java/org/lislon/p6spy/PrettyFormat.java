package org.lislon.p6spy;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class PrettyFormat implements MessageFormattingStrategy {

    public static final String ENV_FORMAT_CONFIG_NAME = "spy.lislon.pretty";
    public static final Mode DEFAULT_MODE = Mode.MULTI;

    private final Mode currentMode = getModeFromEnv();

    enum Mode {
        SINGLE, MULTI
    }

    private static Mode getModeFromEnv() {
        try {
            String strMode = System.getProperty(ENV_FORMAT_CONFIG_NAME);
            if (strMode == null) {
                strMode = System.getenv(ENV_FORMAT_CONFIG_NAME);
            }
            if (strMode != null) {
                Set<String> allowededModes = Arrays.stream(Mode.values()).map(Enum::name).collect(Collectors.toSet());
                if (allowededModes.contains(strMode.toUpperCase())) {
                    return Mode.valueOf(strMode.toUpperCase());
                } else {
                    System.err.println(ENV_FORMAT_CONFIG_NAME + " can be only one of following: " + String.join(",", allowededModes));
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to get " + ENV_FORMAT_CONFIG_NAME + ":" + e.getMessage());
        }
        return DEFAULT_MODE;
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
            String sql, String url) {
        String time = Instant.ofEpochMilli(Long.parseLong(now))
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .format(DateTimeFormatter.ISO_LOCAL_TIME);

        if (currentMode == Mode.MULTI) {
            sql = formatSql(category, sql);
        }
        return time + " " + elapsed + "ms " + sql + "";
    }

    private String formatSql(String category, String sql) {
        if (sql == null || sql.trim().equals("")) {
            return "";
        }
        if (Category.STATEMENT.getName().equals(category)) {
            String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
            if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                sql = FormatStyle.DDL.getFormatter().format(sql);
            } else {
                sql = FormatStyle.BASIC.getFormatter().format(sql);
            }
        }

        return sql;
    }
}