package io.exeqt.vjdbc.engine.execution.result

import io.exeqt.engine.execution.conversion.ConversionService
import spock.lang.Specification

import java.sql.ResultSet

/**
 *
 * @author anatolii vakaliuk
 */
class ResultSetRowAdapterSpecification extends Specification {

    def "column labels should return column names with preserved order" () {
        given:
            def resultSetMock = Mock(ResultSet)
            def conversionServiceMock = Mock(ConversionService)
            def resultSetAdapter = new ResultSetRowAdapter(conversionServiceMock, resultSetMock)
        when:
            def labels = resultSetAdapter.columnLabels()
        then:
            labels.size() == 3
    }
}
