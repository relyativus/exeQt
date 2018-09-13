package io.exeqt.vjdbc.engine;

import io.exeqt.engine.execution.conversion.ConversionService;
import io.exeqt.engine.session.SessionFactory;
import io.exeqt.vjdbc.engine.session.JdbcSessionFactory;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;

import javax.sql.DataSource;

/**
 * @author anatolii vakaliuk
 */
public class VertxJdbcSessionFactory {
    private final DataSource dataSource;
    private final ConversionService conversionService;

    public VertxJdbcSessionFactory(final DataSource dataSource, ConversionService conversionService) {
        this.dataSource = dataSource;
        this.conversionService = conversionService;
    }

    public SessionFactory create() {
        final JDBCClient client = JDBCClient.create(Vertx.vertx(), dataSource);
        return new JdbcSessionFactory(io.vertx.reactivex.ext.jdbc.JDBCClient.newInstance(client), conversionService);
    }
}
