package io.exeqt.engine.execution;

import lombok.Getter;

import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * Describes query and it characteristics for execution engine
 *
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

    /**
     * Convenient builder for {@link Query} object
     */
    public static final class QueryBuilder {
        private static final Object[] EMPTY = new Object[0];
        private String sql;
        private Object[] arguments = EMPTY;
        private int timeout = Integer.getInteger("query.timeout", 100);
        private int fetchSize = Integer.getInteger("query.fetch_size", 50);

        /**
         * Specifies query string which need to be executed. This is required parameter
         *
         * @param sql
         * @return
         */
        public QueryBuilder setSql(final String sql) {
            Objects.requireNonNull(sql);
            this.sql = sql;
            return this;
        }

        /**
         * Specifies prepared arguments for query. This is optional parameter
         *
         * @param arguments array of arguments in order that specified in query itself
         * @return builder
         */
        public QueryBuilder setArguments(Object[] arguments) {
            if (nonNull(arguments) && arguments.length > 0) {
                this.arguments = arguments;
            }
            return this;
        }

        /**
         * Specifies query timeout. This is optional parameter and configurable via global configuration
         *
         * @param timeout timeout in milliseconds
         * @return builder
         */
        public QueryBuilder setTimeout(int timeout) {
            if (timeout > 0) {
                this.timeout = timeout;
            }
            return this;
        }

        /**
         * Specifies amount of rows need to be fetched when streaming result rows. This is optional and configurable by default
         * global configuration
         *
         * @param fetchSize amount of rows to fetch
         * @return builder
         */
        public QueryBuilder setFetchSize(int fetchSize) {
            if (fetchSize > 0) {
                this.fetchSize = fetchSize;
            }
            return this;
        }

        /**
         * Creates an instance of {@link Query} object based on configured parameters
         *
         * @return query object
         */
        public Query build() {
            Objects.requireNonNull(sql);
            return new Query(sql, arguments, timeout, fetchSize);
        }
    }
}
