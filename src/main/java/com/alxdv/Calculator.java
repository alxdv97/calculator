package com.alxdv;

import com.generated.Term;
import com.simplecalculator.SimpleCalculator;
import com.generated.SimpleCalculator.Expressions;
import com.generated.SimpleCalculator.ExpressionResults;
import com.generated.SimpleCalculator.ExpressionResults.ExpressionResult;
import com.generated.SimpleCalculator.Expressions.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator implements SimpleCalculator
{
    private static final Logger LOG = Logger.getLogger(Calculator.class.getName());

    @Override
    public void calculate(final Path file, final Path resultFile)
    {
        final Expressions expressions = MarshallingUtils.unmarshallExpression(file);

        if (expressions != null)
        {
            ExpressionResults expressionResults = new ExpressionResults();

            expressions.getExpression()
                    .stream()
                    .map(this::calculateExpression)
                    .filter(Objects::nonNull)
                    .map(Double::parseDouble)
                    .map(result -> {
                        final ExpressionResult expressionResult = new ExpressionResult();
                        expressionResult.setResult(result);
                        return expressionResult;
                    })
                    .forEach(expResult -> expressionResults.getExpressionResult().add(expResult));

            MarshallingUtils.marshallExpression(resultFile, expressionResults);
        }
    }

    protected String calculateExpression(final Expression expression)
    {
        return evaluate(expression.getOperation()).toString();
    }


    private BigDecimal evaluate(final Term operation)
    {
        if (operation.getArg().isEmpty())
        {
            operation.getOperation().forEach(childOperation -> operation.getArg().add(evaluate(childOperation)));
        }

        return switchExpression(operation);
    }

    private BigDecimal switchExpression(final Term operation)
    {
        try
        {
            switch (operation.getOperationType())
            {
                case "SUM":
                    return sum(operation.getArg().get(0), operation.getArg().get(1));
                case "SUB":
                    return sub(operation.getArg().get(0), operation.getArg().get(1));
                case "MUL":
                    return mul(operation.getArg().get(0), operation.getArg().get(1));
                case "DIV":
                    return div(operation.getArg().get(0), operation.getArg().get(1));
                default:
                    LOG.log((Level.SEVERE), "Could not parse operation type " + operation.getOperationType() + "!");
                    return null;
            }
        } catch (ArithmeticException e)
        {
            LOG.log(Level.SEVERE, "Arithmetic error!");
            return null;
        }
    }


    private BigDecimal sum(final BigDecimal a, final BigDecimal b)
    {
        return a.add(b);
    }

    private BigDecimal sub(final BigDecimal a, final BigDecimal b)
    {
        return a.subtract(b);
    }

    private BigDecimal mul(final BigDecimal a, final BigDecimal b)
    {
        return a.multiply(b);
    }

    private BigDecimal div(final BigDecimal a, final BigDecimal b)
    {
        return a.divide(b, 11, RoundingMode.HALF_EVEN);
    }
}
