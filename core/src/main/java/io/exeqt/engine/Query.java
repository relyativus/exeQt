package io.exeqt.engine;

import lombok.Getter;

import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * @author anatolii vakaliuk
 */
@Getter
public class Query {
    private String sql;
    private Object[] arguments;
    private int timeout;
    private int fetchSize;

    private Query(String sql, Object[] arguments, int timeout, int fetchSize) {
        this.sql = sql;
        this.arguments = arguments;
        this.timeout = timeout;
        this.fetchSize = fetchSize;
    }

    public static QueryBuilder builder() {
        return new QueryBuilder();
    }

    public static final class QueryBuilder {
        private static final Object[] EMPTY = new Object[0];
        private String sql;
        private Object[] arguments = EMPTY;
        private int timeout = Integer.getInteger("query.timeout", 100);
        private int fetchSize = Integer.getInteger("query.fetch_size", 50);

        public QueryBuilder setSql(final String sql) {
            Objects.requireNonNull(sql);
            this.sql = sql;
            return this;
        }

        public QueryBuilder setArguments(Object[] arguments) {
            if (nonNull(arguments) && arguments.length > 0) {
                this.arguments = arguments;
            }
            return this;
        }

        public QueryBuilder setTimeout(int timeout) {
            if (timeout > 0) {
                this.timeout = timeout;
            }
            return this;
        }

        public QueryBuilder setFetchSize(int fetchSize) {
            if (fetchSize > 0) {
                this.fetchSize = fetchSize;
            }
            return this;
        }

        public Query build() {
            Objects.requireNonNull(sql);
            return new Query(sql, arguments, timeout, fetchSize);
        }
    }
}
