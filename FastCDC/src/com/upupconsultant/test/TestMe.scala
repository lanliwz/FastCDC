package com.upupconsultant.test
import com.upupconsultant.io.FileManager._
import com.upupconsultant.io.CDC4CSVFile


object TestMe extends App {
//  openAndPrint("/Users/lanliwz/test1.txt")
//  openFileToList("/Users/lanliwz/sbt/bin/sbt.bat") match {
//    case Some(lines) => lines foreach (println(_))
//    case None => println ("file doesn't exists")  
//    }
//  val writeToTestFile = writeToFile("/Users/lanliwz/test2.txt")
////  val lines = List("a,v,12","a,b,10","a,c,20","b,d,100","b,d,300","d,d,10","a,d,23")
////  val lines = openFileToList("/Users/lanliwz/test1.txt")
//  val lines = fileToList("/Users/lanliwz/test1.txt")
////  writeToTestFile(lines.get)
//  writeToTestFile(lines)
//  openAndPrint("/Users/lanliwz/test2.txt")
  
//  CDC4CSVFile.generateSqlFile("/Users/lanliwz/test1.txt", "/Users/lanliwz/sqltest.txt", "INSERT INTO TB(A,B) VALUES($1,$2)")
CDC4CSVFile.generateSqlTemplate("/Users/lanliwz/test1.txt", "/Users/lanliwz/sqltest.txt")(row => {
  val cols = row.split(',')
  s"INSERT INTO TB(A,B) VALUES('${cols(1)}','${cols(2)}')"
  })
  openAndPrint("/Users/lanliwz/sqltest.txt")
}