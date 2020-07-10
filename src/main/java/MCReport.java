package main.java;

import edu.princeton.cs.algs4.StdIn;

public class MCReport {
  public static void main(String[] args) {
    int size = Integer.parseInt(args[0]);
    int m = Integer.parseInt(args[1]);
    MinCount estimator = new MinCount(m, size);

    double[] xValues = new double[size];
    double[] estimates = new double[size];
    double[] absoluteErrors = new double[size];
    double[] relativeErrors = new double[size];

    int i = 0;
    String random;
    while (!StdIn.isEmpty()) {
      random = StdIn.readString();
      estimator.readElement(random);

      xValues[i] = i;
      estimates[i] = estimator.getEstimateOfCardinality();
      absoluteErrors[i] = estimator.getAbsoluteError();
      relativeErrors[i] = estimator.getRelativeError();
      i++;
    }

    BasicGrapher graph = new BasicGrapher();
    String title = "Cardinality Estimation using Probabilistic Counting";
    String xLabel = "Number of Elements processed";
    String yLabel = "Cardinality Estimation";

    graph.showLinePlot(title, graph.createTable(xLabel, xValues, yLabel, estimates));

    title += ": Absolute Error";
    yLabel = "Absolute Error";

    graph.showLinePlot(title, graph.createTable(xLabel, xValues, yLabel, absoluteErrors));

    title = "Cardinality Estimation using Probabilistic Counting: Relative Error";
    yLabel = "Cardinality Estimation";

    graph.showLinePlot(title, graph.createTable(xLabel, xValues, yLabel, relativeErrors));
  }
}
