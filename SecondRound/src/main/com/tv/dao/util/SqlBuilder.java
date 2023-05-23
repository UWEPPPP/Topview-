package tv.dao.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * sql builder
 *
 * @author 刘家辉
 * @date 2023/05/23
 */
public class SqlBuilder {
    private List<String> columns = new ArrayList<>();
    private String table;
    private String where;
    private String groupBy;
    private String having;
    private String orderBy;
    private String limit;

    public SqlBuilder select(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public SqlBuilder update(String table) {
        this.table = table;
        return this;
    }

    public SqlBuilder insert(String table) {
        this.table = table;
        return this;
    }

    public SqlBuilder delete(String table) {
        this.table = table;
        return this;
    }


    public SqlBuilder set(String... columns) {
        for (int i = 0; i < columns.length; i++) {
            columns[i] = new StringBuilder(columns[i]).append("=?").toString();
        }
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public SqlBuilder setInsert(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }


    public SqlBuilder from(String table) {
        this.table = table;
        return this;
    }

    public SqlBuilder where(String... where) {
        for (int i = 0; i < where.length; i++) {
            if (!Objects.equals(where[i], "AND") && !Objects.equals(where[i], "OR")) {
                if (where[i].charAt(0) == '!') {
                    where[i] = where[i].substring(1);
                    where[i] = new StringBuilder(where[i]).append("!=?").toString();
                } else if (where[i].charAt(0) == '@') {
                    where[i] = where[i].substring(1);
                    where[i] = new StringBuilder(where[i]).append(" LIKE ?").toString();
                } else {
                    where[i] = new StringBuilder(where[i]).append("=?").toString();
                }
            }
        }

        this.where = String.join(" ", where);
        return this;
    }

    public SqlBuilder groupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public SqlBuilder having(String having) {
        this.having = having;
        return this;
    }

    public SqlBuilder orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public SqlBuilder limit(int limit) {
        this.limit = String.valueOf(limit);
        return this;
    }

    public String buildSelect() {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (columns.size() > 0) {
            sql.append(String.join(", ", columns));
        } else {
            sql.append("*");
        }
        sql.append(" FROM ").append(table);
        if (where != null&& !"".equals(where)) {
            sql.append(" WHERE ").append(where);
        }
        if (groupBy != null) {
            sql.append(" GROUP BY ").append(groupBy);
        }
        if (having != null) {
            sql.append(" HAVING ").append(having);
        }
        if (orderBy != null) {
            sql.append(" ORDER BY ").append(orderBy);
        }
        if (limit != null) {
            sql.append(" LIMIT ").append(limit);
        }
        return sql.toString();
    }

    public String buildUpdate() {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(table).append(" SET ");
        if (columns.size() > 0) {
            sql.append(String.join(", ", columns));
        } else {
            sql.append("*");
        }
        if (where != null&&!"".equals(where)) {
            sql.append(" WHERE ").append(where);
        }
        return sql.toString();
    }

    public String buildInsert() {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(table);
        if (columns.size() > 0) {
            sql.append(" (");
            sql.append(String.join(", ", columns));
            sql.append(") VALUES (");
            for (int i = 0; i < columns.size(); i++) {
                if (i == 0) {
                    sql.append("?");
                } else {
                    sql.append(",?");
                }
            }
            sql.append(")");
        } else {
            sql.append("*");
        }
        if (where != null&&!"".equals(where)) {
            sql.append(" WHERE ").append(where);
        }
        return sql.toString();
    }

    public String buildDelete() {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(table);
        if (where != null&&!"".equals(where)) {
            sql.append(" WHERE ").append(where);
        }
        return sql.toString();
    }
}