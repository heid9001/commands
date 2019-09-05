package execution;

import java.util.*;

public abstract class BaseCommand implements Command
{

    public static final List<Class<?>> SELLCOMMAND_TYPES;
    public static final List<Class<?>> BUYCOMMAND_TYPES;
    public static final List<Class<?>> SALESREPORTQUERY_TYPES;

    public static final List<String> SELLCOMMAND_NAMES;
    public static final List<String> BUYCOMMAND_NAMES;
    public static final List<String> SALESREPORTQUERY_NAMES;

    static {

        SELLCOMMAND_TYPES = Collections.<Class<?>>unmodifiableList(Arrays.asList(String.class,
                Double.class,
                Integer.class,
                Date.class));

        BUYCOMMAND_TYPES = Collections.<Class<?>>unmodifiableList(Arrays.asList(String.class,
                Double.class,
                Integer.class,
                Date.class));

        SALESREPORTQUERY_TYPES = Collections.<Class<?>>unmodifiableList(Arrays.asList(
                String.class,
                Date.class));

        SELLCOMMAND_NAMES = Collections.unmodifiableList(Arrays.asList("name", "price", "num", "date"));
        BUYCOMMAND_NAMES = Collections.unmodifiableList(Arrays.asList("name", "price", "num", "date"));
        SALESREPORTQUERY_NAMES = Collections.unmodifiableList(Arrays.asList("name", "date"));
    }

    Long timestamp;
    LinkedHashMap<String, Object> values;
    List<Class<?>> types;
    List<Class<?>> paramsTypes;
    ExecutionState state;

    public BaseCommand(ExecutionState state, List<Class<?>> paramsTypes)
    {
        this.paramsTypes = paramsTypes;
        this.values = new LinkedHashMap<>();
        this.types = new ArrayList<>();
        this.state = state;
    }

    public BaseCommand(List<Class<?>> paramsTypes)
    {
        this.paramsTypes = paramsTypes;
        this.values = new LinkedHashMap<>();
        this.types = new ArrayList<>();
    }

    @Override
    public List<Class<?>> paramTypes()
    {
        return paramsTypes;
    }

    @Override
    public boolean validateParams()
    {
        if (types.size() != paramTypes().size()) return false;
        return Arrays.equals(types.toArray(), paramTypes().toArray());
    }

    @Override
    public abstract void process() throws Exception;

    public void run() throws Exception
    {
        if(! validateParams()) throw new Exception();
        process();
    }

    @Override
    public void addParameter(String name, Object value, Class<?> valCls)
    {
        values.put(name, value);
        types.add(valCls);
    }

    @Override
    public <P> P getParameter(String name)
    {
        return (P) values.get(name);
    }


    @Override
    public ExecutionState getState()
    {
        return state;
    }

    @Override
    public void setState(ExecutionState state)
    {
        this.state = state;
    }

    public Long getTimestamp()
    {
        return timestamp;
    }

    @Override
    public void setTimestamp(Long timestamp)
    {
        this.timestamp = timestamp;
    }
}
