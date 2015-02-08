package com.upupconsultant.io
import scala.io.Source
import java.io.{FileNotFoundException, IOException}
import scala.io.BufferedSource
import java.io.{PrintWriter,FileWriter,File,BufferedWriter}



object FileManager {
  
  def writeToFile(fileName:String) = (lines:Iterable[String])  =>{
    val file = new File(fileName)
    val bout = new BufferedWriter(new FileWriter(file))
    lines foreach (line => bout.write(s"$line\n"))
    bout.close()
    
  }
  
  def openFileToList(fileName:String):Option[Iterable[String]] = {
    try {
      val lines = Control.using(Source.fromFile(fileName)){
        source => (for (line <- source.getLines()) yield line).toIterable
      }
      Some(lines)
    }catch {
      case e:Exception => None
    }
    
  }
  def openAndPrint(fileName:String) ={
    
    val filenm:Source = {
      try{
      Source.fromFile(fileName)
       } catch {
        case e: FileNotFoundException => Source.fromString(s"$fileName doesn't exist");
        case e: IOException => println("IO error");Source.fromString("Empty File");
        case e: Exception => println("error");Source.fromString("Empty File");
      }
    }
    
    Control.using(filenm){
      source=>{
        
        for(lines<-source.getLines()){
          println(lines)
        }
     
      }
    }
  }

}

object Control {

  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    }  
    finally {
      resource.close()
    }

}
