package com.nable.athena.geomovement

import org.apache.avro.Schema
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.HttpResponse
import org.json.JSONObject
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.generic.GenericRecord
import org.apache.avro.generic.GenericData
import java.util.Properties
import org.apache.kafka.clients.producer.RecordMetadata

class GeoActivityEventKafkaPublisher extends EventPublisher[GeoActivityEvent, RecordMetadata] {

  override def publish(geoActivityEvent: GeoActivityEvent): RecordMetadata = {

    val keySchemaResponse: JSONObject = Unirest
      .get(s"${Config.get("kafka.schemaregistry")}/subjects/customer_geo_activity_event-key/versions/1")
      .asJson().getBody.getObject

    val valueSchemaResponse: JSONObject = Unirest
      .get(s"${Config.get("kafka.schemaregistry")}/subjects/customer_geo_activity_event-value/versions/1")
      .asJson().getBody.getObject

    val keySchema: Schema = new Schema.Parser().parse(keySchemaResponse.get("schema").toString())
    val valueSchema: Schema = new Schema.Parser().parse(valueSchemaResponse.get("schema").toString())

    val geoActivityEventKey = new GenericData.Record(keySchema)
    val geoActivityEventValue = new GenericData.Record(valueSchema)

    geoActivityEventKey.put("customer_id", geoActivityEvent.customer_id)

    geoActivityEventValue.put("customer_id", geoActivityEvent.customer_id)
    geoActivityEventValue.put("event", geoActivityEvent.event)
    geoActivityEventValue.put("source_id", geoActivityEvent.source_id)
    geoActivityEventValue.put("source", geoActivityEvent.source)
    geoActivityEventValue.put("location_id", geoActivityEvent.location_id)
    geoActivityEventValue.put("timestamp", geoActivityEvent.timestamp)

    val kafkaProps: Properties = new Properties()
    kafkaProps.put("bootstrap.servers", Config.get("kafka.bootstrap.servers"))
    kafkaProps.put("message.send.max.retries", "3")
    kafkaProps.put("request.required.acks", "1")
    kafkaProps.put("client.id", Config.get("kafka.client.id"))
    kafkaProps.put("schema.registry.url", Config.get("kafka.schemaregistry"))
    kafkaProps.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    kafkaProps.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")

    val producer: KafkaProducer[GenericData.Record, GenericData.Record] = new KafkaProducer[GenericData.Record, GenericData.Record](kafkaProps)

    val record = new ProducerRecord(Config.get("kafka.topic.geoactivityevent").toString(), geoActivityEventKey, geoActivityEventValue)

     val ack =  producer.send(record).get
     
     producer.close()
     
     return ack

  }
}