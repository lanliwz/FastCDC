package com.upupconsultant.test
import com.upupconsultant.io.FileManager._


object TestMe extends App {
  openAndPrint("/Users/lanliwz/test.txt")
//  openFileToList("/Users/lanliwz/sbt/bin/sbt.bat") match {
//    case Some(lines) => lines foreach (println(_))
//    case None => println ("file doesn't exists")  
//    }
  val writeToTestFile = writeToFile("/Users/lanliwz/test.txt")
  val lines = List("a","b","c","d","e")
  writeToTestFile(lines)
  
}