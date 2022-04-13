package com.example.demo

import com.example.demo.model.Foo
import com.example.demo.repository.jpa.FooRepository
import com.example.demo.service.TestService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.support.TransactionTemplate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ContextConfiguration
class TestKafkaConsumer1Tests : BaseIntegrationTests() {

	@TestConfiguration
	class TestConfig

	@Autowired
	private lateinit var kafkaTemplate: KafkaTemplate<String,String>

	@SpyBean
	private lateinit var testConsumerService: TestService

	private val countDownLatch = CountDownLatch(1)

	@Autowired(required = false)
	private lateinit var jpaTransactionManager: JpaTransactionManager



	@Autowired
	private lateinit var fooRepository: FooRepository



	@Test
	fun `it should consume published message`() {
		//given
		val message = "hello world"
		Mockito.doCallRealMethod().doAnswer {
			countDownLatch.countDown()
		}.`when`(testConsumerService).process(message)

		//when
		val txTemplate = TransactionTemplate(jpaTransactionManager)
		txTemplate.executeWithoutResult {
			fooRepository.save(Foo(message))
			kafkaTemplate.send("test-topic", message).get()
			//throw RuntimeException("xxx")
		}

		countDownLatch.await(5, TimeUnit.SECONDS)

		//then
		Mockito.verify(testConsumerService,Mockito.times(1)).process(message)
	}
}
