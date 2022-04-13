package com.example.demo

import com.example.demo.service.TestService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.transaction.KafkaTransactionManager
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.support.TransactionTemplate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ContextConfiguration
class TestKafkaConsumer2Tests : BaseIntegrationTests() {

	@TestConfiguration
	class TestConfig

	@Autowired
	private lateinit var kafkaTemplate: KafkaTemplate<String,String>

	@SpyBean
	private lateinit var testService: TestService

	private val countDownLatch = CountDownLatch(1)

	@Autowired
	private lateinit var kafkaTransactionManager: KafkaTransactionManager<*,*>

	@Test
	fun `it should consume published message`() {
		//given
		val message = "hello world"
		Mockito.doCallRealMethod().doAnswer {
			countDownLatch.countDown()
		}.`when`(testService).process(message)

		//when
		val txTemplate = TransactionTemplate(kafkaTransactionManager)
		txTemplate.executeWithoutResult {
			kafkaTemplate.send("test-topic",message).get()
		}

		countDownLatch.await(5,TimeUnit.SECONDS)

		//then
		Mockito.verify(testService, Mockito.times(1)).process(message)
	}
}
