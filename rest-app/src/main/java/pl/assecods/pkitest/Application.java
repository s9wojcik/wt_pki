package pl.assecods.pkitest;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class Application {
	
    // CONFIG PUBLISHER	
    @Bean
    Queue publisherQueue(@Value("${publisher.queue.name}") String queueName) {
        return new Queue(queueName, false);
    }
    
    @Bean
    TopicExchange publisherExchange(@Value("${publisher.exchange.name}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean()
    Binding publisherBinding(Queue publisherQueue, TopicExchange publisherExchange) {
        return BindingBuilder.bind(publisherQueue).to(publisherExchange).with(publisherQueue.getName());
    }
    
    @Bean
    @Primary    
    ConnectionFactory publisherConnectionFactory(
			@Value("${publisher.host}") String host,
			@Value("${publisher.port}") int port,
			@Value("${publisher.user}") String user,
			@Value("${publisher.password}") String password) { 

    	return getConnectionFactory(host, port, user, password);
	}

    // CONFIG CONSUMER
    @Bean
    Queue consumerQueue(@Value("${consumer.queue.name}") String queueName) {
        return new Queue(queueName, false);
    }
    
    @Bean
    TopicExchange consumerExchange(@Value("${consumer.exchange.name}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean
    @Qualifier    
    ConnectionFactory consumerConnectionFactory(
			@Value("${consumer.host}") String host,
			@Value("${consumer.port}") int port,
			@Value("${consumer.user}") String user,
			@Value("${consumer.password}") String password) { 

    	return getConnectionFactory(host, port, user, password);
	}
    
    @Bean() 
    Binding consumerBinding(Queue consumerQueue, TopicExchange consumerExchange) {
        return BindingBuilder.bind(consumerQueue).to(consumerExchange).with(consumerQueue.getName());
    }

    private ConnectionFactory getConnectionFactory(String host, int port, String user, String password) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);
        
        return connectionFactory;
    }
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
