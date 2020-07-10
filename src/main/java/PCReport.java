package main.java;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PCReport {
  public static void main(String[] args) {
    // int size = Integer.parseInt(args[0]);
    //    StdOut.println(size);
    if (StdIn.isEmpty()) StdOut.println("Empty");
    else StdOut.println("Not Empty");
    //    int m = Integer.parseInt(args[1]);
    //    ProbabilisticCounting estimator = new ProbabilisticCounting(m, size);
    //
    //    double[] xValues = new double[size];
    //    double[] estimates = new double[size];
    //    double[] absoluteErrors = new double[size];
    //    double[] relativeErrors = new double[size];
    //
    //    int i = 0;
    //    String random = "";
    //
    //    while (!StdIn.isEmpty()) {
    //      random = StdIn.readString();
    //      estimator.readElement(random);
    //
    //      xValues[i] = i;
    //      estimates[i] = estimator.getEstimateOfCardinality();
    //      absoluteErrors[i] = estimator.getAbsoluteError();
    //      relativeErrors[i] = estimator.getRelativeError();
    //      i++;
    //      StdOut.println(i);
    //    }
    //
    //    BasicGrapher graph = new BasicGrapher();
    //    String title = "Cardinality Estimation using Probabilistic Counting";
    //    String xLabel = "Number of Elements processed";
    //    String yLabel = "Cardinality Estimation";
    //
    //    graph.show(title, graph.createTable(xLabel, xValues, yLabel, estimates));
    //
    //    title += ": Absolute Error";
    //    yLabel = "Absolute Error";
    //
    //    graph.show(title, graph.createTable(xLabel, xValues, yLabel, absoluteErrors));
    //
    //    title = "Cardinality Estimation using Probabilistic Counting: Relative Error";
    //    yLabel = "Cardinality Estimation";
    //
    //    graph.show(title, graph.createTable(xLabel, xValues, yLabel, relativeErrors));
  }
}
