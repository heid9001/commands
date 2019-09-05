import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        test();
    }

    public static void normalRun() throws Exception
    {
        Parser parser = new Parser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        parser.parse(reader);
    }

    public static void test() throws Exception
    {
        StringBuilder builder = new StringBuilder();
        builder.append("NEWPRODUCT foo\n");
        builder.append("PURCHASE foo 2 1000 2018-01-01\n");
        builder.append("DEMAND foo 2 2000 2018-01-02\n");
        builder.append("NEWPRODUCT bar\n");
        builder.append("NEWPRODUCT bar\n");
        builder.append("PURCHASE bar 1 1000 2018-01-01\n");
        builder.append("DEMAND bar 1 2000 2018-01-01\n");
        builder.append("SALESREPORT bar 2018-01-01\n");
        builder.append("SALESREPORT foo 2018-01-01\n");
        BufferedReader reader = new BufferedReader(new StringReader(builder.toString()));
        Parser parser = new Parser();
        parser.parse(reader);
    }
}
