package com.alxdv;

import static org.junit.Assert.assertEquals;

import com.generated.SimpleCalculator.ExpressionResults;
import com.generated.SimpleCalculator.ExpressionResults.ExpressionResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class CalculatorTest
{
    private Calculator calculator;
    private Path source;
    private Path target;

    @Before
    public void setup()
    {
        calculator = new Calculator();
        source = Paths.get("./src/test/java/files/samples/SampleTest.xml");
        target = Paths.get("./src/test/java/files/samples/SampleTestResult.xml");
    }

    @After
    public void clearTargetFile() throws IOException
    {
        FileChannel.open(target, StandardOpenOption.WRITE).truncate(0).close();
    }

    @Test
    public void calculateCorrectTest() throws IOException
    {
        calculator.calculate(source, target);

        final ExpressionResults expressionResults = MarshallingUtils.unmarshallExpressionResult(target);
        final List<ExpressionResult> expressionResult = expressionResults.getExpressionResult();

        assertEquals(-2443.75, expressionResult.get(0).getResult(), 10e9);
        assertEquals(-59747.58686350021, expressionResult.get(1).getResult(), 10e9);
    }

    @Test
    public void wrongSourceFileTest() throws IOException
    {
        calculator.calculate(Paths.get("./"), target);

        assertEquals(0, Files.lines(target).count());
    }

    @Test
    public void simpleExpressionTest()
    {
        Path simpleSource = Paths.get("./src/test/java/files/testfiles/SimpleTest.xml");
        Path simpleTarget = Paths.get("./src/test/java/files/testfiles/SimpleTestResult.xml");

        calculator.calculate(simpleSource, simpleTarget);

        final ExpressionResults expressionResults = MarshallingUtils.unmarshallExpressionResult(simpleTarget);
        final List<ExpressionResult> expressionResult = expressionResults.getExpressionResult();

        assertEquals(-2, expressionResult.get(0).getResult(),10e-5);

    }
}
