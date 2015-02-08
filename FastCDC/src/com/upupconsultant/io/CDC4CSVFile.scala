package com.upupconsultant.io
import com.upupconsultant.io.FileManager

object CDC4CSVFile {
  def generateSqlFile(src: String, dest: String, sql: String): Unit = {
    val srclist = FileManager.openFileToList(src)
    if (srclist != None) {
      val sqllist = for (x <- srclist.get) yield {
        val srccolumn = x.split(',')
        s"$sql values('${srccolumn(0)}')"

      }
      val sqlfile = FileManager.writeToFile(dest)
      sqlfile(sqllist)
    }
  }
  def sumByKey(src: Iterable[String]): Iterable[String] = {
    val grouped = src.groupBy { x => x.split(',').slice(1, 2) }
    val result: Iterable[String] = for (x <- grouped) yield {
      var sum = 0.0f
      for (y <- x._2) {
        val col1Value = y.split(',')(3).toFloat
        sum += col1Value
      }
      x._1(0) + ',' + x._1(1) + ',' + sum

    }
    result
  }
  def latestByKey(src: Iterable[String]): Iterable[String] = {
    val grouped = src.groupBy { x => x.split(',').slice(1, 2) }
    val result: Iterable[String] = for (x <- grouped) yield {
      var maxSeq = 0L
      var currVal = ""
      for (y <- x._2) {
        val col1Value = y.split(',')(3).toLong
        if (maxSeq < col1Value) {
          maxSeq = col1Value
          currVal = y
        } else {
          None
        }
      }
      currVal

    }
    result
  }
  def filterByValue(src: Iterable[String],indexes:List[Integer],values:List[String]) ={
    val result = src.filter(x => {
      val cols = x.split(',')
//      indexes for (index <- indexes) 
      var cond=true
      for (ind <-indexes) {
      cond = cols(ind).contains(values(ind)) && cond
      }
      cond
      

    })
  }
    

}