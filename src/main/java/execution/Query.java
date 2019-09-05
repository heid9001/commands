package execution;

import java.math.BigDecimal;

/**
 * Query could return values
 */
public interface Query extends Command
{
    BigDecimal getResult();
}
