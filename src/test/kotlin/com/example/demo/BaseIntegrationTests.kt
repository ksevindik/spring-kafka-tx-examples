package com.example.demo

import org.h2.tools.Server
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ActiveProfiles
import javax.sql.DataSource

@SpringBootTest
@EmbeddedKafka(brokerProperties = ["transaction.state.log.replication.factor=2"],count = 2)
@ActiveProfiles("test")
abstract class BaseIntegrationTests {
    @Autowired
    protected lateinit var dataSource: DataSource

    fun openH2Console() {
        Server.startWebServer(DataSourceUtils.getConnection(dataSource))
    }
}