import exception.NegativePriceException;
import execution.*;

import java.util.List;

public class CommandFactory {

    public static <T extends Command> T createCommand(Class<?> cls, Object... params) throws Exception
    {
        T command = (T) cls.newInstance();
        String prefix = cls.getSimpleName().toUpperCase();
        List<Class<?>> types = (List<Class<?>>) cls.getField(prefix+"_TYPES").get(null);
        List<String> names = (List<String>) cls.getField(prefix+"_NAMES").get(null);

        int len = names.size();

        for(int i = 0; i < len; i++) {
            String paramName = names.get(i);
            String firstUpcase = paramName.substring(0,1).toUpperCase() + paramName.substring(1);
            Object value = params[i];
            try {
                CommandFactory.class.getMethod("validate"+firstUpcase, types.get(i))
                                .invoke(null, value);
            } catch (NoSuchMethodException e) {
                // if exception called, no validation method available for this parameter
            }

            command.addParameter(paramName, value, types.get(i));
        }

        return command;
    }

    public static boolean validatePrice(Double price) throws NegativePriceException
    {
        if (price <= 0) {
            return false;
        }
        return true;
    }
}
