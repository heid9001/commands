package execution;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * ExecutionState is a global state object, Commands and Queries are using this class to mutate it's data by reference.
 * BigDecimal is used because of IEEE754 standart.
 */
public class ExecutionState
{
    int sold = 0;
    int bought = 0;
    MathContext scale = new MathContext(2);
    BigDecimal profit = new BigDecimal(0.0, scale);
    List<AccessibleCommand> commands;
    List<Query> queries;
    Comparator<Query> queryComparator = new Comparator<Query>()
    {
        @Override
        public int compare(Query a, Query b) {
            long delta = a.getTimestamp() - b.getTimestamp();
            if(delta > 0) {
                return 1;
            } else if (delta < 0) {
                return -1;
            }
            return 0;
        }
    };

    public ExecutionState()
    {
        reset();
        commands = new ArrayList<>();
        queries = new ArrayList<>();
    }

    public void reset()
    {
        sold = 0;
        bought = 0;
        profit = new BigDecimal(0.0, scale);
    }

    public void addCommand(AccessibleCommand command)
    {
        command.setState(this);
        command.setTimestamp(System.nanoTime());
        this.commands.add(command);
    }

    public void addQuery(Query query)
    {
        query.setState(this);
        query.setTimestamp(System.nanoTime());
        this.queries.add(query);
        this.queries.sort(queryComparator);
    }

    public BigDecimal getProfit()
    {
        return profit;
    }

    public List<AccessibleCommand> getCommands()
    {
        return commands;
    }
}
