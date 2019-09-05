package execution;

import java.util.List;

/**
 * Command could be dinamicly structured with fields
 */
public interface Command
{
    List<Class<?>> paramTypes();
    boolean validateParams();
    void process() throws Exception;
    void run() throws Exception;
    void addParameter(String name, Object value, Class<?> valCls);
    <P> P getParameter(String name);
    ExecutionState getState();
    void setState(ExecutionState state);
    Long getTimestamp();
    void setTimestamp(Long timestamp);
    void init();
}
