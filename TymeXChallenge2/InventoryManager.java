import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryManager {
    public static void main(String[] args) {
        // Create a list of products
        List<Product> inventory = new ArrayList<>();
        inventory.add(new Product("Laptop", 999.99, 5));
        inventory.add(new Product("Smartphone", 499.99, 10));
        inventory.add(new Product("Tablet", 299.99, 0));
        inventory.add(new Product("Smartwatch", 199.99, 3));

        // Task 1: Calculate total inventory value
        double totalValue = calculateTotalValue(inventory);
        System.out.println("Task 1: Total Inventory Value:");
        System.out.printf("%.2f%n", totalValue); // Print with two decimal places
        System.out.println(); // Add space

        // Task 2: Find the most expensive product
        String expensiveProduct = getMostExpensiveProduct(inventory);
        System.out.println("Task 2: Most Expensive Product:");
        System.out.println(expensiveProduct);
        System.out.println(); // Add space

        // Task 3: Check if "Headphones" is in stock
        boolean isHeadphonesAvailable = isProductInStock(inventory, "Headphones");
        System.out.println("Task 3: Is 'Headphones' in stock?");
        System.out.println(isHeadphonesAvailable);
        System.out.println(); // Add space

        // Task 4: Sort by price ascending
        System.out.println("Task 4 (Ascending): Products sorted by price:");
        List<Product> sortedByPriceAscending = sortProductsByPrice(inventory, true);
        for (Product product : sortedByPriceAscending) {
            System.out.printf("%s - $%.2f%n", product.name, product.price);
        }
        System.out.println(); // Add space

        // Task 5: Sort by price descending
        System.out.println("Task 5 (Descending): Products sorted by price:");
        List<Product> sortedByPriceDescending = sortProductsByPrice(inventory, false);
        for (Product product : sortedByPriceDescending) {
            System.out.printf("%s - $%.2f%n", product.name, product.price);
        }
        System.out.println(); // Add space

        // Task 6: Sort by quantity ascending
        System.out.println("Task 6 (Ascending): Products sorted by quantity:");
        List<Product> sortedByQuantityAscending = sortProductsByQuantity(inventory, true);
        for (Product product : sortedByQuantityAscending) {
            System.out.printf("%s - Quantity: %d%n", product.name, product.quantity);
        }
        System.out.println(); // Add space

        // Task 7: Sort by quantity descending
        System.out.println("Task 7 (Descending): Products sorted by quantity:");
        List<Product> sortedByQuantityDescending = sortProductsByQuantity(inventory, false);
        for (Product product : sortedByQuantityDescending) {
            System.out.printf("%s - Quantity: %d%n", product.name, product.quantity);
        }
    }

    // Calculate the total value of inventory
    public static double calculateTotalValue(List<Product> products) {
        double total = 0;
        for (Product product : products) {
            total += product.price * product.quantity;
        }
        return total;
    }

    // Find the most expensive product
    public static String getMostExpensiveProduct(List<Product> products) {
        if (products.isEmpty()) {
            return "No products available";
        }
        Product expensive = products.get(0);
        for (Product product : products) {
            if (product.price > expensive.price) {
                expensive = product;
            }
        }
        return expensive.name;
    }

    // Check if a product is in stock
    public static boolean isProductInStock(List<Product> products, String productName) {
        for (Product product : products) {
            if (product.name.equalsIgnoreCase(productName) && product.quantity > 0) {
                return true;
            }
        }
        return false;
    }

    // Sort products by price
    public static List<Product> sortProductsByPrice(List<Product> products, boolean ascending) {
        List<Product> sortedList = new ArrayList<>(products);
        sortedList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return ascending ? Double.compare(p1.price, p2.price) : Double.compare(p2.price, p1.price);
            }
        });
        return sortedList;
    }

    // Sort products by quantity
    public static List<Product> sortProductsByQuantity(List<Product> products, boolean ascending) {
        List<Product> sortedList = new ArrayList<>(products);
        sortedList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return ascending ? Integer.compare(p1.quantity, p2.quantity) : Integer.compare(p2.quantity, p1.quantity);
            }
        });
        return sortedList;
    }
}
