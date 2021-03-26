package cloud.dest.terminal.utils;

import cloud.dest.terminal.variable.Variable;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WriteJsonFile {

    private WriteJsonFile() {
    }

    public static void writeVariables(List<Variable> variables, String absoluteFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(absoluteFile), variables);
    }

}
