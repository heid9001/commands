package exception;

import entity.Product;

public class DuplicateProductException extends Exception{
    public DuplicateProductException(Product product) {
        super(String.format("Product %s already exists!", product.getName()));
    }
}
