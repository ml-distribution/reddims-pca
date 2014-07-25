# hadoop-pca

A hadoop implementation of **Principal Component Analysis** and its subroutines(i.e, vector mean & covariance).

![hadoop logo](http://hadoop.apache.org/images/hadoop-logo.jpg)

![mahout logo](http://mahout.apache.org/images/mahout-logo.png)

## Prerequisitives

* Java Runtime Environment 7
* Maven 3.1.0
* Hadoop 1.1.2
* Mahout 0.8

## usage how-to

Each of commands listed below receive IntWritable-VectorWritable [SequenceFile](http://blog.cloudera.com/blog/2011/01/hadoop-io-sequence-map-set-array-bloommap-files/) as their input and produce corresponding result as output.

* vector-mean: calculates mean vector from a set of vectors.
* covariacne: calculates covariance matrix.
* pca: calculates basis axis of covariance matrix.

All results are formatted to IntWritable-VectorWritable SequenceFile. For explanation use, I attached additional IntWritable-VectorWritable SequenceFile named *example.mat*. This file includes 10 vectors listed below, whose key ranges [0, 10). All usage examples included in this document will employ this file.

* Key: 0, Value: (0.69, 0.49)
* Key: 1, Value: (-1.31, -1.21)
* Key: 2, Value: (0.39, 0.99)
* Key: 3, Value: (0.09, 0.29)
* Key: 4, Value: (1.29, 1.09)
* Key: 5, Value: (0.49, 0.79)
* Key: 6, Value: (0.19, -0.31)
* Key: 7, Value: (-0.81, -0.81)
* Key: 8, Value: (-0.31, -0.31)
* Key: 9, Value: (-0.71, -1.01)

You can also check the contents of these SequenceFiles using mahout's *seqdumper* command:

> mahout seqdumper -i /examples.mat

### vector-mean

calculates vector mean.

> ./vector-mean /examples.mat /vector-mean.out 10 2 # calculates mean of 10 2 dimensional vectors and store the output in /vector-mean.out

### covariance

calculates covariance matrix from given matrix & its mean vector.

> ./covariance /examples.mat /covariance.out /vector-mean.out/part-r-00000 10 2 # calculates covariance matrix from examples.mat, whose mean vector is stored in /vector-mean.out/part-r-00000.

### pca

calculates given set of vectors, represented as a matrix.

> ./pca /examples.mat /pca.out 10 2 # calculates basis axis from examples.mat, which includes 10 2-dimensional vectors.
mahout seqdumper -i /pca.out/rawEigenvectors # check output
