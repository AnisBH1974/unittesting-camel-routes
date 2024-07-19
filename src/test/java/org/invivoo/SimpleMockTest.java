package org.invivoo;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SimpleMockTest extends CamelSpringTestSupport {

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		// loads a Spring XML file
		return new ClassPathXmlApplicationContext("camel-context-simple.xml");
	}

	@EndpointInject("mock:b")
	protected MockEndpoint resultEndpoint;

	@Produce("direct:start")
	protected ProducerTemplate template;

	@Test
	public void testMock() throws Exception {

		String sendBody = "World";
		String expectedBody = "Hello World";
		resultEndpoint.expectedBodiesReceived(expectedBody);
		template.sendBodyAndHeader( sendBody , "", "");
		resultEndpoint.assertIsSatisfied();
	}
	/*@Test
	public void testMock() throws Exception {
		getMockEndpoint("mock:result").expectedBodiesReceived("Hello World");

		template.sendBody("direct:start", "Hello World");

		MockEndpoint.assertIsSatisfied(context);
	}

	@Override
	protected RoutesBuilder createRouteBuilder() {
		return new RouteBuilder() {
			@Override
			public void configure() {
				from("direct:start").to("mock:result");
			}
		};
	} */

}