package com.nable.athena.geomovement

import java.util.Properties
import java.io.FileInputStream
import java.io.FileNotFoundException

object GeoMovement {

  def main(args: Array[String]): Unit = {

    try {

      val props = new Properties()
      props.load(new FileInputStream(args(0)))
      Config.load(props)
      this.doMainOptions

    } catch {
      case e: ArrayIndexOutOfBoundsException => {
        System.err.println("java properties file path is required.")
      }
      case e: FileNotFoundException => {
        System.err.println(e)
      }
      case e: Exception => {
        System.err.println(e)

      }
    }

  }

  def doMainOptions: Unit = {
  
     var o = 0
    
     try {

      println("\n1->Publish geo tracking event")
      println("2->Publish geo activity event")
      println("3->Exit")
      print("\nChoose option: ")
      o = scala.io.StdIn.readInt()

      o match {
        case 1 => this.doGeoTrackingEvent
        case 2 => this.doGeoActivityEvent
        case 3 => return
      }

    } catch {
      case e: Exception => {
        throw new IllegalArgumentException("Invalid Input")         
      }
    }

   

  }

  def doGeoTrackingEvent: Unit = {

    var hasConfirmed: Boolean = false
    var hasAddMore: Boolean = false

    do {

      print("\nDo you want to generate a geo tracking event (Y/n) ?: ")
      hasConfirmed = scala.io.StdIn.readBoolean()

      if (hasConfirmed) {

        val geoTrackingEvent = new GeoTrackingEvent

        print(s"\nCustomer id: ${geoTrackingEvent.customer_id}")
        print("\nDo you want to override auto generated customer id (Y/n) ?: ")
        val hasCustomeridOverride = scala.io.StdIn.readBoolean()

        if (hasCustomeridOverride) {
          print(s"Enter customer id : ")
          geoTrackingEvent.customer_id = scala.io.StdIn.readLine()
        }

        print("Choose event source: \n  1.mobile\n  2.web\n  3.custom \nchoose: ")
        val sourceOption = scala.io.StdIn.readInt()
        val sources = Array("mobile", "web", "custom")
        val source = sources(sourceOption - 1)
        if (source == "custom") {
          print("Enter custom source (ex: my_custom_source): ")
          geoTrackingEvent.source = scala.io.StdIn.readLine()
        } else {
          geoTrackingEvent.source = source
        }

        print("Enter Altitude in meters (optional): ")
        val altitude = scala.io.StdIn.readLine()
        if (!altitude.isEmpty()) {
          geoTrackingEvent.altitude = altitude.toDouble
        }

        print("Enter Speed in meters/sec (optional): ")
        val speed = scala.io.StdIn.readLine()
        if (!speed.isEmpty()) {
          geoTrackingEvent.speed = speed.toDouble
        }

        print("Enter geo coordinates (ex: 0.00,0.00) <lat>,<long>: ")
        val latlong = scala.io.StdIn.readLine()
        geoTrackingEvent.latitude = latlong.split(",")(0).toDouble
        geoTrackingEvent.longitude = latlong.split(",")(1).toDouble

        print("Choose pubsub provider: \n  1.kafka\n  2.redis\n  3.custom \nchoose: ")
        val pubsubOption = scala.io.StdIn.readInt()
        val pubsubproviders = Array("kafka", "redis", "custom")
        val pubsub = pubsubproviders(pubsubOption - 1)

        if (pubsub == "custom") {
          print("Enter provider class (ex: org.nable.athena.geomovement.MyProvider): ")
          val classname = scala.io.StdIn.readLine()
          println(s"emitting event to ${classname}")
        }

        if (pubsub == "kafka" || pubsub == "redis") {

          try {
            println(s"publishing on ${pubsub} ...")
            val kafkaPublisher = new GeoTrackingEventKafkaPublisher
            kafkaPublisher.publish(geoTrackingEvent)
            println(s"successfully published on ${pubsub}")
          } catch {

            case e: Exception => {
              println(s"failed publishing on ${pubsub} ${e}")

            }
          }

        }

        print("Do you want to add more (Y/n) ?: ")
        hasAddMore = scala.io.StdIn.readBoolean()

      }

    } while (hasConfirmed & hasAddMore)

    this.doMainOptions

  }

