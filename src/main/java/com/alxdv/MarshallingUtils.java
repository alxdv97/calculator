package com.alxdv;

import com.generated.SimpleCalculator;
import com.generated.SimpleCalculator.Expressions;
import com.generated.SimpleCalculator.ExpressionResults;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarshallingUtils
{
    private static final Logger LOG = Logger.getLogger(MarshallingUtils.class.getName());

    public static Expressions unmarshallExpression(final Path file)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(SimpleCalculator.class);
            return ((SimpleCalculator) context.createUnmarshaller()
                    .unmarshal(new FileReader(file.toFile()))).getExpressions();
        } catch (JAXBException e)
        {
            LOG.log(Level.SEVERE, "Could not unmarshall expressions from " + file.getFileName() + " file!");
            return null;
        } catch (FileNotFoundException e)
        {
            LOG.log(Level.SEVERE, "File " + file.getFileName() + " not found!");
            return null;
        }
    }

    public static void marshallExpression(final Path resultFile, final ExpressionResults expressionResults)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(SimpleCalculator.class);
            SimpleCalculator simpleCalculator = new SimpleCalculator();
            simpleCalculator.setExpressionResults(expressionResults);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(simpleCalculator, resultFile.toFile());
        } catch (JAXBException e)
        {
            LOG.log(Level.SEVERE, "Could not marshall expressions from " + resultFile.getFileName() + " file!");
        }
    }

    public static ExpressionResults unmarshallExpressionResult(final Path file)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(SimpleCalculator.class);
            return ((SimpleCalculator) context.createUnmarshaller()
                    .unmarshal(new FileReader(file.toFile()))).getExpressionResults();
        } catch (JAXBException e)
        {
            LOG.log(Level.SEVERE, "Could not unmarshall expression results from " + file.getFileName() + " file!");
            return null;
        } catch (FileNotFoundException e)
        {
            LOG.log(Level.SEVERE, "File " + file.getFileName() + " not found!");
            return null;
        }
    }
}
