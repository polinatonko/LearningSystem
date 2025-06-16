package org.example.learningsystem.core.db.util;

import lombok.NoArgsConstructor;
import org.example.learningsystem.multitenancy.db.exception.DatabaseOperationException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DatabaseUtils {

    private static final String SCHEMA_NAME_PATTERN = "\\w+";

    public static void executeSchemaUpdate(DataSource dataSource, String queryPattern, String schema) {
        validateSchemaName(schema);
        var query = queryPattern.formatted(schema);
        try (var statement = dataSource.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }

    public static List<String> queryForStringList(DataSource dataSource, String query) {
        try (var statement = dataSource.getConnection().createStatement()) {
            var result = statement.executeQuery(query);
            var list = new ArrayList<String>();
            while (result.next()) {
                list.add(result.getString(1));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }

    private static void validateSchemaName(String schema) {
        if (!schema.matches(SCHEMA_NAME_PATTERN)) {
            throw new IllegalArgumentException("Invalid schema name provided [schema = %s]".formatted(schema));
        }
    }
}
