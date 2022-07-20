package br.com.henrique.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private List<String> errors;
     public ApiErrors(String message){
       this.errors = Arrays.asList(message);
  }
    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
