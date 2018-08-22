package frameworkInfra.utils.parsers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HtmlParser {

    public static void copyLinesToNewFile(String src, String dest, int from, int to){
        BufferedReader br = null;
        BufferedWriter bw = null;
        FileWriter fw = null;

        try{
            br = new BufferedReader(new FileReader(src));
            fw = new FileWriter(dest, true);
            bw = new BufferedWriter(fw);
            String line = br.readLine();

            for( int i = 0; i < from && line != null; i++)
            {
                line = br.readLine();
            }

            for( int i = from; i <= to && line != null; i++)
            {
                bw.write(line);
                bw.write("\n");
                line = br.readLine();
            }

            br.close();
            bw.close();
        }
        catch(Exception e){

        }
    }

    public static int countLinesInFile(String file){
        BufferedReader br = null;
        int count = 0;
        try{
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                count++;
            }
            br.close();
        }
        catch(Exception e){

        }
        return count;
    }

    public static void replaceStringInFile(String file, String find, String replace){
        Path path = Paths.get(file);
        Charset charset = StandardCharsets.UTF_8;

        String content = null;
        try {
            content = new String(Files.readAllBytes(path), charset);
        content = content.replaceAll(find, replace);
        Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
