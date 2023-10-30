package kafka.producer;

import kafka.data.RandomDataGenerator;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;

import java.util.Properties;

import kafka.protobuf.UserPhoneOuterClass.*;

public class KafkaProducerExample {
    public static void main(String[] args) throws InterruptedException {
        String bootstrapServers = "localhost:9092"; // Thay đổi địa chỉ Kafka của bạn
        String topicName = "tracking_user";

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class.getName()); // Custom Protobuf serializer
        properties.put("schema.registry.url", "http://localhost:8081");

        Producer<String, UserPhone> producer = new KafkaProducer<>(properties);

        // Create fake UserPhone data and send it to Kafka
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, UserPhone> record = new ProducerRecord<>(topicName, "data", getUserPhone());

            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        System.out.println("Error sending message: " + e.getMessage());
                    } else {
                        System.out.println("Sent to Kafka: \n" + getUserPhone());
                    }
                }
            });
            Thread.sleep(1000);
        }

        producer.flush();
        producer.close();

    }

    private static UserPhone getUserPhone() {
        // Create fake UserPhone data and send it to Kafka
        RandomDataGenerator generator = new RandomDataGenerator();
        return UserPhone.newBuilder()
                .setPhone(generator.getPhone())
                .setUserId(generator.getUserId())
                .setLat(generator.getLat())
                .setLon(generator.getLon())
                .setTimestamp(System.currentTimeMillis())
                .build();
    }

}
