package kafka.consumer;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import kafka.protobuf.UserPhoneOuterClass.*;

public class KafkaConsumerExample {
    public static void main(String[] args) {
        String bootstrapServers = "10.90.182.195:9092";
        String topicName = "tracking_user";
        String group = "users";

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put("group.id",group);
        properties.put("auto.commit.interval.ms", "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaProtobufDeserializer.class.getName()); // Custom Protobuf serializer
        properties.put(KafkaProtobufDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        properties.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, UserPhone.class.getName());

        try (Consumer<String, UserPhone> consumer = new KafkaConsumer<>(properties);) {
            consumer.subscribe(Collections.singleton(topicName));
            for (int i =0;i<100;i++) {
                ConsumerRecords<String, UserPhone> records = consumer.poll(1000L);
                for (ConsumerRecord<String, UserPhone> record : records) {
                    UserPhone userPhone = record.value();
                    System.out.println("Received UserPhone - Phone: " + userPhone.getPhone() +
                            ", User ID: " + userPhone.getUserId() +
                            ", Latitude: " + userPhone.getLat() +
                            ", Longitude: " + userPhone.getLon() +
                            ", Timestamp: " + userPhone.getTimestamp());
                }
            }
        }
    }
}

