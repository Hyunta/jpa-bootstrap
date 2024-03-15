package persistence.sql.dml;

import persistence.sql.meta.Column;
import persistence.sql.meta.IdColumn;
import persistence.sql.meta.Table;

import java.lang.reflect.Field;

public class DeleteQueryBuilder {
    private static final String DELETE_QUERY_TEMPLATE = "DELETE FROM %s";
    private static final String WHERE_CLAUSE_TEMPLATE = " WHERE %s = %s";

    private static class InstanceHolder {
        private static final DeleteQueryBuilder INSTANCE = new DeleteQueryBuilder();
    }

    public static DeleteQueryBuilder getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public String build(Object object) {
        Table table = Table.from(object.getClass());
        IdColumn idColumn = table.getIdColumn();
        String deleteQuery = String.format(DELETE_QUERY_TEMPLATE, table.getName());
        String whereClause = String.format(WHERE_CLAUSE_TEMPLATE, idColumn.getName(), getObject(object, idColumn));
        return deleteQuery + whereClause;
    }

    private Object getObject(Object object, Column column) {
        try {
            Field field = column.getField();
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + column.getName(), e);
        }
    }
}
