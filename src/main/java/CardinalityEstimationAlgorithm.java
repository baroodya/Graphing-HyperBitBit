package main.java;

public interface CardinalityEstimationAlgorithm {
  /* input methods */
  public void readElement(String element);

  public void readSyntheticElement(double element);

  /* output methods */
  public int getSize(); // exact number of calls to readElement()

  public double getEstimateOfCardinality(); // get estimate of n *right now*

  /* Helper Methods to carry out needed tasks */
  public void resetAlgorithm();

  public void resetAlgorithm(int m);
}
