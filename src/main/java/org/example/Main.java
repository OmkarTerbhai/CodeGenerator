package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.javapoet.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = Main.class.getResourceAsStream("/inputSpec.json");
        InputSpec user = objectMapper.readValue(is, InputSpec.class);

        List<MethodSpec> methodSpecs = new ArrayList<>();

        for(APISpec api : user.apis) {
            AnnotationSpec requestMapping = null;

            ParameterSpec nameParameter = null;
            AnnotationSpec requestParam = null;
            for (ParamInput param : api.params) {
                switch (api.apiType) {
                    case "GET" -> {
                        requestMapping = AnnotationSpec.builder(GetMapping.class)
                                .addMember("value", "$S", api.apiName)
                                .build();

                        requestParam = AnnotationSpec.builder(RequestParam.class)
                                .addMember("value", "$S", param.paramName)
                                .build();
                    }

                    case "POST" -> {
                        requestMapping = AnnotationSpec.builder(PostMapping.class)
                                .addMember("value", "$S", api.apiName)
                                .build();

                        requestParam = AnnotationSpec.builder(RequestBody.class)
                                .build();
                    }
                }
                nameParameter = ParameterSpec.builder(String.class, param.paramName)
                        .addAnnotation(requestParam)
                        .build();
                MethodSpec methodSpec = MethodSpec.methodBuilder(api.methodName)
                        .addAnnotation(requestMapping)
                        .addStatement("System.out.println(\"Hello\")")
                        .addParameter(nameParameter)
                        .build();

                methodSpecs.add(methodSpec);

            }
        }

        AnnotationSpec requestMapping = AnnotationSpec.builder(RequestMapping.class)
                .addMember("value", "$S", user.requestMapping)
                .build();

        TypeSpec typeSpec = TypeSpec
                .classBuilder(user.controllerName)
                .addAnnotation(requestMapping)
                .addMethods(methodSpecs)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.controller", typeSpec)
                .build();

        // Write the generated file to the specified path
        javaFile.writeTo(Paths.get("./src/main/java"));
    }
}