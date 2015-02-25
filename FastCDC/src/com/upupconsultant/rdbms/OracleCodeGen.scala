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
        override def tableName = dbTableName => dbTableName match {
      case "COF_INVENTORY" => "CoffeeInventory"
      case _ => super.tableName(dbTableName)
    }
    // override generator responsible for tables
    override def Table = new Table(_){
      table =>
      // customize table value (TableQuery) name (uses tableName as a basis)
      override def TableValue = new TableValue{
        override def rawName = super.rawName.uncapitalize
      }
      // override generator responsible for columns
      override def Column = new Column(_){
        // customize Scala column names
        override def rawName = (table.model.name.table,this.model.name) match {
          case ("COFFEES","COF_NAME") => "name"
          case ("COFFEES","SUP_ID") => "supplierId"
          case ("SUPPLIERS","SUP_ID") => "id"
          case ("SUPPLIERS","SUP_NAME") => "name"
          case ("COF_INVENTORY","QUAN") => "quantity"
          case ("COF_INVENTORY","COF_NAME") => "coffeeName"
          case _ => super.rawName
        }
      }
    }
  }
}
