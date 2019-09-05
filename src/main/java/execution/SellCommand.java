package execution;

import java.math.BigDecimal;
import java.util.Date;

public class SellCommand extends BaseCommand implements AccessibleCommand
{

    String name;
    Double price;
    int num;
    Date date;

    public SellCommand(ExecutionState state)
    {
        super(state, SELLCOMMAND_TYPES);
    }

    public SellCommand()
    {
        super(SELLCOMMAND_TYPES);
    }

    public void fill(String name, Double price, int num, Date date)
    {
        addParameter("name", name, String.class);
        addParameter("price", price, Double.class);
        addParameter("num", num, Integer.class);
        addParameter("date", date, Date.class);
    }

    @Override
    public void process() throws Exception
    {
        init();
        if( state.bought - num < 0) throw new Exception("on sell, buy counter < sell counter");
        state.profit = state.profit
                .add(new BigDecimal(price, state.scale)
                        .multiply(new BigDecimal(num, state.scale))
                );
        state.sold += num;
        state.bought -= num;
    }

    public void init()
    {
        name = getParameter("name");
        price = getParameter("price");
        num = getParameter("num");
        date = getParameter("date");
    }


    @Override
    public Date getDate()
    {
        return date;
    }
}
