package algs;

public interface CardinalityEstimationAlgorithm {
    /* input methods */
    //Read a real element as a string
    void readElement(String element);

    // Read a synthetic random double between 0 and 1
    void readSyntheticElement(double element);

    /* output methods */
    int getSize(); // exact number of calls to readElement()

    double getEstimateOfCardinality(); // get estimate of n *right now*

    // Reset the algorithm such that this.m = m
    void resetAlgorithm(int newM);
}
