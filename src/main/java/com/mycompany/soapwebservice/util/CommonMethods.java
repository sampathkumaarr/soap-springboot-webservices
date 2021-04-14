package com.mycompany.soapwebservice.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Function;

public class CommonMethods {

    public static Function<Exception, String> convertExceptionIntoString = (Exception exception) -> {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    };


}
