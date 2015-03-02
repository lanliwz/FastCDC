package com.upupconsultant.rdbms
import java.sql.{ Connection, DriverManager }
import com.mchange.v2.c3p0.ComboPooledDataSource
import oracle.jdbc.driver
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import javax.sql.DataSource
import scala.slick.jdbc.{GetResult,StaticQuery}
import scala.slick.jdbc.StaticQuery.interpolation

object OracleConnector {
  var cpds: ComboPooledDataSource = new ComboPooledDataSource()
  cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
  cpds.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:DIODS5");
  cpds.setUser("wzhang");
  cpds.setPassword("welcome123");

  // the settings below are optional -- c3p0 can work with defaults
  cpds.setMinPoolSize(1);
  cpds.setAcquireIncrement(1);
  cpds.setMaxPoolSize(2);
  
  def testSlick():Unit = {
    val db:Database = Database.forDataSource(cpds.asInstanceOf[DataSource]) 
    val query= sql"select table_name from dba_tables".as[(String)]
    db.withSession { 
      implicit session => 
        val rst = query.list
        rst.foreach (x=>println(x))
      }
  }
  
  
  def test():Unit = {
      val connection = cpds.getConnection
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT * FROM DUAL")  
    
  }
  
  def jdbcConnection: Unit = {
    val url = "jdbc:oracle:thin@DIODS5"
    val driver = "oracle.jdbc.Driver"
    val username = "root"
    val password = "root"

    try {
      Class.forName(driver)
      var connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT * FROM dual")
      while (rs.next) {
        val host = rs.getString("host")
        val user = rs.getString("user")
        println("host = %s, user = %s".format(host, user))
      }
      connection.close()
    } catch {
      case e: Exception => e.printStackTrace
    }
    //    connection.close

  }

}