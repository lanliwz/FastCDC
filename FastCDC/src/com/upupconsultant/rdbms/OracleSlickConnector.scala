package com.upupconsultant.rdbms
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import scala.util.Properties._

object OracleSlickConnector {
  val slickdriver = "com.typesafe.slick.driver.oracle.OracleDriver"
  val jdbcdriver = "oracle.jdbc.driver.OracleDriver"
  val db = Database.forURL(url="jdbc:oracle:thin:@localhost:1521:DIODS6", user="wzhang", password="welcome123" , driver="oracle.jdbc.driver.OracleDriver")
  def database(dbname:String,user:String) = dbname match {
    case "DIODS6" => Database.forURL(url="jdbc:oracle:thin:@localhost:1521:DIODS6", user="wzhang", password="welcome123" , driver="oracle.jdbc.driver.OracleDriver")
  }
}