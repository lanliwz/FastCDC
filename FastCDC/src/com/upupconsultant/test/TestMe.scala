package com.upupconsultant.test
import com.upupconsultant.io.FileManager._

object TestMe extends App {
//  openAndPrint("/Users/lanliwz/sbt/bin/sbt.bat")
  val content : Option[List[String]] = openFileToList("/Users/lanliwz/sbt/bin/sbt.bat")
  if (content!=None)
    content.get.foreach{println(_)}
}