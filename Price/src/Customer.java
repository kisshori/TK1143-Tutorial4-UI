public class Customer {
    private String nameCust;
    private double salary;

    public Customer(String nameCust, double salary) {
        this.nameCust = nameCust;
        this.salary = salary;
    }

    public String getName() {
        return nameCust;
    }

    public double getSalary() {
        return salary;
    }
}