package com.upupconsultant.io
import com.upupconsultant.io.FileManager

  /** example call
  CDC4CSVFile.generateSqlTemplate("/Users/lanliwz/test1.txt", "/Users/lanliwz/sqltest.txt")(row => {
  val cols = row.split(',')
  s"INSERT INTO TB(A,B) VALUES('${cols(1)}','${cols(2)}')"
  })
  
  */
object CDC4CSVFile {

  
  def generateSqlTemplate(src: String, dest: String)(f: String => String): Unit = {
    val srclist = FileManager.openFileToList(src)
    if (srclist != None) {
      val sqllist = for (x <- srclist.get) yield {
        f(x)
      }
      val sqlfile = FileManager.writeToFile(dest)
      sqlfile(sqllist)
    }
  }
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
    val grouped = src.groupBy { x => {
      val key=x.split(',').slice(0, 2)
      var b:StringBuilder= new StringBuilder()
      key.addString(b, ",")
      b.toString()
      }
    }
    
    val result: Iterable[String] = for (x <- grouped) yield {
      var sum = 0.0f
      for (y <- x._2) {
        val col1Value = y.split(',')(2).toFloat
        sum += col1Value
      }
      s"${x._1},$sum"

    }
    result
  }
   def sumByKeyTemplate(src: Iterable[String])(key:String =>String)(agg:String => Float): Iterable[String] = {
    val grouped = src.groupBy { x => key(x)}
    
    val result: Iterable[String] = for (x <- grouped) yield {
      var sum = 0.0f
      for (y <- x._2) {
       val colValue =agg(y)
       sum+=colValue
      }
      s"${x._1},$sum"

    }
    result
  }
  def latestByKey(src: Iterable[String]): Iterable[String] = {
      val grouped = src.groupBy { x => {
      val key=x.split(',').slice(0, 2)
      var b:StringBuilder= new StringBuilder()
      key.addString(b, ",")
      b.toString()
      }
    }
    val result: Iterable[String] = for (x <- grouped) yield {
      var maxSeq = 0L
      var currVal = ""
      for (y <- x._2) {
        val col1Value = y.split(',')(2).toLong
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
  def latestByKeyTemplate(src: Iterable[String])(key:String => String)(col:String =>Long): Iterable[String] = {
    val grouped = src.groupBy { x => key(x) }
    val result: Iterable[String] = for (x <- grouped) yield {
      var maxSeq = 0L
      var currVal = ""
      for (y <- x._2) {
        val col1Value = col(y)
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