public class DataSet {
    private double sum;
    private Measurable max;
    private int count;

    public DataSet() { sum = 0; count = 0; max = null; }

    public void add(Measurable x) {
        sum += x.getMeasure();
        if (count == 0 || x.getMeasure() > max.getMeasure())
            max = x;
        count++;
    }
    public Measurable getMax() { return max; }
    public double getAverage() { return count == 0 ? 0 : sum / count; }
}