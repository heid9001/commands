import entity.Product;
import exception.DuplicateProductException;
import execution.*;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser is used for creating commands from STDIN.
 */
public class Parser {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Map<Product, ExecutionState> execution;

    Pattern[] patterns = new Pattern[]{
            Pattern.compile("(?<cmd>NEWPRODUCT)" +
                    " (?<name>[a-zA-Z0-9]+)"),

            Pattern.compile("(?<cmd>PURCHASE)" +
                    " (?<name>[a-zA-Z]+)" +
                    " (?<num>\\d+)" +
                    " (?<price>(\\d+(\\.\\d+)?))" +
                    " (?<date>.+)"),

            Pattern.compile("(?<cmd>DEMAND)" +
                    " (?<name>[a-zA-Z]+)" +
                    " (?<num>\\d+)" +
                    " (?<price>(\\d+(\\.\\d+)?))" +
                    " (?<date>.+)"),

            Pattern.compile("(?<cmd>SALESREPORT)" +
                    " (?<name>[a-zA-Z]+)" +
                    " (?<date>.+)")
    };

    public Parser()
    {
        execution = new HashMap<>();
    }

    /**
     * map ExecutionState to Product
     * @param matcher
     * @throws Exception
     */
    void handleNewProduct(Matcher matcher) throws Exception
    {
        String name = matcher.group("name");
        Product product = new Product(name);
        if ( execution.containsKey(product)) throw new DuplicateProductException(product);
        execution.put(product, new ExecutionState());
    }

    /**
     * append BuyCommand for late execution to state
     * @param matcher
     * @throws Exception
     */
    void handleBuyCommand(Matcher matcher) throws Exception
    {
        String name = matcher.group("name");
        Product product = new Product(name);
        if(! execution.containsKey(product)) throw new Exception();
        ExecutionState state = execution.get(product);
        BuyCommand command = CommandFactory.<BuyCommand>createCommand(
                BuyCommand.class,
                name,
                Double.parseDouble(matcher.group("price")),
                Integer.parseInt(matcher.group("num")),
                dateFormat.parse(matcher.group("date"))
        );
        state.addCommand(command);
    }

    /**
     * append SellCommand for late execution to state
     * @param matcher
     * @throws Exception
     */
    void handleSellCommand(Matcher matcher) throws Exception
    {
        String name = matcher.group("name");
        Product product = new Product(name);
        if(! execution.containsKey(product)) throw new Exception();
        ExecutionState state = execution.get(product);
        SellCommand command = CommandFactory.<SellCommand>createCommand(
                SellCommand.class,
                name,
                Double.parseDouble(matcher.group("price")),
                Integer.parseInt(matcher.group("num")),
                dateFormat.parse(matcher.group("date"))
        );
        state.addCommand(command);
    }

    /**
     * Executes all previously added Commands
     * @param matcher
     * @return
     * @throws Exception
     */
    String execSalesReportQuery(Matcher matcher) throws Exception
    {
        String name = matcher.group("name");
        Product product = new Product(name);
        if(! execution.containsKey(product)) throw new Exception();
        ExecutionState state = execution.get(product);
        SalesReportQuery query = CommandFactory.<SalesReportQuery>createCommand(
                SalesReportQuery.class,
                name,
                dateFormat.parse(matcher.group("date"))
        );
        state.addQuery(query);
        query.run();
        return query.getResult().toString();
    }

    /**
     * Simple command router
     * @param line
     * @return
     * @throws Exception
     */
    String parseLine(String line) throws Exception
    {
        Matcher matcher = null;
        boolean matched = false;
        for (Pattern pattern : patterns) {
            matcher = pattern.matcher(line);
            if(matcher.matches()) {
                matched = true;
                break;
            }
        }

        if(! matched) {
            System.out.println("Invalid command signature");
            System.exit(-1);
        }

        StringBuilder sb = new StringBuilder();
        String commandName = matcher.group("cmd");
        System.out.println(commandName);

        switch (commandName) {

            case "NEWPRODUCT":{
                handleNewProduct(matcher);
                break;
            }

            case "PURCHASE":
                handleBuyCommand(matcher);
                break;


            case "DEMAND":
                handleSellCommand(matcher);
                break;

            case "SALESREPORT":
                sb.append(execSalesReportQuery(matcher));
                break;
        }

        return sb.toString();
    }

    public void parse(BufferedReader reader) throws Exception
    {
        String line;
        while ( (line = reader.readLine()) != null ) {
            try{
                String result = parseLine(line);
                if (result.length() > 0) {
                    System.out.println(out + "\n");
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
            
        }
    }

}
