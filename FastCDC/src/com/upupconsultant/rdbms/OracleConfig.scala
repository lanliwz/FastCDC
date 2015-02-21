package com.upupconsultant.rdbms

object OracleConfig {
  val initScripts = Seq("drop-tables.sql", "create-tables.sql", "populate-tables.sql")
  val scripts = initScripts.map("runscript from 'src/sql/" + _ + "'").mkString("\\;")
  val url = "jdbc:oracle:thin:@localhost:1521:DIODS5"
  val (user,password) = ("wzhang", "welcome123") 
  val jdbcDriver = "oracle.jdbc.driver.OracleDriver"
  val oracleSlickDriver = "com.typesafe.slick.driver.oracle.OracleDriver"
}