  def doGeoActivityEvent: Unit = {

    var again: Boolean = false
    var hasConfirmed: Boolean = false
    var hasAddMore: Boolean = false

    do {

      print("\nDo you want to generate a geo activity event (Y/n) ?: ")
      hasConfirmed = scala.io.StdIn.readBoolean()

      if (hasConfirmed) {

       

        val geoActivityEvent = new GeoActivityEvent

        print(s"\nCustomer id: ${geoActivityEvent.customer_id}")
        print("\nDo you want to override auto generated customer id (Y/n) ?: ")
        val hasCustomeridOverride = scala.io.StdIn.readBoolean()

        if (hasCustomeridOverride) {
          print(s"Enter customer id : ")
          geoActivityEvent.customer_id = scala.io.StdIn.readLine()
        }
        
        
        print("Choose event source: \n  1.atm_machine\n  2.pos_machine\n  3.mobile_exchange\n  4.bank_premises\n  5.custom \nchoose: ")
        val eventSourceOption = scala.io.StdIn.readInt()
        val eventSources = Array("atm_machine", "pos_machine","mobile_exchange","bank_premises","custom")
        val eventSource = eventSources(eventSourceOption - 1)
        if (eventSource == "custom") {
          print("Enter custom event source (ex: my_event_source ): ")
          geoActivityEvent.source = scala.io.StdIn.readLine()
        } else {
          geoActivityEvent.source = eventSource
        }
        
        
        print(s"Event source id: ${geoActivityEvent.source_id}")
        print("\nDo you want to override auto generated event source id (Y/n) ?: ")
        val hasSourceidOverride = scala.io.StdIn.readBoolean()

        if (hasSourceidOverride) {
          print(s"Enter event source id : ")
          geoActivityEvent.source_id = scala.io.StdIn.readLine()
        }
        
         
        
        print(s"Choose event for ${geoActivityEvent.source}: \n  1.cash_deposit\n  2.cash_withdrawal\n  3.voice_call\n  4.pos_transaction\n  5.custom \nchoose: ")
        val eventOption = scala.io.StdIn.readInt()
        val events = Array("cash_deposit", "cash_withdrawal","voice_call","pos_transaction","custom")
        val event = events(eventOption - 1)
        if (event == "custom") {
          print("Enter custom event (ex: my_custom_event): ")
          geoActivityEvent.event = scala.io.StdIn.readLine()
        } else {
          geoActivityEvent.event = event
        }
        
        
    

        print(s"Location id: ${geoActivityEvent.location_id}")
        print(s"\nDo you want to override auto generated location id for ${geoActivityEvent.source} (Y/n) ?: ")
        val hasLocationidOverride = scala.io.StdIn.readBoolean()

        if (hasLocationidOverride) {
          print(s"Enter location id : ")
          geoActivityEvent.location_id = scala.io.StdIn.readLine()
        }
        
         print("Choose pubsub provider: \n  1.kafka\n  2.redis\n  3.custom \nchoose: ")
        val pubsubOption = scala.io.StdIn.readInt()
        val pubsubproviders = Array("kafka", "redis", "custom")
        val pubsub = pubsubproviders(pubsubOption - 1)

        if (pubsub == "custom") {
          print("Enter provider class (ex: org.nable.athena.geomovement.MyProvider): ")
          val classname = scala.io.StdIn.readLine()
          println(s"emitting event to ${classname}")
        }

        if (pubsub == "kafka" || pubsub == "redis") {

          try {
            println(s"publishing on ${pubsub} ...")
            val kafkaPublisher = new GeoActivityEventKafkaPublisher
            kafkaPublisher.publish(geoActivityEvent)
            println(s"successfully published on ${pubsub}")
          } catch {

            case e: Exception => {
              println(s"failed publishing on ${pubsub} ${e}")

            }
          }

        }

        print("Do you want to add more (Y/n) ?: ")
        hasAddMore = scala.io.StdIn.readBoolean()

      }

    } while (hasConfirmed & hasAddMore)

    this.doMainOptions

  }

}