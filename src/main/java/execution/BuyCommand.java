package execution;

import java.math.BigDecimal;
import java.util.Date;

public class BuyCommand extends BaseCommand implements AccessibleCommand
{
    String name;
    Double price;
    int num;
    Date date;

    public BuyCommand(ExecutionState state)
    {
        super(state, BUYCOMMAND_TYPES);
    }

    public BuyCommand()
    {
        super(BUYCOMMAND_TYPES);
    }

    @Override
    public void process() throws Exception
    {
        init();
        state.profit = state.profit
                .subtract(new BigDecimal(price, state.scale)
                        .multiply(new BigDecimal(num, state.scale))
                );
        state.bought += num;
    }

    public void init()
    {
        name = super.<String>getParameter("name");
        price = super.<Double>getParameter("price");
        num = super.<Integer>getParameter("num");
        date = super.<Date>getParameter("date");
    }

    @Override
    public Date getDate()
    {
        return date;
    }

}
