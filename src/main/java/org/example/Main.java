package org.example;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        MethodSpec methodSpec = MethodSpec
                .methodBuilder("demo")
                .build();

        TypeSpec typeSpec = TypeSpec
                .classBuilder("Hello")
                .addMethod(methodSpec)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.controller", typeSpec)
                .build();

        // Write the generated file to the specified path
        javaFile.writeTo(Paths.get("./src/main/java"));
    }
}