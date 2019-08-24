package com.learnenglish.vocabulary.logic.preparation;

import java.util.Scanner;

public class TestClass {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
//        ConcatFilesFromTo.run(40, 0, "HP");
        createFile();
        sc.nextLine();
    }

    public static void createFile() {
        ConcatFilesFromTo.run(10,0,"HP");
    }
}
