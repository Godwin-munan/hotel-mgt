/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 *
 * @author godwi
 */
public class CompressionUtil {


  public static byte[] compress(byte[] data) throws IOException {  
   Deflater deflater = new Deflater();  
   deflater.setInput(data);  

   ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);   

   deflater.finish();  
   byte[] buffer = new byte[1024];   
   while (!deflater.finished()) {  
    int count = deflater.deflate(buffer); // returns the generated code... index  
    outputStream.write(buffer, 0, count);   
   }  
   outputStream.close();  
   byte[] output = outputStream.toByteArray();  

   return output;  
  }  

  public static byte[] decompress(byte[] data) throws IOException, DataFormatException {  
   Inflater inflater = new Inflater();   
   inflater.setInput(data);  

   ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);  
   byte[] buffer = new byte[1024];  
   while (!inflater.finished()) {  
    int count = inflater.inflate(buffer);  
    outputStream.write(buffer, 0, count);  
   }  
   outputStream.close();  
   byte[] output = outputStream.toByteArray();  

   return output;  
  }  
    
}
