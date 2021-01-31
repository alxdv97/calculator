package com.alxdv;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.LogManager;

/**
 * Enter class.
 */
public class CalculatorApp
{
    public static void main(String[] args)
    {
        try {
            LogManager.getLogManager().readConfiguration();
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }

        Calculator calculator = new Calculator();
        calculator.calculate(Paths.get(args[0]),
                Paths.get(args[1]));
    }
}
