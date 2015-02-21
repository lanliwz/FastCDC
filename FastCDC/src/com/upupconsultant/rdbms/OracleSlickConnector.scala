package com.upupconsultant.rdbms
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import scala.util.Properties._

object OracleSlickConnector {
  val slickdriver = "com.typesafe.slick.driver.oracle.OracleDriver"
  val jdbcdriver = "oracle.jdbc.driver.OracleDriver"
  val db = Database.forURL("jdbc:oracle:thin:@localhost:1521:DIODS5", "wzhang", "welcome123", null, "oracle.jdbc.driver.OracleDriver")

}