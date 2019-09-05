package entity;

public final class Product
{
    final String name;

    public Product(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        return hashCode() == o.hashCode();
    }
}
