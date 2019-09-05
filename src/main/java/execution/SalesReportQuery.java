package execution;

import java.math.BigDecimal;
import java.util.Date;

public class SalesReportQuery  extends BaseCommand implements Query
{

    String name;
    Date date;
    BigDecimal result;

    public SalesReportQuery(ExecutionState state)
    {
        super(state, SALESREPORTQUERY_TYPES);
    }

    public SalesReportQuery()
    {
        super(SALESREPORTQUERY_TYPES);
    }

    @Override
    public void process() throws Exception
    {
        init();
        for (AccessibleCommand command : getState().getCommands()){
            command.init();
            if(! command.getDate().equals(date) ) continue;
            if( command.getTimestamp() > timestamp ) continue;
            command.run();
        }
        result = state.getProfit();
    }

    public void init()
    {
        name = getParameter("name");
        date = getParameter("date");
    }

    @Override
    public BigDecimal getResult()
    {
        return result;
    }

}
