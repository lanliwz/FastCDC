package com.upupconsultant.rdbms
import scala.slick.model.Model
import scala.slick.jdbc.meta.createModel
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import com.typesafe.slick.driver.oracle.OracleDriver
import OracleConfig._


object OracleCodeGen {
    def main(args: Array[String]) = {
    codegen.writeToFile(
          oracleSlickDriver,
          args(0),
          "demo",
          "Tables",
          "Tables.scala"
        )
  }


  val db = Database.forURL(url, user = user, password = password, driver = jdbcDriver)
  // filter out desired tables
  val included = Seq("COFFEES", "SUPPLIERS", "COF_INVENTORY")
  val model = db.withSession { implicit session =>
    val tables = OracleDriver.getTables.list.filter(t => included contains t.name.name)
    createModel(tables, OracleDriver)

  }
  com.typesafe.slick.driver.oracle.OracleDriver

  val codegen = new scala.slick.codegen.SourceCodeGenerator(model) {
    // customize Scala entity name (case class, etc.)
    override def entityName = dbTableName => dbTableName match {
      case "COFFEES"       => "Coffee"
      case "SUPPLIERS"     => "Supplier"
      case "COF_INVENTORY" => "CoffeeInventoryItem"
      case _               => super.entityName(dbTableName)
    }
  }
}
