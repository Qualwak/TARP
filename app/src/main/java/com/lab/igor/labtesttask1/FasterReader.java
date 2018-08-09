package com.lab.igor.labtesttask1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FasterReader {

    //    public static void main(String[] args) throws IOException {
//        String value = "Alfuzosin";
//        System.out.println(searchingInFile(value));
//    }
    public boolean searchingInFile(String value) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("test_8_output.txt"));
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        boolean flag = false;
        String line = bufferedReader.readLine().toLowerCase();
        while (line != null) {
            if (line.equals(value.toLowerCase())) {
                flag = true;
                break;
            }
            line = bufferedReader.readLine();
            if (line != null)
                line = line.toLowerCase();

        }
        bufferedReader.close();
        return flag;
    }

    public String[] returning() {
        String[] string = {};
        return string;
    }
}
