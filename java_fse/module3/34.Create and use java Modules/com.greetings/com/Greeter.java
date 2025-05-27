package com.greetings;

import com.utils.StringUtil;

public class Greeter {
    public static void main(String[] args) {
        String name = "world";
        String capitalized = StringUtil.capitalize(name);
        System.out.println("Hello, " + capitalized + "!");
    }
}