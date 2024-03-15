package com.example.server.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class DataController {
  @GetMapping("/api/data")
  public List<String> getData() {
    List<String> list = new ArrayList<>();
    list.clear();

    list.add("dane 1");
    list.add("dane 2");
    list.add("dane 3");

    return list;
  }
}
